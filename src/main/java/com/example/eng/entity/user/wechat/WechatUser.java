package com.example.eng.entity.user.wechat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 *   "openid": "OPENID",
 *   "nickname": NICKNAME,
 *   "sex": 1,
 *   "province":"PROVINCE",
 *   "city":"CITY",
 *   "country":"COUNTRY",
 *   "headimgurl":"https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
 *   "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
 *   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
 * }
 * @author yxy
 * @date 2025/06/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatUser {
    private String avatarUrl;
    private String city;
    private int gender;
    private String language;
    private String nickName;
    private String province;
}
