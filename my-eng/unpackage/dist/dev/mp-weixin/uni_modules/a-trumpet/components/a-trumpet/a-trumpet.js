"use strict";
const common_vendor = require("../../../../common/vendor.js");
const _sfc_main = {
  emits: [],
  props: {
    isPlay: {
      type: [Boolean],
      default: false
    },
    direction: {
      type: String,
      default: "right"
    },
    size: {
      type: Number,
      default: 24
    },
    color: {
      type: String,
      default: "#222"
    }
  },
  data() {
    return {};
  },
  computed: {
    audioStyel() {
      return {
        transform: `scale(${this.size / 24})`
      };
    },
    boxStyel() {
      const directDic = { right: "0deg", bottom: "90deg", left: "180deg", top: "270deg" };
      const dir = directDic[this.direction || "left"];
      const style = {
        transform: `rotate(${dir})`,
        width: this.size + "px",
        height: this.size + "px"
      };
      return style;
    }
  },
  methods: {}
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: $props.color,
    b: $props.color,
    c: $props.color,
    d: common_vendor.s($options.audioStyel),
    e: $props.isPlay ? 1 : "",
    f: common_vendor.s($options.boxStyel)
  };
}
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-36c09c05"]]);
wx.createComponent(Component);
//# sourceMappingURL=../../../../../.sourcemap/mp-weixin/uni_modules/a-trumpet/components/a-trumpet/a-trumpet.js.map
