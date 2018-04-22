package edu.hems.relay.server.cmd;

public class HELP extends BaseSmtpCmd {
	
	public HELP() {
		isPermittedAnytime = true;
		successReplyCode = "211";
	}

}
