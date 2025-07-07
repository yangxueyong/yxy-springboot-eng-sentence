"use strict";
const common_vendor = require("../vendor.js");
function showHint(content) {
  common_vendor.index.showModal({
    title: "温馨提示",
    content,
    showCancel: false
  });
}
const getRandomItems = (changeGridList, count) => {
  const result = /* @__PURE__ */ new Set();
  if (!changeGridList.length || count <= 0) {
    return result;
  }
  if (count >= changeGridList.length) {
    count = changeGridList.length;
  }
  const maxIndex = changeGridList.length;
  while (result.size < count) {
    common_vendor.index.__f__("log", "at common/utils/common.js:76", "正在找数据：", count);
    const randomIndex = Math.floor(Math.random() * maxIndex);
    result.add(randomIndex);
  }
  return result;
};
const getRandomItem = (changeGridList, processedSet) => {
  const maxIndex = changeGridList.length;
  let randomIndex = -1;
  do {
    randomIndex = Math.floor(Math.random() * maxIndex);
    common_vendor.index.__f__("log", "at common/utils/common.js:91", "正在随机颜色-->", randomIndex);
  } while (processedSet.has(randomIndex) && processedSet.size < changeGridList.length);
  processedSet.add(randomIndex);
  return randomIndex;
};
function getRandomInRangeExcluding(a, b, c) {
  if (!Number.isInteger(a) || !Number.isInteger(b) || !Number.isInteger(c)) {
    throw new Error("所有参数都必须是整数");
  }
  if (a < 0 || b < 0 || c < 0) {
    throw new Error("所有参数都必须是正整数");
  }
  if (a > b) {
    [a, b] = [b, a];
  }
  if (c < a || c > b) {
    return Math.floor(Math.random() * (b - a + 1)) + a;
  }
  const validNumbersCount = b - a;
  if (validNumbersCount === 0) {
    throw new Error("没有有效的数字可供选择");
  }
  let randomNum;
  do {
    randomNum = Math.floor(Math.random() * (b - a + 1)) + a;
  } while (randomNum === c);
  return randomNum;
}
exports.getRandomInRangeExcluding = getRandomInRangeExcluding;
exports.getRandomItem = getRandomItem;
exports.getRandomItems = getRandomItems;
exports.showHint = showHint;
//# sourceMappingURL=../../../.sourcemap/mp-weixin/common/utils/common.js.map
