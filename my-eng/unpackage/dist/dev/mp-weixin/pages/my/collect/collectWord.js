"use strict";
const common_vendor = require("../../../common/vendor.js");
if (!Array) {
  const _easycom_a_trumpet2 = common_vendor.resolveComponent("a-trumpet");
  _easycom_a_trumpet2();
}
const _easycom_a_trumpet = () => "../../../uni_modules/a-trumpet/components/a-trumpet/a-trumpet.js";
if (!Math) {
  _easycom_a_trumpet();
}
const _sfc_main = {
  __name: "collectWord",
  setup(__props) {
    return (_ctx, _cache) => {
      return {
        a: common_vendor.p({
          isPlay: "false"
        }),
        b: common_vendor.p({
          isPlay: true
        }),
        c: common_vendor.p({
          isPlay: _ctx.isplay,
          color: "#1100ff"
        }),
        d: common_vendor.p({
          isPlay: _ctx.isplay,
          size: 50
        }),
        e: common_vendor.p({
          isPlay: _ctx.isplay,
          direction: "right"
        })
      };
    };
  }
};
wx.createPage(_sfc_main);
//# sourceMappingURL=../../../../.sourcemap/mp-weixin/pages/my/collect/collectWord.js.map
