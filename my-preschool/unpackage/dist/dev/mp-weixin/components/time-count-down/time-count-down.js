"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "time-count-down",
  props: {
    seconds: { type: Number, required: true },
    // 传入的总秒数
    autoStart: { type: Boolean, default: true }
    // 是否自动开始
  },
  emits: ["countDownFinish"],
  setup(__props, { expose: __expose, emit: __emit }) {
    const props = __props;
    const emit = __emit;
    let remainingSeconds = common_vendor.ref(props.seconds);
    let expendSeconds = common_vendor.ref(0);
    let timer = null;
    const formattedTime = common_vendor.ref("00:00:00");
    function formatTime(type, remainingSeconds2) {
      const hours = Math.floor(remainingSeconds2.value / 3600);
      const minutes = Math.floor(remainingSeconds2.value % 3600 / 60);
      const seconds = remainingSeconds2.value % 60;
      let formattedTime2 = `${hours.toString().padStart(2, "0")}:${minutes.toString().padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`;
      if ("calcDown" == type) {
        formattedTime.value = formattedTime2;
      }
      return formattedTime2;
    }
    function start(mySeconds) {
      remainingSeconds = common_vendor.ref(mySeconds);
      expendSeconds = common_vendor.ref(0);
      formatTime("calcDown", remainingSeconds);
      if (timer) {
        clearInterval(timer);
      }
      timer = setInterval(() => {
        if (remainingSeconds.value <= 0) {
          clearInterval(timer);
          emit("countDownFinish");
        } else {
          remainingSeconds.value = remainingSeconds.value - 1;
          expendSeconds.value++;
        }
        formatTime("calcDown", remainingSeconds);
      }, 1e3);
    }
    function getExpendTime() {
      common_vendor.index.__f__("log", "at components/time-count-down/time-count-down.vue:59", "正在获取->", expendSeconds);
      return formatTime("calcExpand", expendSeconds);
    }
    const stop = () => {
      if (timer)
        clearInterval(timer);
    };
    const reset = (newSeconds) => {
      remainingSeconds.value = newSeconds ?? props.seconds;
    };
    common_vendor.onMounted(() => {
    });
    common_vendor.onBeforeUnmount(() => {
      stop();
    });
    __expose({ start, stop, reset, getExpendTime });
    return (_ctx, _cache) => {
      return {
        a: common_vendor.t(formattedTime.value)
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1332da4b"]]);
wx.createComponent(Component);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/components/time-count-down/time-count-down.js.map
