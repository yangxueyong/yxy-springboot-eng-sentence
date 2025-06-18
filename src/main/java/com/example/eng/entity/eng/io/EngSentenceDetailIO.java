package com.example.eng.entity.eng.io;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Schema(name="com.example.yxy.entity.eng.EngSentenceDetailIO",description="句型详情表")
@Data()
public class EngSentenceDetailIO {

    /**
     * 主表id
     */
    @Schema(description="主表id"/*,name="mainId*"*/)
    private String mainId;

    /**
     * 详情id
     */
    @Schema(description="详情id"/*,name="mainId*"*/)
    private String detailId;

    /**
     * 用户id
     */
    @Schema(description="用户id"/*,name="mainId*"*/)
    private Long userId;
}