<template>
	<scroll-view class="customMumPopup" scroll-y="true">
		<view class="uni-common-mt">
			<view class="uni-form-item uni-column">
				<view class="title">开始数字</view>
				<input class="uni-input" type="number" v-model="iptBeginValue" maxlength="2" placeholder="请输入开始数字" />
			</view>
			<view class="uni-form-item uni-column">
				<view class="title">结束数字</view>
				<input class="uni-input" type="number" v-model="iptEndValue" maxlength="3" placeholder="请输入结束数字" />
			</view>
			<view class="uni-form-item uni-column">
				<view class="title">游戏时间，单位为秒</view>
				<input class="uni-input" type="number" v-model="iptSecondValue"  maxlength="4" placeholder="游戏时间不能小于5秒或者大于7200秒" />
			</view>
			<view class="customNumBtnCls">
				<button class="mini-btn" type="primary" size="mini" @click="onNumFinish">确 定</button>
			</view>	
		</view>
	</scroll-view> 
</template>

<script setup>
	import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
	import { showHint } from "@/common/utils/common.js";
	const emit = defineEmits(['customNumFinish']);  
	const iptBeginValue = ref(null);
	const iptEndValue = ref(null);
	const iptSecondValue = ref(null);
	
	function onNumFinish(){
		
		console.log("iptBeginValue.value",iptBeginValue.value);
		console.log("iptEndValue.value",iptEndValue.value);
		console.log("iptSecondValue.value",iptSecondValue.value);
		
		let beginVal = parseInt(iptBeginValue.value);
		let endVal = parseInt(iptEndValue.value);
		let secondVal = parseInt(iptSecondValue.value);
		
		if(beginVal <= 0){
			showHint("开始的数字不能小于0");
			return;
		}
		if(endVal <= 0){
			showHint("结束的数字不能小于0");
			return;
		}
		if(endVal <= beginVal){
			showHint("结束的数字不能小于或等于开始的数字");
			return;
		}
		let cha = endVal - beginVal ;
		if(cha < 4 || cha > 100){
			showHint("数字相差不能少于4个或者大于100个");
			return;
		}
		if(secondVal < 5 || secondVal > 7200){
			showHint("游戏时间不能小于5秒或者大于7200秒");
			return;
		}
		 
	   let fontSize = "30rpx";
	   if(cha <= 10){
		   fontSize = "100rpx";
	   }else if(cha <= 16){
		   fontSize = "80rpx";
	   }else if(cha <= 25){
		   fontSize = "70rpx";
	   }else if(cha <= 36){
		   fontSize = "60rpx";
	   }else if(cha <= 49){
		   fontSize = "50rpx";
	   }else if(cha <= 64){
		   fontSize = "40rpx";
	   }
	   
	   let { width, height } = calculateLayout(cha + 1);
	   
	   var myCustom = {
		   id : new Date().getTime(),
		   beginNum : beginVal,
		   endNum : endVal,
		   typeKey:"myCustom",
		   "itemCls":"v_item",
		   "fontSize":fontSize,
		   "gameTime":secondVal,
		   "colNum":width,
		   "numSum":endVal,
		   "title":"自定义 " + width+" ✖️ " + height,
		   "note":beginVal + "-"+ endVal +"的数字"
	   } 
		emit("customNumFinish",myCustom);
	}
	
	function calculateLayout(num) {
	  if (num <= 0) return [0, 0];
	  
	  // 计算最接近的平方根作为初始宽度
	  let width = Math.ceil(Math.sqrt(num));
	  let height = width;
	  
	  // 尝试优化布局，使宽度和高度尽可能接近
	  while (width * height >= num) {
	    // 如果减少高度后仍然能容纳所有数字
	    if ((width - 1) * height >= num) {
	      width--;
	    } 
	    // 如果减少宽度后不能容纳，尝试减少高度
	    else if (width * (height - 1) >= num) {
	      height--;
	    } else {
	      break;
	    }
	  }
	  
	  // 确保最终布局能容纳所有数字
	  while (width * height < num) {
	    if (width <= height) {
	      width++;
	    } else {
	      height++;
	    }
	  }
	  
	  // 返回宽度和高度，确保宽度 >= 高度（更接近正方形）
	  return width >= height ? {width, height} : {height, width};
	} 
</script>

<style  lang="scss" scoped>
		.customMumPopup{
			background: #fff;
			// padding: 130rpx;
			border-radius: 30rpx 30rpx 30rpx 30rpx;
			width: 80vw;
			max-height: 60vh;
			overflow: hidden; 
			font-size: 30rpx; 
		}
		.uni-common-mt{
			margin: 30rpx;
			background-color: #fff;
		}
		input{
			padding: 20rpx 5rpx;
			  font-size: 16px;
			  line-height: 1.5;
			  color: #333;
			  background-color: #fff;
			  border: 1px solid #ddd;
			  border-radius: 6px;
			  transition: all 0.3s ease;
			  // box-sizing: border-box;
			  // outline: none;
			margin-bottom: 30rpx;
			margin-top: 10rpx;
		}
		.customNumBtnCls{
			display: flex;
			justify-content: center;  /* 水平居中 */
			align-items: center;     /* 垂直居中 */
			// background-color: #18a058;
		}
</style>