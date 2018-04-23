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
							//throw new RuntimeException("Invalid command: " + smtpCommand);
						
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

	
	
	public static void main1(String[] args) throws Exception {
		String host = "localhost";
		int port = 4040;
		String from = "from@from.net";
		String toAddr = "to@to.net";

		Socket servSocket = new Socket(host, port);
		DataOutputStream os = new DataOutputStream(servSocket.getOutputStream());
		DataInputStream is = new DataInputStream(servSocket.getInputStream());

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(servSocket.getInputStream()));

		if (servSocket != null && os != null && is != null) {
			os.writeBytes("HELO\r\n");
			System.out.println(inFromServer.readLine());
			os.writeBytes("MAIL From:" + from + " \r\n");
			System.out.println(inFromServer.readLine());
			os.writeBytes("RCPT To:" + toAddr + "\r\n");
			System.out.println(inFromServer.readLine());
			os.writeBytes("DATA\r\n");
			System.out.println(inFromServer.readLine());
			os.writeBytes("X-Mailer: Java\r\n");
			System.out.println(inFromServer.readLine());
			os.writeBytes(
					"DATE: " + DateFormat.getDateInstance(DateFormat.FULL, Locale.US).format(new Date()) + "\r\n");
			System.out.println(inFromServer.readLine());
			os.writeBytes("From:" + from + "\r\n");
			System.out.println(inFromServer.readLine());
			os.writeBytes("To:" + toAddr + "\r\n");
			System.out.println(inFromServer.readLine());

			os.writeBytes("Subject:\r\n");
			os.writeBytes("body\r\n");
			os.writeBytes("\r\n.\r\n");
			os.writeBytes("QUIT\r\n");
			while (true) {
				String responseline = inFromServer.readLine();
				System.out.println("response from server: " + responseline);
				if (responseline.indexOf("BYE") != -1)
					break;
			}

		}

		servSocket.close();
	}

}
