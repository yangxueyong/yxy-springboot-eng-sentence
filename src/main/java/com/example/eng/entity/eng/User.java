package com.example.eng.entity.eng;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
//import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户信息表实体类
 *
 * @author 系统
 * @date Create Time: Mon Oct 14 13:55:32 CST 2024
 */
@Schema(name="com.example.yxy.entity.eng.User",description="用户信息表*")
@Data()
public class User {
    /**
     * id
     */
    @Schema(name="id"/*,name="id*"*/)
    private String id;

    /**
     * 三方类型 wx:微信 al:阿里
     */
    @Schema(name="三方类型 wx:微信 al:阿里"/*,name="third_type*"*/)
    private String thirdType;

    /**
     * 用户名
     */
    @Schema(name="open_id"/*,name="open_id*"*/)
    private String openId;

    /**
     * 电话
     */
    @Schema(name="电话"/*,name="phone_no*"*/)
    private String phoneNo;

    /**
     * 名称
     */
    @Schema(name="名称"/*,name="cn_name*"*/)
    private String enName;

    /**
     * 三方头像地址
     */
    @Schema(name="性别 0女 1男"/*,name="三方头像地址*"*/)
    private String thirdHeadImgPath;

    /**
     * 最近学习的mainId
     */
    @Schema(name="最近学习的mainId"/*,name="last_study_main_id*"*/)
    private String lastStudyMainId;
    /**
     * 最近学习的detailId
     */
    @Schema(name="最近学习的detailId"/*,name="last_study_detail_id*"*/)
    private String lastStudyDetailId;
    /**
     * 最近学习的wordId
     */
    @Schema(name="最近学习的wordId"/*,name="last_study_word_id*"*/)
    private String lastStudyWordId;

    /**
     * available可用 unavailable不可用
     */
    @Schema(name="available可用 unavailable不可用"/*,name="status*"*/)
    private String status;

    /**
     * 创建时间
     */
    @Schema(name="创建时间"/*,name="create_time*"*/)
    private String createTime;

    /**
     * 修改时间
     */
    @Schema(name="修改时间"/*,name="update_time*"*/)
    private Date updateTime;

}