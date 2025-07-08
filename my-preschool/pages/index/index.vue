<template>
  <view class="content"> 
    <navigator url="/pages/num-Huarongdao/num-Huarongdao">
      <view class="pageCls">
        基础数字训练
      </view>
    </navigator>
    <navigator url="/pages/num-memory/num-memory">
      <view class="pageCls">
        综合训练
      </view>
    </navigator> 
  </view>
</template>

<script setup>
</script>

<style lang="scss" scoped>
.content {
  display: flex;
  width: 100vw;
  height: 100vh;
  flex-direction: column;
  align-items: center;
  gap: 50rpx;
  justify-content: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.pageCls {
  width: 70vw;
  height: 240rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 36rpx;
  font-weight: 500;
  color: #fff;
  background: linear-gradient(145deg, #6a11cb 0%, #2575fc 100%);
  border-radius: 24rpx;
  box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  
  /* 添加光晕效果 */
  &:before {
    content: '';
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: rgba(255, 255, 255, 0.1);
    transform: rotate(45deg);
    transition: all 0.6s ease;
    pointer-events: none;
  }
  
  &:hover:before {
    left: 100%;
  }
  
  &:active {
    transform: scale(0.96);
    box-shadow: 0 5rpx 15rpx rgba(0, 0, 0, 0.2);
  }
  
  /* 添加图标或装饰元素 */
  &:after {
    content: '→';
    position: absolute;
    right: 30rpx;
    opacity: 0;
    transition: all 0.3s ease;
  }
  
  &:hover:after {
    opacity: 1;
    right: 20rpx;
  }
}
</style>