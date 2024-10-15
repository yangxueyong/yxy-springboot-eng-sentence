package com.example.eng.entity.eng;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户信息表实体类
 *
 * @author 系统
 * @date Create Time: Mon Oct 14 13:55:32 CST 2024
 */
@ApiModel(value="com.example.yxy.entity.eng.User",description="用户信息表")
@Data()
public class User {
    /**
     * id
     */
    @ApiModelProperty(value="id",name="id")
    private String id;

    /**
     * 三方类型 wx:微信 al:阿里
     */
    @ApiModelProperty(value="三方类型 wx:微信 al:阿里",name="third_type")
    private String thirdType;

    /**
     * 用户名
     */
    @ApiModelProperty(value="open_id",name="open_id")
    private String openId;

    /**
     * 电话
     */
    @ApiModelProperty(value="电话",name="phone_no")
    private String phoneNo;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称",name="cn_name")
    private String enName;

    /**
     * 三方头像地址
     */
    @ApiModelProperty(value="性别 0女 1男",name="三方头像地址")
    private String thirdHeadImgPath;

    /**
     * 最近学习的mainId
     */
    @ApiModelProperty(value="最近学习的mainId",name="last_study_main_id")
    private String lastStudyMainId;
    /**
     * 最近学习的detailId
     */
    @ApiModelProperty(value="最近学习的detailId",name="last_study_detail_id")
    private String lastStudyDetailId;
    /**
     * 最近学习的wordId
     */
    @ApiModelProperty(value="最近学习的wordId",name="last_study_word_id")
    private String lastStudyWordId;

    /**
     * available可用 unavailable不可用
     */
    @ApiModelProperty(value="available可用 unavailable不可用",name="status")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",name="create_time")
    private String createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间",name="update_time")
    private Date updateTime;

}