package com.example.eng.mapper.eng;

import com.example.eng.entity.eng.EngSentenceDetail;
import org.apache.ibatis.annotations.Mapper;

/**
* Interface
* EngSentenceDetailMapper
*
* @author 系统
* @date Create Time: Sat Oct 12 17:07:11 CST 2024
*/
@Mapper()
public interface EngSentenceDetailMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(EngSentenceDetail record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(EngSentenceDetail record);

    /**
     * 根据主键id查询
     * @param id
     */
    EngSentenceDetail selectByPrimaryKey(String id);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(EngSentenceDetail record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(EngSentenceDetail record);
}