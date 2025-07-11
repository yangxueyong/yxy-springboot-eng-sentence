<template>
	
		<view class="itemMainTitleCls">
			<view class="item1TitleCls">
				<!-- <view class="rankTitleCls"> </view> -->
				<view class="userInfoCls">
					信息
				</view> 
			</view>
			<view class="item2TitleCls">
				耗时
			</view>
			<view class="item3Cls">
				时间
			</view>
		</view> 
		
		<scroll-view class="box" scroll-y="true">
		<view class="itemMainCls" v-for="item in datas" :key="item.rank">
			<view class="item1Cls">
			  <!-- 动态绑定 class -->
			  <view :class="['rankTitleCls', getRankClass(item.rank)]">{{ item.rank }}</view>
			  <view class="userInfoTitleCls" v-if="!queryParams.queryMe">
				<view class="userHeadTitleCls">
				  <image class="userHeadImgCls" :src="item.userThirdHeadImgPath"></image>
				</view>
				<!-- 动态绑定 class -->
				<view :class="['nicknameTitleCls', getRank1Class(item.rank)]">{{ item.userName }}</view>
			  </view>
			  <view class="userInfoTitleCls" v-if="queryParams.queryMe">
				{{ item.gameName }}
			  </view>
			</view>
			<!-- 动态绑定 class -->
			<view :class="['item2Cls', getRank1Class(item.rank)]">{{ item.consumeTimeStr }}</view>
			<view class="item3Cls">{{ item.createTime }}</view>
		</view> 
		
	</scroll-view>
</template>

<script setup> 
	import {ref} from "vue";
	import {apiQueryGameScoreRankList} from "@/common/api/apis.js";
 	import {onLoad,onUnload,onReachBottom,onShareAppMessage,onShareTimeline,onPullDownRefresh} from "@dcloudio/uni-app";
	import {getSystemWechatUserForward} from "@/common/utils/common.js";
	
	
	
	const datas = ref([]);
	
	const queryParams = {
		pageNum:1,
		pageSize:10
	}
		
	onLoad((e)=>{
		getSystemWechatUserForward();
		
		let {
			gameLevelId = null,
		gameTypeId = null,
		name = null,
		queryMe = null } = e;
		
		queryParams.gameLevelId = gameLevelId;
		queryParams.gameTypeId = gameTypeId;
		queryParams.queryMe = queryMe;
		
		//修改导航标题
		uni.setNavigationBarTitle({
			title:name
		});
		getGameScoreRankList(); 
	});
	
	// 根据 rank 返回对应的 class
	const getRankClass = (rank) => {
	  switch (rank.toString()) { // 转为字符串避免数字比较问题
	    case '1': return 'oneCls';
	    case '2': return 'twoCls';
	    case '3': return 'threeCls';
	    default: return 'otherCls';
	  }
	};
	
	const getRank1Class = (rank) => {
	  switch (rank.toString()) { // 转为字符串避免数字比较问题
	    case '1': return 'one1Cls';
	    case '2': return 'two1Cls';
	    case '3': return 'three1Cls';
	    default: return 'other1Cls';
	  }
	};

	//加载排行数据
	const getGameScoreRankList =async()=>{
		// uni.showLoading({
		// 	title:"加载中.."
		// })
		let res =await apiQueryGameScoreRankList(queryParams); 
		
		console.log("获取到的游戏排行数据->",res);  
		
		datas.value  = res.data ;
		
		uni.stopPullDownRefresh();
		uni.hideLoading(); 
	}
</script>

<style lang="scss" scoped>
.box{
	width: 100vw;
	height: 100vh;
	 display: flex;
	  flex-direction: column; /* 竖向排列 */
	  align-items: center; /* 水平居中 */
	  font-size: $text-font-size-1;
}

.itemMainTitleCls{
	// display: flex;
	width: 95vw; 
	display: flex;  
	margin: 0 auto; 
	box-shadow: 0 6rpx 12rpx -4rpx rgba(0, 0, 0, 0.08);
	margin-bottom: 10rpx;
}
.itemMainTitleCls > view {
  // flex: 1; /* 均分宽度 */
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  flex-direction: column; /* 如果内部元素需要垂直排列 */
}

.itemMainCls { 
  width: 95vw; /* 确保父容器宽度充满 */
  height: 150rpx;
  align-items: center; /* 垂直居中 */
  border-bottom: 1px solid #ccc; 
  // background-color: red;
  display: flex;
  align-items: center;
  margin: 0 auto; /* 水平居中 */ 
  margin-bottom: 20rpx;
  border-radius: 10rpx;
  box-shadow: 0 0 10rpx 2rpx rgba(0, 0, 0, 0.1);
}

.itemMainCls > view {
  // flex: 1; /* 均分宽度 */
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  flex-direction: column; /* 如果内部元素需要垂直排列 */
}

/* 如果需要为每个item单独设置样式 */
.item1TitleCls {
  display: flex;
  width: 35vw;
  align-items: center; /* 垂直居中 */
  /* 可以添加特定样式 */
  height: 80rpx;
  flex-direction: row !important; /* 强制水平排列 */   
}
 
.item1Cls {
	width: 35vw;
	display: flex;
	align-items: center; /* 垂直居中 */
	  /* 可以添加特定样式 */
	height: 150rpx;
	flex-direction: row !important; /* 强制水平排列 */  
	// border-right: 1px solid red;
}

.rankTitleCls{
  width: 50rpx;
  height: 50rpx;
  // background-color: red;
  margin-left: 20rpx;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  flex-direction: column; /* 如果内部元素需要垂直排列 */
  border-radius: 25rpx;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1),
                0 6px 20px rgba(0, 0, 0, 0.1); 
}
.oneCls{
	background-color: red;
	color: #fff;
}

.twoCls{
	background-color: green;
	color: #fff;
}

.threeCls{
	background-color: yellow;
	color: #000;
}

.otherCls{
	background-color: #fff;
	color: #000;
}

.one1Cls{ 
	color: #000;
}

.two1Cls{ 
	color: #000;
}

.three1Cls{ 
	color: #000;
}

.other1Cls{ 
	color: #000;
}

.userInfoTitleCls{
  flex: 1; /* 均分宽度 */
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  flex-direction: column; /* 如果内部元素需要垂直排列 */
  font-size: $text-font-size-3;
} 

.nicknameCls{
  
}
.userHeadCls{ 
  margin-left: 20rpx;
}
.userHeadImgCls{
  height: 70rpx;
  width: 70rpx;
  border-radius: 35rpx;
}

.item2TitleCls {
	width: 35vw;
	// height: 120rpx;
  /* 可以添加特定样式 */  
}
  
.item2Cls {
	width: 35vw;
	// height: 120rpx;
  /* 可以添加特定样式 */
  border-right: 1px solid #ccc;
  border-left: 1px solid #ccc;
  // background-color: blue;
}
.item3Cls {
	width: 30vw;
	font-size: 25rpx;
	color: #757575;
	text-align: center;
	// background-color: red;
  /* 可以添加特定样式 */
}
</style>

