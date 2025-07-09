<template>
  <scroll-view class="box" scroll-y="true">
	<view class="changeNumCls">
		<view class="changeNumRightCls">
			<view class="changeNumTextCls" @click="showGameScoreRank" v-if="btnShowGameScoreRank">排行榜</view>
			<view class="changeNumTextCls" @click="refreshChangeNum">重来</view>
			<view class="changeNumTextCls" @click="funChangeGameType">
				{{currentGameType.title}}
				<!-- <picker mode="selector" :range="gameTypeList" range-key="title" @change="onGameTypeChange">
				  <view class="picker">{{currentGameType.title}}</view>
				</picker> -->
			</view>
			<view class="changeNumTextCls" @click="funChangeGameLevel">
				{{currentLevel.title}}
				<!-- <picker mode="selector" :range="gameLevels" range-key="title" @change="onLevelChange">
				  <view class="picker">{{currentLevel.title}}</view>
				</picker> -->
			</view>
		</view>
	</view>
    <view class="container"> 
    <!-- 游戏类型选择 -->
		<!-- <picker mode="selector" :range="gameTypeList" range-key="title" @change="onGameTypeChange">
		  <view class="picker">当前游戏：{{currentGameType.title}}</view>
		</picker> -->
		
		<!-- 游戏难度选择 -->
		<!-- <picker mode="selector" :range="gameLevels" range-key="title" @change="onLevelChange">
		  <view class="picker">当前难度：{{currentLevel.title}}</view>
		</picker> -->
		
		<view class="game-info">
		  <text>剩余: {{timeLeft}}秒</text>
		  <text>消除了: {{score}}/{{sumNum}}个</text>
		</view>
		
		<view class="game-board-wrapper" :style="{width: boardWidth + 'px'}">
		  <view class="game-board" :style="boardStyle">
			<view 
			  v-for="(item, index) in boardItems" 
			  :key="index"
			  class="game-item"
			  :class="{
				'active': activeIndexes.includes(index),
				'hidden': item.hidden,
				'matched': item.matched
			  }"
			  :style="getItemStyle(item)"
			  @click="handleItemClick(index)"
			>
			  <template v-if="currentGameType.id === 'kk1'">
				{{item.hidden ? '' : item.value}}
			  </template>
			  <template v-else-if="currentGameType.id === 'kk2'">
				<view class="color-item" :style="{backgroundColor: item.value}"></view>
			  </template>
			  <template v-else-if="currentGameType.id === 'kk3' || currentGameType.id === 'kk4'">
				<image :src="item.value" mode="aspectFit" class="animal-image"></image>
			  </template>
			</view>
		  </view>
		  
		  <canvas 
			canvas-id="lineCanvas" 
			class="line-canvas"
			:style="canvasStyle"
			@touchmove.prevent
			@touchstart.prevent
		  ></canvas>
		</view>
	  </view>
  </scroll-view>
  
  <uni-popup ref="levelPopup" :is-mask-click="true"  >
  	<scroll-view class="numPopup" scroll-y="true">
  		<uni-section title="请选择数字类型" type="line">
  			<uni-list v-for="item in gameLevels" :key="item.id">
  				<view @click="onChangeGameLevel(item)" class="gridNumMainCls">
  					<view class="gridNumLeftClss">
  						<view class="gridNumTitleCls">{{item.title}}</view>
  						<view class="gridNumNoteCls">{{item.note}}</view>
  					</view>
  					<view class="gridNumRightCls" @click.stop="rmoveMyGridlevel(item)" v-if="item.typeKey == 'myCustom'">
  						删除
  					</view>
  				 </view>
  			</uni-list>
  		</uni-section>
  	</scroll-view> 
  </uni-popup>
  <uni-popup ref="customMumPopup" :is-mask-click="true"  >
  	<custom-set-remove
  	ref="customMumSet"
  	@customNumFinish="onFunCustomNumFinish" ></custom-set-remove>
  </uni-popup>
  <uni-popup ref="gameTypePopup" :is-mask-click="true"  >
  	<scroll-view class="numPopup" scroll-y="true"> 
  		<!-- <scroll-view class="container" scroll-y="true"> -->
  			<uni-section title="请选择游戏类型" type="line">
  				<uni-list v-for="item in gameTypeList" :key="item.id">
  					<view @click="onChangeGameType(item)" >
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
</template>

<script setup>
 import { ref, computed, onMounted, watch, nextTick } from 'vue'
 import {playAll_failVoice,playAll_successVoice,playItem_failVoice,playItem_successVoice} from "@/common/utils/request.js"
 import {apiGetGameTypeList,apiGetGameAnimalList,apiGetGameColorList,apiGetNumLevelList,apiSaveGameScore} from "@/common/api/apis.js";
 import {onShareAppMessage,onReachBottom,onPullDownRefresh} from "@dcloudio/uni-app";
 
 //排行帮
 let btnShowGameScoreRank = ref(true);
// 游戏类型列表
const gameTypeList = ref([]);
// 难度等级
const gameLevels = ref([]);
// 颜色列表
const changeColorList = ref([]);
// 动物列表
const changeAnimalList = ref([]);
// 存储到缓存中的key
let storeMyRemoveGameLevelListKey = "myRemoveGameLevelListKey";

let storeChangeRemoveGameLevelKey = "myChangeRemoveGameLevelKey";
let storeChangeRemoveGameTypeKey = "myChangeRemoveGameTypeKey";

//分享给好友
onShareAppMessage((e)=>{
	return {
		title:"小朋友认数字来啦~",
		path:"/pages/know-num/know-num"
	}
})


const getgameTypeList =async(code)=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetGameTypeList({ "type":"remove" });  
	gameTypeList.value = res.data; 
	console.log("获取到的游戏类型->",res.data);
	uni.hideLoading();  
}

const getGameLevelList =async(code)=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetNumLevelList({ "type":"remove" });  
	gameLevels.value = res.data;  
	uni.hideLoading();  
} 

const getChangeColorList =async(code)=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetGameColorList({ });  
	changeColorList.value = res.data; 
	uni.hideLoading(); 
}

const getChangeAnimalList =async(code)=>{ 
	uni.showLoading({
		title:"加载中.."
	})
	let res =await apiGetGameAnimalList({ });  
	changeAnimalList.value = res.data; 
	uni.hideLoading(); 
}

// 保存成绩
const saveGameScore =async(game_level_id, game_type_id, consume_time)=>{ 
	
	console.log("game_level_id",game_level_id);
	console.log("game_type_id",game_type_id);
	console.log("consume_time",consume_time);
	
	let res =await apiSaveGameScore({ "gameLevelId":game_level_id, "gameTypeId":game_type_id, "consumeTime":consume_time });  
}


//刷新
function refreshChangeNum(){
	uni.showModal({
		title: '提示',
		content: '确定重新开始吗？',
		success: function (res) {
			if (res.confirm) {
				initGame();
			} else if (res.cancel) {
				console.log('用户点击取消');
			}
		}
	});
}

//打开排行榜
function showGameScoreRank(){
	uni.navigateTo({
		url:"/pages/game-score-rank/game-score-rank?gameLevelId=" + currentLevel.value.id 
		+ "&gameTypeId=" + currentGameType.value.id 
		+ "&name=" + currentGameType.value.title + " - " + currentLevel.value.title
	})
}

function funChangeGameType(){
	gameTypePopup.value.open();
}

function onChangeGameType(item){
	console.log("itemitemitemitemitemitem->",item);
	currentGameType.value = item;
	uni.setStorageSync(storeChangeRemoveGameTypeKey, item);
	gameTypePopup.value.close();
}

function funChangeGameLevel(){
	levelPopup.value.open();
}

// 是否展示排行
function showRank(){
	if(currentLevel.value.typeKey == "myCustom"){
		btnShowGameScoreRank.value = false;
	}else{
		btnShowGameScoreRank.value = true;
	}
}

function onChangeGameLevel(item){
	let typeKey = item.typeKey;
	console.log("你选择了->", item);
	if(typeKey == "custom"){
		customMumSet.value.setnumv(currentGameType.value.id);
		customMumPopup.value.open(); 
		levelPopup.value.close();
		return;
	} 
	
	currentLevel.value = item;
	uni.setStorageSync(storeChangeRemoveGameLevelKey, item);
	showRank();
	levelPopup.value.close(); 
}

// 删除我的矩阵
function rmoveMyGridlevel(param){ 
	gameLevels.value = gameLevels.value.filter(item => item.id !== param.id);
	let myGameLevelList = uni.getStorageSync(storeMyRemoveGameLevelListKey);
	if(myGameLevelList != null && myGameLevelList.length > 0){
		myGameLevelList = myGameLevelList.filter(item => item.id !== param.id);
		uni.setStorageSync(storeMyRemoveGameLevelListKey, myGameLevelList);
	}
	if(uni.getStorageSync(storeChangeRemoveGameLevelKey) == param.id){
		uni.removeStorageSync(storeChangeRemoveGameLevelKey);
	}
}

function onFunCustomNumFinish(e){
	console.log("你输入的内容->", e);
	customMumPopup.value.close(); 
	
	let myGameLevelList = uni.getStorageSync(storeMyRemoveGameLevelListKey) || [];
	myGameLevelList.unshift(e);
	gameLevels.value.unshift(e);
	
	//我自定义的所有级别
	uni.setStorageSync(storeMyRemoveGameLevelListKey, myGameLevelList); 
	//我选中的级别
	uni.setStorageSync(storeChangeRemoveGameLevelKey, e);
	//选中当前这个级别
	currentLevel.value = e;
}

// 设置数据
async function selectData(){ 
	let myGameLevelList = uni.getStorageSync(storeMyRemoveGameLevelListKey);
	if(myGameLevelList != null && myGameLevelList.length > 0){
		gameLevels.value = [ ...myGameLevelList, ...gameLevels.value];
	}
	
	currentLevel.value = uni.getStorageSync(storeChangeRemoveGameLevelKey) || gameLevels.value[0];
	
	currentGameType.value = uni.getStorageSync(storeChangeRemoveGameTypeKey) || gameTypeList.value[0];
	
}



let gameBeginTime = new Date();
const levelPopup = ref(null);
const gameTypePopup = ref(null);
const customMumPopup = ref(null);
const customMumSet = ref(null);

const currentGameType = ref({})
const currentLevel = ref({})
const boardItems = ref([])
const activeIndexes = ref([])
const score = ref(0)
const sumNum = ref(0)
const timeLeft = ref(0)
const gameTimer = ref(null)
const itemSize = ref(50) // 最小项目大小
const boardWidth = ref(300)
const boardHeight = ref(300)
const colNum = ref(3)
const rowNum = ref(3)
const screenInfo = ref({
  width: 375,
  height: 667,
  safeArea: { left: 0, right: 375, top: 0, bottom: 667 }
})
const isProcessing = ref(false) // 是否正在处理配对

// 计算样式
const boardStyle = computed(() => ({
  width: `${boardWidth.value}px`,
  height: `${boardHeight.value}px`,
  display: 'grid',
  gridTemplateColumns: `repeat(${colNum.value}, 1fr)`,
  gridTemplateRows: `repeat(${rowNum.value}, 1fr)`,
  gap: '1px'
}))

const canvasStyle = computed(() => ({
  width: `${boardWidth.value}px`,
  height: `${boardHeight.value}px`,
  position: 'absolute',
  top: 0,
  left: 0
}))

// 获取项目样式
const getItemStyle = (item) => {
  const style = {
    width: '100%',
    height: '100%',
    fontSize: `${Math.min(itemSize.value * 0.5, 24)}px`,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    aspectRatio: '1/1'
  }
  
  if (currentGameType.value.id === 'kk2' && !item.hidden) {
    style.backgroundColor = item.value
    style.border = '1px solid #eee'
  }
  
  return style
}

// 计算布局
const calculateLayout = () => {
  return new Promise((resolve) => {
    uni.getSystemInfo({
      success: (res) => {
        screenInfo.value = {
          width: res.windowWidth || res.screenWidth,
          height: res.windowHeight || res.screenHeight,
          safeArea: res.safeArea || {
            left: 0,
            right: res.windowWidth || res.screenWidth,
            top: 0,
            bottom: res.windowHeight || res.screenHeight
          }
        }
        
        // 根据当前难度设置项目总数
        const numSum = parseInt(currentLevel.value.numSum)
        
        // 确保项目总数是偶数
        const totalItems = numSum % 2 === 0 ? numSum : numSum - 1
        
        // 计算最佳行列数，使矩阵尽可能接近正方形
        const minItemSize = 50 // 最小项目大小
        const padding = 20 // 两边留白
        const maxBoardWidth = screenInfo.value.safeArea.right - screenInfo.value.safeArea.left - padding * 2
        
        // 初始列数为平方根取整
        let bestColNum = Math.floor(Math.sqrt(totalItems))
        let bestRowNum = Math.ceil(totalItems / bestColNum)
        
        // 调整列数以使矩阵更接近正方形且不超过最大宽度
        while (true) {
          const candidateColNum = bestColNum + 1
          const candidateRowNum = Math.ceil(totalItems / candidateColNum)
          
          // 检查是否更接近正方形且不超过最大宽度
          const currentRatio = Math.abs(bestRowNum - bestColNum)
          const newRatio = Math.abs(candidateRowNum - candidateColNum)
          
          const currentItemSize = Math.min(maxBoardWidth / bestColNum, (screenInfo.value.height * 0.6) / bestRowNum)
          const newItemSize = Math.min(maxBoardWidth / candidateColNum, (screenInfo.value.height * 0.6) / candidateRowNum)
          
          if (newRatio < currentRatio && newItemSize >= minItemSize) {
            bestColNum = candidateColNum
            bestRowNum = candidateRowNum
          } else {
            break
          }
        }
        
        // 确保项目大小不小于最小值
        let calculatedItemSize = Math.min(
          maxBoardWidth / bestColNum,
          (screenInfo.value.height * 0.6) / bestRowNum
        )
        
        calculatedItemSize = Math.max(calculatedItemSize, minItemSize)
        
        // 如果项目大小被限制为最小值，重新计算列数
        if (calculatedItemSize === minItemSize) {
          bestColNum = Math.min(Math.floor(maxBoardWidth / minItemSize), totalItems)
          bestRowNum = Math.ceil(totalItems / bestColNum)
          calculatedItemSize = minItemSize
        }
        
        // 更新布局参数
        colNum.value = bestColNum
        rowNum.value = bestRowNum
        itemSize.value = calculatedItemSize
        boardWidth.value = itemSize.value * colNum.value
        boardHeight.value = itemSize.value * rowNum.value
        
        resolve()
      }
    })
  })
}

// 重置游戏状态
const resetGameState = () => {
  clearInterval(gameTimer.value)
  gameTimer.value = null
  activeIndexes.value = []
  isProcessing.value = false
  gameBeginTime = new Date();
  clearCanvas()
}



// 初始化游戏
const initGame = async () => {
  showRank();
  // selectData();
  resetGameState();
  
  // if (currentGameType.value.id === 'kk1') {
    timeLeft.value = currentLevel.value.gameTime ? parseInt(currentLevel.value.gameTime) : 120;
  // } else {
    // timeLeft.value = 120 // 其他游戏类型固定2分钟
  // }
  
  score.value = 0
  
  await calculateLayout()
  generateBoard()
  startTimer()
}

// 生成游戏板
const generateBoard = () => {
  let pairs = []
  const totalItems = colNum.value * rowNum.value
  
  // 确保总项目数是偶数
  const evenTotalItems = totalItems % 2 === 0 ? totalItems : totalItems - 1
  
  switch (currentGameType.value.id) {
    case 'kk1': // 数字消消乐
      const numSum = Math.min(parseInt(currentLevel.value.numSum), evenTotalItems)
	  const beginNum = parseInt(currentLevel.value.beginNum) || 1;
      const numPairs = Math.floor(numSum / 2)
      for (let i = 1; i <= numPairs; i++) {
        pairs.push(i + (beginNum - 1), i + (beginNum - 1))
      }
      break;
      
    case 'kk2': // 颜色消消乐
      const colorCount = Math.min(changeColorList.value.length, Math.floor(evenTotalItems / 2))
      // 随机选择颜色并确保有足够的配对
      const selectedColors = []
      for (let i = 0; i < Math.ceil(evenTotalItems / 2 / colorCount); i++) {
        selectedColors.push(...[...changeColorList.value]
          .sort(() => Math.random() - 0.5)
          .slice(0, colorCount)
          .map(color => color.value))
      }
      
      // 确保有足够的配对
      for (let i = 0; i < Math.floor(evenTotalItems / 2); i++) {
        const color = selectedColors[i % selectedColors.length]
        pairs.push(color, color)
      }
      break;
      
    case 'kk3': // 动物消消乐
      const animalCount = Math.min(changeAnimalList.value.length, Math.floor(evenTotalItems / 2))
      // 随机选择动物并确保有足够的配对
      const selectedAnimals = []
      for (let i = 0; i < Math.ceil(evenTotalItems / 2 / animalCount); i++) {
        selectedAnimals.push(...[...changeAnimalList.value]
          .sort(() => Math.random() - 0.5)
          .slice(0, animalCount)
          .map(animal => animal.imgSrc))
      }
      
      // 确保有足够的配对
      for (let i = 0; i < Math.floor(evenTotalItems / 2); i++) {
        const animal = selectedAnimals[i % selectedAnimals.length]
        pairs.push(animal, animal)
      }
      break;
      
    case 'kk4': // 动物和影子消消乐
      const shadowAnimalCount = Math.min(changeAnimalList.value.length, Math.floor(evenTotalItems / 2))
      // 随机选择动物并确保有足够的配对
      const selectedShadowAnimals = []
      for (let i = 0; i < Math.ceil(evenTotalItems / 2 / shadowAnimalCount); i++) {
        selectedShadowAnimals.push(...[...changeAnimalList.value]
          .sort(() => Math.random() - 0.5)
          .slice(0, shadowAnimalCount))
      }
      
      // 确保有足够的配对
      for (let i = 0; i < Math.floor(evenTotalItems / 2); i++) {
        const animal = selectedShadowAnimals[i % selectedShadowAnimals.length]
        pairs.push(animal.imgSrc, animal.shadowSrc)
      }
      break;
  }
  
  // 随机打乱
  pairs = pairs.sort(() => Math.random() - 0.5)
  // 更新总个数
  sumNum.value = pairs.length;
  
  // 创建游戏项
  boardItems.value = pairs.map(value => ({
    value,
    hidden: false,
    matched: false,
    key: currentGameType.value.id === 'kk4' 
      ? value && value.includes('(1)') ? value.replace('(1)', '') : value
      : value
  }))
}

// 处理点击事件
const handleItemClick = (index) => {
  if (isProcessing.value) return // 如果正在处理配对，则忽略点击
  
  const item = boardItems.value[index]
  
  if (item.hidden || item.matched || activeIndexes.value.includes(index)) return
  
  activeIndexes.value.push(index)
  
  let lgt = activeIndexes.value.length;
   
  // 如果已经有2个激活项，先取最后两个
  if (activeIndexes.value.length >= 2) {
    activeIndexes.value = activeIndexes.value.slice(-2);
  }
  
 
  
  if (activeIndexes.value.length === 2) {
    const [firstIndex, secondIndex] = activeIndexes.value
    const firstItem = boardItems.value[firstIndex]
    const secondItem = boardItems.value[secondIndex]
    
    let isMatch = false
    
    if (currentGameType.value.id === 'kk4') {
      // 动物和影子模式特殊匹配逻辑
      const firstIsShadow = firstItem.value && firstItem.value.includes('(1)')
      const secondIsShadow = secondItem.value && secondItem.value.includes('(1)')
      
      if (firstIsShadow !== secondIsShadow) {
        const imgSrc = firstIsShadow ? secondItem.value : firstItem.value
        const shadowSrc = firstIsShadow ? firstItem.value : secondItem.value
        
        const baseImgSrc = imgSrc.replace(/\.[^/.]+$/, "").replace('(1)', '')
        const baseShadowSrc = shadowSrc.replace(/\.[^/.]+$/, "").replace('(1)', '')
        
        isMatch = baseShadowSrc.includes(baseImgSrc) || baseImgSrc.includes(baseShadowSrc)
      }
    } else {
      isMatch = firstItem.key === secondItem.key
    }
    
    if (isMatch) {
      // isProcessing.value = true
      drawLightningLine(firstIndex, secondIndex)
      
      // 立即隐藏元素，但保留视觉效果
      firstItem.matched = true
      secondItem.matched = true
	  activeIndexes.value = [];
      // 播放音效
	  playItem_successVoice();
	  
      setTimeout(() => {
        firstItem.hidden = true
        secondItem.hidden = true
        
        score.value = boardItems.value.filter(item => item.hidden === true).length;
		// boardItems.value
        isProcessing.value = false
        
        checkGameEnd()
      }, 500)
    } else {
	  // 播放音效
	  // playItem_failVoice();
      // isProcessing.value = true
      setTimeout(() => {
        // activeIndexes.value = []
        isProcessing.value = false
      }, 500)
    }
  }
}

// 绘制闪电线
const drawLightningLine = (index1, index2) => {
  const ctx = uni.createCanvasContext('lineCanvas', this)
  
  const x1 = (index1 % colNum.value) * itemSize.value + itemSize.value / 2
  const y1 = Math.floor(index1 / colNum.value) * itemSize.value + itemSize.value / 2
  const x2 = (index2 % colNum.value) * itemSize.value + itemSize.value / 2
  const y2 = Math.floor(index2 / colNum.value) * itemSize.value + itemSize.value / 2
  
  ctx.clearRect(0, 0, boardWidth.value, boardHeight.value)
  
  // 主闪电线
  ctx.setStrokeStyle('#ff0000')
  ctx.setLineWidth(3)
  ctx.setLineJoin('round')
  ctx.setLineCap('round')
  
  const segments = 10
  const points = [{x: x1, y: y1}]
  
  for (let i = 1; i < segments; i++) {
    const ratio = i / segments
    points.push({
      x: x1 + (x2 - x1) * ratio + (Math.random() - 0.5) * 20,
      y: y1 + (y2 - y1) * ratio + (Math.random() - 0.5) * 20
    })
  }
  
  points.push({x: x2, y: y2})
  
  ctx.beginPath()
  ctx.moveTo(points[0].x, points[0].y)
  points.slice(1).forEach(point => ctx.lineTo(point.x, point.y))
  ctx.stroke()
  
  // 发光效果
  ctx.setStrokeStyle('rgba(255, 102, 102, 0.7)')
  ctx.setLineWidth(1)
  ctx.beginPath()
  ctx.moveTo(points[0].x, points[0].y)
  points.slice(1).forEach(point => ctx.lineTo(point.x, point.y))
  ctx.stroke()
  
  // 粒子效果
  points.forEach((point, i) => {
    if (i < points.length - 1) {
      for (let j = 0; j < 3; j++) {
        const ratio = Math.random()
        const px = point.x + (points[i+1].x - point.x) * ratio
        const py = point.y + (points[i+1].y - point.y) * ratio
        
        ctx.beginPath()
        ctx.arc(px + (Math.random() - 0.5) * 10, py + (Math.random() - 0.5) * 10, 
               1 + Math.random() * 2, 0, Math.PI * 2)
        ctx.setFillStyle('rgba(255, 153, 153, 0.8)')
        ctx.fill()
      }
    }
  })
  
  ctx.draw()
  
  setTimeout(clearCanvas, 500)
}

// 清除画布
const clearCanvas = () => {
  const ctx = uni.createCanvasContext('lineCanvas', this)
  ctx.clearRect(0, 0, boardWidth.value, boardHeight.value)
  ctx.draw()
}

// 开始计时器
const startTimer = () => {
  resetGameState()
  gameTimer.value = setInterval(() => {
    timeLeft.value--
    
    if (timeLeft.value <= 0) {
	  playAll_failVoice();
	  isProcessing.value = true;
      clearInterval(gameTimer.value)
      uni.showToast({
        title: '时间到！游戏结束',
        icon: 'none'
      })
    }
  }, 1000)
}

// 检查游戏结束
const checkGameEnd = () => {
  const remainingItems = boardItems.value.filter(item => !item.hidden)
  if (remainingItems.length <= 1) {
	//先保存成绩
	saveGameScore(currentLevel.value.id, currentGameType.value.id, new Date().getTime() - gameBeginTime.getTime());
	playAll_successVoice();
    clearInterval(gameTimer.value);
    uni.showToast({
      title: `恭喜完成！`,
      icon: 'none'
    })
  }
}

// 游戏类型变更
const onGameTypeChange = (e) => {
  currentGameType.value = gameTypeList.value[e.detail.value]
  initGame()
}

// 难度变更
const onLevelChange = (e) => {
  currentLevel.value = gameLevels.value[e.detail.value]
  initGame()
}

// 初始化
onMounted(async () => {
	await getgameTypeList();
	await getGameLevelList();
	await getChangeColorList();
	await getChangeAnimalList();
	console.log("获取完了gameType：数据->",gameTypeList.value);
	
	await selectData(); 
	
	console.log("开始初始化游戏");
	// initGame();
})

// 监听变化
watch([currentGameType, currentLevel], () => {
  initGame()
}, { immediate: true })
</script>

<style lang="scss" scoped>
	.box{
		width: 100vw;
		height: 100vh; 
		background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
		overflow: hidden; 
	}
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px; 
  min-height: 100vh;
  box-sizing: border-box;
  // height: 100vh;
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
	
.picker {
  // padding: 12px 20px;
  // background-color: #4a6baf;
  // color: white;
  // border-radius: 25px;
  // margin: 8px 0;
  // font-weight: bold;
  // box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  // width: 90%;
  // max-width: 400px;
  // text-align: center;
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

.game-info {
  display: flex;
  justify-content: space-between;
  width: 90%;
  max-width: 400px;
  margin: 10px 0;
  font-size: 18px;
  font-weight: bold;
  color: #333;
  padding: 0 10px;
}

.game-board-wrapper {
  position: relative;
  margin: 10px 0;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.game-board {
  background-color: #e0e0e0;
}

.game-item {
  background-color: #ffffff;
  border: 1px solid #ddd;
  font-weight: bold;
  color: #333;
  border-radius: 5px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  overflow: hidden;
}

.game-item.active {
  background-color: #ffeb3b;
  transform: scale(0.95);
  box-shadow: 0 0 10px rgba(255, 235, 59, 0.7);
}

.game-item.hidden {
  visibility: hidden;
}

.game-item.matched {
  background-color: #8bc34a;
  color: white;
}

.line-canvas {
  pointer-events: none;
  z-index: 10;
}

.color-item {
  width: 80%;
  height: 80%;
  border-radius: 4px;
}

.animal-image {
  width: 80%;
  height: 80%;
  object-fit: contain;
}

/* 响应式调整 */
@media (max-width: 480px) {
  .picker {
    padding: 10px 15px;
    font-size: 14px;
  }
  
  .game-info {
    font-size: 16px;
  }
}

/* 动画效果 */
@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.05); }
  100% { transform: scale(1); }
}

.game-item:active {
  animation: pulse 0.3s ease;
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
	
</style>