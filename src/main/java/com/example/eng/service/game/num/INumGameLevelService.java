package com.example.eng.service.game.num;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eng.entity.game.num.NumGameLevel;
import com.example.eng.entity.game.num.io.QueryNumGameLevelIO;

import java.util.List;

public interface INumGameLevelService extends IService<NumGameLevel> {
    List<NumGameLevel> numLevelList(QueryNumGameLevelIO io);
}
