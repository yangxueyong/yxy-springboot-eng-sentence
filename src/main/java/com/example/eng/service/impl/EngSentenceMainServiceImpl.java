package com.example.eng.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.EngUserOper;
import com.example.eng.entity.eng.io.EngSentenceDetailIO;
import com.example.eng.entity.eng.io.EngSentenceMainIO;
import com.example.eng.entity.eng.io.EngUserOperIO;
import com.example.eng.entity.eng.vo.EngSentenceDetailVO;
import com.example.eng.entity.eng.vo.EngSentenceMainVO;
import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.WechatUser;
import com.example.eng.entity.user.wechat.io.WechatTokenIO;
import com.example.eng.mapper.eng.EngSentenceMainMapper;
import com.example.eng.service.EngSentenceDetailService;
import com.example.eng.service.EngSentenceMainService;
import com.example.eng.service.EngUserOperService;
import com.example.eng.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* EngSentenceMainImpl
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Service
public class EngSentenceMainServiceImpl implements EngSentenceMainService {
    @Autowired
    private EngSentenceMainMapper engSentenceMainMapper;

    @Autowired
    private EngUserOperService engUserOperService;

    @Autowired
    private EngSentenceDetailService detailService;

    @Autowired
    private UserService userService;


    @Override
    public List<EngSentenceMain> selectOrderBySort(EngSentenceMainIO io) {
        return engSentenceMainMapper.selectOrderBySort(io);
    }

    @Override
    public EngSentenceMain selectOrderByRandom(EngSentenceMainIO io) {
        return engSentenceMainMapper.selectOrderByRandom(io);
    }

    /**
     * 获取首页数据
     * @return {@link EngSentenceMainVO}
     */
    @Override
    public EngSentenceMainVO getIndexEngMain() {
        User user = UserContext.getUser();
        String lastStudyMainId = user.getLastStudyMainId();
        EngSentenceMain main = null;

        EngSentenceMainVO vo = new EngSentenceMainVO();

        //如果有上次学习的主句，则从上次学习的主句开始学习
        if(!StrUtil.isEmpty(lastStudyMainId)){
            EngSentenceMainIO io = new EngSentenceMainIO();
            io.setId(lastStudyMainId);
            List<EngSentenceMain> engSentenceMains = selectOrderBySort(io);
            if(!CollectionUtil.isEmpty(engSentenceMains)){
                main = engSentenceMains.get(0);
            }
        }

        //如果没有上次学习的主句，则随机选择一个主句学习
        if(main == null){
            main = selectOrderByRandom(new EngSentenceMainIO());

            //修改用户最近学习的主句
            User userIO = new User();
            userIO.setId(user.getId());
            userIO.setLastStudyMainId(main.getId());
            userService.updateByPrimaryKeySelective(userIO);
        }

        //赋值给返回值对象
        BeanUtil.copyProperties(main,vo);
        getMainOper(vo);

        //获取句子详情
        List<EngSentenceDetailVO> detailVOS = detailService.getEngSentenceDetailVOS(main);
        getDetailOper(detailVOS);
        vo.setDetails(detailVOS);
        return vo;
    }



    /**
     * 查看句子中有没有被隐藏或者标记过
     * @param vos
     */
    private void getDetailOper(List<EngSentenceDetailVO> vos){
        User user = UserContext.getUser();

        List<String> dataIds = vos.stream()
                .map(EngSentenceDetailVO::getId)
                .collect(Collectors.toList());

        Map<String, EngSentenceDetailVO> detailMap = vos.stream()
                .collect(Collectors.toMap(EngSentenceDetail::getId, v -> v, (p1, p2) -> p1));

        EngUserOperIO io = new EngUserOperIO();
        io.setUserId(user.getId());
        io.setDataType(MyConstant.DATA_TYPE_DETAIL);
        io.setDataIdList(dataIds);
        io.setStatus(MyConstant.STATUS_AVAILABLE);
        List<EngUserOper> engUserOpers = engUserOperService.selectByIO(io);
        if(CollectionUtil.isEmpty(engUserOpers)){
            return;
        }
        for (EngUserOper engUserOper : engUserOpers) {
            String operType = engUserOper.getOperType();
            String dataId = engUserOper.getDataId();
            EngSentenceDetailVO vo = detailMap.get(dataId);

            if(ObjUtil.equal(MyConstant.OPER_TYPE_HIDE, operType)){
                vo.setHide(MyConstant.OPER_TYPE_YES);
            }else if(ObjUtil.equal(MyConstant.OPER_TYPE_FLAG, operType)){
                vo.setFlag(MyConstant.OPER_TYPE_YES);
            }
        }
    }

    /**
     * 查看主句有没有被隐藏或者标记过
     * @param vo
     */
    private void getMainOper(EngSentenceMainVO vo){
        User user = UserContext.getUser();

        EngUserOperIO io = new EngUserOperIO();
        io.setUserId(user.getId());
        io.setDataType(MyConstant.DATA_TYPE_MAIN);
        io.setDataIdList(Arrays.asList(vo.getId()));
        io.setStatus(MyConstant.STATUS_AVAILABLE);
        List<EngUserOper> engUserOpers = engUserOperService.selectByIO(io);
        if(CollectionUtil.isEmpty(engUserOpers)){
            return;
        }
        for (EngUserOper engUserOper : engUserOpers) {
            String operType = engUserOper.getOperType();

            if(ObjUtil.equal(MyConstant.OPER_TYPE_HIDE, operType)){
                vo.setHide(MyConstant.OPER_TYPE_YES);
            }else if(ObjUtil.equal(MyConstant.OPER_TYPE_FLAG, operType)){
                vo.setFlag(MyConstant.OPER_TYPE_YES);
            }
        }
    }
}
