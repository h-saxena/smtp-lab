package edu.hems.relay.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class UserClient {

	public static void main(String[] args) throws Exception {
		String pcHost = System.getProperty("relayHost");
		int pcPort = Integer.parseInt(System.getProperty("relayPort"));

		Socket servSocket = null;
		
		try {
			servSocket = new Socket(pcHost, pcPort);
			DataOutputStream os = new DataOutputStream(servSocket.getOutputStream());
			DataInputStream is = new DataInputStream(servSocket.getInputStream());

			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(servSocket.getInputStream()));

			if (servSocket != null && os != null && is != null) {
				System.out.println("Connection Initiation: " + inFromServer.readLine());
				
				Scanner s = new Scanner(System.in);
				os.writeBytes("USER_CLIENT\r\n");
				while (true) {
				    System.out.print("Enter command: ");
				    try {
					    String smtpCommand = s.nextLine();
					    
						if(smtpCommand == null || smtpCommand.isEmpty() )
							continue;
						
						System.out.println("request: " + smtpCommand);
						os.writeBytes(smtpCommand + " \r\n");
						String responseline = inFromServer.readLine();
						System.out.println("response: " + responseline);
						
						if (responseline.toUpperCase().indexOf("BYE") != -1)
							break;
						
						
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
						System.out.println("Please enter a valid smtp command");
						continue;
					}
				}
				
			}
		}
		catch(Exception ex) {
			
		}
		finally {
			if(servSocket != null) {
				servSocket.close();
			}
		}
		
		
	}


}
