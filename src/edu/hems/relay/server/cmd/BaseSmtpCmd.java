package edu.hems.relay.server.cmd;

import java.util.ArrayList;
import java.util.List;

import edu.hems.smtp.client.Command;

public abstract class BaseSmtpCmd {
	
	protected List<String> permittedCmd = new ArrayList<String>();
	protected String successReplyCode = "250";
	protected boolean isPermittedAnytime = false;
	
	Command cmd;
	String responseMsg = "";


	public String getSuccessReplyCode() {
		return successReplyCode;
	}

	public void setSuccessReplyCode(String successReplyCode) {
		this.successReplyCode = successReplyCode;
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(String cmdText) {
		this.cmd = new Command(cmdText);
	}
	
	public boolean isCmdPermittedNext(BaseSmtpCmd nextSmtpCmdToExecute) {
		return permittedCmd.contains(nextSmtpCmdToExecute.getClass().getSimpleName());
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	
}
