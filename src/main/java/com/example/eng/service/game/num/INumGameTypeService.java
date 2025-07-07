package com.example.eng.service.game.num;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eng.entity.game.num.NumGameType;

import java.util.List;

public interface INumGameTypeService extends IService<NumGameType> {
    List<NumGameType> gameTypeList();
}
