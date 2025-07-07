package com.example.eng.service.game.num;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eng.entity.game.num.NumGameAnimal;
import com.example.eng.entity.game.num.NumGameColor;

import java.util.List;

public interface INumGameAnimalService extends IService<NumGameAnimal> {
    List<NumGameAnimal> gameAnimalList();
}
