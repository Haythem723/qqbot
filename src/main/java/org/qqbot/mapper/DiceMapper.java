package org.qqbot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.qqbot.entity.DiceLogItem;
import org.qqbot.entity.DiceMessageItem;

import java.util.List;

@Mapper
public interface DiceMapper extends BaseMapper {

	int insertDiceLog(DiceLogItem item);

	List<DiceLogItem> getSenderDiceLog(String senderId);

	List<DiceMessageItem> getDiceMessage(String diceResult);
}
