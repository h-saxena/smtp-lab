package edu.hems.relay.server.cmd;

public class QUIT extends BaseSmtpCmd {

	public QUIT() {
		//permittedCmd.add(HELO.class.getSimpleName());
		successReplyCode = "221";
		isPermittedAnytime = true;
	}

}
