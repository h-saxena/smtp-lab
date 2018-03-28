package edu.hems.relay.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

		    if (servSocket != null && os != null && is != null) {
		      os.writeBytes("HELO\r\n");
		      os.writeBytes("MAIL From:" + from + " \r\n");
		      os.writeBytes("RCPT To:" + toAddr + "\r\n");
		      os.writeBytes("DATA\r\n");
		      os.writeBytes("X-Mailer: Java\r\n");
		      os.writeBytes("DATE: " + 
		        DateFormat.getDateInstance(
		          DateFormat.FULL, Locale.US).format(new Date()) + "\r\n");
		      os.writeBytes("From:" + from + "\r\n");
		      os.writeBytes("To:" + toAddr + "\r\n");
		    }

		    os.writeBytes("Subject:\r\n");
		    os.writeBytes("body\r\n");
		    os.writeBytes("\r\n.\r\n");
		    os.writeBytes("QUIT\r\n");
		    String responseline = is.readLine();
		    while ((responseline ) != null) { 
		      if (responseline.indexOf("Ok") != -1)
		        break;
		    }
		    servSocket.close();
		  }

}
