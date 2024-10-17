package com.example.eng.entity.eng;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 句型中的单词信息实体类
 * <p>
 * id 主键<br>
 * znName 中文描述<br>
 * enName 英文描述<br>
 * sort 排序<br>
 * localAudioPath 本地英文音频路径<br>
 * status available可用 unavailable不可用<br>
 * createTime 创建时间<br>
 * updateTime 修改时间<br>
*
* @author 系统
* @date Create Time: Wed Oct 16 19:39:59 CST 2024
 */
@Schema(description="句型中的单词信息")
@Data()
public class EngSentenceWord {
    /**
     * 主键
     */
    @Schema(description="主键",name="id")
    @Length(max = 100, message = "主键名长度最长为100")
    private String id;

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
     * 排序
     */
    @Schema(description="排序",name="sort")
    @Length(max = 100, message = "排序名长度最长为100")
    private String sort;

    /**
     * 本地英文音频路径
     */
    @Schema(description="本地英文音频路径",name="localAudioPath")
    @Length(max = 100, message = "本地英文音频路径名长度最长为100")
    private String localAudioPath;

    /**
     * available可用 unavailable不可用
     */
    @Schema(description="available可用 unavailable不可用",name="status")
    @Length(max = 100, message = "available可用 unavailable不可用名长度最长为100")
    private String status;

    /**
     * 创建时间
     */
    @Schema(description="创建时间",name="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @Schema(description="修改时间",name="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}