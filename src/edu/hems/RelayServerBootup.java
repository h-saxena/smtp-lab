/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hems;

import edu.hems.relay.server.MailRelayServer;
import edu.hems.smtp.client.CommandExecuter;

/**
 *
 * @author saxenah
 */
public class RelayServerBootup {

    public static void main(String[] args) {
		Integer serverPort = Integer.parseInt(System.getProperty("serverPort"));

		String relayToHost = null;
		relayToHost = System.getProperty("relayToHost");
		
		Integer relayToPort = null;
		if(System.getProperty("relayToPort") != null)
		relayToPort = Integer.parseInt(System.getProperty("relayToPort"));

        CommandExecuter relayServerCmdExecutor = null;
        if(relayToHost != null && relayToPort != null) {
            try {
            	System.out.println("Testing RelayToHost server communication ......");
            	relayServerCmdExecutor = new CommandExecuter();
            	relayServerCmdExecutor.init(relayToHost, relayToPort);
            	relayServerCmdExecutor.close();
            	System.out.println("***RelayToHost server tested******");
			} catch (Exception e) {
				//throw new RuntimeException("Error communicating to RelayToHost Server", e);
            	System.out.println("RelayToHost server tested failed. " + e.getMessage());
            	System.out.println("-- Unable to start Mail Relay Server --");
            	return;
			}            	
        }
        else {
        	System.out.println("*****INFO: No RelayToHost server configured");
        }

        MailRelayServer server = new MailRelayServer(serverPort, relayToHost, relayToPort);
        new Thread(server).start();
        System.out.println("\n\nMail Relay Server Started at Port# " + serverPort) ;
    }

}
