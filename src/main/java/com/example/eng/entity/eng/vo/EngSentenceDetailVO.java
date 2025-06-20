package com.example.eng.entity.eng.vo;

import com.example.eng.entity.base.BaseFlag;
import com.example.eng.entity.eng.EngSentenceDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
* EngSentenceDetailVo
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Schema(description="扩展信息*")
@Data()
public class EngSentenceDetailVO extends EngSentenceDetail implements BaseFlag {

    private boolean hide;

    private boolean flag;
}