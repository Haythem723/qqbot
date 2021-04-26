package org.qqbot.entity;

import org.qqbot.constant.CommandType;

import java.util.ArrayList;
import java.util.Arrays;

public class Command {
	private CommandType type;
	private ArrayList<String> args;

	public Command() {
		this.type = CommandType.COMMAND_NULL;
		this.args = new ArrayList<String>();
	}

	public Command(CommandType type, String... args) {
		this();
		this.type = type;
		this.args.addAll(Arrays.asList(args));
	}

	public CommandType getType() {
		return type;
	}

	public Command setType(CommandType type) {
		this.type = type;
		return this;
	}

	public ArrayList<String> getArgs() {
		return args;
	}

	public Command setArgs(ArrayList<String> args) {
		this.args = args;
		return this;
	}

	public Command resetArgs() {
		this.args.clear();
		return this;
	}

	public Command addArgs(String arg) {
		this.args.add(arg);
		return this;
	}

	public Command resetAndAddArgs(String arg) {
		return this.resetArgs().addArgs(arg);
	}

	@Override
	public String toString() {
		return this.getType().toString() + ", args: " + this.args.toString();
	}
}
