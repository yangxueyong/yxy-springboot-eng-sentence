package com.example.eng.service.game.num;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eng.entity.game.num.NumGameColor;
import com.example.eng.entity.game.num.NumGameType;

import java.util.List;

public interface INumGameColorService extends IService<NumGameColor> {
    List<NumGameColor> gameColorList();
}
