<template>
	<template>
	  <view class="containerCls" v-if="showCanvas"> 
	    <canvas 
	      canvas-id="fireworksCanvas" 
	      id="fireworksCanvas" 
	      class="canvas"
	      :style="{width: canvasWidth + 'px', height: canvasHeight + 'px'}"
	    ></canvas>
	  </view>
	</template>
	
	<view class="box">
		<view class="changeNumCls"> 
			<view class="changeNumRightCls">
				<view class="changeNumTextCls" @click="showGameScoreRank" v-if="btnShowGameScoreRank">排行榜</view>
				<view class="changeNumTextCls" @click="refreshChangeNum">重来</view>
				<view class="changeNumTextCls" @click="funChangeGameType">{{changeGameTypeText}}</view>
				<view class="changeNumTextCls" @click="funChangeNum">{{changeNumText}}</view>
			</view>
		</view>
		
		<view class="countdownTextMainCls">
			<view class="countdownTextCls">
				<time-count-down
				  ref="myTimeCountDown"
				  :seconds="countdownNum" 
				  :auto-start="true" 
				  @countDownFinish="onTimeFinish"></time-count-down>
			</view>
		</view>
		
		 <view class="v1" ref="v1" > 
			<l-drag 
			:list="dataList" 
			@change="change" 
			:column="gridColumn"  
			:gridHeight="gridHeight" 
			:disabled="gridDisabled"> 
					<!-- // 每一项的插槽 grid 的 content 您传入的数据 -->
					<template #grid="{active, content}" class="v1_1"> 
						<!-- // grid.active 是否为当前拖拽项目 根据自己需要写样式 -->
							<view 
							@click="onNumClick(content)"
							:class="content.itemCls" 
							:style="{fontSize: content.fontSize}">
								<text class="text" :class="{'text-active': active}">{{content.content}}</text>
							</view> 
					</template> 
			</l-drag> 
		 </view>
		 <view class="hintTextMainClass">
			 <view class="hintTextClass">
			 	{{resultText}}
			 </view>
		 </view>
		<uni-popup ref="numPopup" :is-mask-click="true"  > 
			<scroll-view class="numPopup" scroll-y="true">
				<uni-section title="请选择数字类型" type="line">
					<uni-list v-for="item in changeGridList" :key="item.id">
						<view @click="changeGridType(item.id)" class="gridNumMainCls">
							<view class="gridNumLeftClss">
								<view class="gridNumTitleCls">{{item.title}}</view>
								<view class="gridNumNoteCls">{{item.note}}</view>
							</view>
							<view class="gridNumRightCls" @click.stop="rmoveMyGridNum(item)" v-if="item.typeKey == 'myCustom'">
								删除
							</view>
						 </view>
					</uni-list>
				</uni-section>
			</scroll-view> 
		</uni-popup>
		<uni-popup ref="customMumPopup" :is-mask-click="true"  >
			<custom-set-num
			@customNumFinish="onFunCustomNumFinish" ></custom-set-num>
		</uni-popup>
		<uni-popup ref="gameTypePopup" :is-mask-click="true"  >
			<view class="numPopup">
				<view class="container">
					<uni-section title="请选择游戏类型" type="line">
						<uni-list v-for="item in changeGameList" :key="item.id">
							<view @click="changeGameType(item)" >
								<uni-list-item
								 :title="item.title" 
								 showArrow 
								 :note="item.note" 
								 :rightText="item.rightText" />
							 </view>
						</uni-list>
					</uni-section>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script setup> 
import {ref, onMounted,nextTick} from "vue"; 
import {playAll_failVoice,playAll_successVoice,playItem_failVoice,playItem_successVoice} from "@/common/utils/request.js"
import {apiGetGameTypeList,apiGetGameAnimalList,apiGetGameColorList,apiGetNumLevelList,apiSaveGameScore} from "@/common/api/apis.js";
import {startFirework,endFirework,showCanvas,canvasWidth,canvasHeight,initCtx} from "@/common/utils/firework.js";
import {onShareAppMessage,onReachBottom,onPullDownRefresh} from "@dcloudio/uni-app";
import {getSystemWechatUserForward} from "@/common/utils/common.js";

getSystemWechatUserForward();
// 存储我选择的游戏数字
let storeKey = "myChangeItem";
// 存储我选择的游戏方式
let storeGameTypeKey = "myChangeGameType";
// 我自定义的游戏数字
let storeMyGameNumKey = "myGameNum";
//排行帮
let btnShowGameScoreRank = ref(false);
// 数字选择提示层
const numPopup = ref(null);
// 游戏方式提示层
const gameTypePopup = ref(null);
// 数字层
const v1 = ref(null);
// 数字层展示多少列
const gridColumn = ref(null);
// 要展示的数字
const dataList = ref([]);
// 数字类型按钮上的文字
const changeNumText =ref("");
// 游戏类型按钮上的文字
const changeGameTypeText = ref("");
// 倒计时多少秒
const countdownNum = ref(null);
// 倒计时组件
const myTimeCountDown = ref(null);
// 数字层是否可以拖拽
const gridDisabled = ref(false);
// 数字层的高度
const gridHeight = ref(150);
// 游戏结束后的提示语
const resultText = ref("");
// 当前的游戏类型
let currentGameType = "";
// 游戏数字的map对象
const changeGridMap = ref(null);
// 游戏类型的map对象
const changeGameTypeMap = ref(null);
const currentItemGridNums = ref([]);
const myTimeDown = ref(false);
//自定义的时间输入框
const customMumPopup = ref(null);
//目前已经拖拽了成功了多少个
let lastDragSuccessNum = 0;
const changeGridList = ref([]); 

//分享给好友
onShareAppMessage((e)=>{
	return {
		title:"小朋友认数字来啦~",
		path:"/pages/num-Huarongdao/num-Huarongdao"
	}
})

const getChangeGridList =async()=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetNumLevelList({ "type":"simple" });   
	console.log("得到的level-->", res.data);
	changeGridList.value = res.data; 
	uni.hideLoading(); 
	firstInit();
}
getChangeGridList();

const changeGameList = ref([]);

const getChangeGameList =async()=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetGameTypeList({ "type":"simple" });  
	console.log("得到的type-->", res.data);
	changeGameList.value = res.data; 
	uni.hideLoading(); 
	firstInit();
}
getChangeGameList();

//打开排行榜
function showGameScoreRank(){
	uni.navigateTo({
		url:"/pages/game-score-rank/game-score-rank?gameLevelId=" + currentGameItem.id + "&gameTypeId=" + currentGameTypeItem.id + "&name=" + currentGameTypeItem.title + " - " + currentGameItem.title
	})
}

//删除我的矩阵
function rmoveMyGridNum(param){
	console.log("删除开始");
	changeGridList.value = changeGridList.value.filter(item => item.id !== param.id);
	let myGameNumList = uni.getStorageSync(storeMyGameNumKey);
	if(myGameNumList != null && myGameNumList.length > 0){
		myGameNumList = myGameNumList.filter(item => item.id !== param.id);
		uni.setStorageSync(storeMyGameNumKey, myGameNumList);
	}
	if(uni.getStorageSync(storeKey) == param.id){
		uni.removeStorageSync(storeKey);
	}
}
//完成自定义的数字选择
function onFunCustomNumFinish(e){
	console.log("你输入的内容->", e);
	customMumPopup.value.close();
	
	let myGameNumList = uni.getStorageSync(storeMyGameNumKey) || [];
	myGameNumList.unshift(e);
	
	uni.setStorageSync(storeMyGameNumKey, myGameNumList);
	changeGridList.value.unshift(e);
	
	uni.setStorageSync(storeKey, e.id);
	init();
}
// 当前正在玩的关卡
let currentGameItem = null;
let currentGameTypeItem = null;
//更改选择的矩阵时
function changeGridType(id){ 
	clickNumList = ref([]);
	myTimeDown.value = false;
	resultText.value = "";
	lastDragSuccessNum = 0;
	
	console.log("id->",id)
	
	let item = changeGridMap.value.get(id);
	
	let typeKey = item.typeKey;
	
	if(typeKey == "custom"){
		customMumPopup.value.open();
		btnShowGameScoreRank.value = false;
		return;
	}else{
		btnShowGameScoreRank.value = true;
	}
	
	uni.setStorageSync(storeKey, id);
	
	if(item == null){
		return;
	}
	currentGameTypeItem = changeGameTypeMap.value.get(currentGameType);
	if(currentGameTypeItem == null){
		return;
	}
	
	if(numPopup != null && numPopup.value != null){
		numPopup.value.close();
	}
	if(gameTypePopup != null && gameTypePopup.value != null){
		gameTypePopup.value.close();
	}
	gridColumn.value = item.colNum;
	let endNum = item.endNum || item.numSum;
	changeNumText.value = item.title;
	countdownNum.value = item.gameTime;
	let beginNum = item.beginNum;
	changeGameTypeText.value = currentGameTypeItem.title;
	let fontSize = item.fontSize;
	console.log("得到的时间->",item.gameTime)
	calcGridItemWidth();
	let list = ref([]);
	for (var i = beginNum; i <= endNum; i++) {
		let listItem = {"content":i,"itemCls":"v_item","fontSize":fontSize}; 
		list.value.push(listItem);
	}
	
	//随机排序
	randomList(list);
	
	if(currentGameItem == null || currentGameItem.id != item.id){ 
		//开始计时
		myTimeCountDown.value.start(countdownNum.value);
	}
	currentGameItem = item;
	
	if(currentGameType == "b1"){
		//允许拖动
		gridDisabled.value = false;
	}else{
		//
		gridDisabled.value = true;
	}
}

//切换游戏类型
function changeGameType(item){ 
	if(gameTypePopup != null && gameTypePopup.value != null){
		gameTypePopup.value.close();
	}
	if(currentGameType == item.id){
		return;
	}
	let typeKey = item.typeKey; 
	currentGameType = item.id;
	uni.setStorageSync(storeGameTypeKey, currentGameType); 
	init();
}

//打乱数据
function randomList(list){
	currentItemGridNums.value = list.value; 
	let startNum = list.value[0].content;
	
	//如果随机之后的数据第一位没有变化就重新随机
	do{
		list.value = [...list.value].sort(() => Math.random() - 0.5);
	}while(list.value[0].content == startNum); 
	
	dataList.value = list.value;
}
//计算矩阵元素的宽度
function calcGridItemWidth(){
	const SYSTEM_INFO = uni.getSystemInfoSync();
	let itemHeight = (SYSTEM_INFO.windowWidth - 100) / gridColumn.value;
	gridHeight.value = itemHeight;
}
//加载完成时重新发起
 function firstInit(){   
	 // initCtx();
	 if(changeGridList.value == null || changeGameList.value == null){
		 return;
	 }
	 
	 if(changeGridList.value.length <= 0 || changeGameList.value.length <= 0){
	 	 return;
	 }
	// setTimeout(()=>{
	let myGameNumList = uni.getStorageSync(storeMyGameNumKey);
	if(myGameNumList != null && myGameNumList.length > 0){
		changeGridList.value = [ ...myGameNumList, ...changeGridList.value];
	}
	init();
	// },500) 
}
onMounted(()=>{
	initCtx(); 
});
 
 function init(){ 
	 changeGridMap.value = new Map(changeGridList.value.map(item => [item.id, item]));
	 changeGameTypeMap.value = new Map(changeGameList.value.map(item => [item.id, item]));
	 
	 let item = uni.getStorageSync(storeKey) || changeGridList.value[0].id;
	 if(changeGridMap.value.get(item) == null){
		 item = changeGridList.value[0].id;
	 }
	 //游戏类型
	 currentGameType = uni.getStorageSync(storeGameTypeKey) || changeGameList.value[0].id;

	 changeGridType(item); 
 }
 
 function onTimeFinish(){
	 console.log("时间到了");
	 myTimeDown.value = true;
	 gridDisabled.value = true;
	 if(currentGameType == "b1"){
		 calcDragSuccess(true);
	 }else{
		 calcClickSuccess();
	 } 
 }
// 拖拽后新的数据
const newList = ref([]) ;
//计算拖动的完成
function calcDragSuccess(isTimeFinish){ 
	let flag = true;
	let successNum = 0 ;
	let failureNum = 0; 
	for (var i = 0; i < newList.value.length; i++) {
		let item = newList.value[i];
		let yesItem = currentItemGridNums.value[i]; 
		if(item.content.content == yesItem.content){
			if(flag){ 
				item.content.itemCls = "v_item_success";
				successNum ++;
			}
		}else{
			flag = false; 
			item.content.itemCls = "v_item";
		}
	}
	if(successNum > lastDragSuccessNum){
		console.log("应该播放成功！")
		playItem_successVoice();
	}else{
		console.log("应该播放失败！")
		playItem_failVoice();
	}	
	lastDragSuccessNum = successNum;
	
	if(flag && newList.value.length != 0){
		success();
	}else{
		failureNum = currentItemGridNums.value.length - successNum;
		//时间到了
		if(isTimeFinish){
			failure(successNum, failureNum);
		}
	}
}
//计算点击的成功
function calcClickSuccess(){
	if(clickNumList.value.length != currentItemGridNums.value.length){
		let successNum = clickNumList.value.length;
		let failureNum = currentItemGridNums.value.length - successNum;
		failure(successNum, failureNum);
	}
}
//拖动了元素之后
function change(v){
	// console.log("拖拽了->",v);
	newList.value = v;  
	calcDragSuccess(false);
}

let clickNumList = ref([]);
function onNumClick(content){ 
	if(currentGameType != "b2"){
		return;
	}
	if(myTimeDown.value){
		return;
	}
	if(content.itemCls == "v_item_success"){
		return;
	} 
	
	let num = content.content; 
	
	clickNumList.value = [...new Set([...clickNumList.value, num])];
	let index = clickNumList.value.length - 1; 
	let rightItemNum = currentItemGridNums.value[index].content;
	
	let clickLastItemNum = num; 
	if(clickLastItemNum != rightItemNum){
		//todo 你找错了 播放音效
		playItem_failVoice();
		console.log("你点错了->");
		clickNumList.value.pop();
		content.itemCls = "v_item_faild";
		setTimeout(()=>{
			content.itemCls = "v_item";
		},1000)
	}else{
		//todo 找对了 播放音效
		playItem_successVoice();
		content.itemCls = "v_item_success";
	}
	
	if(clickNumList.value.length == currentItemGridNums.value.length){
		success();
		return;
	}
	console.log("你手贱吗->",clickNumList.value); 
}


const saveGameScore =async(game_level_id, game_type_id, consume_time)=>{ 
	
	console.log("game_level_id",game_level_id);
	console.log("game_type_id",game_type_id);
	console.log("consume_time",consume_time);
	
	let res =await apiSaveGameScore({ "gameLevelId":game_level_id, "gameTypeId":game_type_id, "consumeTime":consume_time });  
}

//挑战成功
function success(){ 
	// uni.showToast({
	// 	title:"恭喜你！！！挑战成功"
	// });
	myStartFirework();
	let expendTime = myTimeCountDown.value.getExpendTime();
	resultText.value = "恭喜你，挑战成功";
	//todo 可以播放音效
	playAll_successVoice();
	//保存成绩
	saveGameScore(currentGameItem.id, currentGameTypeItem.id, expendTime);
	//停止计时
	myTimeCountDown.value.stop();
	//不允许拖动
	gridDisabled.value = true;
}

//挑战失败
function failure(successNum, failureNum){ 
	// uni.showToast({
	// 	icon:"error",
	// 	title:"时间到了,找到"+successNum+"个,还剩"+failureNum+"个,再接再厉"
	// });
	playAll_failVoice();
	resultText.value = "时间到了,完成"+successNum+"个,还剩"+failureNum+"个,再接再厉";
	//todo 可以播放音效
	//停止计时
	myTimeCountDown.value.stop();
	//不允许拖动
	gridDisabled.value = true;
}

//打开选择矩阵的窗口
function funChangeNum(){
	numPopup.value.open();
}
function funChangeGameType(){
	gameTypePopup.value.open();
}
//重新开始
function refreshChangeNum(){
	uni.showModal({
		title: '提示',
		content: '确定重新开始吗？',
		success: function (res) {
			if (res.confirm) {
				currentGameItem = null;
				init();
			} else if (res.cancel) {
				console.log('用户点击取消');
			}
		}
	});
	
}
 
function myStartFirework(){
	startFirework();
	setTimeout(()=>{
		myEndFirework();
	}, 5000);
}
function myEndFirework(){
	endFirework();
}

// console.log("list--->", list)
</script>

<style lang="scss" scoped> 
	.box{
		// position: relative;  /* 关键：设置父容器为相对定位 */
		width: 100vw;       /* 根据需要设置尺寸 */
		height: 100vh;
		background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
		overflow: hidden; /* 添加这行防止margin塌陷 */
		// background-color: #18a058;
	}
	.containerCls {
	  position: fixed;
	  top: 0;
	  left: 0;
	  width: 100%;
	  height: 100%;
	  pointer-events: none; /* 允许点击穿透 */
	  z-index: 9999; 
	  // background-color: red;
	}
	.countdownTextMainCls{
		// padding-top: 100rpx;
		display: flex;
		// justify-content: space-between; /* 左右分开对齐 */
		align-items: center; /* 如果需要垂直居中可以加上这一行 */ 
		text-align: center; 
		display: flex; 
		justify-content: center;
		
		.countdownTextCls{
			 margin: 10rpx 10rpx; 
			  text-align: center;
			 color: $uni-color-success;
			 font-size: $text-font-size-1;
		}
	}
	.changeNumCls{
		margin-right: 50rpx; 
		margin-top: 50rpx;
		margin-bottom: 50rpx;
		display: flex;
		justify-content: flex-end;  
		
		.changeNumRightCls{
			display: flex;
			align-items: center; /* 子元素垂直居中 */ 
			
			.changeNumTextCls{ 
				 margin: 10rpx 10rpx; 
				 display: flex; 
				 padding: 10rpx;
				 height: 40rpx;
				 border-radius: 20rpx; 
				 font-size: $text-font-size-1;
				 align-items: center; /* 如果需要垂直居中可以加上这一行 */
				 text-align: center;  
				 justify-content: center; 
				 box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 
				               0 6px 20px rgba(0, 0, 0, 0.1);
			}
		}  
	}
	
	.v1{
		margin: 50rpx 50rpx;
		border: 1px solid #18a058;  
	}
	.v1_1{
		// margin: 20rpx;
		padding: 30rpx;
	}
	.v_item{
		flex: 1;
		font-size: 100rpx;
		display: flex;
		justify-content: center;  /* 水平居中 */
		align-items: center;     /* 垂直居中 */
		background-color: #66BB6A;
		height: calc(100% - 20rpx); 
		margin: 10rpx 10rpx 10rpx 10rpx;
		// padding: 10rpx;
		vertical-align: middle;
		text-align: center;
		border-radius: 8px;  
		color: white;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1),
		              0 6px 20px rgba(0, 0, 0, 0.1); 
	}  
	.v_item_success{ 
		flex: 1;
		font-size: 100rpx;
		display: flex;
		justify-content: center;  /* 水平居中 */
		align-items: center;     /* 垂直居中 */
		background-color: #18a058;
		height: calc(100% - 20rpx); 
		margin: 10rpx 10rpx 10rpx 10rpx;
		// padding: 10rpx;
		vertical-align: middle;
		text-align: center;
		border-radius: 8px;  
		color: white;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1),
		              0 6px 20px rgba(0, 0, 0, 0.1); 
	}  
	.v_item_faild{
		flex: 1;
		font-size: 100rpx;
		display: flex;
		justify-content: center;  /* 水平居中 */
		align-items: center;     /* 垂直居中 */
		background-color: #ff0000;
		height: calc(100% - 20rpx); 
		margin: 10rpx 10rpx 10rpx 10rpx;
		// padding: 10rpx;
		vertical-align: middle;
		text-align: center;
		border-radius: 8px;  
		color: white;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1),
		              0 6px 20px rgba(0, 0, 0, 0.1); 
	}
	
	.numPopup{
		background: #fff;
		// padding: 30rpx;
		border-radius: 30rpx 30rpx 30rpx 30rpx;
		width: 80vw;
		max-height: 60vh;
		overflow: hidden; 
		font-size: 30rpx;
	}
	
	
	.hintTextMainClass{
		// background-color: red;
		display: flex;
		// justify-content: space-between; /* 左右分开对齐 */
		align-items: center; /* 如果需要垂直居中可以加上这一行 */ 
		text-align: center; 
		display: flex; 
		justify-content: center; 
		.hintTextClass{
			display: inline-block; 
			text-align: center;
			margin: auto 100rpx;
			font-size: $text-font-size-1;
			// background-color: #18a058;
		}
	}
	
	.gridNumMainCls {
	  display: flex;
	  justify-content: space-between;
	  align-items: center;
	  padding: 12px 16px;
	  margin-bottom: 8px;
	  background-color: #ffffff;
	  border-radius: 8px;
	  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
	  transition: all 0.3s ease;
	}
	
	.gridNumMainCls:active {
	  background-color: #f5f5f5;
	}
	
	.gridNumLeftClss {
	  flex: 1;
	  min-width: 0; /* 防止内容溢出 */
	}
	
	.gridNumTitleCls {
	  font-size: 16px;
	  font-weight: 500;
	  color: #333333;
	  margin-bottom: 4px;
	  white-space: nowrap;
	  overflow: hidden;
	  text-overflow: ellipsis;
	}
	
	.gridNumNoteCls {
	  font-size: 12px;
	  color: #999999;
	  white-space: nowrap;
	  overflow: hidden;
	  text-overflow: ellipsis;
	}
	
	.gridNumRightCls {
	  margin-left: 16px;
	  font-size: 14px;
	  color: #f56c6c;
	  padding: 4px 8px;
	  border-radius: 4px;
	  background-color: rgba(245, 108, 108, 0.1);
	  flex-shrink: 0; /* 防止被压缩 */
	}
</style>

