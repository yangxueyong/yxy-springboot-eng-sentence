package com.example.eng.entity.game;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_game_score")
@Schema(description = "游戏得分记录表")
public class GameScore {

    @TableId(value = "id",type = IdType.AUTO)
    @Schema(name = "id", description = "主键")
    private Long id;

    @TableField("user_id")
    @Schema(name = "user_id", description = "用户ID")
    private String userId;

    @TableField("game_level_id")
    @Schema(name = "game_level_id", description = "游戏关卡ID")
    private String gameLevelId;

    @TableField("game_type_id")
    @Schema(name = "game_type_id", description = "游戏类型ID")
    private String gameTypeId;

    @TableField("consume_time")
    @Schema(name = "consume_time", description = "消耗时间(单位毫秒)")
    private Long consumeTime;

    @TableField("status")
    @Schema(name = "status", description = "available可用，unavailable不可用", defaultValue = "available")
    private String status = "available";

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "create_time", description = "创建时间")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "update_time", description = "更新时间")
    private Date updateTime;
}