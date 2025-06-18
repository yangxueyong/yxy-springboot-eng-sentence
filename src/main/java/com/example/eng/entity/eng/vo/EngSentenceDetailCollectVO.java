package com.example.eng.entity.eng.vo;

import com.example.eng.entity.eng.EngSentenceDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
* EngSentenceDetailCollectVO
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Schema(description="扩展信息*")
@Data()
public class EngSentenceDetailCollectVO extends EngSentenceDetail {
    @Schema(description="收藏id")
    private String collectId;
}