<template>
  <view class="countdownCls">
    {{ formattedTime }}
  </view>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';

const props = defineProps({
  seconds: { type: Number, required: true }, // 传入的总秒数
  autoStart: { type: Boolean, default: true }, // 是否自动开始
});

const emit = defineEmits(['countDownFinish']); // 倒计时结束事件

let remainingSeconds = ref(props.seconds);
let beginTime = 0;
let timer = null;
let msTimer = null;

// 计算格式化时间（HH:mm:ss）
const formattedTime = ref("00:00:00");

function formatTime(type,remainingSeconds){
	const hours = Math.floor(remainingSeconds.value / 3600);
	const minutes = Math.floor((remainingSeconds.value % 3600) / 60);
	const seconds = remainingSeconds.value % 60;
	
	let formattedTime2 = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
	// console.log("formattedTime2->",formattedTime2);
	if("calcDown" == type){
		formattedTime.value = formattedTime2;
	}
	return formattedTime2;
}

// 开始倒计时 起始的秒钟
function start(mySeconds) {
	//剩余的秒数
  remainingSeconds = ref(mySeconds);
  // 话费的毫秒数
  beginTime = new Date().getTime();
  formatTime("calcDown",remainingSeconds);
  if (timer) {
	  clearInterval(timer);
  }
  if (msTimer) {
  	  clearInterval(msTimer);
  }
  
  //倒计时
  timer = setInterval(() => {
    if (remainingSeconds.value <= 0) {
      clearInterval(timer); 
      emit('countDownFinish');
    } else {
      remainingSeconds.value = remainingSeconds.value - 1;
    }
	formatTime("calcDown",remainingSeconds);
  }, 1000);
  
  // //毫秒
  // msTimer = setInterval(() => {
	 //   expendMs.value ++ ;
  // }, 10);
  
};

//获取消耗的时间
function getExpendTime(){
	let expendMs = new Date().getTime() - beginTime;
	console.log("正在获取->", expendMs);
	return expendMs;
	// return formatTime("calcExpand",expendMs);
}

// 停止倒计时
const stop = () => {
  if (timer) clearInterval(timer);
  if (msTimer) clearInterval(msTimer);
};

// 重置倒计时
const reset = (newSeconds) => {
  remainingSeconds.value = newSeconds ?? props.seconds;
};

// 自动开始（如果 autoStart=true）
onMounted(() => {
  // if (props.autoStart) start();
});

// 组件卸载时清除定时器
onBeforeUnmount(() => {
  stop();
});

// 暴露方法，方便父组件调用
defineExpose({ start, stop, reset, getExpendTime });
</script>

<style scoped>
.countdownCls {
  font-family: monospace; /* 等宽字体，避免数字跳动 */
  font-size: 24px;
  color: #fff;
  background-color: #4cd964;
  padding: 10rpx;
  border-radius: 20rpx;
}
</style>