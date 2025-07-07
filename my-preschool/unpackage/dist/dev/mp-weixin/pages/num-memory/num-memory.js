"use strict";
const common_vendor = require("../../common/vendor.js");
const common_utils_common = require("../../common/utils/common.js");
const common_utils_request = require("../../common/utils/request.js");
const common_utils_firework = require("../../common/utils/firework.js");
if (!Array) {
  const _component_template = common_vendor.resolveComponent("template");
  const _easycom_time_count_down2 = common_vendor.resolveComponent("time-count-down");
  const _easycom_l_drag2 = common_vendor.resolveComponent("l-drag");
  const _easycom_uni_list2 = common_vendor.resolveComponent("uni-list");
  const _component_uni_section = common_vendor.resolveComponent("uni-section");
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  const _easycom_custom_set_memory2 = common_vendor.resolveComponent("custom-set-memory");
  const _easycom_uni_list_item2 = common_vendor.resolveComponent("uni-list-item");
  (_component_template + _easycom_time_count_down2 + _easycom_l_drag2 + _easycom_uni_list2 + _component_uni_section + _easycom_uni_popup2 + _easycom_custom_set_memory2 + _easycom_uni_list_item2)();
}
const _easycom_time_count_down = () => "../../components/time-count-down/time-count-down.js";
const _easycom_l_drag = () => "../../uni_modules/lime-drag/components/l-drag/l-drag.js";
const _easycom_uni_list = () => "../../uni_modules/uni-list/components/uni-list/uni-list.js";
const _easycom_uni_popup = () => "../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
const _easycom_custom_set_memory = () => "../../components/custom-set-memory/custom-set-memory.js";
const _easycom_uni_list_item = () => "../../uni_modules/uni-list/components/uni-list-item/uni-list-item.js";
if (!Math) {
  (_easycom_time_count_down + _easycom_l_drag + _easycom_uni_list + _easycom_uni_popup + _easycom_custom_set_memory + _easycom_uni_list_item)();
}
const _sfc_main = {
  __name: "num-memory",
  setup(__props) {
    common_vendor.ref(null);
    common_vendor.ref(null);
    let storeKey = "myMemoryChangeItem";
    let storeGameTypeKey = "myMemoryChangeGameType";
    let storeMyGameNumKey = "myMemoryGameNum";
    const numPopup = common_vendor.ref(null);
    const gameTypePopup = common_vendor.ref(null);
    common_vendor.ref(null);
    const gridColumn = common_vendor.ref(null);
    const dataList = common_vendor.ref([]);
    const changeNumText = common_vendor.ref("");
    const changeGameTypeText = common_vendor.ref("");
    const countdownNum = common_vendor.ref(null);
    const myTimeCountDown = common_vendor.ref(null);
    const gridDisabled = common_vendor.ref(true);
    const gridHeight = common_vendor.ref(150);
    const resultText = common_vendor.ref("");
    const wantFindText = common_vendor.ref("");
    const wantFindImg = common_vendor.ref(null);
    const findText = common_vendor.ref("");
    let currentGameType = "";
    const changeGridMap = common_vendor.ref(null);
    const changeGameTypeMap = common_vendor.ref(null);
    common_vendor.ref([]);
    const myTimeDown = common_vendor.ref(false);
    const customMumPopup = common_vendor.ref(null);
    const customMumSet = common_vendor.ref(null);
    let currentLevel = 1;
    let usedColorSet = /* @__PURE__ */ new Set();
    let usedAnimalSet = /* @__PURE__ */ new Set();
    let currentGameItem = null;
    let currentGameTypeItem = null;
    const levelText = common_vendor.ref("");
    const changeGridList = common_vendor.ref([
      { "id": "1", "levelHideTime": "500", "beginBlock": "2", "beginNum": "2", "levelSumNum": "5", "typeKey": "general", "itemCls": "v_item", "fontSize": "100rpx", "gameTime": "30", "colNum": "3", "numSum": "9", "title": "3 ✖️ 3", "note": "1-9的数字", "rightText": "" },
      { "id": "2", "levelHideTime": "500", "beginBlock": "3", "beginNum": "1", "levelSumNum": "7", "typeKey": "general", "itemCls": "v_item", "fontSize": "80rpx", "gameTime": "60", "colNum": "4", "numSum": "16", "title": "4 ✖️ 4", "note": "1-16的数字", "rightText": "" },
      { "id": "3", "levelHideTime": "500", "beginBlock": "1", "beginNum": "1", "levelSumNum": "10", "typeKey": "general", "itemCls": "v_item", "fontSize": "70rpx", "gameTime": "100", "colNum": "5", "numSum": "25", "title": "5 ✖️ 5", "note": "1-25的数字", "rightText": "" },
      { "id": "4", "levelHideTime": "500", "beginBlock": "1", "beginNum": "1", "levelSumNum": "15", "typeKey": "general", "itemCls": "v_item", "fontSize": "60rpx", "gameTime": "200", "colNum": "6", "numSum": "36", "title": "6 ✖️ 6", "note": "1-36的数字", "rightText": "" },
      { "id": "5", "levelHideTime": "500", "beginBlock": "1", "beginNum": "1", "levelSumNum": "25", "typeKey": "general", "itemCls": "v_item", "fontSize": "50rpx", "gameTime": "300", "colNum": "7", "numSum": "49", "title": "7 ✖️ 7", "note": "1-49的数字", "rightText": "" },
      { "id": "6", "levelHideTime": "500", "beginBlock": "1", "beginNum": "1", "levelSumNum": "30", "typeKey": "general", "itemCls": "v_item", "fontSize": "30rpx", "gameTime": "400", "colNum": "8", "numSum": "64", "title": "8 ✖️ 8", "note": "1-64的数字", "rightText": "" },
      { "id": "7", "levelHideTime": "500", "beginBlock": "1", "beginNum": "1", "levelSumNum": "40", "typeKey": "general", "itemCls": "v_item", "fontSize": "30rpx", "gameTime": "500", "colNum": "9", "numSum": "81", "title": "9 ✖️ 9", "note": "1-81的数字", "rightText": "" },
      { "id": "8", "levelHideTime": "500", "beginBlock": "1", "beginNum": "1", "levelSumNum": "50", "typeKey": "general", "itemCls": "v_item", "fontSize": "30rpx", "gameTime": "600", "colNum": "10", "numSum": "100", "title": "10 ✖️ 10", "note": "1-100的数字", "rightText": "" },
      { "id": "9", "levelHideTime": "500", "beginBlock": "1", "beginNum": "1", "levelSumNum": "1", "typeKey": "custom", "itemCls": "v_item", "fontSize": "30rpx", "gameTime": "600", "colNum": "10", "numSum": "100", "title": "自定义", "note": "自定义时间和数字区间", "rightText": "" }
    ]);
    const changeGameList = common_vendor.ref([
      { "id": "a1", "title": "记忆颜色", "note": "点击颜色出现的地方", "rightText": "" },
      { "id": "a2", "title": "记忆数字", "note": "点击数字出现的地方", "rightText": "" },
      { "id": "a6", "title": "记忆动物", "note": "点击动物出现的地方", "rightText": "" },
      { "id": "a7", "title": "记忆影子", "note": "点击影子对应动物出现的地方", "rightText": "" },
      { "id": "a3", "title": "找出颜色", "note": "找出提示的颜色", "rightText": "" },
      { "id": "a4", "title": "找出数字", "note": "找出提示的数字", "rightText": "" },
      { "id": "a5", "title": "找出动物", "note": "找出提示的动物", "rightText": "" },
      { "id": "a8", "title": "找出动物的影子", "note": "找出提示的动物影子", "rightText": "" }
    ]);
    const changeColorList = common_vendor.ref([
      { "key": "yellow", "value": "yellow", content: "黄色 yellow" },
      { "key": "red", "value": "red", content: "红色 red" },
      { "key": "brown", "value": "brown", content: "棕色 brown" },
      { "key": "purple", "value": "purple", content: "紫色 purple" },
      { "key": "pink", "value": "pink", content: "粉色 pink" },
      { "key": "green", "value": "green", content: "绿色 green" },
      { "key": "blue", "value": "blue", content: "蓝色 blue" },
      { "key": "white", "value": "white", content: "白色 white" },
      { "key": "black", "value": "black", content: "黑色 black" }
    ]);
    const changeAnimalList = common_vendor.ref([
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/longxia.png", "shadowSrc": "https://www.yangxuexue.xyz/images/longxia (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/22-乌龟.png", "shadowSrc": "https://www.yangxuexue.xyz/images/22-乌龟 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/狗狗出售.png", "shadowSrc": "https://www.yangxuexue.xyz/images/狗狗出售 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通大象.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通大象 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通海豚.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通海豚 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通猴子.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通猴子 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通鸡.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通鸡 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通鹿.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通鹿 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通马.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通马 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通绵羊.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通绵羊 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通牛.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通牛 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通企鹅.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通企鹅 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通蛇.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通蛇 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/卡通鱼.png", "shadowSrc": "https://www.yangxuexue.xyz/images/卡通鱼 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/可爱的卡通鸟.png", "shadowSrc": "https://www.yangxuexue.xyz/images/可爱的卡通鸟 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/老虎.png", "shadowSrc": "https://www.yangxuexue.xyz/images/老虎 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/螃蟹.png", "shadowSrc": "https://www.yangxuexue.xyz/images/螃蟹 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/狮子.png", "shadowSrc": "https://www.yangxuexue.xyz/images/狮子 (1).png" },
      { "key": "a", "imgSrc": "https://www.yangxuexue.xyz/images/鼠.png", "shadowSrc": "https://www.yangxuexue.xyz/images/鼠 (1).png" }
    ]);
    function rmoveMyGridNum(param) {
      common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:260", "删除开始");
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
      common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:273", "你输入的内容->", e);
      customMumPopup.value.close();
      let myGameNumList = common_vendor.index.getStorageSync(storeMyGameNumKey) || [];
      myGameNumList.unshift(e);
      common_vendor.index.setStorageSync(storeMyGameNumKey, myGameNumList);
      changeGridList.value.unshift(e);
      common_vendor.index.setStorageSync(storeKey, e.id);
      currentLevel = 1;
      currentGameItem = null;
      usedColorSet = /* @__PURE__ */ new Set();
      init();
    }
    function onChangeGridType(id) {
      currentLevel = 1;
      currentGameItem = null;
      usedColorSet = /* @__PURE__ */ new Set();
      changeGridType(id);
    }
    function changeGridType(id) {
      common_utils_firework.endFirework();
      common_vendor.ref([]);
      myTimeDown.value = false;
      resultText.value = "";
      wantFindText.value = "";
      wantFindImg.value = null;
      currentGameSuccess = false;
      let item = changeGridMap.value.get(id);
      let typeKey = item.typeKey;
      if (typeKey == "custom") {
        customMumPopup.value.open();
        customMumSet.value.setnumv(currentGameType);
        return;
      }
      common_vendor.index.setStorageSync(storeKey, id);
      if (item == null) {
        return;
      }
      currentGameTypeItem = changeGameTypeMap.value.get(currentGameType);
      if (currentGameTypeItem == null) {
        return;
      }
      if (numPopup != null && numPopup.value != null) {
        numPopup.value.close();
      }
      if (gameTypePopup != null && gameTypePopup.value != null) {
        gameTypePopup.value.close();
      }
      gridColumn.value = item.colNum;
      let numSum = item.numSum;
      changeNumText.value = item.title;
      countdownNum.value = item.gameTime;
      item.beginNum;
      changeGameTypeText.value = currentGameTypeItem.title;
      let fontSize = item.fontSize;
      calcGridItemWidth();
      let list = [];
      for (var i = 1; i <= numSum; i++) {
        let listItem = { "itemCls": "v_item", "fontSize": fontSize };
        list.push(listItem);
      }
      if (currentGameItem == null || currentGameItem.id != item.id) {
        myTimeCountDown.value.start(countdownNum.value);
      }
      currentGameItem = item;
      randData(list, item);
    }
    let yesClickFlag = false;
    function randData(list, currentItem) {
      yesClickFlag = false;
      let newIndexSet = common_utils_common.getRandomItems(list, parseInt(currentLevel) - 1 + parseInt(currentGameItem.beginBlock));
      let colorIndex = 0;
      let colorValue = "";
      let content = "";
      let currentNum = currentLevel + (currentItem.beginNum - 1);
      let animalIndex = 0;
      let animalValue = "";
      if (currentGameType == "a1" || currentGameType == "a3") {
        colorIndex = common_utils_common.getRandomItem(changeColorList.value, usedColorSet);
        colorValue = changeColorList.value[colorIndex].value;
        content = "找到 <span style='background-color: " + colorValue + ";font-weight: bold;color: " + colorValue + ";width:10rpx;height:10rpx;box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1),0 6px 20px rgba(0, 0, 0, 0.1); '>123</span>";
        wantFindText.value = content;
      } else if (currentGameType == "a2" || currentGameType == "a4") {
        content = "找到数字 " + currentNum;
        wantFindText.value = content;
      } else if (currentGameType == "a5" || currentGameType == "a6") {
        animalIndex = common_utils_common.getRandomItem(changeAnimalList.value, usedAnimalSet);
        animalValue = changeAnimalList.value[animalIndex].imgSrc;
        wantFindImg.value = animalValue;
      } else if (currentGameType == "a7" || currentGameType == "a8") {
        animalIndex = common_utils_common.getRandomItem(changeAnimalList.value, usedAnimalSet);
        animalValue = changeAnimalList.value[animalIndex].shadowSrc;
        let imgSrc = changeAnimalList.value[animalIndex].imgSrc;
        wantFindImg.value = imgSrc;
      }
      common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:399", "文本->", content);
      levelText.value = currentLevel + "/" + currentItem.levelSumNum;
      findText.value = "0/" + newIndexSet.size;
      newIndexSet.forEach((index) => {
        list[index].show = true;
        if (currentGameType == "a1" || currentGameType == "a3") {
          list[index].backColor = colorValue;
        } else if (currentGameType == "a2" || currentGameType == "a4") {
          list[index].content = currentNum;
        } else if (currentGameType == "a5" || currentGameType == "a6" || currentGameType == "a7" || currentGameType == "a8") {
          list[index].imgSrc = animalValue;
        }
      });
      dataList.value = list;
      let tt = currentItem.levelHideTime * 0.8 * currentLevel;
      if (currentGameType == "a1" || currentGameType == "a2" || currentGameType == "a6" || currentGameType == "a7") {
        setTimeout(() => {
          newIndexSet.forEach((index) => {
            dataList.value[index].show = false;
          });
          yesClickFlag = true;
        }, tt);
      } else {
        for (var i = 0; i < dataList.value.length; i++) {
          let data = dataList.value[i];
          if (!newIndexSet.has(i)) {
            if (currentGameType == "a3") {
              let otherColorIndex = common_utils_common.getRandomInRangeExcluding(0, changeColorList.value.length - 1, colorIndex);
              let otherColorValue = changeColorList.value[otherColorIndex].value;
              data.backColor = otherColorValue;
            } else if (currentGameType == "a4") {
              let num = common_utils_common.getRandomInRangeExcluding(1, dataList.value.length, currentNum);
              data.content = num;
            } else if (currentGameType == "a5") {
              let otherAnimalIndex = common_utils_common.getRandomInRangeExcluding(0, changeAnimalList.value.length - 1, animalIndex);
              let otherAnimalValue = changeAnimalList.value[otherAnimalIndex].imgSrc;
              data.imgSrc = otherAnimalValue;
            } else if (currentGameType == "a8") {
              let otherAnimalIndex = common_utils_common.getRandomInRangeExcluding(0, changeAnimalList.value.length - 1, animalIndex);
              let otherAnimalValue = changeAnimalList.value[otherAnimalIndex].shadowSrc;
              data.imgSrc = otherAnimalValue;
            }
            data.otherShow = true;
          } else {
            common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:453", "撞到了->", i);
            data.mainShow = true;
          }
        }
        newIndexSet.forEach((index) => {
          dataList.value[index].show = false;
        });
        yesClickFlag = true;
      }
    }
    function changeGameType(item) {
      currentLevel = 1;
      currentGameItem = null;
      usedColorSet = /* @__PURE__ */ new Set();
      usedAnimalSet = /* @__PURE__ */ new Set();
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
        common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:497", "--->", changeGridList);
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
      usedColorSet = /* @__PURE__ */ new Set();
      usedAnimalSet = /* @__PURE__ */ new Set();
      myTimeDown.value = true;
      calcDragSuccess();
    }
    common_vendor.ref([]);
    function calcDragSuccess(isTimeFinish) {
      if (!currentGameSuccess) {
        let successLevelNum = currentLevel - 1;
        currentGameItem.levelSumNum - successLevelNum;
        resultText.value = "时间到了,再接再厉";
        common_utils_request.playAll_failVoice();
      }
    }
    common_vendor.ref([]);
    function onNumClick(content) {
      if (!yesClickFlag) {
        return;
      }
      if (myTimeDown.value) {
        return;
      }
      if (content.show) {
        return;
      }
      if ((content.backColor || content.content || content.imgSrc) && !content.show && !content.otherShow) {
        content.show = true;
        common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:556", "找对了");
        content.flashB = true;
        content.hasClicked = true;
        setTimeout(() => {
          content.flashB = false;
        }, 500);
        common_utils_request.playItem_successVoice();
      } else {
        content.flashA = true;
        setTimeout(() => {
          content.flashA = false;
        }, 1e3);
        common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:573", "找错了");
        common_utils_request.playItem_failVoice();
      }
      let allSuccess = true;
      let findSuccess = 0;
      let findFaild = 0;
      for (var i = 0; i < dataList.value.length; i++) {
        let item = dataList.value[i];
        if ((item.backColor || item.content || item.imgSrc) && !item.otherShow) {
          if (!item.show) {
            allSuccess = false;
            findFaild++;
          } else {
            findSuccess++;
          }
        }
      }
      findText.value = findSuccess + "/" + (findSuccess + findFaild);
      if (allSuccess) {
        yesClickFlag = false;
        success();
      }
    }
    let currentGameSuccess = false;
    let timer = null;
    function success() {
      if (currentLevel >= currentGameItem.levelSumNum) {
        myStartFirework();
        myTimeCountDown.value.getExpendTime();
        resultText.value = "恭喜你，挑战成功";
        common_utils_request.playAll_successVoice();
        myTimeCountDown.value.stop();
        yesClickFlag = false;
        currentGameSuccess = true;
        usedColorSet = /* @__PURE__ */ new Set();
        usedAnimalSet = /* @__PURE__ */ new Set();
        if (timer != null) {
          clearTimeout(timer);
        }
      } else {
        common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:624", "准备下一关");
        currentGameSuccess = false;
        timer = setTimeout(() => {
          if (!myTimeDown.value) {
            currentLevel++;
            init();
          }
        }, 500);
      }
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
            currentLevel = 1;
            currentGameItem = null;
            usedColorSet = /* @__PURE__ */ new Set();
            usedAnimalSet = /* @__PURE__ */ new Set();
            init();
          } else if (res.cancel) {
            common_vendor.index.__f__("log", "at pages/num-memory/num-memory.vue:657", "用户点击取消");
          }
        }
      });
    }
    function myStartFirework() {
      common_utils_firework.startFirework();
      setTimeout(() => {
        myEndFirework();
      }, 5e3);
    }
    function myEndFirework() {
      common_utils_firework.endFirework();
    }
    setTimeout(() => {
    }, 1500);
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.unref(common_utils_firework.showCanvas)
      }, common_vendor.unref(common_utils_firework.showCanvas) ? {
        b: common_vendor.unref(common_utils_firework.canvasWidth) + "px",
        c: common_vendor.unref(common_utils_firework.canvasHeight) + "px"
      } : {}, {
        d: common_vendor.o(refreshChangeNum),
        e: common_vendor.t(changeGameTypeText.value),
        f: common_vendor.o(funChangeGameType),
        g: common_vendor.t(changeNumText.value),
        h: common_vendor.o(funChangeNum),
        i: !wantFindImg.value
      }, !wantFindImg.value ? {
        j: wantFindText.value
      } : {}, {
        k: wantFindImg.value
      }, wantFindImg.value ? {
        l: wantFindImg.value
      } : {}, {
        m: common_vendor.sr(myTimeCountDown, "cb3e975e-0", {
          "k": "myTimeCountDown"
        }),
        n: common_vendor.o(onTimeFinish),
        o: common_vendor.p({
          seconds: countdownNum.value,
          ["auto-start"]: true
        }),
        p: common_vendor.t(levelText.value),
        q: common_vendor.w(({
          active,
          content
        }, s0, i0) => {
          return common_vendor.e({
            a: content.show || content.mainShow || content.otherShow
          }, content.show || content.mainShow || content.otherShow ? common_vendor.e({
            b: content.imgSrc
          }, content.imgSrc ? {
            c: content.imgSrc
          } : {}, {
            d: content.content
          }, content.content ? {
            e: common_vendor.t(content.content)
          } : {}, {
            f: content.backColor
          }, content.backColor ? {
            g: content.backColor
          } : {}) : {}, {
            h: common_vendor.o(($event) => onNumClick(content)),
            i: content.flashA ? 1 : "",
            j: content.flashB ? 1 : "",
            k: content.hasClicked ? 1 : "",
            l: content.fontSize,
            m: i0,
            n: s0
          });
        }, {
          name: "grid",
          path: "q",
          vueId: "cb3e975e-1"
        }),
        r: common_vendor.p({
          list: dataList.value,
          column: gridColumn.value,
          gridHeight: gridHeight.value,
          disabled: gridDisabled.value
        }),
        s: resultText.value,
        t: common_vendor.t(findText.value),
        v: common_vendor.f(changeGridList.value, (item, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(item.title),
            b: common_vendor.t(item.note),
            c: item.typeKey == "myCustom"
          }, item.typeKey == "myCustom" ? {
            d: common_vendor.o(($event) => rmoveMyGridNum(item), item.id)
          } : {}, {
            e: common_vendor.o(($event) => onChangeGridType(item.id), item.id),
            f: item.id,
            g: "cb3e975e-4-" + i0 + ",cb3e975e-3"
          });
        }),
        w: common_vendor.p({
          title: "请选择数字类型",
          type: "line"
        }),
        x: common_vendor.sr(numPopup, "cb3e975e-2", {
          "k": "numPopup"
        }),
        y: common_vendor.p({
          ["is-mask-click"]: true
        }),
        z: common_vendor.sr(customMumSet, "cb3e975e-6,cb3e975e-5", {
          "k": "customMumSet"
        }),
        A: common_vendor.o(onFunCustomNumFinish),
        B: common_vendor.sr(customMumPopup, "cb3e975e-5", {
          "k": "customMumPopup"
        }),
        C: common_vendor.p({
          ["is-mask-click"]: true
        }),
        D: common_vendor.f(changeGameList.value, (item, k0, i0) => {
          return {
            a: "cb3e975e-10-" + i0 + "," + ("cb3e975e-9-" + i0),
            b: common_vendor.p({
              title: item.title,
              showArrow: true,
              note: item.note,
              rightText: item.rightText
            }),
            c: common_vendor.o(($event) => changeGameType(item), item.id),
            d: item.id,
            e: "cb3e975e-9-" + i0 + ",cb3e975e-8"
          };
        }),
        E: common_vendor.p({
          title: "请选择游戏类型",
          type: "line"
        }),
        F: common_vendor.sr(gameTypePopup, "cb3e975e-7", {
          "k": "gameTypePopup"
        }),
        G: common_vendor.p({
          ["is-mask-click"]: true
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-cb3e975e"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/num-memory/num-memory.js.map
