package edu.hems.relay.server.cmd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import edu.hems.smtp.client.CommandExecuter;

public class RelayServerSmtpCmdHandler {
    final static String CRLF = "\r\n";
	
	public static BaseSmtpCmd handle(String cmdTextRecieved, BaseSmtpCmd lastCmdPermitted, CommandExecuter relayServerCmdExecutor) {
		BaseSmtpCmd smtpCmdExecuted = null;
		
		BaseSmtpCmd reqSmtpCmd = null;
		String smtpCmd = null;
		
		if(lastCmdPermitted.getClass().equals(DATA.class) || lastCmdPermitted.getClass().equals(MSG.class)) {
			if(cmdTextRecieved.trim().equals(".")) {
				reqSmtpCmd = getSmtpCmdInstance(MSGEND.class.getSimpleName());
				reqSmtpCmd.setCmd(CRLF+"."+CRLF);
			}
			else {
				reqSmtpCmd = getSmtpCmdInstance("MSG");
				reqSmtpCmd.setCmd(cmdTextRecieved + CRLF);
			}
		}
		else { // for every other type of commands 
			
			String[] tokens = cmdTextRecieved.split("\\s+");
			smtpCmd = tokens[0];
			
			reqSmtpCmd = getSmtpCmdInstance(smtpCmd);
			reqSmtpCmd.setCmd(cmdTextRecieved);
			
		}
		
		
		if(reqSmtpCmd.isPermittedAnytime || lastCmdPermitted.isCmdPermittedNext(reqSmtpCmd)) {
			smtpCmdExecuted = reqSmtpCmd;
			
			if(relayServerCmdExecutor != null) {
				try {
					
					if(reqSmtpCmd.getClass().equals(MSG.class)) {
						relayServerCmdExecutor.executeCommandWithNoAck(reqSmtpCmd.getCmd());
						System.out.println(">>>>>>>>>>>>> Handler - Command MSG Exec: " + reqSmtpCmd.getCmd().getCommand());
					}
					else {
						String response = relayServerCmdExecutor.executeCommand(reqSmtpCmd.getCmd());
						if(response.startsWith(reqSmtpCmd.getSuccessReplyCode())) {
							reqSmtpCmd.setResponseMsg(response);
							//smtpCmdExecuted = reqSmtpCmd;
						}
						else {
							throw new RuntimeException("Reply code not as expected. " + response);
						}
						
					}
					
				} catch (Exception e) {
					new RuntimeException("Unable to execute request command: " + cmdTextRecieved);
				} 
				
			}
			else {
				// if no relay server configured to pass the smtp command
				// then just have simple reply back
				
				//smtpCmdExecuted = reqSmtpCmd;
				if (reqSmtpCmd.getClass().equals(QUIT.class)) {
					smtpCmdExecuted.setResponseMsg(smtpCmdExecuted.getSuccessReplyCode() + " " + "BYE");
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
		else 
			throw new RuntimeException("Invalid or Unsupported Command");
		
		return smtpCmd;
	}
	
}
