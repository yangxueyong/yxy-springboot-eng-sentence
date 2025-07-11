<template>
	<scroll-view class="customMumPopup" scroll-y="true">
		<view class="uni-common-mt"> 
			<view class="uni-form-item uni-column" v-if="showNum">
				<view class="title">以哪个数字开始</view>
				<input class="uni-input" type="number" v-model="iptBeginNumValue" maxlength="3" placeholder="请输入以哪个数字开始" />
			</view> 
			<view class="uni-form-item uni-column">
				<view class="title">棋盘方格总个数</view>
				<input class="uni-input" type="number" v-model="iptSumBlockValue" maxlength="3" placeholder="请输入棋盘方格总个数" />
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
	import { showHint, calculateLayout } from "@/common/utils/common.js";
	
	const emit = defineEmits(['customNumFinish']);  
	
	const iptBeginNumValue = ref(null);
	const iptSumBlockValue = ref(null); 
	const iptSecondValue = ref(null);
	
	const showNum = ref(null);
	function setnumv(id){
		console.log("正在自定义->",id);
		if(id == "kk1"){
			showNum.value = true;
		}else{
			showNum.value = false;
		}
	}
	function onNumFinish(){
		let sumBlockVal = iptSumBlockValue.value;
		let secondVal = iptSecondValue.value;
		let beginNumVal = iptBeginNumValue.value;
		 
		if(showNum.value){
			if(!beginNumVal){
			 	showHint("请输入以哪个数字开始");
				return;
			}
		}else{
			beginNumVal = 1;
		}
		
		if(!sumBlockVal){
			showHint("请输入棋盘方格总个数");
			return;
		}
		if(!secondVal){
			showHint("请输入游戏时间");
			return;
		} 
		
		beginNumVal = parseInt(beginNumVal);
		sumBlockVal = parseInt(sumBlockVal);
		secondVal = parseInt(secondVal);
		 
		 if(showNum.value){
		 	if(beginNumVal > 100 || beginNumVal < 1){
		 	 	showHint("开始数字不能小于1或者大于100");
		 		return;
		 	}
		 }
		 
		if(sumBlockVal < 4){
			showHint("方格总数不能小于4个");
			return;
		} 
		
		if(secondVal <= 0){
			showHint("游戏时间不能小于0");
			return;
		} 
		
		console.log("sumBlockVal>",sumBlockVal);
		console.log("secondVal>",secondVal);
		
		
	   let fontSize = "30";
	   if(sumBlockVal <= 10){
		   fontSize = "100";
	   }else if(sumBlockVal <= 16){
		   fontSize = "80";
	   }else if(sumBlockVal <= 25){
		   fontSize = "70";
	   }else if(sumBlockVal <= 36){
		   fontSize = "60";
	   }else if(sumBlockVal <= 49){
		   fontSize = "50";
	   }else if(sumBlockVal <= 64){
		   fontSize = "30";
	   } 
	   
	   let { width, height } = calculateLayout(sumBlockVal);
	   let title = "自定义:" + sumBlockVal;
	   let note = "总共:" + sumBlockVal + ",时间:" + secondVal + "秒" ;
	   if(showNum.value){
		   note = "以" + beginNumVal + "开始," + note;
	   }
	   var myCustom = {
		   id : new Date().getTime(),
		   "levelHideTime" : "500",
		   "beginBlock" : 1,
		   beginNum : beginNumVal, 
		   typeKey:"myCustom",
		   "itemCls":"v_item",
		   "fontSize": fontSize,
		   "gameTime": secondVal,
		   "colNum": width,
		   "numSum": sumBlockVal,
		   "title": title,
		   "note": note
	   }
		console.log("完成-->");
	    emit("customNumFinish",myCustom);
	} 
	
	// 暴露方法，方便父组件调用
	defineExpose({ setnumv });
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