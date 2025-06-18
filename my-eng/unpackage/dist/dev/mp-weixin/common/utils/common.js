"use strict";
const common_vendor = require("../vendor.js");
function getSystemWechatUser() {
  return common_vendor.index.getStorageSync("myUser");
}
function getSystemWechatUserForward() {
  let wechatUser = getSystemWechatUser();
  if (wechatUser == null || wechatUser.openid == null) {
    common_vendor.index.showToast({
      title: "跳转登录中..."
    });
    common_vendor.index.reLaunch({
      url: "/pages/my/my"
    });
  }
}
function repstr(name) {
  return name.replace(",", "").replace(".", "").replace("，", "").replace("。", "").replace("！", "").replace("!", "").replace("、", "").replace("  ", " ");
}
function highlightedText(originalText, compareText) {
  const tokenize = (str) => {
    return str.split(/(\s+|'\w+|\w+'\w+|\w+|[^\w\s])/).filter((t) => t.trim().length > 0);
  };
  const originalTokens = tokenize(originalText.toLowerCase());
  const compareTokens = tokenize(compareText.toLowerCase());
  const compareOriginalTokens = tokenize(compareText);
  let result = [];
  let originalIndex = 0;
  let compareIndex = 0;
  while (compareIndex < compareTokens.length || originalIndex < originalTokens.length) {
    if (originalIndex >= originalTokens.length) {
      result.push(`<span class="diff-added">${compareOriginalTokens[compareIndex]}</span>`);
      compareIndex++;
      continue;
    }
    if (compareIndex >= compareTokens.length) {
      originalIndex++;
      continue;
    }
    if (compareTokens[compareIndex] === originalTokens[originalIndex]) {
      result.push(compareOriginalTokens[compareIndex]);
      originalIndex++;
      compareIndex++;
    } else {
      if (isTypo(compareTokens[compareIndex], originalTokens[originalIndex])) {
        result.push(`<span style="background-color: #ff0000;font-weight: bold;color: #fff;">${compareOriginalTokens[compareIndex]}</span>`);
        originalIndex++;
        compareIndex++;
      } else if (isAddedContent(compareTokens, originalTokens, compareIndex, originalIndex)) {
        result.push(`<span style="background-color: #ffeb3b;font-weight: bold;">${compareOriginalTokens[compareIndex]}</span>`);
        compareIndex++;
      } else {
        result.push(`<span class="diff-modified">${compareOriginalTokens[compareIndex]}</span>`);
        originalIndex++;
        compareIndex++;
      }
    }
  }
  return result.join(" ");
}
function isTypo(word1, word2) {
  if (Math.abs(word1.length - word2.length) > 1)
    return false;
  let diff = 0;
  const maxLength = Math.max(word1.length, word2.length);
  for (let i = 0; i < maxLength; i++) {
    if (word1[i] !== word2[i])
      diff++;
    if (diff > 1)
      return false;
  }
  return true;
}
function isAddedContent(compareTokens, originalTokens, compareIndex, originalIndex) {
  for (let i = 0; i < 3; i++) {
    if (compareIndex + i < compareTokens.length && originalIndex + i < originalTokens.length && compareTokens[compareIndex + i] === originalTokens[originalIndex + i]) {
      return false;
    }
  }
  return true;
}
exports.getSystemWechatUser = getSystemWechatUser;
exports.getSystemWechatUserForward = getSystemWechatUserForward;
exports.highlightedText = highlightedText;
exports.repstr = repstr;
//# sourceMappingURL=../../../.sourcemap/mp-weixin/common/utils/common.js.map
