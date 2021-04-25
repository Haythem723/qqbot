package org.qqbot.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.qqbot.entity.JokeLibItem;
import org.qqbot.mapper.JokeMapper;
import org.qqbot.utils.MybatisUtil;

public class ImpJokeMapper implements JokeMapper {
    @Override
    public JokeLibItem getJoke(String id) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        JokeMapper jokeMapper = sqlSession.getMapper(JokeMapper.class);
        JokeLibItem jokeLibItem = jokeMapper.getJoke(id);
        sqlSession.close();
        return jokeLibItem;
    }
}
