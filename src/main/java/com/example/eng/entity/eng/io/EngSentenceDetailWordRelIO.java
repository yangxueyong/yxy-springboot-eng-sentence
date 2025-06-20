package com.example.eng.entity.eng.io;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 句型中的单词关系信息实体类
 * @author 系统
 * @date Create Time: Wed Oct 16 19:39:59 CST 2024
 */
@Schema(description="句型中的单词关系信息")
@Data()
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EngSentenceDetailWordRelIO {

    /**
     * 主id
     */
    @Schema(description="主id",name="mainId")
    private String mainId;

    /**
     * 句子id
     */
    @Schema(description="句子id",name="detailId")
    private String detailId;

    /**
     * 词id
     */
    @Schema(description="词id",name="wordId")
    private String wordId;


}