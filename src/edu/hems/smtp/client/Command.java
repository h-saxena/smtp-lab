package edu.hems.smtp.client;

public class Command {
	String cmd = null;
	
	public Command(String command) {
		this.cmd = command;
	}
	
	public String getCommand() {
		return cmd;
	}

}
