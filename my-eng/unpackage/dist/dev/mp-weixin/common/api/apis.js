"use strict";
const common_vendor = require("../vendor.js");
const common_utils_request = require("../utils/request.js");
function apiGetWechatMiniSessionKey(data = {}) {
  common_vendor.index.__f__("log", "at common/api/apis.js:5", "查询数据->", data);
  return common_utils_request.request({
    url: "/user/getWechatMiniSessionKey",
    data,
    method: "post"
  });
}
function apiGetEngMain(data = {}) {
  return common_utils_request.request({
    url: "/engMain/getIndexEngMain",
    data,
    method: "post"
  });
}
function apiOperData(data = [{}]) {
  return common_utils_request.request({
    url: "/engMain/operData",
    data,
    method: "post"
  });
}
function apiGetEngWordByDetail(data = {}) {
  return common_utils_request.request({
    url: "/engMain/getEngWordByDetail",
    data,
    method: "post"
  });
}
function apiGetIndexEngNextMain(data = {}) {
  return common_utils_request.request({
    url: "/engMain/getIndexEngNextMain",
    data,
    method: "post"
  });
}
function apiGetIndexEngUpMain(data = {}) {
  return common_utils_request.request({
    url: "/engMain/getIndexEngUpMain",
    data,
    method: "post"
  });
}
function apiSearchEngMain(data = {}) {
  return common_utils_request.request({
    url: "/engMain/getEngMain",
    data,
    method: "post"
  });
}
function apiSearchChangeEngMain(data = {}) {
  return common_utils_request.request({
    url: "/engMain/searchChangeEngMain",
    data,
    method: "post"
  });
}
function apiGetMyCollect(data = {}) {
  return common_utils_request.request({
    url: "/engMain/getMyCollect",
    data,
    method: "post"
  });
}
function apiDelMyCollect(data = {}) {
  return common_utils_request.request({
    url: "/engMain/delMyCollect",
    data,
    method: "post"
  });
}
exports.apiDelMyCollect = apiDelMyCollect;
exports.apiGetEngMain = apiGetEngMain;
exports.apiGetEngWordByDetail = apiGetEngWordByDetail;
exports.apiGetIndexEngNextMain = apiGetIndexEngNextMain;
exports.apiGetIndexEngUpMain = apiGetIndexEngUpMain;
exports.apiGetMyCollect = apiGetMyCollect;
exports.apiGetWechatMiniSessionKey = apiGetWechatMiniSessionKey;
exports.apiOperData = apiOperData;
exports.apiSearchChangeEngMain = apiSearchChangeEngMain;
exports.apiSearchEngMain = apiSearchEngMain;
//# sourceMappingURL=../../../.sourcemap/mp-weixin/common/api/apis.js.map
