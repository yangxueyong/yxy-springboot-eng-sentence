package com.example.eng.service.game;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eng.entity.game.score.GameScore;
import com.example.eng.entity.game.score.io.QueryGameScoreRankIO;
import com.example.eng.entity.game.score.vo.QueryGameScoreRankVO;

import java.util.List;

public interface IGameScoreService extends IService<GameScore> {
    boolean saveGameScore(GameScore gameScore);

    List<QueryGameScoreRankVO> queryGameScoreRank(QueryGameScoreRankIO io);
}
