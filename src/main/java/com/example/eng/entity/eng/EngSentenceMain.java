package com.example.eng.entity.eng;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
public class EngSentenceMain {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键",name="id")
    //@Length(max = 100, message = "主键名长度最长为100")
    private String id;

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
     * available可用 unavailable不可用
     */
    @ApiModelProperty(value="available可用 unavailable不可用",name="status")
    //@Length(max = 100, message = "available可用 unavailable不可用名长度最长为100")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",name="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间",name="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}