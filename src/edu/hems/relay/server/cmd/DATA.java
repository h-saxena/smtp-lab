package edu.hems.relay.server.cmd;

public class DATA extends BaseSmtpCmd {

	public DATA() {
		permittedCmd.add(MSG.class.getSimpleName());
		successReplyCode = "354";
	}
}
