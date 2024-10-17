package com.example.eng.mapper.eng;

import com.example.eng.entity.eng.EngSentenceWord;
import com.example.eng.entity.eng.io.EngSentenceWordIO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Interface
* EngSentenceWordMapper
*
* @author 系统
* @date Create Time: Wed Oct 16 19:39:59 CST 2024
*/
@Mapper()
public interface EngSentenceWordMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(EngSentenceWord record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(EngSentenceWord record);

    /**
     * 根据主键id查询
     * @param id
     */
    EngSentenceWord selectByPrimaryKey(String id);

    List<EngSentenceWord> selectByIO(EngSentenceWordIO io);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(EngSentenceWord record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(EngSentenceWord record);
}