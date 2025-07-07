<template>
	<view class="user-info-container"> 
			<view class="header-input-wrapper">请选择头像</view>
			<button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
			  <image class="avatar" :src="avatarUrl"></image>
			</button> 
			<view class="nickname-input-wrapper">
				<input type="nickname" class="nickname-input" placeholder="请输入昵称" v-model="userNickName">
			</view>
			<view class="nickname-input-wrapper">
				<button type="primary" @click="onChooseSubmit">确定</button>
			</view> 
	</view>
</template>

<script setup> 
	import {ref} from "vue";
	import {isEmpty,showHint} from "@/common/utils/common.js";
	
	const avatarUrl = ref('');
	const userNickName = ref('');
	const emit = defineEmits(["chooseDone"]);
	 
	function onChooseAvatar(e){
		avatarUrl.value = e.detail.avatarUrl;
	}
	function onChooseSubmit(){
		if(isEmpty(avatarUrl.value)){
			showHint("请先设置头像！");
			return;
		}
		if(isEmpty(userNickName.value)){
			showHint("请先设置昵称！");
			return;
		}
		
		const fs = uni.getFileSystemManager();
		console.log(fs)
		let base64Str = fs.readFileSync(avatarUrl.value, 'base64');
		let imgBase64 = `data:image/png;base64,${base64Str}`;
		console.log("imgBase64->", imgBase64);
		
		emit("chooseDone", {"avatarUrl":avatarUrl.value, "userNickName": userNickName.value, "avatarBase64": imgBase64});
	}
 
</script>

<style lang="scss" scoped>
.user-info-container {
  display: flex;
  // height: 80vh;
  flex-direction: column;
  align-items: center;
  padding: 40rpx;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8eb 100%);
  border-radius: 24rpx;
  box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.08);
  margin: 40rpx;
}

/* 头像样式 */
.avatar-wrapper {
  position: relative;
  width: 160rpx;
  height: 160rpx;
  padding: 0;
  margin: 0;
  background: none;
  border: none;
  border-radius: 50%;
  overflow: visible;
  box-shadow: 0 8rpx 25rpx rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.avatar-wrapper::after {
  border: none;
}

.avatar-wrapper:active {
  transform: scale(0.95);
}

.avatar {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 4rpx solid #fff;
  box-shadow: 0 4rpx 15rpx rgba(0, 0, 0, 0.1);
  background-color: #f0f2f5;
}

.edit-icon {
  position: absolute;
  right: -10rpx;
  bottom: -10rpx;
  width: 50rpx;
  height: 50rpx;
  background: #4a90e2;
  color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24rpx;
  box-shadow: 0 4rpx 10rpx rgba(74, 144, 226, 0.3);
}

/* 昵称输入框样式 */
.nickname-input-wrapper {
  position: relative;
  width: 100%;
  margin-top: 60rpx;
  text-align: center; 
}

/* 昵称输入框样式 */
.header-input-wrapper {
  position: relative;
  width: 100%;
  margin-top: 10rpx;
  margin-bottom: 30rpx;
  text-align: center;  
}


.nickname-input {
  left: 10rpx;
  right: 10rpx;
  height: 90rpx;
  padding: 0 30rpx;
  font-size: 32rpx;
  color: #333;
  // background: transparent;
  border: none;
  z-index: 2;
  // position: relative;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.08);
  background-color: #fff;
    // outline: none;
    // transition: all 0.3s ease;
}

.placeholder-style {
  color: #bbb;
  font-size: 32rpx;
}
 
</style>

