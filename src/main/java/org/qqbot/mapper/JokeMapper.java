package org.qqbot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.qqbot.entity.JokeLibItem;

@Mapper
public interface JokeMapper {
	JokeLibItem getJoke(String id);
}
