package com.example.eng.entity.eng.vo;

import com.example.eng.entity.eng.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
* UserVo
*
* @author yangxueyong
* @date 2024-10-12 17:07:11
*/
@Schema(description="扩展信息")
@Data()
public class UserVO extends User {

}