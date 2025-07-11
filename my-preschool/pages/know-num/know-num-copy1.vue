<template>
  <view class="container">
    <picker mode="selector" :range="gameLevels" range-key="title" @change="onLevelChange">
      <view class="picker">当前选择：{{currentLevel.title}}</view>
    </picker>
    
    <view class="game-info">
      <text>时间: {{timeLeft}}s</text>
      <text>分数: {{score}}</text>
    </view>
    
    <view class="game-board" :style="{width: boardWidth + 'px'}">
      <view 
        v-for="(item, index) in boardItems" 
        :key="index"
        class="game-item"
        :class="{
          'active': activeIndexes.includes(index),
          'hidden': item.hidden
        }"
        :style="{
          width: itemSize + 'px',
          height: itemSize + 'px',
          fontSize: currentLevel.fontSize,
          lineHeight: itemSize + 'px'
        }"
        @click="handleItemClick(index)"
      >
        {{item.hidden ? '' : item.value}}
      </view>
      
      <canvas 
        canvas-id="lineCanvas" 
        class="line-canvas"
        :style="{width: boardWidth + 'px', height: boardWidth + 'px'}"
      ></canvas>
    </view>
  </view>
</template>
<script setup>
import { ref, computed, onMounted, watch } from 'vue'

const gameLevels = ref([
  {"id":"cc1","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"100rpx","gameTime":"15","colNum":"3","numSum":"9","title":"3 ✖️ 3","note":"1-9的数字","rightText":""},
  {"id":"cc2","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"80rpx","gameTime":"60","colNum":"4","numSum":"16","title":"4 ✖️ 4","note":"1-16的数字","rightText":""},
  {"id":"cc3","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"70rpx","gameTime":"100","colNum":"5","numSum":"25","title":"5 ✖️ 5","note":"1-25的数字","rightText":""},
  {"id":"cc4","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"60rpx","gameTime":"300","colNum":"6","numSum":"36","title":"6 ✖️ 6","note":"1-36的数字","rightText":""},
  {"id":"cc5","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"50rpx","gameTime":"500","colNum":"7","numSum":"49","title":"7 ✖️ 7","note":"1-49的数字","rightText":""},
  {"id":"cc6","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"30rpx","gameTime":"800","colNum":"8","numSum":"64","title":"8 ✖️ 8","note":"1-64的数字","rightText":""},
  {"id":"cc7","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"30rpx","gameTime":"1000","colNum":"9","numSum":"81","title":"9 ✖️ 9","note":"1-81的数字","rightText":""},
  {"id":"cc8","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"30rpx","gameTime":"1800","colNum":"10","numSum":"100","title":"10 ✖️ 10","note":"1-100的数字","rightText":""}
])

const currentLevel = ref(gameLevels.value[0])
const boardItems = ref([])
const activeIndexes = ref([])
const score = ref(0)
const timeLeft = ref(0)
const gameTimer = ref(null)
const itemSize = ref(80)
const boardWidth = computed(() => itemSize.value * currentLevel.value.colNum)

// 初始化游戏
const initGame = () => {
  clearInterval(gameTimer.value)
  timeLeft.value = parseInt(currentLevel.value.gameTime)
  score.value = 0
  activeIndexes.value = []
  generateBoard()
  startTimer()
}

// 生成游戏板
const generateBoard = () => {
  const colNum = parseInt(currentLevel.value.colNum)
  const numSum = parseInt(currentLevel.value.numSum)
  
  // 生成数字对
  let numbers = []
  for (let i = 1; i <= numSum / 2; i++) {
    numbers.push(i, i)
  }
  
  // 随机打乱数字
  numbers = numbers.sort(() => Math.random() - 0.5)
  
  // 创建游戏项
  boardItems.value = numbers.map(value => ({
    value,
    hidden: false
  }))
}

// 处理点击事件
const handleItemClick = (index) => {
  const item = boardItems.value[index]
  
  // 如果已经隐藏或已经是激活状态，则忽略
  if (item.hidden || activeIndexes.value.includes(index)) return
  
  // 如果已经有两个激活的项，则忽略
  if (activeIndexes.value.length >= 2) return
  
  // 添加到激活项
  activeIndexes.value.push(index)
  
  // 如果有两个激活项，检查是否匹配
  if (activeIndexes.value.length === 2) {
    const [firstIndex, secondIndex] = activeIndexes.value
    const firstItem = boardItems.value[firstIndex]
    const secondItem = boardItems.value[secondIndex]
    
    if (firstItem.value === secondItem.value) {
      // 匹配成功，画线
      drawLine(firstIndex, secondIndex)
      
      // 1秒后隐藏匹配的项
      setTimeout(() => {
        firstItem.hidden = true
        secondItem.hidden = true
        activeIndexes.value = []
        score.value += 10
        
        // 检查游戏是否结束
        checkGameEnd()
      }, 1000)
    } else {
      // 不匹配，1秒后取消激活状态
      setTimeout(() => {
        activeIndexes.value = []
      }, 1000)
    }
  }
}

// 画连接线
const drawLine = (index1, index2) => {
  const ctx = uni.createCanvasContext('lineCanvas', this)
  const colNum = parseInt(currentLevel.value.colNum)
  
  // 计算两个点的坐标
  const x1 = (index1 % colNum) * itemSize.value + itemSize.value / 2
  const y1 = Math.floor(index1 / colNum) * itemSize.value + itemSize.value / 2
  const x2 = (index2 % colNum) * itemSize.value + itemSize.value / 2
  const y2 = Math.floor(index2 / colNum) * itemSize.value + itemSize.value / 2
  
  // 设置线条样式
  ctx.setStrokeStyle('#ff0000')
  ctx.setLineWidth(4)
  ctx.beginPath()
  ctx.moveTo(x1, y1)
  ctx.lineTo(x2, y2)
  ctx.stroke()
  ctx.draw()
  
  // 1秒后清除线条
  setTimeout(() => {
    ctx.clearRect(0, 0, boardWidth.value, boardWidth.value)
    ctx.draw()
  }, 1000)
}

// 开始计时器
const startTimer = () => {
  gameTimer.value = setInterval(() => {
    timeLeft.value--
    
    if (timeLeft.value <= 0) {
      clearInterval(gameTimer.value)
      uni.showToast({
        title: '时间到！游戏结束',
        icon: 'none'
      })
    }
  }, 1000)
}

// 检查游戏是否结束
const checkGameEnd = () => {
  const allHidden = boardItems.value.every(item => item.hidden)
  if (allHidden) {
    clearInterval(gameTimer.value)
    uni.showToast({
      title: '恭喜你完成了所有配对！',
      icon: 'none'
    })
  }
}

// 关卡变更处理
const onLevelChange = (e) => {
  currentLevel.value = gameLevels.value[e.detail.value]
  initGame()
}

// 初始化
onMounted(() => {
  initGame()
})
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20rpx;
}

.picker {
  padding: 20rpx;
  background-color: #f0f0f0;
  border-radius: 10rpx;
  margin-bottom: 20rpx;
}

.game-info {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 20rpx;
}

.game-board {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  background-color: #f8f8f8;
  border-radius: 10rpx;
}

.game-item {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
  border: 1rpx solid #ddd;
  box-sizing: border-box;
  text-align: center;
  transition: all 0.3s;
}

.game-item.active {
  background-color: #ffe0b2;
}

.game-item.hidden {
  visibility: hidden;
}

.line-canvas {
  position: absolute;
  top: 0;
  left: 0;
  pointer-events: none;
}
</style>