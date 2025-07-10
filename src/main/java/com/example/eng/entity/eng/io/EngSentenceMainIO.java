package com.example.eng.entity.eng.io;

import com.example.eng.entity.base.MyPage;
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
public class EngSentenceMainIO extends MyPage {
    /**
     * 主键
     */
    @Schema(name="id",description="主键")
    private String id;

    /**
     * 排序
     */
    @Schema(name="sort",description="sort")
    private String sort;

    /**
     * 搜索的内容
     */
    @Schema(name="searchText",description="searchText")
    private String searchText;

    /**
     * 类型 学习还是练习
     */
    @Schema(name="pageType",description="pageType")
    private String pageType;

    /**
     * 加载类型 随机
     */
    @Schema(name="loadType",description="loadType")
    private String loadType;

    /**
     * 查询类型
     */
    @Schema(name="ckType",description="ckType")
    private String ckType;

}