package com.example.eng.entity.game.score.io;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 查询排行
 * @author yxy
 * @date 2025/07/08
 */
@Data
public class QueryGameScoreRankIO {

    @Schema(name = "game_level_id", description = "游戏关卡ID")
    private String gameLevelId;

    @Schema(name = "game_type_id", description = "游戏类型ID")
    private String gameTypeId;

    @Schema(name = "status", description = "available可用，unavailable不可用", defaultValue = "available")
    private String status = "available";

    @Schema(name = "third_type", description = "三方类型")
    private String thirdType;

}