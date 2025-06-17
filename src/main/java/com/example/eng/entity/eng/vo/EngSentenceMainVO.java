package com.example.eng.entity.eng.vo;

import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceMain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
* EngSentenceMainVo
*
* @author yangxueyong
* @date 2024-10-12 16:51:09
*/
@Schema(description="扩展信息*")
@Data()
public class EngSentenceMainVO extends EngSentenceMain {
    private List<EngSentenceDetailVO> details;

    private boolean hide;

    private boolean flag;

}