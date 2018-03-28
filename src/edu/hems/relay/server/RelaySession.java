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
                        
            
            String line = inFromServer.readLine();

            StringTokenizer tokens = new StringTokenizer(line);
            tokens.nextToken(); 
            
            System.out.println("----- Reading the incoming socket data ----");
            while (!line.isEmpty()) {
                System.out.println(line);
                line = inFromServer.readLine();
            }
            System.out.println("----- Reading socket Done ----");

            DataOutputStream os = new DataOutputStream(output);
            // Send the status line.
            os.writeBytes("ok");

            // Send a blank line
            //os.writeBytes(CRLF);

            output.close();
            input.close();
            System.out.println("Request processed: " );
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }

}
