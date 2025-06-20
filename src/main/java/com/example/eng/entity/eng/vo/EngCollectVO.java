package com.example.eng.entity.eng.vo;

import com.example.eng.entity.base.BaseFlag;
import com.example.eng.entity.eng.EngSentenceWord;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
* EngSentenceWordVo
*
* @author yangxueyong
* @date 2024-10-16 19:39:59
*/
@Schema(description="扩展信息")
@Data()
public class EngCollectVO implements BaseFlag {

    /**
     * 主键
     */
    @Schema(description="主键",name="id")
    @Length(max = 100, message = "主键名长度最长为100")
    private String id;

    /**
     * 句子id
     */
    private String detailId;

    /**
     * 词语id
     */
    private String wordId;


    /**
     * 中文描述
     */
    @Schema(description="中文描述",name="znName")
    @Length(max = 100, message = "中文描述名长度最长为100")
    private String znName;

    /**
     * 英文描述
     */
    @Schema(description="英文描述",name="enName")
    @Length(max = 100, message = "英文描述名长度最长为100")
    private String enName;

    /**
     * web英文音频路径
     */
    @Schema(name="web英文音频路径")
    private String webAudioPath;

    /**
     * 创建时间
     */
    @Schema(name="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;


    private boolean hide;

    private boolean flag;

}