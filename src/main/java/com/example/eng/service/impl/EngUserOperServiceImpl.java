package com.example.eng.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.base.BaseFlag;
import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngUserOper;
import com.example.eng.entity.eng.io.EngUserOperIO;
import com.example.eng.entity.eng.vo.EngSentenceDetailVO;
import com.example.eng.entity.eng.vo.EngSentenceWordVO;
import com.example.eng.entity.user.User;
import com.example.eng.mapper.eng.EngUserOperMapper;
import com.example.eng.service.EngUserOperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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

    /**
     * 查看句子中有没有被隐藏或者标记过
     *
     * @param vos
     */
    public <T extends BaseFlag> void getOper(List<T> vos, String dataType){
        User user = UserContext.getUser();
        if(CollectionUtil.isEmpty(vos)){
            return;
        }
        List<String> dataIds = vos.stream()
                .map(T::getId)
                .collect(Collectors.toList());

        Map<String, T> detailMap = vos.stream()
                .collect(Collectors.toMap(T::getId, v -> v, (p1, p2) -> p1));

        EngUserOperIO io = new EngUserOperIO();
        io.setUserId(user.getId());
        io.setDataType(dataType);
        io.setDataIdList(dataIds);
        io.setStatus(MyConstant.STATUS_AVAILABLE);
        List<EngUserOper> engUserOpers = selectByIO(io);
        if(CollectionUtil.isEmpty(engUserOpers)){
            return;
        }
        for (EngUserOper engUserOper : engUserOpers) {
            String operType = engUserOper.getOperType();
            String dataId = engUserOper.getDataId();
            T vo = detailMap.get(dataId);
            Class<?> clazz = vo.getClass();

            try {
                if (ObjUtil.equal(MyConstant.OPER_TYPE_HIDE, operType)) {
                    Field boolField = clazz.getDeclaredField("hide");
                    boolField.setAccessible(true);
                    boolField.setBoolean(vo, MyConstant.OPER_TYPE_YES);
                } else if (ObjUtil.equal(MyConstant.OPER_TYPE_FLAG, operType)) {
                    Field boolField = clazz.getDeclaredField("flag");
                    boolField.setAccessible(true);
                    boolField.setBoolean(vo, MyConstant.OPER_TYPE_YES);
                }
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }


}
