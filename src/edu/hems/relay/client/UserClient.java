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

public class UserClient {

	public static void main(String[] args) throws Exception {
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
