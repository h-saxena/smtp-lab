package edu.hems.relay.server.cmd;

public class BEGIN extends BaseSmtpCmd {
	
	public BEGIN() {
		permittedCmd.add(HELO.class.getSimpleName());
	}

}
