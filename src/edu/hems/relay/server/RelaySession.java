/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hems.relay.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import edu.hems.relay.server.cmd.BaseSmtpCmd;
import edu.hems.relay.server.cmd.QUIT;
import edu.hems.relay.server.cmd.RelayServerSmtpCmdHandler;
import edu.hems.smtp.client.CommandExecuter;

/**
 *
 * @author saxenah
 */
public class RelaySession implements Runnable {

    final static String CRLF = "\r\n";

    protected Socket clientSocket = null;
    protected String sessionName = null;
    CommandExecuter relayServerCmdExecutor = null;
    
    BaseSmtpCmd lastPermittedCmd = RelayServerSmtpCmdHandler.getSmtpCmdInstance("BEGIN");

    public RelaySession(Socket clientSocket, CommandExecuter relayServerCmdExecutor, String sessionName) {
        this.clientSocket = clientSocket;
        this.sessionName = sessionName;
        this.relayServerCmdExecutor = relayServerCmdExecutor;
    }

    public void run() {
    	Thread.currentThread().setName(this.sessionName);
    	
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(input));
            DataOutputStream os = new DataOutputStream(output);
                        
            
            String line = null; //inFromServer.readLine();            
            
            boolean keepItAlive = true;
            while(keepItAlive) {
                Thread.sleep(800);

            	line = inFromServer.readLine();

                if(line == null)
                	continue;
                
                System.out.println(this.sessionName + " - " + "Command Recieved: " + line);

            	try {
					lastPermittedCmd =  RelayServerSmtpCmdHandler.handle(line, lastPermittedCmd, this.relayServerCmdExecutor);
					os.writeBytes(lastPermittedCmd.getResponseMsg()+ CRLF);
					System.out.println(this.sessionName + " - Last executed Cmd: " + lastPermittedCmd.getClass().getName());
					if(lastPermittedCmd.getClass().equals(QUIT.class)) {
						keepItAlive = false;
					}
				} catch (Exception e) {
					os.writeBytes("Error executing command: " + e.getMessage()+ CRLF);
				}
                
            }

            output.close();
            input.close();
            
        } catch (IOException | InterruptedException e) {
        	System.out.println(this.sessionName + " - Error1: " + e.getMessage());
        }
        if(relayServerCmdExecutor != null)
			try {
				relayServerCmdExecutor.close();
			} catch (IOException e) {
	        	System.out.println(this.sessionName + " - Error2: " + e.getMessage());
			}
        
        System.out.println(this.sessionName + " : sesson is done " );
    }

}
