package edu.hems.relay.server.cmd;

public class BEGIN extends BaseSmtpCmd {
	
	public BEGIN() {
		this.permittedCmd.add(HELO.class.getSimpleName());
		this.successReplyCode = "220";
		this.responseMsg = this.successReplyCode + " *** Welcome to HEMANT SMTP RELAY SERVER *** ";
	}

}
