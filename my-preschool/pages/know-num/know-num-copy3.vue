<template>
  <view class="container">
    <picker mode="selector" :range="gameLevels" range-key="title" @change="onLevelChange">
      <view class="picker">当前选择：{{currentLevel.title}}</view>
    </picker>
    
    <view class="game-info">
      <text>时间: {{timeLeft}}s</text>
      <text>分数: {{score}}</text>
    </view>
    
    <view class="game-board" :style="{width: boardWidth + 'px', height: boardHeight + 'px'}">
      <view 
        v-for="(item, index) in boardItems" 
        :key="index"
        class="game-item"
        :class="{
          'active': activeIndexes.includes(index),
          'hidden': item.hidden,
          'matched': item.matched
        }"
        :style="{
          width: itemSize + 'px',
          height: itemSize + 'px',
          fontSize: fontSize + 'px',
          lineHeight: itemSize + 'px'
        }"
        @click="handleItemClick(index)"
      >
        {{item.hidden ? '' : item.value}}
      </view>
      
      <canvas 
        canvas-id="lineCanvas" 
        class="line-canvas"
        :style="{width: boardWidth + 'px', height: boardHeight + 'px'}"
        @touchmove.prevent
        @touchstart.prevent
      ></canvas>
    </view>
  </view>
</template>

<script setup>
import { ref, computed, onMounted, watch, nextTick } from 'vue'

const gameLevels = ref([
  {"id":"cc1","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"24","gameTime":"15","numSum":"9","title":"初级","note":"1-9的数字","rightText":""},
  {"id":"cc2","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"20","gameTime":"60","numSum":"16","title":"中级","note":"1-16的数字","rightText":""},
  {"id":"cc3","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"18","gameTime":"100","numSum":"25","title":"高级","note":"1-25的数字","rightText":""},
  {"id":"cc4","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"16","gameTime":"300","numSum":"36","title":"专家","note":"1-36的数字","rightText":""},
  {"id":"cc5","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"14","gameTime":"500","numSum":"49","title":"大师","note":"1-49的数字","rightText":""},
  {"id":"cc6","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"14","gameTime":"500","numSum":"100","title":"超级","note":"1-100的数字","rightText":""}
])

const currentLevel = ref(gameLevels.value[0])
const boardItems = ref([])
const activeIndexes = ref([])
const score = ref(0)
const timeLeft = ref(0)
const gameTimer = ref(null)
const itemSize = ref(80)
const boardWidth = ref(300)
const boardHeight = ref(300)
const fontSize = ref(24)
const screenWidth = ref(375)
const screenHeight = ref(667)
const colNum = ref(3)

// 计算行列数和项目大小
const calculateLayout = () => {
  uni.getSystemInfo({
    success: (res) => {
      screenWidth.value = res.windowWidth
      screenHeight.value = res.windowHeight
      
      const numSum = parseInt(currentLevel.value.numSum)
      const minItemSize = 50 // 最小项目大小
      const padding = 20 // 两边留白
      const maxBoardWidth = screenWidth.value - padding * 2
      
      // 计算最佳列数，使矩阵尽可能接近正方形
      let bestColNum = Math.floor(Math.sqrt(numSum))
      let bestRowNum = Math.ceil(numSum / bestColNum)
      
      // 尝试调整列数，使矩阵更接近正方形
      while (true) {
        const candidateColNum = bestColNum + 1
        const candidateRowNum = Math.ceil(numSum / candidateColNum)
        
        // 如果调整后的矩阵更接近正方形且宽度不超过屏幕
        const currentRatio = Math.abs(bestRowNum - bestColNum)
        const newRatio = Math.abs(candidateRowNum - candidateColNum)
        
        const currentItemSize = Math.min(maxBoardWidth / bestColNum, (screenHeight.value * 0.6) / bestRowNum)
        const newItemSize = Math.min(maxBoardWidth / candidateColNum, (screenHeight.value * 0.6) / candidateRowNum)
        
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
        (screenHeight.value * 0.6) / bestRowNum
      )
      
      calculatedItemSize = Math.max(calculatedItemSize, minItemSize)
      
      // 如果项目大小被限制为最小值，重新计算列数
      if (calculatedItemSize === minItemSize) {
        bestColNum = Math.min(Math.floor(maxBoardWidth / minItemSize), numSum)
        bestRowNum = Math.ceil(numSum / bestColNum)
        calculatedItemSize = minItemSize
      }
      
      colNum.value = bestColNum
      itemSize.value = calculatedItemSize
      boardWidth.value = itemSize.value * colNum.value
      boardHeight.value = itemSize.value * bestRowNum
      fontSize.value = parseInt(currentLevel.value.fontSize)
      
      // 确保字体大小不会超过项目大小
      if (fontSize.value > itemSize.value * 0.6) {
        fontSize.value = itemSize.value * 0.6
      }
    }
  })
}

// 初始化游戏
const initGame = () => {
  clearInterval(gameTimer.value)
  timeLeft.value = parseInt(currentLevel.value.gameTime)
  score.value = 0
  activeIndexes.value = []
  
  calculateLayout()
  generateBoard()
  startTimer()
  clearCanvas()
}

// 生成游戏板
const generateBoard = () => {
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
    hidden: false,
    matched: false
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
      // 匹配成功，画闪电线
      drawLightningLine(firstIndex, secondIndex)
      
      // 1秒后标记为匹配并隐藏
      setTimeout(() => {
        firstItem.hidden = true
        secondItem.hidden = true
        firstItem.matched = true
        secondItem.matched = true
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

// 绘制闪电效果的连线
const drawLightningLine = (index1, index2) => {
  const ctx = uni.createCanvasContext('lineCanvas', this)
  
  // 计算两个点的坐标
  const x1 = (index1 % colNum.value) * itemSize.value + itemSize.value / 2
  const y1 = Math.floor(index1 / colNum.value) * itemSize.value + itemSize.value / 2
  const x2 = (index2 % colNum.value) * itemSize.value + itemSize.value / 2
  const y2 = Math.floor(index2 / colNum.value) * itemSize.value + itemSize.value / 2
  
  // 清除画布
  ctx.clearRect(0, 0, boardWidth.value, boardHeight.value)
  
  // 设置闪电效果 - 改为红色
  ctx.setStrokeStyle('#ff0000')
  ctx.setLineWidth(3)
  ctx.setLineJoin('round')
  ctx.setLineCap('round')
  
  // 生成闪电路径
  const segments = 10
  const points = []
  
  // 添加起点
  points.push({x: x1, y: y1})
  
  // 生成中间点
  for (let i = 1; i < segments; i++) {
    const ratio = i / segments
    const midX = x1 + (x2 - x1) * ratio
    const midY = y1 + (y2 - y1) * ratio
    
    // 添加随机偏移量
    const offsetX = (Math.random() - 0.5) * 20
    const offsetY = (Math.random() - 0.5) * 20
    
    points.push({
      x: midX + offsetX,
      y: midY + offsetY
    })
  }
  
  // 添加终点
  points.push({x: x2, y: y2})
  
  // 绘制闪电主路径
  ctx.beginPath()
  ctx.moveTo(points[0].x, points[0].y)
  
  for (let i = 1; i < points.length; i++) {
    ctx.lineTo(points[i].x, points[i].y)
  }
  
  ctx.stroke()
  
  // 绘制闪电发光效果
  ctx.setStrokeStyle('#ff6666')
  ctx.setLineWidth(1)
  ctx.beginPath()
  ctx.moveTo(points[0].x, points[0].y)
  
  for (let i = 1; i < points.length; i++) {
    ctx.lineTo(points[i].x, points[i].y)
  }
  
  ctx.stroke()
  
  // 绘制闪电粒子效果
  for (let i = 0; i < points.length - 1; i++) {
    const p1 = points[i]
    const p2 = points[i + 1]
    
    // 计算两点之间的随机粒子
    for (let j = 0; j < 3; j++) {
      const ratio = Math.random()
      const px = p1.x + (p2.x - p1.x) * ratio
      const py = p1.y + (p2.y - p1.y) * ratio
      
      // 随机偏移
      const offsetX = (Math.random() - 0.5) * 10
      const offsetY = (Math.random() - 0.5) * 10
      
      ctx.beginPath()
      ctx.arc(px + offsetX, py + offsetY, 1 + Math.random() * 2, 0, Math.PI * 2)
      ctx.setFillStyle('#ff9999')
      ctx.fill()
    }
  }
  
  ctx.draw()
  
  // 1.5秒后清除线条
  setTimeout(() => {
    clearCanvas()
  }, 1000)
}

// 清除画布
const clearCanvas = () => {
  const ctx = uni.createCanvasContext('lineCanvas', this)
  ctx.clearRect(0, 0, boardWidth.value, boardHeight.value)
  ctx.draw()
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
      title: `恭喜你完成了所有配对！得分: ${score.value}`,
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

// 监听关卡变化
watch(currentLevel, () => {
  initGame()
})
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20rpx;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.picker {
  padding: 20rpx 40rpx;
  background-color: #4a6baf;
  color: white;
  border-radius: 50rpx;
  margin-bottom: 30rpx;
  font-weight: bold;
  box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.1);
}

.game-info {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 30rpx;
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  padding: 0 20rpx;
}

.game-board {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  background-color: #e0e0e0;
  border-radius: 20rpx;
  box-shadow: 0 8rpx 16rpx rgba(0, 0, 0, 0.1);
  margin-bottom: 20rpx;
}

.game-item {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
  border: 2rpx solid #ddd;
  box-sizing: border-box;
  text-align: center;
  transition: all 0.3s ease;
  font-weight: bold;
  color: #333;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.05);
}

.game-item.active {
  background-color: #ffeb3b;
  transform: scale(0.95);
  box-shadow: 0 0 20rpx rgba(255, 235, 59, 0.7);
}

.game-item.hidden {
  visibility: hidden;
}

.game-item.matched {
  background-color: #8bc34a;
  color: white;
}

.line-canvas {
  position: absolute;
  top: 0;
  left: 0;
  pointer-events: none;
  z-index: 10;
}

/* 添加动画效果 */
@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.05); }
  100% { transform: scale(1); }
}

.game-item:active {
  animation: pulse 0.3s ease;
}
</style>