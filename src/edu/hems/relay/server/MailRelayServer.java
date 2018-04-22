/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hems.relay.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import edu.hems.smtp.client.CommandExecuter;

/**
 *
 * @author hsaxena
 */
public class MailRelayServer  implements Runnable {

    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    
    protected Integer          serverPort   = null;
    protected String relayToHost = null;
    protected Integer relayToPort = null; 
    
    protected AtomicInteger counter = new AtomicInteger(0);
    		

    public MailRelayServer(Integer port, String relayToHost, Integer relayToPort){
        this.serverPort = port;
        
        this.relayToHost = relayToHost;
        this.relayToPort = relayToPort;
    }

    public void run(){
        openServerSocket();
        while(! this.isStopped){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            CommandExecuter relayServerCmdExecutor = null;
            if(relayToHost != null && relayToPort != null) {
                try {
                	relayServerCmdExecutor = new CommandExecuter();
                	relayServerCmdExecutor.init(relayToHost, relayToPort);
    			} catch (Exception e) {
    				throw new RuntimeException("Error communicating to RelayToHost Server", e);
    			}            	
            }
            new Thread(
                new RelaySession(
                    clientSocket,relayServerCmdExecutor, "smtp-relay-session" + counter.incrementAndGet())
            ).start();
        }
        System.out.println("Server Stopped.") ;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port at " + this.serverPort, e);
        }
    }
    
}
