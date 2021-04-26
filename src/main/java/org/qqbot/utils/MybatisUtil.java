package org.qqbot.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.qqbot.mapper.BaseMapper;
import org.qqbot.mapper.HelpMapper;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author diyigemt
 * mybatis工具类
 */
public class MybatisUtil implements InitializeUtil {
	// 全局mybatis工厂 官方文档推荐只有一个
	private static SqlSessionFactory factory;

	private static final MybatisUtil INSTANCE = new MybatisUtil();

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

	public static MybatisUtil getInstance() {
		return INSTANCE;
	}

	/**
	 * 获取一个sqlsession
	 * 官方推荐执行完事务后需要释放
	 * @return 一个session
	 */
	public SqlSession getSqlSession() {
		if (factory == null) {
			init();
		}
		return factory.openSession();
	}

	public <T extends BaseMapper, K> K getSingleData(Class<T> mapperClass, Class<K> resClass, String methodName, String arg1) {
		SqlSession sqlSession = MybatisUtil.getInstance().getSqlSession();
		T mapper = sqlSession.getMapper(mapperClass);
		K res = null;
		try {
			Method method = mapperClass.getMethod(methodName, arg1.getClass());
			res = (K) method.invoke(mapper, arg1);
		} catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return res;
	}

	public <T extends BaseMapper, K> List<K> getListData(Class<T> mapperClass, Class<K> resClass, String methodName) {
		SqlSession sqlSession = MybatisUtil.getInstance().getSqlSession();
		T mapper = sqlSession.getMapper(mapperClass);
		List<K> res = null;
		try {
			Method method = mapperClass.getMethod(methodName);
			res = (List<K>) method.invoke(mapper);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return res;
	}

	public <T extends BaseMapper, K> List<K> getListData(Class<T> mapperClass, Class<K> resClass, String methodName, String arg1) {
		SqlSession sqlSession = MybatisUtil.getInstance().getSqlSession();
		T mapper = sqlSession.getMapper(mapperClass);
		List<K> res = null;
		try {
			Method method = mapperClass.getMethod(methodName);
			res = (List<K>) method.invoke(mapper, arg1);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return res;
	}
}
