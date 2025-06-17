package com.example.eng.entity.eng;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name="com.example.yxy.entity.eng.EngSentenceDetail",description="句型详情表*")
@Data()
public class EngSentenceDetail {
    /**
     * 主键
     */
    @Schema(name="主键"/*,name="id*"*/)
    //@Length(max = 100, message = "主键名长度最长为100*"*/)
    private String id;

    /**
     * 主表id
     */
    @Schema(name="主表id"/*,name="mainId*"*/)
    //@Length(max = 100, message = "主表id名长度最长为100*"*/)
    private String mainId;

    /**
     * 中文描述
     */
    @Schema(name="中文描述"/*,name="znName*"*/)
    //@Length(max = 100, message = "中文描述名长度最长为100*"*/)
    private String znName;

    /**
     * 英文描述
     */
    @Schema(name="英文描述"/*,name="enName*"*/)
    //@Length(max = 100, message = "英文描述名长度最长为100*"*/)
    private String enName;

    /**
     * 排序
     */
    @Schema(name="排序"/*,name="sort*"*/)
    //@Length(max = 100, message = "排序名长度最长为100*"*/)
    private String sort;

    /**
     * 本地英文音频路径
     */
    @Schema(name="本地英文音频路径"/*,name="localAudioPath*"*/)
    @JsonIgnore
    @JSONField(serialize = false)
    //@Length(max = 100, message = "本地英文音频路径名长度最长为100*"*/)
    private String localAudioPath;

    /**
     * web英文音频路径
     */
    @Schema(name="web英文音频路径")
    private String webAudioPath;

    /**
     * available可用 unavailable不可用
     */
    @Schema(name="available可用 unavailable不可用"/*,name="status*"*/)
    //@Length(max = 100, message = "available可用 unavailable不可用名长度最长为100*"*/)
    private String status;

    /**
     * 创建时间
     */
    @Schema(name="创建时间"/*,name="createTime*"*/)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8*")
    private Date createTime;

    /**
     * 修改时间
     */
    @Schema(name="修改时间"/*,name="updateTime*"*/)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8*")
    private Date updateTime;

    public String getWebAudioPath() {
        if(StrUtil.isEmptyIfStr(localAudioPath)){
            return null;
        }
        int i = localAudioPath.lastIndexOf("/");
        System.out.println(localAudioPath + "========> " + i);
        if(i >= 0){
            webAudioPath = "/engMain/files/detail/" + localAudioPath.substring(i + 1);
        }else{
            webAudioPath = "/engMain/files/detail/" + localAudioPath;
        }
        return webAudioPath;
	}
}