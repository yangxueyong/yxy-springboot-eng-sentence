package com.example.eng.entity.game.num.io;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "数字游戏类型查询IO")
public class QueryNumGameTypeIO {

    @TableField("type")
    @Schema(name = "type", description = "类型")
    private String type;

}