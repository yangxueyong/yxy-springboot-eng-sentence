package com.example.eng.mapper.eng;

import com.example.eng.entity.eng.EngSentenceDetailWordRel;
import org.apache.ibatis.annotations.Mapper;

/**
* Interface
* EngSentenceDetailWordRelMapper
*
* @author 系统
* @date Create Time: Wed Oct 16 19:39:59 CST 2024
*/
@Mapper()
public interface EngSentenceDetailWordRelMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(EngSentenceDetailWordRel record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(EngSentenceDetailWordRel record);

    /**
     * 根据主键id查询
     * @param id
     */
    EngSentenceDetailWordRel selectByPrimaryKey(String id);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(EngSentenceDetailWordRel record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(EngSentenceDetailWordRel record);
}