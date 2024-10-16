package com.example.eng.entity.eng;

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
public class EngSentenceMain {
    /**
     * 主键
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
     * available可用 unavailable不可用
     */
    @Schema(description="available可用 unavailable不可用",name="status")
    private String status;

    /**
     * 创建时间
     */
    @Schema(description="创建时间",name="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8*")
    private Date createTime;

    /**
     * 修改时间
     */
    @Schema(description="修改时间",name="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8*")
    private Date updateTime;
}