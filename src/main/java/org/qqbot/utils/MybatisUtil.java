package org.qqbot.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.qqbot.mapper.HelpMapper;

import java.io.IOException;
import java.io.Reader;

/**
 * @author diyigemt
 * mybatis工具类
 */
public class MybatisUtil implements InitializeUtil {
	// 全局mybatis工厂 官方文档推荐只有一个
	private static SqlSessionFactory factory;

	/**
	 * 根据配置文件 初始化工厂
	 */
	public static void init() {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("mybatis-config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		factory = new SqlSessionFactoryBuilder().build(reader);
	}

	/**
	 * 获取一个sqlsession
	 * 官方推荐执行完事务后需要释放
	 * @return 一个session
	 */
	public static SqlSession getSqlSession() {
		if (factory == null) {
			init();
		}
		return factory.openSession();
	}
}
