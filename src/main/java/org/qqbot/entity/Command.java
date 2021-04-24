package org.qqbot.entity;

import org.qqbot.constant.CommandType;

public class Command {
	private CommandType type;
	private String[] args;

	public Command() {

	}

	public Command(CommandType type, String... args) {
		this.type = type;
		this.args = args;
	}

	public CommandType getType() {
		return type;
	}

	public Command setType(CommandType type) {
		this.type = type;
		return this;
	}

	public String[] getArgs() {
		return args;
	}

	public Command setArgs(String[] args) {
		this.args = args;
		return this;
	}
}
