package com.example.eng.service.impl.game.num;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.game.num.NumGameLevel;
import com.example.eng.entity.game.num.NumGameType;
import com.example.eng.entity.game.num.io.QueryNumGameTypeIO;
import com.example.eng.mapper.game.num.NumGameLevelMapper;
import com.example.eng.mapper.game.num.NumGameTypeMapper;
import com.example.eng.service.game.num.INumGameLevelService;
import com.example.eng.service.game.num.INumGameTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumGameTypeServiceImpl extends ServiceImpl<NumGameTypeMapper, NumGameType> implements INumGameTypeService {

    /**
     *
     * @return {@link List}<{@link NumGameType}>
     */
    @Override
    public List<NumGameType> gameTypeList(QueryNumGameTypeIO io) {
        List<NumGameType> gameTypeList = lambdaQuery()
                .eq(NumGameType::getStatus, MyConstant.STATUS_AVAILABLE)
                .eq(NumGameType::getType, io.getType())
                .orderByAsc(NumGameType::getSort)
                .list();
        return gameTypeList;
    }
}
