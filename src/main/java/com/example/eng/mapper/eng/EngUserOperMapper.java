package com.example.eng.mapper.eng;

import com.example.eng.entity.eng.EngUserOper;
import com.example.eng.entity.eng.io.EngUserOperIO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Interface
* EngUserOperMapper
*
* @author 系统
* @date Create Time: Tue Jun 17 14:47:17 CST 2025
*/
@Mapper()
public interface EngUserOperMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    int deleteByIO(EngUserOperIO io);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(EngUserOper record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(EngUserOper record);

    /**
     * 根据主键id查询
     * @param id
     */
    EngUserOper selectByPrimaryKey(Long id);

    List<EngUserOper> selectByIO(EngUserOperIO io);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(EngUserOper record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(EngUserOper record);
}