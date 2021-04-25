package org.qqbot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.qqbot.constant.ConstantVoice;

/**
 * @author diyigemt
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class VoiceInfoItem {
	private int id;
	private String fileName;
	private String description;

	public String getUrl() {
		return ConstantVoice.MEA_VOICE_BASE_URL + this.fileName;
	}

	public String getFileName() {
		return fileName;
	}
}
