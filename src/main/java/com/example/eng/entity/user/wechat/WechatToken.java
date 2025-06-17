package com.example.eng.entity.user.wechat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 *   "access_token":"ACCESS_TOKEN",
 *   "expires_in":7200,
 *   "refresh_token":"REFRESH_TOKEN",
 *   "openid":"OPENID",
 *   "scope":"SCOPE",
 *   "is_snapshotuser": 1,
 *   "unionid": "UNIONID"
 * }
 * @author yxy
 * @date 2025/06/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatToken {
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private int is_snapshotuser;
    private String unionid;
}
