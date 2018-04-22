package edu.hems.relay.server.cmd;

public class RCPT extends BaseSmtpCmd {

	public RCPT() {
		permittedCmd.add(RCPT.class.getSimpleName());
		permittedCmd.add(DATA.class.getSimpleName());
	}

}
