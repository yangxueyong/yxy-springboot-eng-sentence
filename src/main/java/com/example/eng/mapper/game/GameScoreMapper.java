package com.example.eng.mapper.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.eng.entity.game.score.GameScore;
import com.example.eng.entity.game.score.io.QueryGameScoreRankIO;
import com.example.eng.entity.game.score.vo.QueryGameScoreRankVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameScoreMapper extends BaseMapper<GameScore> {
    List<QueryGameScoreRankVO> queryGameScoreRank(QueryGameScoreRankIO io);
}
