package edu.hems.relay.server.cmd;

public class MSGEND extends BaseSmtpCmd {

	public MSGEND() {
		permittedCmd.add(MAIL.class.getSimpleName());
	}
}
