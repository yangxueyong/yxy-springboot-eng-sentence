<template>
  <view class="container">
    <!-- 第一个元素 -->
    <view class="box" id="box1" style="top: 100px; left: 50px;">Box 1</view>
    
    <!-- 第二个元素 -->
    <view class="box" id="box2" style="top: 300px; left: 250px;">Box 2</view>
    
    <!-- SVG 连线 -->
    <svg class="connector" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; pointer-events: none;">
      <path 
        :d="pathData" 
        stroke="#3498db" 
        stroke-width="2" 
        fill="none"
      />
    </svg>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const pathData = ref('')

onMounted(() => {
  updatePath()
})

function updatePath() {
  // 获取两个元素的位置
  const box1 = uni.createSelectorQuery().select('#box1')
  const box2 = uni.createSelectorQuery().select('#box2')
  
  box1.boundingClientRect(rect1 => {
    box2.boundingClientRect(rect2 => {
      // 计算两个元素的中心点
      const x1 = rect1.left + rect1.width / 2
      const y1 = rect1.top + rect1.height / 2
      const x2 = rect2.left + rect2.width / 2
      const y2 = rect2.top + rect2.height / 2
      
      // 控制点，用于创建贝塞尔曲线
      const ctrlX = (x1 + x2) / 2
      const ctrlY = (y1 + y2) / 2 - 100  // 调整这个值可以改变曲线的弯曲程度
      
      // 创建二次贝塞尔曲线路径
      pathData.value = `M${x1},${y1} Q${ctrlX},${ctrlY} ${x2},${y2}`
    }).exec()
  }).exec()
}
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  width: 100%;
  height: 100vh;
}

.box {
  position: absolute;
  width: 100px;
  height: 60px;
  background-color: #f1c40f;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
}
</style>