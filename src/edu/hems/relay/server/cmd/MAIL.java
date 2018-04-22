package edu.hems.relay.server.cmd;

public class MAIL extends BaseSmtpCmd {

	public MAIL() {
		permittedCmd.add(RCPT.class.getSimpleName());
	}

}
