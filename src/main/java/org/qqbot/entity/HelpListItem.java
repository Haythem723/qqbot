package org.qqbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HelpListItem {
	private int id;
	private String command;
	private String shortcut;

	public String getId() {
		return String.valueOf(this.id);
	}

	public int getIntId() {
		return this.id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.command)
				.append("  缩写: ")
				.append(this.shortcut == null ? "无" : this.shortcut);
		return sb.toString();
	}
}
