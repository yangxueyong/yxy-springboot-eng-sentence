package com.example.eng.mapper.eng;

import com.example.eng.entity.user.User;
import com.example.eng.entity.user.wechat.io.WechatUserTokenIO;
import org.apache.ibatis.annotations.Mapper;

/**
* Interface
* UserMapper
*
* @author 系统
* @date Create Time: Sat Oct 12 17:07:11 CST 2024
*/
@Mapper()
public interface UserMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(User record);

    /**
     * 根据主键id查询
     * @param id
     */
    User selectByPrimaryKey(String id);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(User record);

    User selectByOpenId(WechatUserTokenIO io);

}