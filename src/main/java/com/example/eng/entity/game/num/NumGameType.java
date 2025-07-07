package com.example.eng.entity.game.num;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_num_game_type")
@Schema(description = "数字游戏类型表")
public class NumGameType {

    @TableField("id")
    @Schema(name = "id", description = "主键")
    private String id;

    @TableField("sort")
    @Schema(name = "sort", description = "排序字段")
    private String sort;

    @TableField("title")
    @Schema(name = "title", description = "标题")
    private String title;

    @TableField("note")
    @Schema(name = "note", description = "描述")
    private String note;

    @TableField("rightText")
    @Schema(name = "rightText", description = "右侧文字")
    private String rightText;

    @TableField("status")
    @Schema(name = "status", description = "类型")
    private String status;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "create_time", description = "创建时间")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "update_time", description = "更新时间")
    private Date updateTime;
}