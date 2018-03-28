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

        MailRelayServer server = new MailRelayServer(4040);
        new Thread(server).start();
        System.out.println("Mail Relay Server Started.") ;
    }

}
