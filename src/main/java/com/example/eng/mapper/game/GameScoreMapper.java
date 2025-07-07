package com.example.eng.mapper.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.eng.entity.game.GameScore;
import com.example.eng.entity.game.num.NumGameLevel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameScoreMapper extends BaseMapper<GameScore> {
}
