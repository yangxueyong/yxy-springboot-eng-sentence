package com.example.eng.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.eng.EngUserOper;
import com.example.eng.entity.eng.io.EngUserOperIO;
import com.example.eng.entity.user.User;
import com.example.eng.mapper.eng.EngUserOperMapper;
import com.example.eng.service.EngUserOperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* EngUserOperImpl
*
* @author yangxueyong
* @date 2025-06-17 14:47:17
*/
@Service
public class EngUserOperServiceImpl implements EngUserOperService {
    @Autowired
    private EngUserOperMapper engUserOperMapper;

    @Override
    public List<EngUserOper> selectByIO(EngUserOperIO io) {
        return engUserOperMapper.selectByIO(io);
    }

    @Override
    public int insertSelective(EngUserOper record) {
        return engUserOperMapper.insertSelective(record);
    }

    @Override
    public int deleteByIO(EngUserOperIO io) {
        return engUserOperMapper.deleteByIO(io);
    }

    @Override
    public void operData(List<EngUserOper> operList) {
        User user = UserContext.getUser();
        EngUserOperIO io = new EngUserOperIO();
        io.setUserId(user.getId());
        io.setDataType(operList.get(0).getDataType());
        io.setDataIdList(operList.stream().map(EngUserOper::getDataId).collect(Collectors.toList()));
        deleteByIO(io);

        for (EngUserOper engUserOper : operList) {
            String operType = engUserOper.getOperType();
            if(StrUtil.isEmptyIfStr(operType)){
                continue;
            }
            if(!ObjUtil.equal(MyConstant.OPER_TYPE_HIDE, operType)
                    && !ObjUtil.equal(MyConstant.OPER_TYPE_FLAG, operType)){
                continue;
            }
            engUserOper.setUserId(user.getId());
            engUserOper.setStatus(MyConstant.STATUS_AVAILABLE);
            engUserOper.setCreateTime(DateUtil.date());
            insertSelective(engUserOper);
        }
    }

}
