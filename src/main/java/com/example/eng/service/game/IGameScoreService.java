package com.example.eng.service.game;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eng.entity.game.GameScore;
import com.example.eng.entity.game.num.NumGameLevel;

import java.util.List;

public interface IGameScoreService extends IService<GameScore> {
    boolean saveGameScore(GameScore gameScore);
}
