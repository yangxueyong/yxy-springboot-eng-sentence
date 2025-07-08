package com.example.eng.entity.game.num.io;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "数字游戏级别查询IO")
public class QueryNumGameLevelIO {

    @TableField("type")
    @Schema(name = "type", description = "类型")
    private String type;

}