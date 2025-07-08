package com.example.eng.service.impl.game;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eng.config.interceptor.UserContext;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.game.score.GameScore;
import com.example.eng.entity.game.num.NumGameLevel;
import com.example.eng.entity.game.score.io.QueryGameScoreRankIO;
import com.example.eng.entity.game.score.vo.QueryGameScoreRankVO;
import com.example.eng.mapper.game.GameScoreMapper;
import com.example.eng.service.game.IGameScoreService;
import com.example.eng.service.game.num.INumGameLevelService;
import com.example.eng.util.MyDateUtil;
import com.example.eng.util.UserUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 动物图片相关
 * @author yxy
 * @date 2025/07/07
 */
@Service
public class GameScoreServiceImpl extends ServiceImpl<GameScoreMapper, GameScore> implements IGameScoreService {


    @Autowired
    private INumGameLevelService iNumGameLevelService;

    /**
     * 保存成绩
     * @param gameScore
     * @return boolean
     */
    @Override
    public boolean saveGameScore(GameScore gameScore) {
        //校验游戏级别是否有效
        List<NumGameLevel> levelList = iNumGameLevelService.lambdaQuery()
                .eq(NumGameLevel::getId, gameScore.getGameLevelId())
                .eq(NumGameLevel::getStatus, MyConstant.STATUS_AVAILABLE)
                .last("LIMIT 1").list();
        if(CollectionUtil.isEmpty(levelList)){
            return false;
        }
        //检验用户有没有成绩
        String userId = String.valueOf(UserContext.getUser().getId());
        List<GameScore> scoreList = this.lambdaQuery()
                .eq(GameScore::getGameLevelId, gameScore.getGameLevelId())
                .eq(GameScore::getGameTypeId, gameScore.getGameTypeId())
                .eq(GameScore::getUserId, String.valueOf(userId))
                .last("LIMIT 1").list();
        gameScore.setUserId(userId);

        if(!CollectionUtil.isEmpty(scoreList)){
            GameScore dbGameScore = scoreList.get(0);
            //数据库里的时间为空，或者数据库的花费比本次的时间多，则更新
            if(dbGameScore.getConsumeTime() == null || dbGameScore.getConsumeTime() > gameScore.getConsumeTime()){
                UpdateWrapper<GameScore> updateWrapper = new UpdateWrapper<>();
                updateWrapper
                        .eq("game_level_id", gameScore.getGameLevelId())
                        .eq("game_type_id", gameScore.getGameTypeId())
                        .eq("user_id", userId) ;
                gameScore.setUpdateTime(DateUtil.date());
                return this.update(gameScore, updateWrapper);
            }
        }else{
            return this.save(gameScore);
        }
        return false;
    }

    /**
     * 获取成绩排行
     * @param io
     * @return {@link List}<{@link QueryGameScoreRankVO}>
     */
    @Override
    public List<QueryGameScoreRankVO> queryGameScoreRank(QueryGameScoreRankIO io) {
        io.setThirdType(UserUtil.getThirdType());
        //只查询前20条数据
        PageHelper.startPage(1, 20);
        List<QueryGameScoreRankVO> list = this.baseMapper.queryGameScoreRank(io);
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            QueryGameScoreRankVO vo = list.get(i);
            vo.setRank(String.valueOf(i + 1));
            Long consumeTime = vo.getConsumeTime();
            vo.setConsumeTimeStr(MyDateUtil.formatMillis(consumeTime));
        }
        return list;
    }




}
