package org.qqbot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.qqbot.entity.JokeLibItem;

@Mapper
public interface JokeMapper extends BaseMapper {
	JokeLibItem getJoke(String id);
}
