package com.example.eng.entity.eng;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户操作信息实体类
 * <p>
 * id 主键<br>
 * operType 操作类型 hide:隐藏，flag:标记<br>
 * dataType main:大类，detail:句子，word：单词<br>
 * dataId 数据值<br>
 * userId 用户id<br>
 * status available可用 unavailable不可用<br>
 * createTime 创建时间<br>
 * updateTime 修改时间<br>
*
* @author 系统
* @date Create Time: Tue Jun 17 14:47:17 CST 2025
 */
@Schema(description="用户操作信息")
@Data()
public class EngUserOper {
    /**
     * 主键
     */
    @Schema(description="主键",name="id")
    private Long id;

    /**
     * 操作类型 hide:隐藏，flag:标记
     */
    @Schema(description="操作类型 hide:隐藏，flag:标记",name="operType")
    @Length(max = 100, message = "操作类型 hide:隐藏，flag:标记名长度最长为100")
    private String operType;

    /**
     * main:大类，detail:句子，word：单词
     */
    @Schema(description="main:大类，detail:句子，word：单词",name="dataType")
    @Length(max = 100, message = "main:大类，detail:句子，word：单词名长度最长为100")
    private String dataType;

    /**
     * 数据值
     */
    @Schema(description="数据值",name="dataId")
    @Length(max = 100, message = "数据值名长度最长为100")
    private String dataId;

    /**
     * 用户id
     */
    @Schema(description="用户id",name="userId")
    private Long userId;

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