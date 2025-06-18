"use strict";
const common_vendor = require("../../common/vendor.js");
const common_utils_common = require("../../common/utils/common.js");
const common_api_apis = require("../../common/api/apis.js");
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_search_bar2 = common_vendor.resolveComponent("search-bar");
  (_easycom_uni_icons2 + _easycom_search_bar2)();
}
const _easycom_uni_icons = () => "../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
const _easycom_search_bar = () => "../../components/search-bar/search-bar2.js";
if (!Math) {
  (_easycom_uni_icons + _easycom_search_bar)();
}
const _sfc_main = {
  __name: "practice",
  setup(__props) {
    common_utils_common.getSystemWechatUserForward();
    const mySearchBar = common_vendor.ref(null);
    const mainData = common_vendor.ref({});
    const datas = common_vendor.ref([{}]);
    const getEngMain = async (loadType) => {
      common_vendor.index.showLoading({
        title: "加载中..."
      });
      let res = await common_api_apis.apiGetEngMain({
        pageType: "practice",
        loadType
      });
      common_vendor.index.__f__("log", "at pages/practice/practice.vue:83", "获取到的主页数据->", res);
      mainData.value = res.data;
      datas.value = res.data.details;
      common_vendor.index.stopPullDownRefresh();
      common_vendor.index.hideLoading();
    };
    getEngMain("");
    function randomLoad() {
      getEngMain("random");
    }
    const openSearch = async () => {
      mySearchBar.value.open();
    };
    const onFunSearchData = async (e) => {
      let res = await common_api_apis.apiSearchEngMain({
        searchText: e
      });
      mySearchBar.value.loadData(res.data);
    };
    const onFunSearchChangeItem = async (e) => {
      common_vendor.index.__f__("log", "at pages/practice/practice.vue:112", "页面得到了->", e);
      mySearchBar.value.close();
      await common_api_apis.apiSearchChangeEngMain({
        id: e,
        pageType: "practice"
      });
      getEngMain();
    };
    const getIndexEngNextMain = async () => {
      let res = await common_api_apis.apiGetIndexEngNextMain({
        "type": "next",
        pageType: "practice"
      });
      common_vendor.index.__f__("log", "at pages/practice/practice.vue:126", "获取到的主页数据->", res);
      mainData.value = res.data;
      datas.value = res.data.details;
      common_vendor.index.stopPullDownRefresh();
      common_vendor.index.hideLoading();
    };
    const getIndexEngUpMain = async () => {
      let res = await common_api_apis.apiGetIndexEngUpMain({
        "type": "up",
        pageType: "practice"
      });
      common_vendor.index.__f__("log", "at pages/practice/practice.vue:138", "获取到的主页数据->", res);
      mainData.value = res.data;
      datas.value = res.data.details;
      common_vendor.index.stopPullDownRefresh();
      common_vendor.index.hideLoading();
    };
    function verify(e) {
      const item = findItemById(e.id);
      const enName = item.enName;
      const myInput = item.myInput;
      if (!myInput) {
        common_vendor.index.showToast({
          title: "请输入答案",
          icon: "error"
        });
        return;
      }
      const newEnName = common_utils_common.repstr(enName);
      const newMyInput = common_utils_common.repstr(myInput);
      const htext = common_utils_common.highlightedText(newEnName, newMyInput);
      item.compareMyInput = "你的答案:" + htext;
      common_vendor.index.__f__("log", "at pages/practice/practice.vue:164", "比较后的数据->", htext);
      item.hide = false;
      if (newEnName == newMyInput) {
        common_vendor.index.showToast({
          title: "恭喜，回答正确"
        });
      } else {
        common_vendor.index.showToast({
          title: "回答错误",
          duration: 800,
          icon: "error"
        });
      }
    }
    function findItemById(id) {
      return datas.value.find((item) => item.id === id);
    }
    function eye(e) {
      e.hide = !e.hide;
    }
    return (_ctx, _cache) => {
      return {
        a: common_vendor.p({
          type: "refreshempty",
          size: "20"
        }),
        b: common_vendor.o(randomLoad),
        c: common_vendor.p({
          type: "back",
          size: "20"
        }),
        d: common_vendor.o(getIndexEngUpMain),
        e: common_vendor.p({
          type: "forward",
          size: "20"
        }),
        f: common_vendor.o(getIndexEngNextMain),
        g: common_vendor.p({
          type: "search",
          size: "20"
        }),
        h: common_vendor.o(openSearch),
        i: common_vendor.t(mainData.value.enName),
        j: common_vendor.f(datas.value, (item, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(item.znName),
            b: item.myInput,
            c: common_vendor.o(($event) => item.myInput = $event.detail.value, item.id),
            d: common_vendor.o(($event) => verify(item), item.id),
            e: "338bd53f-4-" + i0,
            f: !item.hide
          }, !item.hide ? {
            g: common_vendor.t(item.enName)
          } : {}, {
            h: item.hide
          }, item.hide ? {} : {}, {
            i: item.compareMyInput,
            j: common_vendor.o(($event) => eye(item), item.id),
            k: "338bd53f-5-" + i0,
            l: common_vendor.p({
              size: "25",
              type: item.hide ? "eye-slash" : "eye"
            }),
            m: item.id
          });
        }),
        k: common_vendor.p({
          type: "auth",
          size: "25"
        }),
        l: common_vendor.sr(mySearchBar, "338bd53f-6", {
          "k": "mySearchBar"
        }),
        m: common_vendor.o(onFunSearchData),
        n: common_vendor.o(onFunSearchChangeItem)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-338bd53f"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/practice/practice.js.map
