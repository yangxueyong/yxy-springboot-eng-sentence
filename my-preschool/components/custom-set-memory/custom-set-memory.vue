<template>
	<scroll-view class="customMumPopup" scroll-y="true">
		<view class="uni-common-mt">
			<view class="uni-form-item uni-column" v-if="showNum">
				<view class="title">首关的数字</view>
				<input class="uni-input" type="number" v-model="iptBeginNumValue" maxlength="2" placeholder="请输入首关的数字" />
			</view>
			<view class="uni-form-item uni-column">
				<view class="title">首关的块数</view>
				<input class="uni-input" type="number" v-model="iptBeginBlockValue" maxlength="2" placeholder="请输入首关出现的块数" />
			</view>
			<view class="uni-form-item uni-column">
				<view class="title">总关卡</view>
				<input class="uni-input" type="number" v-model="iptLevelSumValue" maxlength="2" placeholder="请输入总关卡,最大为99关" />
			</view>
			<view class="uni-form-item uni-column">
				<view class="title">棋盘大小</view>
				<input class="uni-input" type="number" v-model="iptWidthValue" maxlength="2" placeholder="请输入棋盘大小,输入2表示生产2行2列的棋盘" />
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
	
	const iptBeginNumValue = ref("1");
	const iptBeginBlockValue = ref("1");
	const iptLevelSumValue = ref("2");
	const iptWidthValue = ref("2");
	const iptSecondValue = ref("60");
	
	const showNum = ref(null);
	function setnumv(id){
		if(id == "a2" || id == "a4"){
			showNum.value = true;
		}else{
			showNum.value = false;
		}
	}
	function onNumFinish(){
		if(showNum.value){
			if(!iptBeginNumValue.value){
				showHint("请输入请输入首关的数字");
				return;
			}
			if(iptBeginNumValue.value <= 0){
				showHint("首关的数字不能小于0");
				return;
			}
		}
		if(!iptBeginBlockValue.value){
			showHint("请输入首关出现的块数");
			return;
		}
		if(iptBeginBlockValue.value <= 0){
			showHint("首关出现的块数不能小于0");
			return;
		}
		
		if(!iptLevelSumValue.value){
			showHint("请输入总关卡");
			return;
		}
		if(iptLevelSumValue.value <= 0){
			showHint("总关卡不能小于0");
			return;
		}
		
		if(!iptWidthValue.value){
			showHint("请输入棋盘大小");
			return;
		}
		if(iptWidthValue.value < 2){
			showHint("棋盘不能小于2");
			return;
		}
		
		console.log("iptLevelSumValue.value->",iptLevelSumValue.value);
		console.log("iptBeginBlockValue.value->",iptBeginBlockValue.value);
		
		//最后一关会出现的块的个数 这个个数不能大于棋盘的块数
		let lastLevelBlockNum = parseInt(iptLevelSumValue.value) + parseInt(iptBeginBlockValue.value) - 1;
		let sumBlockNum = iptWidthValue.value * iptWidthValue.value;
		if(lastLevelBlockNum > sumBlockNum){
			showHint("棋盘大小为" + iptWidthValue.value  + "✖️" + iptWidthValue.value + ",无法装下最后一关的块数" + lastLevelBlockNum + "，请重新调整");
			return;
		} 
		 
		 
	   let fontSize = "30rpx";
	   if(sumBlockNum <= 10){
		   fontSize = "100rpx";
	   }else if(sumBlockNum <= 16){
		   fontSize = "80rpx";
	   }else if(sumBlockNum <= 25){
		   fontSize = "70rpx";
	   }else if(sumBlockNum <= 36){
		   fontSize = "60rpx";
	   }else if(sumBlockNum <= 49){
		   fontSize = "50rpx";
	   }else if(sumBlockNum <= 64){
		   fontSize = "40rpx";
	   } 
	   
	   let note = "";
	   if(showNum.value){
		   note = "首关的数字:" + iptBeginNumValue.value + ",";
	   }
	   note += "首关的块数:" + iptBeginBlockValue.value + ",总关卡:"+ iptLevelSumValue.value +",棋盘大小:" + iptWidthValue.value;
	   
	   var myCustom = {
		   id : new Date().getTime(),
		   "levelHideTime" : "500",
		   "beginBlock" : iptBeginBlockValue.value,
		   beginNum : iptBeginNumValue.value,
		   levelSumNum : iptLevelSumValue.value,
		   typeKey:"myCustom",
		   "itemCls":"v_item",
		   "fontSize":fontSize,
		   "gameTime":iptSecondValue.value,
		   "colNum":iptWidthValue.value,
		   "numSum":sumBlockNum,
		   "title":"自定义 " + iptWidthValue.value+" ✖️ " + iptWidthValue.value,
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