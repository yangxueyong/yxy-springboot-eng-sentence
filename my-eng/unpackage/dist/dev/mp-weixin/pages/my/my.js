"use strict";
const common_vendor = require("../../common/vendor.js");
const common_api_apis = require("../../common/api/apis.js");
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  _easycom_uni_icons2();
}
const _easycom_uni_icons = () => "../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
if (!Math) {
  _easycom_uni_icons();
}
const _sfc_main = {
  __name: "my",
  setup(__props) {
    const userinfo = common_vendor.ref(common_vendor.index.getStorageSync("myUser") || { "nickName": "未登录", "avatarUrl": "/static/images/user/noLogin.png" });
    function userLogin(type, getPhoneRes) {
      common_vendor.index.login({
        provider: "weixin",
        success: function(loginRes) {
          common_vendor.index.getUserInfo({
            provider: "weixin",
            success: function(info) {
              userinfo.value = info.userInfo;
              common_vendor.index.setStorageSync("myUser", userinfo.value);
              getUserOpenId();
            }
          });
        },
        fail: function(err) {
        }
      });
    }
    function getUserOpenId() {
      common_vendor.index.login({
        "provider": "weixin",
        "onlyAuthorize": true,
        // 微信登录仅请求授权认证
        success: function(event) {
          const { code } = event;
          getWechatMiniSessionKey(code);
        },
        fail: function(err) {
        }
      });
    }
    const getWechatMiniSessionKey = async (code) => {
      let res = await common_api_apis.apiGetWechatMiniSessionKey({
        code,
        wechatUser: common_vendor.index.getStorageSync("myUser")
      });
      let sessionKey = res.data.session_key;
      let openid = res.data.openid;
      common_vendor.index.__f__("log", "at pages/my/my.vue:116", "获取到的sessionKey->", sessionKey);
      common_vendor.index.__f__("log", "at pages/my/my.vue:117", "获取到的openid->", openid);
      userinfo.value.sessionKey = sessionKey;
      userinfo.value.openid = openid;
      common_vendor.index.__f__("log", "at pages/my/my.vue:121", "获取到的userinfo->", userinfo);
      common_vendor.index.setStorageSync("myUser", userinfo.value);
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: userinfo.value
      }, userinfo.value ? {
        b: userinfo.value.avatarUrl,
        c: common_vendor.t(userinfo.value.nickName),
        d: common_vendor.t(userinfo.value.province || userinfo.value.city),
        e: common_vendor.o(($event) => userLogin())
      } : {}, {
        f: common_vendor.p({
          size: "30",
          type: "flag-filled"
        }),
        g: common_vendor.p({
          size: "30",
          type: "forward"
        }),
        h: common_vendor.p({
          size: "30",
          type: "flag-filled"
        }),
        i: common_vendor.p({
          size: "30",
          type: "forward"
        }),
        j: common_vendor.p({
          size: "30",
          type: "flag-filled"
        }),
        k: common_vendor.p({
          size: "30",
          type: "forward"
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-2f1ef635"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/my/my.js.map
