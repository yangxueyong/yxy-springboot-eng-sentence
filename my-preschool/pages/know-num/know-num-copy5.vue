<template>
  <view class="container">
    <!-- 游戏类型选择 -->
    <picker mode="selector" :range="changeGameList" range-key="title" @change="onGameTypeChange">
      <view class="picker">当前游戏：{{currentGameType.title}}</view>
    </picker>
    
    <!-- 游戏难度选择 -->
    <picker mode="selector" :range="gameLevels" range-key="title" @change="onLevelChange">
      <view class="picker">当前难度：{{currentLevel.title}}</view>
    </picker>
    
    <view class="game-info">
      <text>时间: {{timeLeft}}s</text>
      <text>分数: {{score}}</text>
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
          <template v-if="currentGameType.id === 'a1'">
            {{item.hidden ? '' : item.value}}
          </template>
          <template v-else-if="currentGameType.id === 'a2'">
            <view class="color-item" :style="{backgroundColor: item.value}"></view>
          </template>
          <template v-else-if="currentGameType.id === 'a3' || currentGameType.id === 'a4'">
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
</template>

<script setup>
import { ref, computed, onMounted, watch, nextTick } from 'vue'

// 游戏类型列表
const changeGameList = ref([
  {"id":"a1","note":"数字消消","rightText":"","sort":"01","status":"available","title":"数字消消","type":"xx"},
  {"id":"a2","note":"颜色消消","rightText":"","sort":"01","status":"available","title":"颜色消消","type":"xx"},
  {"id":"a3","note":"动物消消","rightText":"","sort":"01","status":"available","title":"动物消消","type":"xx"},
  {"id":"a4","note":"动物和影子消消","rightText":"","sort":"01","status":"available","title":"动物和影子消消","type":"xx"}
])

// 难度等级
const gameLevels = ref([
  {"id":"cc1","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"24","gameTime":"15","numSum":"9","title":"初级","note":"1-9的数字","rightText":""},
  {"id":"cc2","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"20","gameTime":"60","numSum":"16","title":"中级","note":"1-16的数字","rightText":""},
  {"id":"cc3","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"18","gameTime":"100","numSum":"25","title":"高级","note":"1-25的数字","rightText":""},
  {"id":"cc4","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"16","gameTime":"300","numSum":"36","title":"专家","note":"1-36的数字","rightText":""},
  {"id":"cc5","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"14","gameTime":"500","numSum":"49","title":"大师","note":"1-49的数字","rightText":""},
  {"id":"cc6","type":"memory","beginNum":"1","typeKey":"general","itemCls":"v_item","fontSize":"14","gameTime":"500","numSum":"100","title":"超级","note":"1-100的数字","rightText":""}
])

// 颜色列表
const changeColorList = ref([
  {"content":"黄色 yellow","createTime":1751962136000,"id":"cba0ec4f-5bd2-11f0-8c35-b8cef6abb4dc","key":"yellow","status":"available","value":"#FFD700"},
  {"content":"红色 red","createTime":1751962136000,"id":"cba0ef33-5bd2-11f0-8c35-b8cef6abb4dc","key":"red","status":"available","value":"#FF0000"},
  {"content":"棕色 brown","createTime":1751962136000,"id":"cba0efb2-5bd2-11f0-8c35-b8cef6abb4dc","key":"brown","status":"available","value":"#A52A2A"},
  {"content":"紫色 purple","createTime":1751962136000,"id":"cba0f018-5bd2-11f0-8c35-b8cef6abb4dc","key":"purple","status":"available","value":"#800080"},
  {"content":"粉色 pink","createTime":1751962136000,"id":"cba0f05f-5bd2-11f0-8c35-b8cef6abb4dc","key":"pink","status":"available","value":"#FFC0CB"},
  {"content":"绿色 green","createTime":1751962136000,"id":"cba0f0a1-5bd2-11f0-8c35-b8cef6abb4dc","key":"green","status":"available","value":"#008000"},
  {"content":"蓝色 blue","createTime":1751962136000,"id":"cba0f0e6-5bd2-11f0-8c35-b8cef6abb4dc","key":"blue","status":"available","value":"#0000FF"},
  {"content":"白色 white","createTime":1751962136000,"id":"cba0f137-5bd2-11f0-8c35-b8cef6abb4dc","key":"white","status":"available","value":"#FFFFFF"},
  {"content":"黑色 black","createTime":1751962136000,"id":"cba0f18a-5bd2-11f0-8c35-b8cef6abb4dc","key":"black","status":"available","value":"#000000"}
])

// 动物列表
const changeAnimalList = ref([
  {"createTime":1751962136000,"id":"cbb771f2-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/longxia.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/longxia (1).png","status":"available"},
  {"createTime":1751962136000,"id":"cbb7751e-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/22-乌龟.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/22-乌龟 (1).png","status":"available"},
  {"createTime":1751962136000,"id":"cbb7759f-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/狗狗出售.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/狗狗出售 (1).png","status":"available"},
  {"createTime":1751962136000,"id":"cbb775f6-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/卡通大象.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/卡通大象 (1).png","status":"available"},
  {"createTime":1751962136000,"id":"cbb7763b-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/卡通海豚.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/卡通海豚 (1).png","status":"available"},
  {"createTime":1751962136000,"id":"cbb7767d-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/卡通猴子.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/卡通猴子 (1).png","status":"available"},
  {"createTime":1751962136000,"id":"cbb776b3-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/卡通鸡.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/卡通鸡 (1).png","status":"available"},
  {"createTime":1751962136000,"id":"cbb776ea-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/卡通鹿.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/卡通鹿 (1).png","status":"available"},
  {"createTime":1751962136000,"id":"cbb77733-5bd2-11f0-8c35-b8cef6abb4dc","imgSrc":"https://www.yangxuexue.xyz//images/卡通马.png","key":"a","shadowSrc":"https://www.yangxuexue.xyz//images/卡通马 (1).png","status":"available"}
])

const currentGameType = ref(changeGameList.value[0])
const currentLevel = ref(gameLevels.value[0])
const boardItems = ref([])
const activeIndexes = ref([])
const score = ref(0)
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
  
  if (currentGameType.value.id === 'a2' && !item.hidden) {
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
        
        // 计算最佳行列数，使矩阵尽可能接近正方形
        const minItemSize = 50 // 最小项目大小
        const padding = 20 // 两边留白
        const maxBoardWidth = screenInfo.value.safeArea.right - screenInfo.value.safeArea.left - padding * 2
        
        // 初始列数为平方根取整
        let bestColNum = Math.floor(Math.sqrt(numSum))
        let bestRowNum = Math.ceil(numSum / bestColNum)
        
        // 调整列数以使矩阵更接近正方形且不超过最大宽度
        while (true) {
          const candidateColNum = bestColNum + 1
          const candidateRowNum = Math.ceil(numSum / candidateColNum)
          
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
          bestColNum = Math.min(Math.floor(maxBoardWidth / minItemSize), numSum)
          bestRowNum = Math.ceil(numSum / bestColNum)
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

// 初始化游戏
const initGame = async () => {
  clearInterval(gameTimer.value)
  
  if (currentGameType.value.id === 'a1') {
    timeLeft.value = parseInt(currentLevel.value.gameTime)
  } else {
    timeLeft.value = 120 // 其他游戏类型固定2分钟
  }
  
  score.value = 0
  activeIndexes.value = []
  
  await calculateLayout()
  generateBoard()
  startTimer()
  clearCanvas()
}

// 生成游戏板
const generateBoard = () => {
  let pairs = []
  const totalItems = colNum.value * rowNum.value
  
  switch (currentGameType.value.id) {
    case 'a1': // 数字消消乐
      const numSum = Math.min(parseInt(currentLevel.value.numSum), totalItems)
      for (let i = 1; i <= numSum / 2; i++) {
        pairs.push(i, i)
      }
      break
      
    case 'a2': // 颜色消消乐
      const colorCount = Math.min(changeColorList.value.length, Math.floor(totalItems / 2))
      const selectedColors = [...changeColorList.value]
        .sort(() => Math.random() - 0.5)
        .slice(0, colorCount)
        .map(color => color.value)
      
      // 确保有足够的配对，允许重复使用颜色
      while (pairs.length < totalItems) {
        const color = selectedColors[Math.floor(Math.random() * selectedColors.length)]
        pairs.push(color, color)
      }
      break
      
    case 'a3': // 动物消消乐
      const animalCount = Math.min(changeAnimalList.value.length, Math.floor(totalItems / 2))
      const selectedAnimals = [...changeAnimalList.value]
        .sort(() => Math.random() - 0.5)
        .slice(0, animalCount)
      
      // 确保有足够的配对，允许重复使用动物
      while (pairs.length < totalItems) {
        const animal = selectedAnimals[Math.floor(Math.random() * selectedAnimals.length)]
        pairs.push(animal.imgSrc, animal.imgSrc)
      }
      break
      
    case 'a4': // 动物和影子消消乐
      const shadowAnimalCount = Math.min(changeAnimalList.value.length, Math.floor(totalItems / 2))
      const selectedShadowAnimals = [...changeAnimalList.value]
        .sort(() => Math.random() - 0.5)
        .slice(0, shadowAnimalCount)
      
      // 确保有足够的配对，允许重复使用动物和影子
      while (pairs.length < totalItems) {
        const animal = selectedShadowAnimals[Math.floor(Math.random() * selectedShadowAnimals.length)]
        pairs.push(animal.imgSrc, animal.shadowSrc)
      }
      break
  }
  
  // 随机打乱
  pairs = pairs.slice(0, totalItems).sort(() => Math.random() - 0.5)
  
  // 创建游戏项
  boardItems.value = pairs.map(value => ({
    value,
    hidden: false,
    matched: false,
    key: currentGameType.value.id === 'a4' 
      ? value.includes('(1)') ? value.replace('(1)', '') : value
      : value
  }))
}

// 处理点击事件
const handleItemClick = (index) => {
  const item = boardItems.value[index]
  
  if (item.hidden || activeIndexes.value.includes(index) || activeIndexes.value.length >= 2) return
  
  activeIndexes.value.push(index)
  
  if (activeIndexes.value.length === 2) {
    const [firstIndex, secondIndex] = activeIndexes.value
    const firstItem = boardItems.value[firstIndex]
    const secondItem = boardItems.value[secondIndex]
    
    let isMatch = false
    
    if (currentGameType.value.id === 'a4') {
      // 动物和影子模式特殊匹配逻辑
      const firstIsShadow = firstItem.value.includes('(1)')
      const secondIsShadow = secondItem.value.includes('(1)')
      
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
      drawLightningLine(firstIndex, secondIndex)
      
      setTimeout(() => {
        firstItem.hidden = true
        secondItem.hidden = true
        firstItem.matched = true
        secondItem.matched = true
        activeIndexes.value = []
        score.value += 10
        
        checkGameEnd()
      }, 1000)
    } else {
      setTimeout(() => {
        activeIndexes.value = []
      }, 1000)
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
  
  setTimeout(clearCanvas, 1000)
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

// 检查游戏结束
const checkGameEnd = () => {
  const allHidden = boardItems.value.every(item => item.hidden || item.value === null)
  if (allHidden) {
    clearInterval(gameTimer.value)
    uni.showToast({
      title: `恭喜完成！得分: ${score.value}`,
      icon: 'none'
    })
  }
}

// 游戏类型变更
const onGameTypeChange = (e) => {
  currentGameType.value = changeGameList.value[e.detail.value]
  initGame()
}

// 难度变更
const onLevelChange = (e) => {
  currentLevel.value = gameLevels.value[e.detail.value]
  initGame()
}

// 初始化
onMounted(() => {
  initGame()
})

// 监听变化
watch([currentGameType, currentLevel], () => {
  initGame()
})
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  background-color: #f5f5f5;
  min-height: 100vh;
  box-sizing: border-box;
}

.picker {
  padding: 12px 20px;
  background-color: #4a6baf;
  color: white;
  border-radius: 25px;
  margin: 8px 0;
  font-weight: bold;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 90%;
  max-width: 400px;
  text-align: center;
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
</style>