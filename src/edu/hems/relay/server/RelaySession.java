/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hems.relay.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author saxenah
 */
public class RelaySession implements Runnable {

    final static String CRLF = "\r\n";

    protected Socket clientSocket = null;
    protected String serverText = null;

    public RelaySession(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(input));
            DataOutputStream os = new DataOutputStream(output);
                        
            
            String line = null; //inFromServer.readLine();            
            
            boolean keepItAlive = true;
            while(keepItAlive) {
                line = inFromServer.readLine();
                //StringTokenizer tokens = new StringTokenizer(line);
                //String command = tokens.nextToken(); 
                System.out.println(line);
                //System.out.println(command);
                if(line.startsWith("QUIT")) {
                    os.writeBytes("Thanks for using relay server\r\n");
                    os.writeBytes("BYE\r\n");
                    keepItAlive = false;
                }
                else {
                	os.writeBytes("200 : " + line +  " : OK\r\n"  );
                }
                Thread.sleep(500);
            }

            output.close();
            input.close();
            System.out.println("Request processed: " );
        } catch (IOException | InterruptedException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }

}
