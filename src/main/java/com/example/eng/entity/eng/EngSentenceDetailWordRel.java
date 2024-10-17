package com.example.eng.entity.eng;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 句型中的单词关系信息实体类
 * <p>
 * id 主键<br>
 * mainId 主表id<br>
 * detailId 详情id<br>
 * wordId 单词id<br>
 * sort 排序<br>
 * status available可用 unavailable不可用<br>
 * createTime 创建时间<br>
 * updateTime 修改时间<br>
*
* @author 系统
* @date Create Time: Wed Oct 16 19:39:59 CST 2024
 */
@Schema(description="句型中的单词关系信息")
@Data()
public class EngSentenceDetailWordRel {
    /**
     * 主键
     */
    @Schema(description="主键",name="id")
    @Length(max = 100, message = "主键名长度最长为100")
    private String id;

    /**
     * 主表id
     */
    @Schema(description="主表id",name="mainId")
    @Length(max = 100, message = "主表id名长度最长为100")
    private String mainId;

    /**
     * 详情id
     */
    @Schema(description="详情id",name="detailId")
    @Length(max = 100, message = "详情id名长度最长为100")
    private String detailId;

    /**
     * 单词id
     */
    @Schema(description="单词id",name="wordId")
    @Length(max = 100, message = "单词id名长度最长为100")
    private String wordId;

    /**
     * 排序
     */
    @Schema(description="排序",name="sort")
    @Length(max = 100, message = "排序名长度最长为100")
    private String sort;

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @Schema(description="修改时间",name="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;
}