package edu.hems.relay.server.cmd;

public class MSG extends BaseSmtpCmd {

	public MSG() {
		permittedCmd.add(MSG.class.getSimpleName());
		permittedCmd.add(MSGEND.class.getSimpleName());
	}

}
