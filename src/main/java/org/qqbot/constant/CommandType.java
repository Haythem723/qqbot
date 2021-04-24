package org.qqbot.constant;

public enum CommandType {
	COMMAND_NULL("0"),
	COMMAND_HELP(ConstantMenu.COMMAND_HELP),
	COMMAND_DICE(ConstantMenu.COMMAND_DICE),
	COMMAND_NEED(ConstantMenu.COMMAND_NEED),
	COMMAND_MEA_BUTTON(ConstantMenu.COMMAND_MEA_BUTTON),
	COMMAND_MANA_BUTTON(ConstantMenu.COMMAND_MANA_BUTTON),
	COMMAND_GET_HAPPY(ConstantMenu.COMMAND_GET_HAPPY),
	COMMAND_SEARCH_IMAGE(ConstantMenu.COMMAND_SEARCH_IMAGE),
	;
	private final String index;

	private CommandType(String index) {
		this.index = index;
	}

	public String getIndex() {
		return this.index;
	}

	public static CommandType getTypeById(String id) {
		for (CommandType type : CommandType.values()) {
			if (type.index.equals(id)) {
				return type;
			}
		}
		return COMMAND_NULL;
	}
}
