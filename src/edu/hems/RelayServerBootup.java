/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hems;

import edu.hems.relay.server.MailRelayServer;

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
		
        MailRelayServer server = new MailRelayServer(serverPort, relayToHost, relayToPort);
        new Thread(server).start();
        System.out.println("Mail Relay Server Started.") ;
    }

}
