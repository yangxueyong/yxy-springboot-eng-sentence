package com.example.eng.entity.game.num;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "数字游戏关卡表")
@TableName("t_num_game_level")
public class NumGameLevel {

    @Schema(name = "id", description = "主键")
    @TableField(value = "id")
    private String id;

    @Schema(name = "sort", description = "排序")
    @TableField(value = "sort")
    private String sort;

    @TableField("type")
    @Schema(name = "type", description = "类型")
    private String type;
    @Schema(name = "levelHideTime", description = "每一关的记忆时间")
    @TableField(value = "levelHideTime")
    private Long levelHideTime;

    @Schema(name = "beginBlock", description = "第一关需要完成多少块")
    @TableField(value = "beginBlock")
    private Long beginBlock;

    @Schema(name = "beginNum", description = "对于数字关卡，第一关以什么数字开始")
    @TableField(value = "beginNum")
    private Long beginNum;

    @Schema(name = "levelSumNum", description = "总的关卡数量")
    @TableField(value = "levelSumNum")
    private Long levelSumNum;

    @Schema(name = "typeKey", description = "类型 general表示普通，custom表示自定义")
    @TableField(value = "typeKey")
    private String typeKey;

    @Schema(name = "itemCls", description = "元素的样式", defaultValue = "v_item")
    @TableField(value = "itemCls")
    private String itemCls = "v_item";

    @Schema(name = "fontSize", description = "字体的大小", defaultValue = "30rpx")
    @TableField(value = "fontSize")
    private String fontSize = "30rpx";

    @Schema(name = "gameTime", description = "游戏时长")
    @TableField(value = "gameTime")
    private Long gameTime;

    @Schema(name = "colNum", description = "棋盘的列数")
    @TableField(value = "colNum")
    private Long colNum;

    @Schema(name = "numSum", description = "棋盘的方块数量")
    @TableField(value = "numSum")
    private Long numSum;

    @Schema(name = "title", description = "标题")
    @TableField(value = "title")
    private String title;

    @Schema(name = "note", description = "备注")
    @TableField(value = "note")
    private String note;

    @Schema(name = "rightText", description = "右侧文字")
    @TableField(value = "rightText")
    private String rightText;

    @Schema(name = "create_time", description = "创建时间")
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "update_time", description = "更新时间")
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Schema(name = "status", description = "available可用，unavailable不可用")
    @TableField(value = "status")
    private String status;

    @TableField("user_type")
    @Schema(name = "user_type", description = "用户类型 all所有人都能玩，general,member只有会员或普通用户能玩")
    private String userType;
}