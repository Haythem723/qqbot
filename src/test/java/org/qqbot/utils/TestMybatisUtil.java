package org.qqbot.utils;

import org.junit.jupiter.api.Test;
import org.qqbot.entity.HelpListItem;
import org.qqbot.mapper.HelpMapper;

public class TestMybatisUtil {
	@Test
	public void testGetHelpList() {
		MybatisUtil.init();
		HelpListItem item = MybatisUtil.getInstance().getSingleData(HelpMapper.class, HelpListItem.class, "getHelpListItem", "搜图");
		System.out.println(item.toString());
	}
}
