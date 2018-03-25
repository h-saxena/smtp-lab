package edu.hems.smtp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommandExecuter {
	
	Socket emailSocket;
	BufferedReader br;
	OutputStream os;
	
	public String init(String hostName, int port) throws UnknownHostException, IOException {
		emailSocket = new Socket(hostName, port);
		
		InputStream is = emailSocket.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));

		// Get a reference to the socket's output stream.
		os = emailSocket.getOutputStream();
		return readResponse();
	}
	
	public String executeCommand(Command cmd) throws UnsupportedEncodingException, IOException {
		String response = null;
		
		String cmdStr = cmd.getCommand();
		cmdStr+="\r\n";
		
		System.out.println("Command: " + cmdStr);		
		writeRequest(cmdStr);
		
		response = readResponse();
		System.out.println("Cmd Response: " + response);
		
		return response;
	}
	
	public String executeMessage(String message) throws IOException {
		String response;
		
		String smtpMessage = "";
		
		smtpMessage += "from: hsaxena@radar.gsw.edu \r\n";
		smtpMessage += "to: hemantsaxena@yahoo.com \r\n";
		smtpMessage += "subject: smtp lab message \r\n";
		smtpMessage += message;
		
		smtpMessage += "\r\n.\r\n";
		writeRequest(smtpMessage);
		
		response = readResponse();
		System.out.println("Message Response: " + response);
		
		return response;
	}
	
	private void writeRequest(String req) throws UnsupportedEncodingException, IOException {
		os.write(req.getBytes("US-ASCII"));
	}
	
	private String readResponse() throws IOException {
		return br.readLine();
	}
	
	public void close() throws IOException {
		os.close();
		br.close();
		emailSocket.close();
	}

}
