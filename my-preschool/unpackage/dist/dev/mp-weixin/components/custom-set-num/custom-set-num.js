"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "custom-set-num",
  emits: ["customNumFinish"],
  setup(__props, { emit: __emit }) {
    const emit = __emit;
    const iptBeginValue = common_vendor.ref(null);
    const iptEndValue = common_vendor.ref(null);
    const iptSecondValue = common_vendor.ref(null);
    function onNumFinish() {
      if (iptBeginValue.value <= 0) {
        common_vendor.index.showModal({
          title: "温馨提示",
          content: "开始的数字不能小于0",
          showCancel: false
        });
        return;
      }
      if (iptEndValue.value <= 0) {
        common_vendor.index.showModal({
          title: "温馨提示",
          content: "结束的数字不能小于0",
          showCancel: false
        });
        return;
      }
      if (iptEndValue.value <= iptBeginValue.value) {
        common_vendor.index.showModal({
          title: "温馨提示",
          content: "结束的数字不能小于或等于开始的数字",
          showCancel: false
        });
        return;
      }
      let cha = iptEndValue.value - iptBeginValue.value;
      if (cha < 4 || cha > 100) {
        common_vendor.index.showModal({
          title: "温馨提示",
          content: "数字相差不能少于4个或者大于100个",
          showCancel: false
        });
        return;
      }
      if (iptSecondValue.value < 5 || iptSecondValue.value > 7200) {
        common_vendor.index.showModal({
          title: "温馨提示",
          content: "游戏时间不能小于5秒或者大于7200秒",
          showCancel: false
        });
        return;
      }
      let fontSize = "30rpx";
      if (cha <= 10) {
        fontSize = "100rpx";
      } else if (cha <= 16) {
        fontSize = "80rpx";
      } else if (cha <= 25) {
        fontSize = "70rpx";
      } else if (cha <= 36) {
        fontSize = "60rpx";
      } else if (cha <= 49) {
        fontSize = "50rpx";
      } else if (cha <= 64) {
        fontSize = "40rpx";
      }
      let { width, height } = calculateLayout(cha + 1);
      var myCustom = {
        id: (/* @__PURE__ */ new Date()).getTime(),
        beginNum: iptBeginValue.value,
        endNum: iptEndValue.value,
        typeKey: "myCustom",
        "itemCls": "v_item",
        "fontSize": fontSize,
        "gameTime": iptSecondValue.value,
        "colNum": width,
        "numSum": iptEndValue.value,
        "title": "自定义 " + width + " ✖️ " + height,
        "note": iptBeginValue.value + "-" + iptEndValue.value + "的数字"
      };
      common_vendor.index.__f__("log", "at components/custom-set-num/custom-set-num.vue:103", "完成-->");
      emit("customNumFinish", myCustom);
    }
    function calculateLayout(num) {
      if (num <= 0)
        return [0, 0];
      let width = Math.ceil(Math.sqrt(num));
      let height = width;
      while (width * height >= num) {
        if ((width - 1) * height >= num) {
          width--;
        } else if (width * (height - 1) >= num) {
          height--;
        } else {
          break;
        }
      }
      while (width * height < num) {
        if (width <= height) {
          width++;
        } else {
          height++;
        }
      }
      return width >= height ? { width, height } : { height, width };
    }
    return (_ctx, _cache) => {
      return {
        a: iptBeginValue.value,
        b: common_vendor.o(($event) => iptBeginValue.value = $event.detail.value),
        c: iptEndValue.value,
        d: common_vendor.o(($event) => iptEndValue.value = $event.detail.value),
        e: iptSecondValue.value,
        f: common_vendor.o(($event) => iptSecondValue.value = $event.detail.value),
        g: common_vendor.o(onNumFinish)
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-186eaf2c"]]);
wx.createComponent(Component);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/components/custom-set-num/custom-set-num.js.map
