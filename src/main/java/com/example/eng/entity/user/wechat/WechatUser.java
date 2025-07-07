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

    /**
     * 头像的鹅http链接
     */
    private String avatarUrl;

    /**
     * 头像的base64编码
     */
    private String avatarBase64;
    private String city;
    private int gender;
    private String language;
    private String nickName;
    private String province;
}
