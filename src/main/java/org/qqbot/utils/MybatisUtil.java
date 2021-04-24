package org.qqbot.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.qqbot.mapper.HelpMapper;

import java.io.IOException;
import java.io.Reader;

public class MybatisUtil {
	private static SqlSessionFactory factory;

	public static void init() {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("mybatis-config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		factory = new SqlSessionFactoryBuilder().build(reader);
		factory.getConfiguration().addMapper(HelpMapper.class);
	}

	public static SqlSession getSqlSession() {
		if (factory == null) {
			init();
		}
		return factory.openSession();
	}
}
