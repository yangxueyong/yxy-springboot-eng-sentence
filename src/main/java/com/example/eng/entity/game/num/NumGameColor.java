package com.example.eng.entity.game.num;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_num_game_color")
@Schema(description = "数字游戏颜色表")
public class NumGameColor {

    @TableField("id")
    @Schema(name = "id", description = "主键")
    private String id;

    @TableField("`key`")
    @Schema(name = "key", description = "键")
    private String key;

    @TableField("value")
    @Schema(name = "value", description = "值")
    private String value;

    @TableField("content")
    @Schema(name = "content", description = "文本")
    private String content;

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