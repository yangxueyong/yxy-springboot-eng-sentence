package com.example.eng.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.EngUserOper;
import com.example.eng.entity.eng.io.EngSentenceDetailIO;
import com.example.eng.entity.eng.io.EngSentenceMainIO;
import com.example.eng.entity.eng.io.EngSentenceMainNextIO;
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
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    @Override
    public EngSentenceMain selectNextBySort(EngSentenceMainIO io) {
        return engSentenceMainMapper.selectNextBySort(io);
    }

    @Override
    public EngSentenceMain selectUpBySort(EngSentenceMainIO io) {
        return engSentenceMainMapper.selectUpBySort(io);
    }

    /**
     * 获取首页数据
     * @return {@link EngSentenceMainVO}
     */
    @Override
    public EngSentenceMainVO getIndexEngMain() {

        //获取上次学习的主句
        EngSentenceMain main = getLastEngSentenceMain();

        //如果没有上次学习的主句，则随机选择一个主句学习
        if(main == null){
            main = selectOrderByRandom(new EngSentenceMainIO());

            saveUserLastMain(main);
        }

        EngSentenceMainVO vo = getEngSentenceMainVO(main);
        return vo;
    }

    /**
     * 保存用户最近看的句子
     * @param main
     */
    private void saveUserLastMain(EngSentenceMain main) {
        User user = UserContext.getUser();
        //修改用户最近学习的主句
        User userIO = new User();
        userIO.setId(user.getId());
        userIO.setLastStudyMainId(main.getId());
        userService.updateByPrimaryKeySelective(userIO);
    }

    @NotNull
    private EngSentenceMainVO getEngSentenceMainVO(EngSentenceMain main) {
        EngSentenceMainVO vo = new EngSentenceMainVO();
        //赋值给返回值对象
        BeanUtil.copyProperties(main,vo);
        getMainOper(vo);

        //获取句子详情
        List<EngSentenceDetailVO> detailVOS = detailService.getEngSentenceDetailVOS(main);
        getDetailOper(detailVOS);
        vo.setDetails(detailVOS);
        return vo;
    }

    @Nullable
    private EngSentenceMain getLastEngSentenceMain() {
        User user = UserContext.getUser();
        String lastStudyMainId = user.getLastStudyMainId();

        //如果有上次学习的主句，则从上次学习的主句开始学习
        if(!StrUtil.isEmpty(lastStudyMainId)){
            EngSentenceMainIO io = new EngSentenceMainIO();
            io.setId(lastStudyMainId);
            return getEngSentenceMain(io);
        }
        return null;
    }

    private EngSentenceMain getEngSentenceMain(EngSentenceMainIO io) {
        List<EngSentenceMain> engSentenceMains = selectOrderBySort(io);
        if(!CollectionUtil.isEmpty(engSentenceMains)){
            return engSentenceMains.get(0);
        }
        return null;
    }

    @Override
    public EngSentenceMainVO getIndexEngNextMain(EngSentenceMainNextIO ioo) {
        String type = ioo.getType();
        User user = UserContext.getUser();
        String userType = user.getUserType();
        Date memberDueDate = user.getMemberDueDate();
        if(ObjUtil.equal(MyConstant.USER_TYPE_GENERAL, userType)
                || (memberDueDate != null && memberDueDate.before(DateUtil.date()))){
            throw new RuntimeException("没有其他内容了---");
        }
        EngSentenceMain mylastEngSentenceMain = getLastEngSentenceMain();

        EngSentenceMainIO io = EngSentenceMainIO.builder()
                .sort(mylastEngSentenceMain.getSort())
                .build();

        EngSentenceMain nextEngSentenceMain = null;
        if(ObjUtil.equal(MyConstant.TYPE_NEXT, type)){
            nextEngSentenceMain = selectNextBySort(io);
        }else{
            nextEngSentenceMain = selectUpBySort(io);
        }

        //没有下一页或者上一页
        if(nextEngSentenceMain == null){
            if(ObjUtil.equal(MyConstant.TYPE_NEXT, type)){
                nextEngSentenceMain = selectNextBySort(new EngSentenceMainIO());
            }else{
                nextEngSentenceMain = selectUpBySort(new EngSentenceMainIO());
            }
        }

        //保存用户最近看的句子
        saveUserLastMain(nextEngSentenceMain);

        EngSentenceMainVO vo = getEngSentenceMainVO(nextEngSentenceMain);
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
