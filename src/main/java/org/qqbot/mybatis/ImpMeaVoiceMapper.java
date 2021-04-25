package org.qqbot.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.qqbot.entity.HelpListItem;
import org.qqbot.entity.VoiceInfoItem;
import org.qqbot.mapper.MeaVoiceMapper;
import org.qqbot.utils.MybatisUtil;

import java.util.List;

public class ImpMeaVoiceMapper implements MeaVoiceMapper {
	@Override
	public VoiceInfoItem getVoiceInfo(String id) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		MeaVoiceMapper mapper = sqlSession.getMapper(MeaVoiceMapper.class);
		VoiceInfoItem voiceInfo = mapper.getVoiceInfo(id);
		sqlSession.close();
		return voiceInfo;
	}
}
