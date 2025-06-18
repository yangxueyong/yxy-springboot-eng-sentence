package com.example.eng.entity.eng.io;

import com.example.eng.entity.base.MyPage;
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
public class EngSentenceCollectDelIO  {

    @Schema(name="id",description="id")
    private String id;

}