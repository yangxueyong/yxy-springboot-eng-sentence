<template>
<view class="box">
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
	<!-- <my-firework ref="myFireWorkComponent"></my-firework> -->
	
	<view class="gameCls">
		<view class="changeNumCls"> 
			<view class="changeNumRightCls">
				<view class="changeNumTextCls" @click="showGameScoreRank" v-if="btnShowGameScoreRank">排行榜</view>
				<view class="changeNumTextCls" @click="refreshChangeNum">重来</view>
				<view class="changeNumTextCls" @click="funChangeGameType">{{changeGameTypeText}}</view>
				<view class="changeNumTextCls" @click="funChangeNum">{{changeNumText}}</view>
			</view>
		</view>
		
		<view class="countdownTextMainCls"> 
			<rich-text v-if="!wantFindImg" class="hintTextClass" :nodes="wantFindText"></rich-text>
			<view v-if="wantFindImg" class="hintTextClass">
				<text class="findTextCls">找到 </text> 
				<image class="findImgCls" :src="wantFindImg" mode="aspectFill"></image>
			</view>
			<view class="countdownTextCls">
				<time-count-down
				  ref="myTimeCountDown"
				  :seconds="countdownNum" 
				  :auto-start="true" 
				  @countDownFinish="onTimeFinish"></time-count-down>
			</view>
			<view class="levelTextCls">
				第{{levelText}}关
			</view>
		</view>
		
		 <view class="v1" ref="v1" > 
			<l-drag 
			:list="dataList" 
			:column="gridColumn"  
			:gridHeight="gridHeight" 
			:disabled="gridDisabled"> 
					<!-- // 每一项的插槽 grid 的 content 您传入的数据 -->
					<template #grid="{active, content}" class="v1_1"> 
						<!-- // grid.active 是否为当前拖拽项目 根据自己需要写样式 -->
							<view 
							@click="onNumClick(content)"
							class="v_item" 
							:class="{
							        'flash-animation-a': content.flashA,
							        'flash-animation-b': content.flashB,
							        'keep-scaled': content.hasClicked
							      }"
							:style="{fontSize: content.fontSize}">
								<view class="dataTextCls" v-if="content.show || content.mainShow || content.otherShow">  
									<image class="dataTextImgCls" v-if="content.imgSrc" :src="content.imgSrc" mode="aspectFit"></image>
									<view class="dataTextNumCls" v-if="content.content" >{{content.content}}</view>
									<view class="dataTextNumCls" v-if="content.backColor" :style="{backgroundColor: content.backColor}"></view>
								</view> 
								
								<!-- <view class="dataTextCls" > 
									<view class="dataTextNumCls">abc</view>
								</view> -->
							</view> 
					</template> 
			</l-drag> 
		 </view>
		 <view class="hintTextMainClass">
			 <rich-text class="hintTextClass" :nodes="resultText">
				 
			 </rich-text> 
			 <view class="findTextClass">
			 	找到{{findText}}个
			 </view>
		 </view>
		<uni-popup ref="numPopup" :is-mask-click="true"  > 
			<scroll-view class="numPopup" scroll-y="true">
				<uni-section title="请选择数字类型" type="line">
					<uni-list v-for="item in changeGridList" :key="item.id">
						<view @click="onChangeGridType(item.id)" class="gridNumMainCls">
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
			<custom-set-memory
			ref="customMumSet"
			@customNumFinish="onFunCustomNumFinish" ></custom-set-memory>
		</uni-popup>
		<uni-popup ref="gameTypePopup" :is-mask-click="true"  >
			<scroll-view class="numPopup" scroll-y="true"> 
				<!-- <scroll-view class="container" scroll-y="true"> -->
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
				<!-- </scroll-view> -->
			</scroll-view>
		</uni-popup>
	  </view>
		
	  <!-- <view class="fireworkCls">
		  <template>
			<my-firework ref="myFireWorkComponent"></my-firework>
		  </template>
	  </view> -->
 </view> 
</template>

<script setup> 
 import {ref, onMounted,nextTick,onUnmounted} from "vue"; 
 import {getRandomItems,getRandomItem,getRandomItemValue,getRandomInRangeExcluding} from "@/common/utils/common.js"
 import {playAll_failVoice,playAll_successVoice,playItem_failVoice,playItem_successVoice} from "@/common/utils/request.js"
 import {startFirework,endFirework,showCanvas,canvasWidth,canvasHeight} from "@/common/utils/firework.js";
 import {apiGetGameTypeList,apiGetGameAnimalList,apiGetGameColorList,apiGetNumLevelList,apiSaveGameScore} from "@/common/api/apis.js";
 import { onShow, onHide } from '@dcloudio/uni-app';
 import {onShareAppMessage,onReachBottom,onPullDownRefresh} from "@dcloudio/uni-app";
 
 let kk = ref(null);
 let myFireWorkComponent = ref(null);
// 存储我选择的游戏数字
let storeKey = "myMemoryChangeItem";
// 存储我选择的游戏方式
let storeGameTypeKey = "myMemoryChangeGameType";
// 我自定义的游戏数字
let storeMyGameNumKey = "myMemoryGameNum";
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
const gridDisabled = ref(true);
// 数字层的高度
const gridHeight = ref(150);
// 游戏结束后的提示语
const resultText = ref("");
//需要找到什么
const wantFindText = ref("");
const wantFindImg = ref(null);
//找到了多少
const findText = ref("");
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
const customMumSet = ref(null);
//目前已经拖拽了成功了多少个
let lastDragSuccessNum = 0;
// 当前的级别
let currentLevel = 1;
let usedColorSet = new Set(); 
let usedAnimalSet = new Set(); 

// 当前正在玩的关卡
let currentGameItem = null;
let currentGameTypeItem = null;
//当前的关卡信息
const levelText = ref("");

//关卡
const changeGridList = ref([]); 
//分享给好友
onShareAppMessage((e)=>{
	return {
		title:"小朋友认数字来啦~",
		path:"/pages/num-memory/num-memory"
	}
})
	
//当前页面被隐藏
onHide(() => {
	myTimeCountDown.value.pauseInterval(); 
});

// 页面被展示
onShow(() => {
	myTimeCountDown.value.resumeInterval(); 
});

const getChangeGridList =async(code)=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetNumLevelList({ "type":"memory" });  
	changeGridList.value = res.data; 
	console.log("得到的数据===>", res.data);
	uni.hideLoading(); 
	firstInit();
}
getChangeGridList();

// 游戏类型
const changeGameList = ref([]);


const getChangeGameList =async(code)=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetGameTypeList({ "type":"memory" });  
	changeGameList.value = res.data; 
	console.log("获取到的游戏类型->",res.data);
	uni.hideLoading(); 
	firstInit();
}
getChangeGameList();

// 颜色
const changeColorList = ref([]);

const getChangeColorList =async(code)=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetGameColorList({ });  
	changeColorList.value = res.data; 
	uni.hideLoading(); 
	firstInit();
}
getChangeColorList();

// 动物
const changeAnimalList = ref([]);
const getChangeAnimalList =async(code)=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetGameAnimalList({ });  
	changeAnimalList.value = res.data; 
	uni.hideLoading(); 
	firstInit();
}
getChangeAnimalList();

const saveGameScore =async(game_level_id, game_type_id, consume_time)=>{ 
	
	console.log("game_level_id",game_level_id);
	console.log("game_type_id",game_type_id);
	console.log("consume_time",consume_time);
	
	let res =await apiSaveGameScore({ "gameLevelId":game_level_id, "gameTypeId":game_type_id, "consumeTime":consume_time });  
}

// 删除我的矩阵
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
	
	currentLevel = 1;
	currentGameItem = null;
	usedColorSet = new Set();
	init();
}

function onChangeGridType(id){
	currentLevel = 1;
	currentGameItem = null;
	usedColorSet = new Set();
	changeGridType(id);
}
//更改选择的矩阵时
function changeGridType(id){  
	endFirework();
	clickNumList = ref([]);
	myTimeDown.value = false;
	resultText.value = "";
	wantFindText.value = "";
	wantFindImg.value = null;
	lastDragSuccessNum = 0;
	currentGameSuccess = false; 
	
	let item = changeGridMap.value.get(id); 
	let typeKey = item.typeKey;
	
	if(typeKey == "custom"){
		customMumPopup.value.open();
		customMumSet.value.setnumv(currentGameType);
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
	let numSum = item.numSum ;
	changeNumText.value = item.title;
	countdownNum.value = item.gameTime;
	let beginNum = item.beginNum;
	changeGameTypeText.value = currentGameTypeItem.title;
	let fontSize = item.fontSize;
	// console.log("得到的时间->",item.gameTime);
	calcGridItemWidth();
	let list = [];
	for (var i = 1; i <= numSum; i++) {
		let listItem = {"itemCls":"v_item","fontSize":fontSize}; 
		list.push(listItem);
	} 
	
	// console.log("currentGameItem.id->", currentGameItem != null ? currentGameItem.id : "") ;
	// console.log("item.id->", item.id);
	
	if(currentGameItem == null || currentGameItem.id != item.id){
		//开始计时
		myTimeCountDown.value.start(countdownNum.value); 
	}
	
	currentGameItem = item;
	
	randData(list, item); 
}

let yesClickFlag = false;
// 重新计算数据
function randData(list, currentItem){
	yesClickFlag = false;
	
	//随机获取数据(就是随机获取哪些元素被标记)
	let newIndexSet = getRandomItems(list, parseInt(currentLevel) - 1 + parseInt(currentGameItem.beginBlock) );  
	
	//随机的颜色
	let colorIndex = 0; 
	let colorValue = "";
	let content = "";
	//数字
	let currentNum = currentLevel + (currentItem.beginNum - 1);
	
	//动物
	let animalIndex = 0;
	let animalValue = "";
	if(currentGameType == "a1" || currentGameType == "a3" ){
		colorIndex = getRandomItem(changeColorList.value, usedColorSet);
		colorValue = changeColorList.value[colorIndex].value;
		// content = "找到<span style='background-color: "+colorValue+";font-weight: bold;color: #fff;'>" + changeColorList.value[colorIndex].content + "</span>";
		content = "找到 <span style='background-color: "+colorValue+";font-weight: bold;color: "+colorValue+";width:10rpx;height:10rpx;box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1),0 6px 20px rgba(0, 0, 0, 0.1); '>123</span>";
		wantFindText.value = content;
	}else if(currentGameType == "a2" || currentGameType == "a4" ){
		content = "找到数字 " + currentNum; 
		wantFindText.value = content;
	}else if(currentGameType == "a5" || currentGameType == "a6"){
		animalIndex = getRandomItem(changeAnimalList.value, usedAnimalSet);
		animalValue = changeAnimalList.value[animalIndex].imgSrc;
		// content = "找到 <span style='width:10rpx;height:10rpx;backgroundImage: url("+animalValue+")' ></span>"; 
		wantFindImg.value = animalValue;
	}else if(currentGameType == "a7" || currentGameType == "a8"){
		animalIndex = getRandomItem(changeAnimalList.value, usedAnimalSet);
		animalValue = changeAnimalList.value[animalIndex].shadowSrc;
		let imgSrc = changeAnimalList.value[animalIndex].imgSrc;
		// content = "找到 <span style='width:10rpx;height:10rpx;backgroundImage: url("+animalValue+")' ></span>"; 
		wantFindImg.value = imgSrc;
	}
	console.log("文本->",content);
	levelText.value = currentLevel + "/" + currentItem.levelSumNum;
	
	findText.value = 0 + "/" + newIndexSet.size;//本关卡共" + newIndexSet.size + "个，已找到0个，还剩" + newIndexSet.size + "个";
	// 遍历Set
	newIndexSet.forEach((index) => { 
		list[index].show = true;
	  //颜色
	  if(currentGameType == "a1" || currentGameType == "a3"){
		  list[index].backColor = colorValue;
	  }else if(currentGameType == "a2" || currentGameType == "a4"){
		  list[index].content = currentNum;
	  }else if(currentGameType == "a5" || currentGameType == "a6" || currentGameType == "a7" || currentGameType == "a8"){
		  list[index].imgSrc = animalValue;
	  }
	}); 
	//这一步会先展示被标记的元素
	dataList.value = list;
	
	//这一步将标记的元素隐藏掉
	let tt = currentItem.levelHideTime * 0.8 * currentLevel ; 
	
	//隐藏正确答案的类型
	if(currentGameType == "a1" || currentGameType == "a2" || currentGameType == "a6" || currentGameType == "a7"){
		setTimeout(()=>{
			newIndexSet.forEach((index) => { 
				dataList.value[index].show = false;
			});
			yesClickFlag = true;
		}, tt);
		
		//不隐藏正确答案
	}else{ 
		for (var i = 0; i < dataList.value.length; i++) {
			let data = dataList.value[i]; 
			if(!newIndexSet.has(i)){
				if(currentGameType == "a3" ){ 
					let otherColorIndex = getRandomInRangeExcluding(0, changeColorList.value.length - 1 ,colorIndex);
					let otherColorValue = changeColorList.value[otherColorIndex].value; 
					data.backColor = otherColorValue;
				}else if(currentGameType == "a4" ){
					let num = getRandomInRangeExcluding(1, dataList.value.length, currentNum);
					data.content = num;
				}else if(currentGameType == "a5" ){ 
					let otherAnimalIndex = getRandomInRangeExcluding(0, changeAnimalList.value.length - 1 ,animalIndex);
					let otherAnimalValue = changeAnimalList.value[otherAnimalIndex].imgSrc; 
					data.imgSrc = otherAnimalValue;
				}else if(currentGameType == "a8" ){ 
					let otherAnimalIndex = getRandomInRangeExcluding(0, changeAnimalList.value.length - 1 ,animalIndex);
					let otherAnimalValue = changeAnimalList.value[otherAnimalIndex].shadowSrc; 
					data.imgSrc = otherAnimalValue;
				}
				data.otherShow = true;
			}else{
				console.log("撞到了->",i);
				data.mainShow = true;
			}  
		}
		newIndexSet.forEach((index) => {
			dataList.value[index].show = false; 
		});
		yesClickFlag = true;
	}
	
}

//切换游戏类型
function changeGameType(item){ 
	currentLevel = 1;
	currentGameItem = null;
	usedColorSet = new Set();
	usedAnimalSet = new Set();
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

 
//计算矩阵元素的宽度
function calcGridItemWidth(){
	const SYSTEM_INFO = uni.getSystemInfoSync();
	let itemHeight = (SYSTEM_INFO.windowWidth - 100) / gridColumn.value;
	gridHeight.value = itemHeight;
}
//加载完成时重新发起
 function firstInit(){   
	 if(changeGridList.value == null || changeGameList.value == null || changeColorList.value == null || changeAnimalList.value == null){
		 return;
	 }
	 
	 if(changeGridList.value.length <= 0 || changeGameList.value.length <= 0 || changeColorList.value.length <= 0 || changeAnimalList.value.length <= 0){
	 	 return;
	 }
	  
	let myGameNumList = uni.getStorageSync(storeMyGameNumKey);
	if(myGameNumList != null && myGameNumList.length > 0){
		changeGridList.value = [ ...myGameNumList, ...changeGridList.value];
	}
	console.log("--->",changeGridList);
	init(); 
 }; 
 

 
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
	 usedColorSet = new Set();
	 usedAnimalSet = new Set();
	 myTimeDown.value = true; 
	 calcDragSuccess(true);
 }
// 拖拽后新的数据
const newList = ref([]) ;
//计算拖动的完成
function calcDragSuccess(isTimeFinish){  
	if(!currentGameSuccess){  
		let successLevelNum = currentLevel - 1;
		let failureNum = currentGameItem.levelSumNum - successLevelNum; 
		resultText.value = "时间到了,再接再厉";
		playAll_failVoice();
	} 
}
 
// //拖动了元素之后
// function change(v){
// 	// console.log("拖拽了->",v);
// 	newList.value = v;  
// 	calcDragSuccess(false);
// }

let clickNumList = ref([]);
function onNumClick(content){ 
	// 如果无法点击的话
	if(!yesClickFlag){
		return;
	}
	if(myTimeDown.value){
		return;
	}
	if(content.show){
		return;
	} 
	
	if((content.backColor || content.content || content.imgSrc) && !content.show && !content.otherShow){ 
		content.show = true;
		console.log("找对了");
		// type b的闪缩一次并保持
		content.flashB = true;
		content.hasClicked = true;
		setTimeout(() => {
		  content.flashB = false;
		}, 500); // 动画持续时间
		
		//找对了
		playItem_successVoice();
	}else{
		 // type a的闪缩几次效果
		content.flashA = true;
		setTimeout(() => {
		  content.flashA = false;
		}, 1000); // 动画持续时间
		
		console.log("找错了");
		playItem_failVoice();
	} 
	
	let allSuccess = true;
	let findSuccess = 0;
	let findFaild = 0;
	for (var i = 0; i < dataList.value.length; i++) {
		let item = dataList.value[i]; 
		if((item.backColor || item.content || item.imgSrc) && !item.otherShow) {
			//没有被找到的
			if(!item.show){
				allSuccess = false; 
				findFaild ++;
			}else{
				findSuccess ++;
			}
		} 
	} 
	
	findText.value = findSuccess + "/" + (findSuccess + findFaild);
	
	//当前关卡所有的都被找到了
	if(allSuccess){
		yesClickFlag = false; 
		success();
	}
	
}

let currentGameSuccess = false;
let timer = null;
//挑战成功
function success(){    
	if(currentLevel >= currentGameItem.levelSumNum){
		myStartFirework();
		//花费的时间
		let expendTime = myTimeCountDown.value.getExpendTime();
		// resultText.value = "恭喜你，花了" + expendTime + "，挑战成功";
		resultText.value = "恭喜你，挑战成功";
		//todo 可以播放音效
		playAll_successVoice();
		//保存成绩
		saveGameScore(currentGameItem.id, currentGameTypeItem.id, expendTime);
		//停止计时
		myTimeCountDown.value.stop();
		yesClickFlag = false;
		currentGameSuccess = true;
		usedColorSet = new Set();
		usedAnimalSet = new Set();
		if(timer != null){
			clearTimeout(timer);
		}
	}else{ 
		console.log("准备下一关")
		currentGameSuccess = false;
		timer = setTimeout(()=>{
			if(!myTimeDown.value){
				//加载下一关
				currentLevel ++;
				
				init();
			}
		},500);
	}
}
 
//打开选择矩阵的窗口
function funChangeNum(){
	numPopup.value.open();
}
function funChangeGameType(){
	gameTypePopup.value.open();
}
//打开排行榜
function showGameScoreRank(){
	uni.navigateTo({
		url:"/pages/game-score-rank/game-score-rank?gameLevelId=" + currentGameItem.id + "&gameTypeId=" + currentGameTypeItem.id + "&name=" + currentGameTypeItem.title + " - " + currentGameItem.title
	})
}
//重新开始
function refreshChangeNum(){
	uni.showModal({
		title: '提示',
		content: '确定重新开始吗？',
		success: function (res) {
			if (res.confirm) {
				currentLevel = 1;
				currentGameItem = null;
				usedColorSet = new Set();
				usedAnimalSet = new Set();
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
 
 setTimeout(()=>{
	 // myFireWorkComponent.value.startFirework();
 },1500);


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
	.container{
		width: 100vw;
		height: 100vh;
		background-color: red;
	}
	
	.findImgCls{
		width: 50rpx;
		height: 50rpx;
		border-radius: 8px;  
		margin-left: 10rpx;
	}
	
	.canvas {
	  width: 100%;
	  height: 100%;
	   // background-color: red;
	}
	
	
	.gameCls{
		// position: absolute;
		width: 100%;
		height: 100%;
		/* 其他样式 */
		// background-color: #f0f0f0;
		// display: flex;
		// justify-content: center;
		// align-items: center;
		// font-size: 24px;
	}
	.fireworkCls {
		position: absolute;
		/* 通过调整这些值控制覆盖位置 */
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		// background-color: #18a058;
		z-index: 2;  /* 确保这个元素在上层 */
		/* 其他样式 */ 
		color: white; 
		border-radius: 5px;
		font-size: 16px;
		width: 100%;
		height: 100%;
	}
	.countdownTextMainCls{
		// margin-top: 100rpx;
		// padding-top: 100rpx;
		display: flex;
		// justify-content: space-between; /* 左右分开对齐 */
		align-items: center; /* 如果需要垂直居中可以加上这一行 */ 
		text-align: center; 
		display: flex; 
		justify-content: center; 
		.hintTextClass{ 
			position: absolute; 
			left: 0; 
			margin-left: 50rpx;
			font-size: $text-font-size-1;
			// align-items: center;     /* 垂直居中对齐 */
			// justify-content: center; /* 水平居中对齐 */
			// background-color: #18a058;
			
			.findTextCls{
			  display: inline-flex;          /* 启用 Flex 布局 */
			  // align-items: baseline;    /* 垂直居中 */
			  // justify-content: center; /* 水平居中（可选） */
			  // height: 70rpx;          /* 必须设置高度 */
			  // background: #f0f0f0;    /* 方便观察效果 */
			  font-size: $text-font-size-1;
			}
			.findImgCls {
			  /* 可以根据需要设置图片大小 */
			  width: 70rpx;
			  height: 70rpx;
			  display: inline-flex; /* 让文本和图片内联排列 */
			    vertical-align: middle; /* 垂直对齐 */
			}
		}
		.countdownTextCls{ 
			 margin: 10rpx 10rpx; 
			  text-align: center;
			 color: $uni-color-success;
			 font-size: $text-font-size-1;
		}
		.levelTextCls {
		  position: absolute;     /* 绝对定位脱离 Flex 流 */
		  right: 0;               /* 居右 */
		  margin-right: 70rpx;    /* 可选，增加右边距 */
		  font-size: $text-font-size-1;
		  color: #333333;
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
		margin: 20rpx 50rpx;
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
		background:
				linear-gradient(to bottom,transparent,#fff 400rpx),
				linear-gradient(to right,#beecd8 20%,#F4E2D8);
		height: calc(100% - 20rpx); 
		margin: 10rpx 10rpx 10rpx 10rpx;
		// padding: 10rpx;
		vertical-align: middle;
		text-align: center;
		border-radius: 8px;  
		color: white;
		color: black;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1),
		              0 6px 20px rgba(0, 0, 0, 0.1); 
	}  
	.v_item_success{ 
		flex: 1;
		font-size: 100rpx;
		display: flex;
		justify-content: center;  /* 水平居中 */
		align-items: center;     /* 垂直居中 */ 
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
		  display: flex;           /* 启用 Flex 布局 */
		  justify-content: center; /* 主轴上居中 */
		  align-items: center;     /* 交叉轴上居中 */
		  position: relative;      /* 用于绝对定位 levelTextCls */
		  width: 100%;            /* 确保父容器宽度足够 */
		  height: 60rpx;          /* 示例高度，可根据需要调整 */ 
		  
		.hintTextClass{ 
			position: absolute; 
			left: 0;
			text-align: center; 
			justify-content: center;
			align-items: center;
			margin-left: 50rpx;
			font-size: 35rpx;  
			// background-color: #18a058;
		}
		.findTextClass{
			 position: absolute;     /* 绝对定位脱离 Flex 流 */
			  right: 0;               /* 居右 */
			  margin-right: 50rpx;    /* 可选，增加右边距 */
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
	.dataTextCls{
		width: 100%;
		height: 100%;
		border-radius: 8px;  
		// background-color: red;
		display: flex;
		text-align: center;
		justify-content: center;
		align-items: center;
		.dataTextNumCls{
			width: 100%;
			height: 100%;
			display: grid;
			place-items: center;  
			 border-radius: 8px;  
		}
		.dataTextImgCls{
			width: 80%;
			height: 80%;
			border-radius: 8px;   
			// margin: auto 0px;
			display: inline-flex; /* 让文本和图片内联排列 */
		    vertical-align: middle; /* 垂直对齐 */
		}
		
	}


	
	.flash-animation-a {
	  animation: flashScale 0.25s ease-in-out 3;
	}
	
	.flash-animation-b {
	  animation: flashScale 0.5s ease-in-out 1;
	}
	
	.keep-scaled {
	  transform: scale(0.9);
	  opacity: 0.8;
	  border: 1px solid red;
	  box-shadow: 0 10px 30px 2px rgba(0, 0, 0, 0.2),
	                0 15px 50px 4px rgba(0, 0, 0, 0.2);
	}
	
	@keyframes flashScale {
	  0% {
	    transform: scale(1);
	  }
	  50% {
	    transform: scale(0.9);
	  }
	  100% {
	    transform: scale(1);
	  }
	}
</style>

