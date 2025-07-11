package com.example.eng.web;

import com.example.eng.entity.game.num.io.QueryNumGameLevelIO;
import com.example.eng.entity.game.num.io.QueryNumGameTypeIO;
import com.example.eng.entity.game.score.GameScore;
import com.example.eng.entity.game.num.NumGameAnimal;
import com.example.eng.entity.game.num.NumGameColor;
import com.example.eng.entity.game.num.NumGameLevel;
import com.example.eng.entity.game.num.NumGameType;
import com.example.eng.entity.game.score.io.QueryGameScoreRankIO;
import com.example.eng.entity.game.score.vo.QueryGameScoreRankVO;
import com.example.eng.entity.res.R;
import com.example.eng.service.game.IGameScoreService;
import com.example.eng.service.game.num.INumGameAnimalService;
import com.example.eng.service.game.num.INumGameColorService;
import com.example.eng.service.game.num.INumGameLevelService;
import com.example.eng.service.game.num.INumGameTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("/game")
@Tag(name = "游戏相关")
public class GameNumController {

    @Autowired
    private INumGameLevelService iNumGameLevelService;

    @Autowired
    private INumGameTypeService iNumGameTypeService;

    @Autowired
    private INumGameColorService iNumGameColorService;

    @Autowired
    private INumGameAnimalService iNumGameAnimalService;

    @Autowired
    private IGameScoreService iGameScoreService;


    @RequestMapping("/numLevelList")
    @Operation(summary = "获取所有level列表")
    public R<List<NumGameLevel>> numLevelList(@RequestBody @Validated QueryNumGameLevelIO io){
        return R.data(iNumGameLevelService.numLevelList(io));
    }

    @RequestMapping("/gameTypeList")
    @Operation(summary = "获取所有游戏类型列表")
    public R<List<NumGameType>> gameTypeList(@RequestBody @Validated QueryNumGameTypeIO io){
        return R.data(iNumGameTypeService.gameTypeList(io));
    }


    @RequestMapping("/gameColorList")
    @Operation(summary = "获取游戏中所有颜色列表")
    public R<List<NumGameColor>> gameColorList(){
        return R.data(iNumGameColorService.gameColorList());
    }


    @RequestMapping("/gameAnimalList")
    @Operation(summary = "获取游戏中所有动物列表")
    public R<List<NumGameAnimal>> gameAnimalList(){
        return R.data(iNumGameAnimalService.gameAnimalList());
    }

    @RequestMapping("/saveGameScore")
    @Operation(summary = "保存游戏得分")
    public R saveGameScore(@RequestBody @Validated GameScore gameScore){
        boolean b = iGameScoreService.saveGameScore(gameScore);
        return R.success("保存成功");
    }

    @RequestMapping("/queryGameScoreRankList")
    @Operation(summary = "获取游戏成绩排名")
    public R queryGameScoreRankList(@RequestBody @Validated QueryGameScoreRankIO io){
        List<QueryGameScoreRankVO> list = iGameScoreService.queryGameScoreRank(io);
        return R.data(list);
    }


}
