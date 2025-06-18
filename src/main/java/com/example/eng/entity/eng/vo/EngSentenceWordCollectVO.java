package com.example.eng.entity.eng.vo;

import com.example.eng.entity.eng.EngSentenceWord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
* EngSentenceWordVo
*
* @author yangxueyong
* @date 2024-10-16 19:39:59
*/
@Schema(description="扩展信息")
@Data()
public class EngSentenceWordCollectVO extends EngSentenceWord {

    @Schema(description="收藏id")
    private Long collectId;
}