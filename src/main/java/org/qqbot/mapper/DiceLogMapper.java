package org.qqbot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.qqbot.entity.DiceLogItem;

import java.util.List;

@Mapper
public interface DiceLogMapper extends BaseMapper {

	int insertDiceLog(DiceLogItem item);

	List<DiceLogItem> getSenderDiceLog(String senderId);
}
