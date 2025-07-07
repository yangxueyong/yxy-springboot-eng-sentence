"use strict";
const common_vendor = require("../../common/vendor.js");
if (!Array) {
  const _easycom_my_firework2 = common_vendor.resolveComponent("my-firework");
  _easycom_my_firework2();
}
const _easycom_my_firework = () => "../../components/my-firework/my-firework.js";
if (!Math) {
  _easycom_my_firework();
}
const _sfc_main = {
  __name: "know-num",
  setup(__props) {
    return (_ctx, _cache) => {
      return {
        a: common_vendor.sr("myFireWorkComponent", "e74a4e46-0")
      };
    };
  }
};
wx.createPage(_sfc_main);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/know-num/know-num.js.map
