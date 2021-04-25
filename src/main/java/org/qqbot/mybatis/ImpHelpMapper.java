package org.qqbot.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.qqbot.entity.HelpInfoItem;
import org.qqbot.entity.HelpListItem;
import org.qqbot.mapper.HelpMapper;
import org.qqbot.utils.MybatisUtil;

import java.util.List;

public class ImpHelpMapper implements HelpMapper {
	@Override
	public List<HelpListItem> getHelpList() {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		HelpMapper mapper = sqlSession.getMapper(HelpMapper.class);
		List<HelpListItem> helpList = mapper.getHelpList();
		sqlSession.close();
		return helpList;
	}

	@Override
	public List<HelpInfoItem> getHelpInfo(String helpId) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		HelpMapper mapper = sqlSession.getMapper(HelpMapper.class);
		List<HelpInfoItem> helpInfo = mapper.getHelpInfo(helpId);
		sqlSession.close();
		return helpInfo;
	}

	@Override
	public HelpListItem getHelpListItem(String command) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		HelpMapper mapper = sqlSession.getMapper(HelpMapper.class);
		HelpListItem helpListItem = mapper.getHelpListItem(command);
		sqlSession.close();
		return helpListItem;
	}
}
