package edu.hems.relay.server.cmd;

public class HELO extends BaseSmtpCmd {
	
	public HELO() {
		permittedCmd.add(MAIL.class.getSimpleName());
	}

}
