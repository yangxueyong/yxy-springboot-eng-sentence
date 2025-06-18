"use strict";
const common_vendor = require("../vendor.js");
const common_utils_common = require("./common.js");
const BASE_URL = "http://172.20.10.10:8080";
function getDetailVoicePath(webPath) {
  let user = common_utils_common.getSystemWechatUser();
  return BASE_URL + webPath + "?openId=" + user.openid + "&thirdType=wechat";
}
function getWordVoicePath(webPath) {
  let user = common_utils_common.getSystemWechatUser();
  return BASE_URL + webPath + "?openId=" + user.openid + "&thirdType=wechat";
}
function request(config = {}) {
  let {
    url,
    data = {},
    method = "GET",
    header = {}
  } = config;
  let user = common_utils_common.getSystemWechatUser();
  url = BASE_URL + url;
  header["openId"] = user.openid;
  header["thirdType"] = "wechat";
  return new Promise((resolve, reject) => {
    common_vendor.index.request({
      url,
      data,
      method,
      header,
      success: (res) => {
        if (res.data.code == "200") {
          resolve(res.data);
        } else if (res.data.code != "200") {
          common_vendor.index.showModal({
            title: "错误提示",
            content: res.data.msg,
            showCancel: false
          });
          reject(res.data);
        } else {
          common_vendor.index.showToast({
            title: res.data.msg,
            icon: "none"
          });
          reject(res.data);
        }
      },
      fail: (err) => {
        reject(err);
      }
    });
  });
}
exports.getDetailVoicePath = getDetailVoicePath;
exports.getWordVoicePath = getWordVoicePath;
exports.request = request;
//# sourceMappingURL=../../../.sourcemap/mp-weixin/common/utils/request.js.map
