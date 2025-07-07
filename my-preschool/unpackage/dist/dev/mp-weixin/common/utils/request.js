"use strict";
const common_vendor = require("../vendor.js");
const BASE_URL = "https://www.yangxuexue.xyz";
function playAll_failVoice() {
  playVoice(BASE_URL + "/voice/all_fail.mp3");
}
function playAll_successVoice() {
  playVoice(BASE_URL + "/voice/all_success.mp3");
}
function playItem_failVoice() {
  playVoice(BASE_URL + "/voice/item_fail.mp3");
}
function playItem_successVoice() {
  playVoice(BASE_URL + "/voice/item_success.mp3");
}
let innerAudioContext = null;
function playVoice(audioUrl) {
  if (innerAudioContext == null) {
    innerAudioContext = common_vendor.index.createInnerAudioContext();
    innerAudioContext.autoplay = true;
  } else {
    innerAudioContext.stop();
  }
  innerAudioContext.src = audioUrl;
  innerAudioContext.play();
  innerAudioContext.onPlay(() => {
    common_vendor.index.hideLoading();
  });
  innerAudioContext.onError((res) => {
    common_vendor.index.__f__("log", "at common/utils/request.js:37", res);
    common_vendor.index.hideLoading();
  });
  innerAudioContext.onEnded((res) => {
    common_vendor.index.__f__("log", "at common/utils/request.js:41", "停止播放->", res);
    common_vendor.index.hideLoading();
  });
}
exports.playAll_failVoice = playAll_failVoice;
exports.playAll_successVoice = playAll_successVoice;
exports.playItem_failVoice = playItem_failVoice;
exports.playItem_successVoice = playItem_successVoice;
//# sourceMappingURL=../../../.sourcemap/mp-weixin/common/utils/request.js.map
