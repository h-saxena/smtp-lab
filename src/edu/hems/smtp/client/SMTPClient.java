package edu.hems.smtp.client;

import java.io.*;
import java.net.*;
import java.util.*;

public class SMTPClient {

	public static void main(String[] args) throws Exception {
		String pcHost = System.getProperty("relayHost");
		int pcPort = Integer.parseInt(System.getProperty("relayPort"));
		
		String fromEmail = System.getProperty("mailFrom");
		String toEmail = System.getProperty("mailTo");
		String subject = "This is from Hemant SMTP Lab";
		
		CommandExecuter cmdExecutor = new CommandExecuter();
		
		String response1 = cmdExecutor.initCall(pcHost, pcPort);
		if (!response1.startsWith("220")) {
			throw new Exception("220 reply not received from server.\n");
		}
		
		String response2 = cmdExecutor.executeCommand(new Command("HELO pg-01"));
		if (!response2.startsWith("250")) {
			throw new Exception("250 reply not received from server.\n");
		}
		
		String response3 = cmdExecutor.executeCommand(new Command("MAIL From: " + fromEmail));
		if (!response3.startsWith("250")) {
			throw new Exception("250 reply not received from server.\n");
		}
		
		String response4 = cmdExecutor.executeCommand(new Command("RCPT TO: " + toEmail));
		if (!response4.startsWith("250")) {
			throw new Exception("250 reply not received from server.\n");
		}
		
		String response5= cmdExecutor.executeCommand(new Command("DATA"));
		if (!response5.startsWith("354")) {
			throw new Exception("354 reply not received from server.\n");
		}
		

		String message = "Hello, Student\r\n" + 
				"\r\n" + 
				"This is test message from lab. Please ignore.\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"--\r\n" + 
				"Tester\r\n";
		String response6= cmdExecutor.executeMessage(message, subject, fromEmail, toEmail);
		if (!response6.startsWith("250")) {
			throw new Exception("250 reply not received from server.\n");
		}
		
		
		String response7 = cmdExecutor.executeCommand(new Command("QUIT"));
		if (!response7.startsWith("221")) {
			throw new Exception("2221 reply not received from server.\n");
		}

		cmdExecutor.close();
	}
	
}
