package com.example.eng.entity.eng.io;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class EngSentenceMainIO {
    /**
     * 主键
     */
    @Schema(name="id",description="主键")
    private String id;

}