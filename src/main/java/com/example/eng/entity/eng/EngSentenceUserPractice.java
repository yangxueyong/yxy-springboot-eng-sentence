package com.example.eng.entity.eng;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 练习表实体类
 * <p>
 * id 主键<br>
 * mainId 主表id<br>
 * detailId 详情表id<br>
 * wordId 单词id<br>
 * practiceType 练习类型 sentence:句子 word:单词<br>
 * practiceVal 练习结果 success:成功 fail:失败<br>
 * lastTime 最近练习的时间<br>
 * createTime 创建时间<br>
 * updateTime 修改时间<br>
*
* @author 系统
* @date Create Time: Sat Oct 12 17:07:11 CST 2024
 */
@ApiModel(value="com.example.yxy.entity.eng.EngSentenceUserPractice",description="练习表")
@Data()
public class EngSentenceUserPractice {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",name="id")
    //@Length(max = 100, message = "主键名长度最长为100")
    private String id;

    /**
     * 主表id
     */
    @ApiModelProperty(value="主表id",name="mainId")
    //@Length(max = 100, message = "主表id名长度最长为100")
    private String mainId;

    /**
     * 详情表id
     */
    @ApiModelProperty(value="详情表id",name="detailId")
    //@Length(max = 100, message = "详情表id名长度最长为100")
    private String detailId;

    /**
     * 单词id
     */
    @ApiModelProperty(value="单词id",name="wordId")
    //@Length(max = 100, message = "单词id名长度最长为100")
    private String wordId;

    /**
     * 练习类型 sentence:句子 word:单词
     */
    @ApiModelProperty(value="练习类型 sentence:句子 word:单词",name="practiceType")
    //@Length(max = 100, message = "练习类型 sentence:句子 word:单词名长度最长为100")
    private String practiceType;

    /**
     * 练习结果 success:成功 fail:失败
     */
    @ApiModelProperty(value="练习结果 success:成功 fail:失败",name="practiceVal")
    //@Length(max = 100, message = "练习结果 success:成功 fail:失败名长度最长为100")
    private String practiceVal;

    /**
     * 最近练习的时间
     */
    @ApiModelProperty(value="最近练习的时间",name="lastTime")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date lastTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",name="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间",name="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;
}