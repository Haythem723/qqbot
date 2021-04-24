package org.qqbot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.qqbot.entity.HelpInfoItem;
import org.qqbot.entity.HelpListItem;

import java.util.List;

@Mapper
public interface HelpMapper {
	List<HelpListItem> getHelpList();

	List<HelpInfoItem> getHelpInfo(String helpId);

	HelpListItem getHelpListItem(String command);
}
