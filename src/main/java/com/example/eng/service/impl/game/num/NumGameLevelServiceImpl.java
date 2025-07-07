package com.example.eng.service.impl.game.num;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.game.num.NumGameLevel;
import com.example.eng.mapper.eng.UserMapper;
import com.example.eng.mapper.game.num.NumGameLevelMapper;
import com.example.eng.service.UserService;
import com.example.eng.service.game.num.INumGameLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumGameLevelServiceImpl extends ServiceImpl<NumGameLevelMapper, NumGameLevel> implements INumGameLevelService {

    /**
     * 获取所有级别
     * @return {@link List}<{@link NumGameLevel}>
     */
    public List<NumGameLevel> numLevelList(){
        List<NumGameLevel> levelList = lambdaQuery()
                .eq(NumGameLevel::getStatus, MyConstant.STATUS_AVAILABLE)
                .orderByAsc(NumGameLevel::getSort)
                .list();
        return levelList;
    }
}
