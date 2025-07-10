package com.example.eng.entity.eng.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 句型主表实体类
 * <p>
 * id 主键<br>
 * znName 中文描述<br>
 * enName 英文描述<br>
 * sort 排序<br>
 * status available可用 unavailable不可用<br>
 * createTime 创建时间<br>
 * updateTime 修改时间<br>
*
* @author 系统
* @date Create Time: Sat Oct 12 16:51:09 CST 2024
 */
@Data()
public class SearchEngSentenceVO {
    /**
     * MAIN_ID
     */
    @Schema(description="主键",name="id")
    private String id;

    /**
     * 中文描述
     */
    @Schema(description="中文描述",name="znName")
    private String znName;

    /**
     * 英文描述
     */
    @Schema(description="英文描述",name="enName")
    private String enName;

    /**
     * 排序
     */
    @Schema(description="排序",name="sort")
    private String sort;

    /**
     * 类型 main主表，detail详情表
     */
    @Schema(description="类型",name="type")
    private String type;

    /**
     * 音频路径
     */
    @Schema(description="音频路径",name="localAudioPath")
    private String localAudioPath;

    /**
     * 音频路径web
     */
    @Schema(description="音频路径web",name="webAudioPath")
    private String webAudioPath;



}