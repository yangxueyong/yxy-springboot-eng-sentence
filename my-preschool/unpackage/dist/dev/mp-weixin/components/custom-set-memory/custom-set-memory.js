"use strict";
const common_vendor = require("../../common/vendor.js");
const common_utils_common = require("../../common/utils/common.js");
const _sfc_main = {
  __name: "custom-set-memory",
  emits: ["customNumFinish"],
  setup(__props, { expose: __expose, emit: __emit }) {
    const emit = __emit;
    const iptBeginNumValue = common_vendor.ref("1");
    const iptBeginBlockValue = common_vendor.ref("1");
    const iptLevelSumValue = common_vendor.ref("2");
    const iptWidthValue = common_vendor.ref("2");
    const iptSecondValue = common_vendor.ref("60");
    const showNum = common_vendor.ref(null);
    function setnumv(id) {
      if (id == "a2" || id == "a4") {
        showNum.value = true;
      } else {
        showNum.value = false;
      }
    }
    function onNumFinish() {
      if (showNum.value) {
        if (!iptBeginNumValue.value) {
          common_utils_common.showHint("请输入请输入首关的数字");
          return;
        }
        if (iptBeginNumValue.value <= 0) {
          common_utils_common.showHint("首关的数字不能小于0");
          return;
        }
      }
      if (!iptBeginBlockValue.value) {
        common_utils_common.showHint("请输入首关出现的块数");
        return;
      }
      if (iptBeginBlockValue.value <= 0) {
        common_utils_common.showHint("首关出现的块数不能小于0");
        return;
      }
      if (!iptLevelSumValue.value) {
        common_utils_common.showHint("请输入总关卡");
        return;
      }
      if (iptLevelSumValue.value <= 0) {
        common_utils_common.showHint("总关卡不能小于0");
        return;
      }
      if (!iptWidthValue.value) {
        common_utils_common.showHint("请输入棋盘大小");
        return;
      }
      if (iptWidthValue.value < 2) {
        common_utils_common.showHint("棋盘不能小于2");
        return;
      }
      common_vendor.index.__f__("log", "at components/custom-set-memory/custom-set-memory.vue:89", "iptLevelSumValue.value->", iptLevelSumValue.value);
      common_vendor.index.__f__("log", "at components/custom-set-memory/custom-set-memory.vue:90", "iptBeginBlockValue.value->", iptBeginBlockValue.value);
      let lastLevelBlockNum = parseInt(iptLevelSumValue.value) + parseInt(iptBeginBlockValue.value) - 1;
      let sumBlockNum = iptWidthValue.value * iptWidthValue.value;
      if (lastLevelBlockNum > sumBlockNum) {
        common_utils_common.showHint("棋盘大小为" + iptWidthValue.value + "✖️" + iptWidthValue.value + ",无法装下最后一关的块数" + lastLevelBlockNum + "，请重新调整");
        return;
      }
      let fontSize = "30rpx";
      if (sumBlockNum <= 10) {
        fontSize = "100rpx";
      } else if (sumBlockNum <= 16) {
        fontSize = "80rpx";
      } else if (sumBlockNum <= 25) {
        fontSize = "70rpx";
      } else if (sumBlockNum <= 36) {
        fontSize = "60rpx";
      } else if (sumBlockNum <= 49) {
        fontSize = "50rpx";
      } else if (sumBlockNum <= 64) {
        fontSize = "40rpx";
      }
      let note = "";
      if (showNum.value) {
        note = "首关的数字:" + iptBeginNumValue.value + ",";
      }
      note += "首关的块数:" + iptBeginBlockValue.value + ",总关卡:" + iptLevelSumValue.value + ",棋盘大小:" + iptWidthValue.value;
      var myCustom = {
        id: (/* @__PURE__ */ new Date()).getTime(),
        "levelHideTime": "500",
        "beginBlock": iptBeginBlockValue.value,
        beginNum: iptBeginNumValue.value,
        levelSumNum: iptLevelSumValue.value,
        typeKey: "myCustom",
        "itemCls": "v_item",
        "fontSize": fontSize,
        "gameTime": iptSecondValue.value,
        "colNum": iptWidthValue.value,
        "numSum": sumBlockNum,
        "title": "自定义 " + iptWidthValue.value + " ✖️ " + iptWidthValue.value,
        "note": note
      };
      common_vendor.index.__f__("log", "at components/custom-set-memory/custom-set-memory.vue:137", "完成-->");
      emit("customNumFinish", myCustom);
    }
    __expose({ setnumv });
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: showNum.value
      }, showNum.value ? {
        b: iptBeginNumValue.value,
        c: common_vendor.o(($event) => iptBeginNumValue.value = $event.detail.value)
      } : {}, {
        d: iptBeginBlockValue.value,
        e: common_vendor.o(($event) => iptBeginBlockValue.value = $event.detail.value),
        f: iptLevelSumValue.value,
        g: common_vendor.o(($event) => iptLevelSumValue.value = $event.detail.value),
        h: iptWidthValue.value,
        i: common_vendor.o(($event) => iptWidthValue.value = $event.detail.value),
        j: iptSecondValue.value,
        k: common_vendor.o(($event) => iptSecondValue.value = $event.detail.value),
        l: common_vendor.o(onNumFinish)
      });
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-bf248b4d"]]);
wx.createComponent(Component);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/components/custom-set-memory/custom-set-memory.js.map
