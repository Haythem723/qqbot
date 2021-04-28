package org.qqbot.mapper;

import com.sun.tools.javac.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.qqbot.entity.DiceLogItem;

@Mapper
public interface DiceLogMapper extends BaseMapper {

	int insertDiceLog(DiceLogItem item);

	List<DiceLogItem> getSenderDiceLog(String senderId);
}
