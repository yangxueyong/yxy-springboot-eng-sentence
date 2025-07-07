package com.example.eng.service.impl.game.num;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eng.config.param.web.MyImgConfig;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.game.num.NumGameAnimal;
import com.example.eng.entity.game.num.NumGameType;
import com.example.eng.mapper.game.num.NumGameAnimalMapper;
import com.example.eng.mapper.game.num.NumGameTypeMapper;
import com.example.eng.service.game.num.INumGameAnimalService;
import com.example.eng.service.game.num.INumGameTypeService;
import com.example.eng.util.MyImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动物图片相关
 * @author yxy
 * @date 2025/07/07
 */
@Service
public class NumGameAnimalServiceImpl extends ServiceImpl<NumGameAnimalMapper, NumGameAnimal> implements INumGameAnimalService {

    @Autowired
    private MyImgConfig myImgConfig;

    /**
     * 获取动物图片
     * @return {@link List}<{@link NumGameType}>
     */
    @Override
    public List<NumGameAnimal> gameAnimalList() {
        List<NumGameAnimal> gameTypeList = lambdaQuery()
                .eq(NumGameAnimal::getStatus, MyConstant.STATUS_AVAILABLE)
                .list();
        if(CollectionUtil.isEmpty(gameTypeList)){
            return null;
        }
        for (NumGameAnimal numGameAnimal : gameTypeList) {
            numGameAnimal.setImgSrc(MyImgUtil.getAnimalImgSrc(myImgConfig, numGameAnimal.getImgSrc()));
            numGameAnimal.setShadowSrc(MyImgUtil.getAnimalImgSrc(myImgConfig, numGameAnimal.getShadowSrc()));
        }
        return gameTypeList;
    }
}
