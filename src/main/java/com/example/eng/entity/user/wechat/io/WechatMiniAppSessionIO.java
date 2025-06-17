package com.example.eng.entity.user.wechat.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 *   avatarUrl: "https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132"
 * city: ""
 * country: ""
 * gender: 0
 * language: ""
 * nickName: "微信用户"
 * openid: "oAPWN7bEV4wzKB-qZUAwCmt8NxvQ"
 * province: ""
 * sessionKey: "wB9zo3MD/bFX0NzHuvFCaw=="
 * }
 * @author yxy
 * @date 2025/06/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatMiniAppSessionIO {
    private String avatarUrl;
    private String city;
    private int gender;
    private String language;
    private String nickName;
    private String province;

    private String openid;
    private String sessionKey;
}
