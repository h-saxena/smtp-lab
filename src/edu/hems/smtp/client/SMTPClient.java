package edu.hems.smtp.client;

import java.io.*;
import java.net.*;
import java.util.*;

public class SMTPClient {

	public static void main(String[] args) throws Exception {
		// TODO code application logic here
		// Establish a TCP connection with the mail server.
		//System.out.println("Enter the mail server you wish to connect to (example:  edge.nunet.nova.edu):\n");
		String hostName = new String();
		//Scanner emailScanner = new Scanner(System.in);smtp.gswcm.net
		hostName = "smtp.gswcm.net"; //emailScanner.next();
		Socket emailSocket = new Socket(hostName, 25);
		// Create a BufferedReader to read a line at a time.
		InputStream is = emailSocket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		// Read greeting from the server.
		String response = br.readLine();
		System.out.println("Response 1: " + response);
		if (!response.startsWith("220")) {
			throw new Exception("220 reply not received from server.\n");
		}
		// Get a reference to the socket's output stream.
		OutputStream os = emailSocket.getOutputStream();
		// Send HELO command and get server response.
		//System.out.println("Enter the name of your mail domain (example:  hotmail.com):");
		//String heloDomain = emailScanner.next();
		String fullHeloCommand = "HELO " + "pg-01" + "\r\n";
		System.out.print("Command: " + fullHeloCommand);
		os.write(fullHeloCommand.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println("Response 2: " + response);
		if (!response.startsWith("250")) {
			throw new Exception("250 reply not received from server.\n");
		}
		// Send MAIL FROM command.
		//System.out.println("Please enter your (source) e-mail address (example: me@myexample.com:\n");
		//String sourceAddress = emailScanner.next();
		String mailFromCommand = "MAIL From: " + "hsaxena@radar.gsw.edu" + "\r\n";
		System.out.println("Command: " + mailFromCommand);
		os.write(mailFromCommand.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println("Response 3: " + response);
		if (!response.startsWith("250"))
			throw new Exception("250 reply not received from server.\n");

		String mailFromCommand2 = "SUBJECT: " + "This is for smtp assignment" + "\r\n";
		System.out.println("Command: " + mailFromCommand2);
		os.write(mailFromCommand2.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println("Response 3.1: " + response);
		if (!response.startsWith("250"))
			throw new Exception("250 reply not received from server.\n");
		
		// Send RCPT TO command.
		//System.out.println("Please type the destination e-mail address (example:  example@nova.edu):\n");
		String destEmailAddress = new String();
		//destEmailAddress = emailScanner.next();
		String fullAddress = new String();
		fullAddress = "RCPT TO: " + "java.hemant@gmail.com" + "\r\n";
		System.out.println("Command: "+ fullAddress);
		os.write(fullAddress.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println("Response 4: " + response);
		if (!response.startsWith("250")) {
			throw new Exception("250 reply not received from server.\n");
		}
		// Send DATA command.
		String dataString = new String();
		dataString = "DATA\r\n";
		System.out.println("Command: " + dataString);
		os.write(dataString.getBytes("US-ASCII"));
		response = br.readLine();
		if (!response.startsWith("354"))
			throw new Exception("354 reply not received from server.\n");
		System.out.println("Response 5: " + response);
		// Send message data.
		//System.out.println("Enter your message, enter '.' on a separate line to end message data entry:\n");
		String input = "\r\n from: hsaxena@radar.gsw.edu\r\n" + 
				"to: java.hemant@gmail.com\r\n" + 
				"subject: This is the President\r\n" + 
				"\r\n" + 
				"Hello, Student\r\n" + 
				"\r\n" + 
				"--\r\n" + 
				"President\r\n" + 
				"\r\n.\r\n";
//		while (input.charAt(0) != '.') {
//			input = emailScanner.next();
//			os.write(input.getBytes("US-ASCII"));
//		}
		// End with line with a single period.
		os.write(input.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println("Response 6: " + response);
		if (!response.startsWith("250"))
			throw new Exception("250 reply not received from server\n");

		// Send QUIT command.
		String quitCommand = new String();
		quitCommand = "QUIT\r\n";
		System.out.println("Command: " + quitCommand);
		os.write(quitCommand.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println("Response 7: " + response);
		emailSocket.close();
	}
}
