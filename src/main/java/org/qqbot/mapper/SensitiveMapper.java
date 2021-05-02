package org.qqbot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.qqbot.entity.SensitiveItem;
import java.util.List;

@Mapper
public interface SensitiveMapper extends BaseMapper{
    List<SensitiveItem> getSensitiveList();
}
