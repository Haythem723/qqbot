package org.qqbot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.qqbot.entity.VoiceInfoItem;

@Mapper
public interface MeaVoiceMapper extends BaseMapper {
	VoiceInfoItem getVoiceInfo(String id);
}
