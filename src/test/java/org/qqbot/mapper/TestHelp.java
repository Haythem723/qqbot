package org.qqbot.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.qqbot.entity.HelpListItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestHelp {
	@Test
	public void testHelp() {
		try {
			// 读取配置文件 mybatis-config.xml
			InputStream config = Resources
					.getResourceAsStream("mybatis-config.xml");
			// 根据配置文件构建SqlSessionFactory
			SqlSessionFactory ssf = new SqlSessionFactoryBuilder()
					.build(config);
			// 通过 SqlSessionFactory 创建 SqlSession
			SqlSession ss = ssf.openSession();
			// SqlSession执行映射文件中定义的SQL，并返回映射结果
			/*
			 * com.mybatis.mapper.UserMapper.selectUserById 为 UserMapper.xml
			 * 中的命名空间+select 的 id
			 */
			List<HelpListItem> res = ss.selectList(
					"org.qqbot.mapper.SensitiveMapper.getSensitiveList");
			System.out.println(res);
			// 提交事务
			ss.commit();
			// 关闭 SqlSession
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
