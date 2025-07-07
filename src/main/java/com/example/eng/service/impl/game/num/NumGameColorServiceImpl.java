package com.example.eng.service.impl.game.num;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.game.num.NumGameColor;
import com.example.eng.entity.game.num.NumGameType;
import com.example.eng.mapper.game.num.NumGameColorMapper;
import com.example.eng.service.game.num.INumGameColorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumGameColorServiceImpl extends ServiceImpl<NumGameColorMapper, NumGameColor> implements INumGameColorService {

    /**
     *
     * @return {@link List}<{@link NumGameType}>
     */
    @Override
    public List<NumGameColor> gameColorList() {
        List<NumGameColor> gameTypeList = lambdaQuery()
                .eq(NumGameColor::getStatus, MyConstant.STATUS_AVAILABLE)
                .list();
        return gameTypeList;
    }
}
