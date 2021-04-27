package org.qqbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HelpInfoItem {
	private int helpId;
	private String usage;
	private String description;

	private String getDivDescription() {
		StringBuilder sb = new StringBuilder();
		String[] split = this.description.split("\\[n]");
		for (int i = 0; i < split.length; i++) {
			sb.append(split[i]);
			if (i + 1 == split.length) continue;
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(". ")
				.append(this.usage)
				.append("    ")
				.append(getDivDescription());
		return sb.toString();
	}
}
