"use strict";
const common_vendor = require("../../common/vendor.js");
const common_utils_request = require("../../common/utils/request.js");
if (!Array) {
  const _easycom_time_count_down2 = common_vendor.resolveComponent("time-count-down");
  const _easycom_l_drag2 = common_vendor.resolveComponent("l-drag");
  const _easycom_uni_list2 = common_vendor.resolveComponent("uni-list");
  const _component_uni_section = common_vendor.resolveComponent("uni-section");
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  const _easycom_custom_set_num2 = common_vendor.resolveComponent("custom-set-num");
  const _easycom_uni_list_item2 = common_vendor.resolveComponent("uni-list-item");
  (_easycom_time_count_down2 + _easycom_l_drag2 + _easycom_uni_list2 + _component_uni_section + _easycom_uni_popup2 + _easycom_custom_set_num2 + _easycom_uni_list_item2)();
}
const _easycom_time_count_down = () => "../../components/time-count-down/time-count-down.js";
const _easycom_l_drag = () => "../../uni_modules/lime-drag/components/l-drag/l-drag.js";
const _easycom_uni_list = () => "../../uni_modules/uni-list/components/uni-list/uni-list.js";
const _easycom_uni_popup = () => "../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
const _easycom_custom_set_num = () => "../../components/custom-set-num/custom-set-num.js";
const _easycom_uni_list_item = () => "../../uni_modules/uni-list/components/uni-list-item/uni-list-item.js";
if (!Math) {
  (_easycom_time_count_down + _easycom_l_drag + _easycom_uni_list + _easycom_uni_popup + _easycom_custom_set_num + _easycom_uni_list_item)();
}
const _sfc_main = {
  __name: "num-Huarongdao",
  setup(__props) {
    let storeKey = "myChangeItem";
    let storeGameTypeKey = "myChangeGameType";
    let storeMyGameNumKey = "myGameNum";
    const numPopup = common_vendor.ref(null);
    const gameTypePopup = common_vendor.ref(null);
    common_vendor.ref(null);
    const gridColumn = common_vendor.ref(null);
    const dataList = common_vendor.ref([]);
    const changeNumText = common_vendor.ref("");
    const changeGameTypeText = common_vendor.ref("");
    const countdownNum = common_vendor.ref(null);
    const myTimeCountDown = common_vendor.ref(null);
    const gridDisabled = common_vendor.ref(false);
    const gridHeight = common_vendor.ref(150);
    const resultText = common_vendor.ref("");
    let currentGameType = "";
    const changeGridMap = common_vendor.ref(null);
    const changeGameTypeMap = common_vendor.ref(null);
    const currentItemGridNums = common_vendor.ref([]);
    const myTimeDown = common_vendor.ref(false);
    const customMumPopup = common_vendor.ref(null);
    let lastDragSuccessNum = 0;
    const changeGridList = common_vendor.ref([
      { "id": "1", "beginNum": "1", "typeKey": "general", "itemCls": "v_item", "fontSize": "100rpx", "gameTime": "15", "colNum": "3", "numSum": "9", "title": "3 ✖️ 3", "note": "1-9的数字", "rightText": "" },
      { "id": "2", "beginNum": "1", "typeKey": "general", "itemCls": "v_item", "fontSize": "80rpx", "gameTime": "60", "colNum": "4", "numSum": "16", "title": "4 ✖️ 4", "note": "1-16的数字", "rightText": "" },
      { "id": "3", "beginNum": "1", "typeKey": "general", "itemCls": "v_item", "fontSize": "70rpx", "gameTime": "100", "colNum": "5", "numSum": "25", "title": "5 ✖️ 5", "note": "1-25的数字", "rightText": "" },
      { "id": "4", "beginNum": "1", "typeKey": "general", "itemCls": "v_item", "fontSize": "60rpx", "gameTime": "300", "colNum": "6", "numSum": "36", "title": "6 ✖️ 6", "note": "1-36的数字", "rightText": "" },
      { "id": "5", "beginNum": "1", "typeKey": "general", "itemCls": "v_item", "fontSize": "50rpx", "gameTime": "500", "colNum": "7", "numSum": "49", "title": "7 ✖️ 7", "note": "1-49的数字", "rightText": "" },
      { "id": "6", "beginNum": "1", "typeKey": "general", "itemCls": "v_item", "fontSize": "30rpx", "gameTime": "800", "colNum": "8", "numSum": "64", "title": "8 ✖️ 8", "note": "1-64的数字", "rightText": "" },
      { "id": "7", "beginNum": "1", "typeKey": "general", "itemCls": "v_item", "fontSize": "30rpx", "gameTime": "1000", "colNum": "9", "numSum": "81", "title": "9 ✖️ 9", "note": "1-81的数字", "rightText": "" },
      { "id": "8", "beginNum": "1", "typeKey": "general", "itemCls": "v_item", "fontSize": "30rpx", "gameTime": "1800", "colNum": "10", "numSum": "100", "title": "10 ✖️ 10", "note": "1-100的数字", "rightText": "" },
      { "id": "9", "beginNum": "1", "typeKey": "custom", "itemCls": "v_item", "fontSize": "30rpx", "gameTime": "600", "colNum": "10", "numSum": "100", "title": "自定义", "note": "自定义时间和数字区间", "rightText": "" }
    ]);
    const changeGameList = common_vendor.ref([
      { "id": "a1", "title": "拖拽排序", "note": "拖拽小方块，按照从小到大的顺序排列", "rightText": "" },
      { "id": "a2", "title": "顺序点击", "note": "按照从小到大的顺序点击小方块", "rightText": "" }
    ]);
    function rmoveMyGridNum(param) {
      common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:153", "删除开始");
      changeGridList.value = changeGridList.value.filter((item) => item.id !== param.id);
      let myGameNumList = common_vendor.index.getStorageSync(storeMyGameNumKey);
      if (myGameNumList != null && myGameNumList.length > 0) {
        myGameNumList = myGameNumList.filter((item) => item.id !== param.id);
        common_vendor.index.setStorageSync(storeMyGameNumKey, myGameNumList);
      }
      if (common_vendor.index.getStorageSync(storeKey) == param.id) {
        common_vendor.index.removeStorageSync(storeKey);
      }
    }
    function onFunCustomNumFinish(e) {
      common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:166", "你输入的内容->", e);
      customMumPopup.value.close();
      let myGameNumList = common_vendor.index.getStorageSync(storeMyGameNumKey) || [];
      myGameNumList.unshift(e);
      common_vendor.index.setStorageSync(storeMyGameNumKey, myGameNumList);
      changeGridList.value.unshift(e);
      common_vendor.index.setStorageSync(storeKey, e.id);
      init();
    }
    function changeGridType(id) {
      clickNumList = common_vendor.ref([]);
      myTimeDown.value = false;
      resultText.value = "";
      lastDragSuccessNum = 0;
      common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:185", "id->", id);
      let item = changeGridMap.value.get(id);
      let typeKey = item.typeKey;
      if (typeKey == "custom") {
        customMumPopup.value.open();
        return;
      }
      common_vendor.index.setStorageSync(storeKey, id);
      if (item == null) {
        return;
      }
      let currentGameItem = changeGameTypeMap.value.get(currentGameType);
      if (currentGameItem == null) {
        return;
      }
      if (numPopup != null && numPopup.value != null) {
        numPopup.value.close();
      }
      if (gameTypePopup != null && gameTypePopup.value != null) {
        gameTypePopup.value.close();
      }
      gridColumn.value = item.colNum;
      let endNum = item.endNum || item.numSum;
      changeNumText.value = item.title;
      countdownNum.value = item.gameTime;
      let beginNum = item.beginNum;
      changeGameTypeText.value = currentGameItem.title;
      let fontSize = item.fontSize;
      common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:219", "得到的时间->", item.gameTime);
      calcGridItemWidth();
      let list = common_vendor.ref([]);
      for (var i = beginNum; i <= endNum; i++) {
        let listItem = { "content": i, "itemCls": "v_item", "fontSize": fontSize };
        list.value.push(listItem);
      }
      randomList(list);
      myTimeCountDown.value.start(countdownNum.value);
      if (currentGameType == "a1") {
        gridDisabled.value = false;
      } else {
        gridDisabled.value = true;
      }
    }
    function changeGameType(item) {
      if (gameTypePopup != null && gameTypePopup.value != null) {
        gameTypePopup.value.close();
      }
      if (currentGameType == item.id) {
        return;
      }
      item.typeKey;
      currentGameType = item.id;
      common_vendor.index.setStorageSync(storeGameTypeKey, currentGameType);
      init();
    }
    function randomList(list) {
      currentItemGridNums.value = list.value;
      let startNum = list.value[0].content;
      do {
        list.value = [...list.value].sort(() => Math.random() - 0.5);
      } while (list.value[0].content == startNum);
      dataList.value = list.value;
    }
    function calcGridItemWidth() {
      const SYSTEM_INFO = common_vendor.index.getSystemInfoSync();
      let itemHeight = (SYSTEM_INFO.windowWidth - 100) / gridColumn.value;
      gridHeight.value = itemHeight;
    }
    common_vendor.onMounted(() => {
      setTimeout(() => {
        let myGameNumList = common_vendor.index.getStorageSync(storeMyGameNumKey);
        if (myGameNumList != null && myGameNumList.length > 0) {
          changeGridList.value = [...myGameNumList, ...changeGridList.value];
        }
        init();
      }, 500);
    });
    function init() {
      changeGridMap.value = new Map(changeGridList.value.map((item2) => [item2.id, item2]));
      changeGameTypeMap.value = new Map(changeGameList.value.map((item2) => [item2.id, item2]));
      let item = common_vendor.index.getStorageSync(storeKey) || changeGridList.value[0].id;
      if (changeGridMap.value.get(item) == null) {
        item = changeGridList.value[0].id;
      }
      currentGameType = common_vendor.index.getStorageSync(storeGameTypeKey) || changeGameList.value[0].id;
      changeGridType(item);
    }
    function onTimeFinish() {
      common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:299", "时间到了");
      myTimeDown.value = true;
      gridDisabled.value = true;
      if (currentGameType == "a1") {
        calcDragSuccess(true);
      } else {
        calcClickSuccess();
      }
    }
    const newList = common_vendor.ref([]);
    function calcDragSuccess(isTimeFinish) {
      let flag = true;
      let successNum = 0;
      let failureNum = 0;
      for (var i = 0; i < newList.value.length; i++) {
        let item = newList.value[i];
        let yesItem = currentItemGridNums.value[i];
        if (item.content.content == yesItem.content) {
          if (flag) {
            item.content.itemCls = "v_item_success";
            successNum++;
          }
        } else {
          flag = false;
          item.content.itemCls = "v_item";
        }
      }
      if (successNum > lastDragSuccessNum) {
        common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:329", "应该播放成功！");
        common_utils_request.playItem_successVoice();
      } else {
        common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:332", "应该播放失败！");
        common_utils_request.playItem_failVoice();
      }
      lastDragSuccessNum = successNum;
      if (flag && newList.value.length != 0) {
        success();
      } else {
        failureNum = currentItemGridNums.value.length - successNum;
        if (isTimeFinish) {
          failure(successNum, failureNum);
        }
      }
    }
    function calcClickSuccess() {
      if (clickNumList.value.length != currentItemGridNums.value.length) {
        let successNum = clickNumList.value.length;
        let failureNum = currentItemGridNums.value.length - successNum;
        failure(successNum, failureNum);
      }
    }
    function change(v) {
      newList.value = v;
      calcDragSuccess(false);
    }
    let clickNumList = common_vendor.ref([]);
    function onNumClick(content) {
      if (currentGameType != "a2") {
        return;
      }
      if (myTimeDown.value) {
        return;
      }
      if (content.itemCls == "v_item_success") {
        return;
      }
      let num = content.content;
      clickNumList.value = [.../* @__PURE__ */ new Set([...clickNumList.value, num])];
      let index = clickNumList.value.length - 1;
      let rightItemNum = currentItemGridNums.value[index].content;
      let clickLastItemNum = num;
      if (clickLastItemNum != rightItemNum) {
        common_utils_request.playItem_failVoice();
        common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:384", "你点错了->");
        clickNumList.value.pop();
        content.itemCls = "v_item_faild";
        setTimeout(() => {
          content.itemCls = "v_item";
        }, 1e3);
      } else {
        common_utils_request.playItem_successVoice();
        content.itemCls = "v_item_success";
      }
      if (clickNumList.value.length == currentItemGridNums.value.length) {
        success();
        return;
      }
      common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:400", "你手贱吗->", clickNumList.value);
    }
    function success() {
      let expendTime = myTimeCountDown.value.getExpendTime();
      resultText.value = "恭喜你，花了" + expendTime + "，挑战成功";
      common_utils_request.playAll_successVoice();
      myTimeCountDown.value.stop();
      gridDisabled.value = true;
    }
    function failure(successNum, failureNum) {
      common_utils_request.playAll_failVoice();
      resultText.value = "时间到了,完成" + successNum + "个,还剩" + failureNum + "个,再接再厉";
      myTimeCountDown.value.stop();
      gridDisabled.value = true;
    }
    function funChangeNum() {
      numPopup.value.open();
    }
    function funChangeGameType() {
      gameTypePopup.value.open();
    }
    function refreshChangeNum() {
      common_vendor.index.showModal({
        title: "提示",
        content: "确定重新开始吗？",
        success: function(res) {
          if (res.confirm) {
            init();
          } else if (res.cancel) {
            common_vendor.index.__f__("log", "at pages/num-Huarongdao/num-Huarongdao.vue:449", "用户点击取消");
          }
        }
      });
    }
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(refreshChangeNum),
        b: common_vendor.t(changeGameTypeText.value),
        c: common_vendor.o(funChangeGameType),
        d: common_vendor.t(changeNumText.value),
        e: common_vendor.o(funChangeNum),
        f: common_vendor.sr(myTimeCountDown, "e0385f5d-0", {
          "k": "myTimeCountDown"
        }),
        g: common_vendor.o(onTimeFinish),
        h: common_vendor.p({
          seconds: countdownNum.value,
          ["auto-start"]: true
        }),
        i: common_vendor.w(({
          active,
          content
        }, s0, i0) => {
          return {
            a: common_vendor.t(content.content),
            b: active ? 1 : "",
            c: common_vendor.o(($event) => onNumClick(content)),
            d: common_vendor.n(content.itemCls),
            e: content.fontSize,
            f: i0,
            g: s0
          };
        }, {
          name: "grid",
          path: "i",
          vueId: "e0385f5d-1"
        }),
        j: common_vendor.o(change),
        k: common_vendor.p({
          list: dataList.value,
          column: gridColumn.value,
          gridHeight: gridHeight.value,
          disabled: gridDisabled.value
        }),
        l: common_vendor.t(resultText.value),
        m: common_vendor.f(changeGridList.value, (item, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(item.title),
            b: common_vendor.t(item.note),
            c: item.typeKey == "myCustom"
          }, item.typeKey == "myCustom" ? {
            d: common_vendor.o(($event) => rmoveMyGridNum(item), item.id)
          } : {}, {
            e: common_vendor.o(($event) => changeGridType(item.id), item.id),
            f: item.id,
            g: "e0385f5d-4-" + i0 + ",e0385f5d-3"
          });
        }),
        n: common_vendor.p({
          title: "请选择数字类型",
          type: "line"
        }),
        o: common_vendor.sr(numPopup, "e0385f5d-2", {
          "k": "numPopup"
        }),
        p: common_vendor.p({
          ["is-mask-click"]: true
        }),
        q: common_vendor.o(onFunCustomNumFinish),
        r: common_vendor.sr(customMumPopup, "e0385f5d-5", {
          "k": "customMumPopup"
        }),
        s: common_vendor.p({
          ["is-mask-click"]: true
        }),
        t: common_vendor.f(changeGameList.value, (item, k0, i0) => {
          return {
            a: "e0385f5d-10-" + i0 + "," + ("e0385f5d-9-" + i0),
            b: common_vendor.p({
              title: item.title,
              showArrow: true,
              note: item.note,
              rightText: item.rightText
            }),
            c: common_vendor.o(($event) => changeGameType(item), item.id),
            d: item.id,
            e: "e0385f5d-9-" + i0 + ",e0385f5d-8"
          };
        }),
        v: common_vendor.p({
          title: "请选择游戏类型",
          type: "line"
        }),
        w: common_vendor.sr(gameTypePopup, "e0385f5d-7", {
          "k": "gameTypePopup"
        }),
        x: common_vendor.p({
          ["is-mask-click"]: true
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-e0385f5d"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/num-Huarongdao/num-Huarongdao.js.map
