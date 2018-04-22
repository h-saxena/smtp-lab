package edu.hems.relay.server.cmd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import edu.hems.smtp.client.CommandExecuter;

public class RelayServerSmtpCmdHandler {
	
	public static BaseSmtpCmd handle(String cmdTextRecieved, BaseSmtpCmd lastCmdPermitted, CommandExecuter relayServerCmdExecutor) {
		BaseSmtpCmd smtpCmdExecuted = null;
		
		String[] tokens = cmdTextRecieved.split("\\s+");
		String smtpCmd = tokens[0];
		
		BaseSmtpCmd reqSmtpCmd = getSmtpCmdInstance(smtpCmd);
		reqSmtpCmd.setCmd(cmdTextRecieved);
		
		if(reqSmtpCmd.isPermittedAnytime || lastCmdPermitted.isCmdPermittedNext(reqSmtpCmd)) {
			
			if(relayServerCmdExecutor != null) {
				try {
					String response = relayServerCmdExecutor.executeCommand(reqSmtpCmd.getCmd());
					if(response.startsWith(reqSmtpCmd.getSuccessReplyCode())) {
						reqSmtpCmd.setResponseMsg(response);
						smtpCmdExecuted = reqSmtpCmd;
					}
					else {
						throw new RuntimeException(response);
					}
				} catch (Exception e) {
					new RuntimeException("Unable to execute request command: " + cmdTextRecieved);
				} 
				
			}
			else {
				smtpCmdExecuted = reqSmtpCmd;
				// if no relay server configured to pass the smtp command
				// then just have simple reply back
				
				if (reqSmtpCmd.getClass().equals(QUIT.class)) {
					smtpCmdExecuted.setResponseMsg("BYE");
				} else {
					smtpCmdExecuted.setResponseMsg(smtpCmdExecuted.getSuccessReplyCode() + " " + cmdTextRecieved + "  Ok");
				}
				
			}
		}
		
		if(!reqSmtpCmd.isPermittedAnytime && !lastCmdPermitted.isCmdPermittedNext(reqSmtpCmd)) {
			throw new RuntimeException(smtpCmd + " : Command Not Permitted after : " + lastCmdPermitted.getClass().getSimpleName()
					+ " : Permitted Commands - " + lastCmdPermitted.permittedCmd);
		}
		
		return smtpCmdExecuted;
	}
	
	public static BaseSmtpCmd getSmtpCmdInstance(String cmd) {
		BaseSmtpCmd smtpCmd = null;
		
		if(cmd.equalsIgnoreCase("BEGIN"))
			smtpCmd = new BEGIN();
		else if(cmd.equalsIgnoreCase("HELO"))
			smtpCmd = new HELO();
		else if(cmd.equalsIgnoreCase("MAIL"))
			smtpCmd = new MAIL();
		else if(cmd.equalsIgnoreCase("RCPT"))
			smtpCmd = new RCPT();
		else if(cmd.equalsIgnoreCase("DATA"))
			smtpCmd = new DATA();
		else if(cmd.equalsIgnoreCase("MSG"))
			smtpCmd = new MSG();
		else if(cmd.equalsIgnoreCase("MSGEND"))
			smtpCmd = new MSGEND();
		else if(cmd.equalsIgnoreCase("QUIT"))
			smtpCmd = new QUIT();
		else if(cmd.equalsIgnoreCase("HELP"))
			smtpCmd = new HELP();
		else if(cmd.equalsIgnoreCase("."))
			smtpCmd = new MSGEND();
		else 
			throw new RuntimeException("Invalide or Unsupported Command");
		
		return smtpCmd;
	}
	
	public static void main(String[] args) {
		String str = "HELO pg-01 ";
		String[] splited = str.split("\\s+");
		System.out.println(Arrays.asList(splited));
		
		System.out.println(MAIL.class.getSimpleName());
	}

}
