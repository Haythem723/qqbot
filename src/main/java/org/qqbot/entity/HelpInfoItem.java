package org.qqbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HelpInfoItem {
	private int helpId;
	private String usage;
	private String description;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(". ")
				.append(this.usage)
				.append("    ")
				.append(this.description);
		return sb.toString();
	}
}
