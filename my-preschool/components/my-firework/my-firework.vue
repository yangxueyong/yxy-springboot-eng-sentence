<template>
  <view class="container"> 
    <canvas 
      canvas-id="fireworksCanvas" 
      id="fireworksCanvas" 
      class="canvas"
      :style="{width: canvasWidth + 'px', height: canvasHeight + 'px'}"
    ></canvas>
  </view>
</template>

<script setup>
import { ref, onMounted, onUnmounted, getCurrentInstance } from 'vue'

const canvasWidth = ref(300)
const canvasHeight = ref(500)
const particles = ref([])
const animationTimer = ref(null)
const startTime = ref(0)
const duration = 5000 // 5秒

// 初始化粒子
const initParticles = () => {
  const count = 50 // 适当减少粒子数量以提高性能
  particles.value = []
  
  for (let i = 0; i < count; i++) {
    particles.value.push({
      x: Math.random() * canvasWidth.value,
      y: -10, // 从屏幕上方开始
      vx: Math.random() * 2 - 1, // 水平速度
      vy: Math.random() * 2 + 2, // 垂直速度
      size: Math.random() * 3 + 2,
      color: getRandomColor(),
      alpha: 1,
      decay: Math.random() * 0.02 + 0.01 // 透明度衰减
    })
  }
}

// 生成随机颜色
const getRandomColor = () => {
  const colors = [
    '#FF5252', '#FF4081', '#E040FB', '#7C4DFF', 
    '#536DFE', '#448AFF', '#40C4FF', '#18FFFF',
    '#64FFDA', '#69F0AE', '#B2FF59', '#EEFF41', 
    '#FFFF00', '#FFD740', '#FFAB40', '#FF6E40'
  ]
  return colors[Math.floor(Math.random() * colors.length)]
}

// 绘制粒子
const drawParticles = () => {
  const ctx = uni.createCanvasContext('fireworksCanvas', getCurrentInstance())
  ctx.clearRect(0, 0, canvasWidth.value, canvasHeight.value)
  
  particles.value.forEach(particle => {
    ctx.beginPath()
    ctx.arc(particle.x, particle.y, particle.size, 0, Math.PI * 2)
    ctx.setFillStyle(particle.color)
    ctx.setGlobalAlpha(particle.alpha)
    ctx.fill()
  })
  
  ctx.draw()
}

// 更新粒子位置
const updateParticles = () => {
  const currentTime = Date.now()
  const elapsed = currentTime - startTime.value
  
  if (elapsed >= duration) {
    // 动画结束
    stopAnimation()
    return false
  }
  
  particles.value.forEach(particle => {
    particle.x += particle.vx
    particle.y += particle.vy
    particle.alpha -= particle.decay
    
    // 给粒子一些随机运动
    particle.vx += (Math.random() - 0.5) * 0.2
    particle.vy += Math.random() * 0.1
    
    // 重置超出屏幕的粒子
    if (particle.y > canvasHeight.value || particle.alpha <= 0) {
      resetParticle(particle)
    }
  })
  
  return true
}

// 重置粒子
const resetParticle = (particle) => {
  particle.x = Math.random() * canvasWidth.value
  particle.y = -10
  particle.alpha = 1
  particle.vx = Math.random() * 2 - 1
  particle.vy = Math.random() * 2 + 2
}

// 动画循环
const animate = () => {
  const shouldContinue = updateParticles()
  drawParticles()
  console.log("进来了 大哥")
  if (shouldContinue) {
    animationTimer.value = setTimeout(() => {
      animate()
    }, 16) // 约60fps
  }
}

// 停止动画
const stopAnimation = () => {
  if (animationTimer.value) {
    clearTimeout(animationTimer.value)
    animationTimer.value = null
  }
    const ctx = uni.createCanvasContext('fireworksCanvas', getCurrentInstance())
    ctx.clearRect(0, 0, canvasWidth.value, canvasHeight.value)
    ctx.draw()
}

// 开始动画
const startAnimation = () => {
  startTime.value = Date.now()
  initParticles()
  animate()
}

onMounted(() => {
  // 获取屏幕尺寸
  uni.getSystemInfo({
    success: (res) => {
      canvasWidth.value = res.windowWidth
      canvasHeight.value = res.windowHeight * 0.8 // 使用屏幕高度的80%
      startAnimation() // 确保在获取尺寸后开始动画
    }
  })
})

onUnmounted(() => {
  stopAnimation()
})
</script>

<style>
.container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none; /* 允许点击穿透 */
  z-index: 9999; 
}

.canvas {
  width: 100%;
  height: 100%;
}
</style>