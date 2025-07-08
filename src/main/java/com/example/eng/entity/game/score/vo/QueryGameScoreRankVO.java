package com.example.eng.entity.game.score.vo;

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
public class QueryGameScoreRankVO {

    @Schema(name = "rank", description = "排行")
    private String rank;

    @Schema(name = "typeTitle", description = "类型名称")
    private String typeTitle;

    @Schema(name = "levelTitle", description = "级别名称")
    private String levelTitle;

    @Schema(name = "userName", description = "用户名称")
    private String userName;

    @Schema(name = "userThirdHeadImgPath", description = "用户头像")
    private String userThirdHeadImgPath;

    @Schema(name = "consumeTime", description = "用户成绩")
    private Long consumeTime;

    @Schema(name = "consumeTimeStr", description = "用户成绩字符串")
    private String consumeTimeStr;

    @Schema(name = "createTime", description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}