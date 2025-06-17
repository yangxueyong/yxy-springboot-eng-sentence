package com.example.eng.service;

import com.example.eng.entity.eng.EngUserOper;
import com.example.eng.entity.eng.io.EngUserOperIO;

import java.util.List;

/**
* EngUserOperService
*
* @author yangxueyong
* @date 2025-06-17 14:47:17
*/
public interface EngUserOperService {
    List<EngUserOper> selectByIO(EngUserOperIO io);

    void operData(List<EngUserOper> operList);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(EngUserOper record);

    int deleteByIO(EngUserOperIO io);
}
