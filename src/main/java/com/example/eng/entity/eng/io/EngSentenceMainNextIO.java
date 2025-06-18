package com.example.eng.entity.eng.io;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EngSentenceMainNextIO {
    /**
     * 类型 上一页还是下一页
     */
    @Schema(name="type",description="类型")
    private String type;



    /**
     * 类型 学习还是练习
     */
    @Schema(name="pageType",description="pageType")
    private String pageType;


}