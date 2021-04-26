package org.qqbot.mapper;

import org.junit.jupiter.api.Test;
import org.qqbot.entity.HelpListItem;
import org.qqbot.utils.MybatisUtil;

import java.util.Arrays;
import java.util.List;

public class TestGeneralInterface {
	@Test
	public void testGeneralFunction() {
		List<HelpListItem> getHelpList = MybatisUtil.getInstance().getListData(HelpMapper.class, HelpListItem.class, "getHelpList");
		System.out.println(Arrays.toString(getHelpList.toArray()));
	}
}
