"use strict";
const common_vendor = require("../../../common/vendor.js");
const common_utils_common = require("../../../common/utils/common.js");
const common_api_apis = require("../../../common/api/apis.js");
const common_utils_request = require("../../../common/utils/request.js");
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_a_trumpet2 = common_vendor.resolveComponent("a-trumpet");
  const _easycom_uni_load_more2 = common_vendor.resolveComponent("uni-load-more");
  (_easycom_uni_icons2 + _easycom_a_trumpet2 + _easycom_uni_load_more2)();
}
const _easycom_uni_icons = () => "../../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
const _easycom_a_trumpet = () => "../../../uni_modules/a-trumpet/components/a-trumpet/a-trumpet.js";
const _easycom_uni_load_more = () => "../../../uni_modules/uni-load-more/components/uni-load-more/uni-load-more.js";
if (!Math) {
  (_easycom_uni_icons + _easycom_a_trumpet + _easycom_uni_load_more)();
}
const _sfc_main = {
  __name: "collectSentence",
  setup(__props) {
    common_utils_common.getSystemWechatUserForward();
    const playMap = /* @__PURE__ */ new Map();
    const noData = common_vendor.ref(false);
    const queryParams = {
      pageNum: 1,
      pageSize: 10
    };
    common_vendor.onLoad((e) => {
      let { collectType = null, name = null } = e;
      queryParams.collectType = collectType;
      common_vendor.index.setNavigationBarTitle({
        title: name
      });
      getEngMain();
    });
    common_vendor.onPullDownRefresh(() => {
      queryParams.pageNum = 1;
      getEngMain();
    });
    common_vendor.onReachBottom(() => {
      common_vendor.index.__f__("log", "at pages/my/collect/collectSentence.vue:72", "触地了-->");
      if (noData.value)
        return;
      queryParams.pageNum++;
      getEngMain();
    });
    const datas = common_vendor.ref([]);
    const getEngMain = async (code) => {
      let res = await common_api_apis.apiGetMyCollect(queryParams);
      common_vendor.index.__f__("log", "at pages/my/collect/collectSentence.vue:86", "获取到的收藏数据->", res);
      datas.value = [...datas.value, ...res.data];
      if (queryParams.pageSize > res.data.length) {
        noData.value = true;
      }
      common_vendor.index.stopPullDownRefresh();
      common_vendor.index.hideLoading();
    };
    const delMyCollect = async (id) => {
      await common_api_apis.apiDelMyCollect({
        id
      });
      datas.value = datas.value.filter((item) => item.id !== id);
      common_vendor.index.stopPullDownRefresh();
      common_vendor.index.hideLoading();
    };
    function eye(e) {
      e.hide = !e.hide;
    }
    function play(e, type) {
      let eid = type + e.id;
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
      common_vendor.index.__f__("log", "at pages/my/collect/collectSentence.vue:144", "音频地址->", audioUrl);
      const innerAudioContext = common_vendor.index.createInnerAudioContext();
      innerAudioContext.autoplay = true;
      innerAudioContext.src = audioUrl;
      innerAudioContext.onPlay(() => {
        common_vendor.index.__f__("log", "at pages/my/collect/collectSentence.vue:149", "开始播放");
        common_vendor.index.hideLoading();
      });
      innerAudioContext.onError((res) => {
        common_vendor.index.__f__("log", "at pages/my/collect/collectSentence.vue:153", res);
        common_vendor.index.hideLoading();
      });
      innerAudioContext.onEnded((res) => {
        common_vendor.index.__f__("log", "at pages/my/collect/collectSentence.vue:159", "停止播放->", res);
        e.isPlay = false;
        common_vendor.index.hideLoading();
      });
      playMap.set(eid, innerAudioContext);
    }
    function findItemById(id) {
      return datas.value.find((item) => id.includes(item.id));
    }
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: datas.value.length
      }, datas.value.length ? {
        b: common_vendor.f(datas.value, (item, k0, i0) => {
          return {
            a: common_vendor.t(item.createTime),
            b: "b566341c-0-" + i0,
            c: common_vendor.o(($event) => delMyCollect(item.id), item.id),
            d: common_vendor.t(item.znName),
            e: common_vendor.t(item.hide == "1" ? "***" : item.enName),
            f: common_vendor.o(($event) => eye(item), item.id),
            g: "b566341c-1-" + i0,
            h: common_vendor.p({
              type: item.hide == "1" ? "eye-slash" : "eye",
              size: "25"
            }),
            i: "b566341c-2-" + i0,
            j: common_vendor.p({
              isPlay: item.isPlay,
              size: "25",
              color: "#28B389"
            }),
            k: common_vendor.o(($event) => play(item, queryParams.collectType), item.id),
            l: item.id
          };
        }),
        c: common_vendor.p({
          type: "trash",
          size: "25"
        })
      } : {}, {
        d: noData.value || datas.value.length
      }, noData.value || datas.value.length ? {
        e: common_vendor.p({
          status: noData.value ? "noMore" : "loading"
        })
      } : {});
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-b566341c"]]);
_sfc_main.__runtimeHooks = 6;
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../../.sourcemap/mp-weixin/pages/my/collect/collectSentence.js.map
