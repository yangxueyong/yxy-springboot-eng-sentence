"use strict";
const common_vendor = require("./common/vendor.js");
if (!Array) {
  const _easycom_uni_search_bar2 = common_vendor.resolveComponent("uni-search-bar");
  const _easycom_uni_list_item2 = common_vendor.resolveComponent("uni-list-item");
  const _easycom_uni_list2 = common_vendor.resolveComponent("uni-list");
  const _easycom_uni_drawer2 = common_vendor.resolveComponent("uni-drawer");
  const _component_uni_section = common_vendor.resolveComponent("uni-section");
  (_easycom_uni_search_bar2 + _easycom_uni_list_item2 + _easycom_uni_list2 + _easycom_uni_drawer2 + _component_uni_section)();
}
const _easycom_uni_search_bar = () => "./uni_modules/uni-search-bar/components/uni-search-bar/uni-search-bar.js";
const _easycom_uni_list_item = () => "./uni_modules/uni-list/components/uni-list-item/uni-list-item.js";
const _easycom_uni_list = () => "./uni_modules/uni-list/components/uni-list/uni-list.js";
const _easycom_uni_drawer = () => "./uni_modules/uni-drawer/components/uni-drawer/uni-drawer.js";
if (!Math) {
  (_easycom_uni_search_bar + _easycom_uni_list_item + _easycom_uni_list + _easycom_uni_drawer)();
}
const _sfc_main = {
  __name: "search-bar",
  emits: ["funSearchData", "funSearchChangeItem"],
  setup(__props, { expose: __expose, emit: __emit }) {
    const emit = __emit;
    const showLeft = common_vendor.ref(null);
    const showLeftWidth = common_vendor.ref(null);
    const searchResDatas = common_vendor.ref(null);
    function open() {
      showLeft.value.open();
    }
    function close() {
      showLeft.value.close();
    }
    function loadData(res) {
      common_vendor.index.__f__("log", "at components/search-bar/search-bar.vue:42", "组件得到的数据->", res);
      searchResDatas.value = res;
    }
    __expose({
      open,
      close,
      loadData
    });
    function search(e) {
      emit("funSearchData", e.value);
    }
    function changeItem(item) {
      common_vendor.index.__f__("log", "at components/search-bar/search-bar.vue:57", "正在选择其中一项", item.id);
      emit("funSearchChangeItem", item.id);
    }
    function getSystemWith() {
      common_vendor.index.getSystemInfo({
        success: (res) => {
          common_vendor.index.__f__("log", "at components/search-bar/search-bar.vue:64", "获取到的系统信息", res);
          showLeftWidth.value = res.windowWidth - 50;
        }
      });
    }
    getSystemWith();
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.o(search),
        b: common_vendor.p({
          radius: "5",
          placeholder: "自动显示隐藏",
          clearButton: "auto",
          cancelButton: "none"
        }),
        c: searchResDatas.value
      }, searchResDatas.value ? {
        d: common_vendor.f(searchResDatas.value, (item, k0, i0) => {
          return {
            a: "57811243-4-" + i0 + ",57811243-3",
            b: common_vendor.p({
              showArrow: true,
              title: item.enName
            }),
            c: common_vendor.o(($event) => changeItem(item), item.id),
            d: item.id
          };
        })
      } : {}, {
        e: common_vendor.sr(showLeft, "57811243-1,57811243-0", {
          "k": "showLeft"
        }),
        f: common_vendor.p({
          mode: "left",
          width: showLeftWidth.value
        }),
        g: common_vendor.p({
          title: "左侧滑出",
          type: "line"
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-57811243"]]);
exports.MiniProgramPage = MiniProgramPage;
//# sourceMappingURL=../.sourcemap/mp-weixin/search-bar.js.map
