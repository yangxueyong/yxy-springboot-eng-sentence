package com.example.eng.entity.eng;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 句型详情表实体类
 * <p>
 * id 主键<br>
 * mainId 主表id<br>
 * znName 中文描述<br>
 * enName 英文描述<br>
 * sort 排序<br>
 * localAudioPath 本地英文音频路径<br>
 * status available可用 unavailable不可用<br>
 * createTime 创建时间<br>
 * updateTime 修改时间<br>
*
* @author 系统
* @date Create Time: Sat Oct 12 17:07:11 CST 2024
 */
@ApiModel(value="com.example.yxy.entity.eng.EngSentenceDetail",description="句型详情表")
@Data()
public class EngSentenceDetail {
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
     * 中文描述
     */
    @ApiModelProperty(value="中文描述",name="znName")
    //@Length(max = 100, message = "中文描述名长度最长为100")
    private String znName;

    /**
     * 英文描述
     */
    @ApiModelProperty(value="英文描述",name="enName")
    //@Length(max = 100, message = "英文描述名长度最长为100")
    private String enName;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序",name="sort")
    //@Length(max = 100, message = "排序名长度最长为100")
    private String sort;

    /**
     * 本地英文音频路径
     */
    @ApiModelProperty(value="本地英文音频路径",name="localAudioPath")
    //@Length(max = 100, message = "本地英文音频路径名长度最长为100")
    private String localAudioPath;

    /**
     * available可用 unavailable不可用
     */
    @ApiModelProperty(value="available可用 unavailable不可用",name="status")
    //@Length(max = 100, message = "available可用 unavailable不可用名长度最长为100")
    private String status;

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