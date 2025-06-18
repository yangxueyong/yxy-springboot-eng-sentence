package com.example.eng.entity.eng.io;

import com.example.eng.entity.eng.EngSentenceWord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
* 查询单词的请求参数
*
* @author yangxueyong
* @date 2024-10-16 19:39:59
*/
@Schema(description="查询单词的请求参数")
@Data()
public class EngSentenceWordIO {

    @Schema(name="id",description="详情id")
    private String detailId;

    /**
     * 用户id
     */
    @Schema(description="用户id"/*,name="mainId*"*/)
    private Long userId;
}