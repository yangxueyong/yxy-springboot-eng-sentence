package com.example.eng.entity.user.wechat.io;


import com.example.eng.entity.user.wechat.WechatUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 *   "code": "xx"
 * }
 * @author yxy
 * @date 2025/06/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatUserTokenIO {
    private String appId;
    private String thirdType;
    private String openId;
}
