package com.example.eng.mapper.eng;

import com.example.eng.entity.eng.EngSentenceMain;
import org.apache.ibatis.annotations.Mapper;

/**
* Interface
* EngSentenceMainMapper
*
* @author 系统
* @date Create Time: Sat Oct 12 17:07:11 CST 2024
*/
@Mapper()
public interface EngSentenceMainMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(EngSentenceMain record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(EngSentenceMain record);

    /**
     * 根据主键id查询
     * @param id
     */
    EngSentenceMain selectByPrimaryKey(String id);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(EngSentenceMain record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(EngSentenceMain record);
}