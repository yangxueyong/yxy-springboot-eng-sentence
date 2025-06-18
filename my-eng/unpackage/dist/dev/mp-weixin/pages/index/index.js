"use strict";
const common_vendor = require("../../common/vendor.js");
const common_utils_common = require("../../common/utils/common.js");
const common_api_apis = require("../../common/api/apis.js");
const common_utils_request = require("../../common/utils/request.js");
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_a_trumpet2 = common_vendor.resolveComponent("a-trumpet");
  const _easycom_search_bar2 = common_vendor.resolveComponent("search-bar");
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  (_easycom_uni_icons2 + _easycom_a_trumpet2 + _easycom_search_bar2 + _easycom_uni_popup2)();
}
const _easycom_uni_icons = () => "../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
const _easycom_a_trumpet = () => "../../uni_modules/a-trumpet/components/a-trumpet/a-trumpet.js";
const _easycom_search_bar = () => "../../components/search-bar/search-bar2.js";
const _easycom_uni_popup = () => "../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
if (!Math) {
  (_easycom_uni_icons + _easycom_a_trumpet + _easycom_search_bar + _easycom_uni_popup)();
}
const _sfc_main = {
  __name: "index",
  setup(__props) {
    common_utils_common.getSystemWechatUserForward();
    const mainData = common_vendor.ref({});
    const flagPopup = common_vendor.ref(null);
    common_vendor.ref(null);
    const currentItem = common_vendor.ref(null);
    const playMap = /* @__PURE__ */ new Map();
    const datas = common_vendor.ref([]);
    const wordsDetails = common_vendor.ref([]);
    const mySearchBar = common_vendor.ref(null);
    const getEngMain = async (code) => {
      common_vendor.index.showLoading({
        title: "加载中.."
      });
      let res = await common_api_apis.apiGetEngMain({
        pageType: "study"
      });
      common_vendor.index.__f__("log", "at pages/index/index.vue:113", "获取到的主页数据->", res);
      mainData.value = res.data;
      datas.value = res.data.details;
      common_vendor.index.stopPullDownRefresh();
      common_vendor.index.hideLoading();
    };
    getEngMain();
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
      common_vendor.index.__f__("log", "at pages/index/index.vue:137", "页面得到了->", e);
      mySearchBar.value.close();
      await common_api_apis.apiSearchChangeEngMain({
        id: e,
        pageType: "study"
      });
      getEngMain();
    };
    const getIndexEngNextMain = async () => {
      let res = await common_api_apis.apiGetIndexEngNextMain({
        "type": "next",
        pageType: "study"
      });
      common_vendor.index.__f__("log", "at pages/index/index.vue:151", "获取到的主页数据->", res);
      mainData.value = res.data;
      datas.value = res.data.details;
      common_vendor.index.stopPullDownRefresh();
      common_vendor.index.hideLoading();
    };
    const getIndexEngUpMain = async () => {
      let res = await common_api_apis.apiGetIndexEngUpMain({
        "type": "up",
        pageType: "study"
      });
      common_vendor.index.__f__("log", "at pages/index/index.vue:163", "获取到的主页数据->", res);
      mainData.value = res.data;
      datas.value = res.data.details;
      common_vendor.index.stopPullDownRefresh();
      common_vendor.index.hideLoading();
    };
    common_vendor.onPullDownRefresh(() => {
      getEngMain();
    });
    const getEngWord = async (id) => {
      let res = await common_api_apis.apiGetEngWordByDetail({ "detailId": id });
      wordsDetails.value = res.data;
      common_vendor.index.__f__("log", "at pages/index/index.vue:179", "查询到的单词-》", res);
    };
    function showDetail(e) {
      currentItem.value = e;
      flagPopup.value.open();
      getEngWord(e.id);
    }
    function changeSentence() {
      currentItem.value.flag = !currentItem.value.flag;
    }
    function changeWord(e) {
      e.flag = !e.flag;
    }
    function submitFlag() {
      let opers = [{}];
      wordsDetails.value.forEach((word) => {
        let operType2 = "noflag";
        if (word.flag) {
          operType2 = "flag";
        }
        let oper2 = {
          operType: operType2,
          dataType: "word",
          dataId: word.id
        };
        opers.push(oper2);
      });
      let operType = "noflag";
      if (currentItem.value.flag) {
        operType = "flag";
      }
      let oper = {
        operType,
        dataType: "detail",
        dataId: currentItem.value.id
      };
      opers.push(oper);
      common_api_apis.apiOperData(opers);
      flagPopup.value.close();
    }
    function eye(e) {
      e.hide = !e.hide;
      let operType = "nohide";
      if (e.hide) {
        operType = "hide";
      }
      const detailDataOper = [{
        operType,
        dataType: "detail",
        dataId: e.id
      }];
      common_vendor.index.__f__("log", "at pages/index/index.vue:245", "参数->", detailDataOper);
      common_api_apis.apiOperData(detailDataOper);
    }
    function play(e, type) {
      let eid = type + e.id;
      common_vendor.index.__f__("log", "at pages/index/index.vue:254", "进入到播放区");
      e.isPlay = !e.isPlay;
      if (!e.isPlay) {
        if (playMap != null && playMap.has(eid)) {
          playMap.get(eid).stop();
        }
        return;
      }
      if (playMap != null) {
        playMap.forEach((value, id) => {
          value.stop();
          if (type == "detail") {
            findItemById(id).isPlay = false;
          } else {
            findWordById(id).isPlay = false;
          }
        });
      }
      e.isPlay = true;
      let audioUrl = null;
      if (type == "detail") {
        audioUrl = common_utils_request.getDetailVoicePath(e.webAudioPath);
      } else {
        audioUrl = common_utils_request.getWordVoicePath(e.webAudioPath);
      }
      common_vendor.index.__f__("log", "at pages/index/index.vue:284", "音频地址->", audioUrl);
      const innerAudioContext = common_vendor.index.createInnerAudioContext();
      innerAudioContext.autoplay = true;
      innerAudioContext.src = audioUrl;
      innerAudioContext.onPlay(() => {
        common_vendor.index.__f__("log", "at pages/index/index.vue:289", "开始播放");
        common_vendor.index.hideLoading();
      });
      innerAudioContext.onError((res) => {
        common_vendor.index.__f__("log", "at pages/index/index.vue:293", res);
        common_vendor.index.hideLoading();
      });
      innerAudioContext.onEnded((res) => {
        common_vendor.index.__f__("log", "at pages/index/index.vue:299", "停止播放->", res);
        e.isPlay = false;
        common_vendor.index.hideLoading();
      });
      playMap.set(eid, innerAudioContext);
    }
    function findItemById(id) {
      return datas.value.find((item) => id.includes(item.id));
    }
    function findWordById(id) {
      return wordsDetails.value.find((item) => id.includes(item.id));
    }
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.p({
          type: "back",
          size: "20"
        }),
        b: common_vendor.o(getIndexEngUpMain),
        c: common_vendor.p({
          type: "forward",
          size: "20"
        }),
        d: common_vendor.o(getIndexEngNextMain),
        e: common_vendor.p({
          type: "search",
          size: "20"
        }),
        f: common_vendor.o(openSearch),
        g: common_vendor.t(mainData.value.enName),
        h: common_vendor.f(datas.value, (item, k0, i0) => {
          return {
            a: common_vendor.t(item.znName),
            b: common_vendor.o(($event) => showDetail(item), item.id),
            c: common_vendor.t(item.hide == true ? "***" : item.enName),
            d: "1cf27b2a-3-" + i0,
            e: common_vendor.p({
              type: item.hide == true ? "eye-slash" : "eye",
              size: "25"
            }),
            f: common_vendor.o(($event) => eye(item), item.id),
            g: "1cf27b2a-4-" + i0,
            h: common_vendor.p({
              isPlay: item.isPlay,
              size: "25",
              color: "#28B389"
            }),
            i: common_vendor.o(($event) => play(item, "detail"), item.id),
            j: item.id
          };
        }),
        i: common_vendor.sr(mySearchBar, "1cf27b2a-5", {
          "k": "mySearchBar"
        }),
        j: common_vendor.o(onFunSearchData),
        k: common_vendor.o(onFunSearchChangeItem),
        l: currentItem.value
      }, currentItem.value ? {
        m: common_vendor.t(currentItem.value.enName),
        n: common_vendor.s(currentItem.value.flag == true ? "background-color:#beecd8" : "background-color:#fff"),
        o: common_vendor.o(changeSentence)
      } : {}, {
        p: common_vendor.f(wordsDetails.value, (item, k0, i0) => {
          return {
            a: common_vendor.t(item.enName),
            b: common_vendor.t(item.znName),
            c: "1cf27b2a-7-" + i0 + ",1cf27b2a-6",
            d: common_vendor.p({
              isPlay: item.isPlay,
              size: "25",
              color: "#28B389"
            }),
            e: common_vendor.o(($event) => play(item, "word"), item.id),
            f: item.id,
            g: common_vendor.s(item.flag == true ? "background-color:#beecd8" : "background-color:#fff"),
            h: common_vendor.o(($event) => changeWord(item), item.id)
          };
        }),
        q: common_vendor.o(submitFlag),
        r: common_vendor.sr(flagPopup, "1cf27b2a-6", {
          "k": "flagPopup"
        }),
        s: common_vendor.p({
          ["is-mask-click"]: true
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1cf27b2a"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/index/index.js.map
