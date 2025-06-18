<template>
	<view class="layoutCls">
		<view class="scrollCls" scroll-y="true" scroll-x="false">
			<view class="contentCls" v-for="item in datas" :key="item.id" v-if="datas.length">
				<view class="favoriteTimeMainCls" >
					<!-- <view class="favoriteTextCls">收藏时间:</view> -->
					<view class="favoriteTimeCls">{{item.createTime}}</view>
					<view class="favoriteDelCls" @click="delMyCollect(item.id)">
						<uni-icons type="trash" size="25"  ></uni-icons>
					</view>
				</view>
				<view class="zhCls" >
					{{item.znName}}
				</view>
				<view class="translationCls">
					<view class="engCls" >
						{{item.hide=='1'?'***':item.enName}}
					</view>
					<view class="hidCls">
						<uni-icons :type="item.hide=='1'?'eye-slash':'eye'" size="25" @click="eye(item)"></uni-icons>
						<view class="play" @click.stop="play(item, queryParams.collectType)">
							<a-trumpet :isPlay="item.isPlay" size="25" color="#28B389"></a-trumpet>
							<!-- <uni-icons type="sound" size="30"  @click="play(item)"></uni-icons> -->
						</view>
					</view>
				</view>
			</view> 
			<view class="loadingLayout" v-if="noData || datas.length">
				<uni-load-more :status="noData?'noMore':'loading'"/>
			</view>
		</view> 
	</view>
</template>

<script setup> 
	import {ref} from "vue";
	import {getSystemWechatUserForward} from "@/common/utils/common.js";
	import {onLoad,onUnload,onReachBottom,onShareAppMessage,onShareTimeline,onPullDownRefresh} from "@dcloudio/uni-app"
	import {apiGetMyCollect,apiDelMyCollect} from "@/common/api/apis.js"
	import {getDetailVoicePath,getWordVoicePath} from "@/common/utils/request.js";
	
	getSystemWechatUserForward();
	const playMap = new Map();
	//没有更多
	const noData = ref(false);
	
	const queryParams = {
		pageNum:1,
		pageSize:10
	}
	
	onLoad((e)=>{
		let {collectType=null,name=null} = e;
		queryParams.collectType = collectType;
		//修改导航标题
		uni.setNavigationBarTitle({
			title:name
		});
		getEngMain(); 
	});
	
	
	//下拉刷新
	onPullDownRefresh(()=>{
		queryParams.pageNum = 1;
		getEngMain();
	})
	
	
	//触底加载更多
	onReachBottom(()=>{
		console.log("触地了-->");
		if(noData.value) return;
		queryParams.pageNum++
		getEngMain();
	})
	
	//我收藏的句子
	const datas=ref([]);
	
	const getEngMain =async(code)=>{
		// uni.showLoading({
		// 	title:"加载中.."
		// })
		let res =await apiGetMyCollect(queryParams); 
		console.log("获取到的收藏数据->",res);  
		datas.value  =  [...datas.value,...res.data] ;
		
		if(queryParams.pageSize > res.data.length){
			noData.value = true;
		}
		
		uni.stopPullDownRefresh();
		uni.hideLoading(); 
	}
	
	const delMyCollect=async(id)=>{ 
		let res =await apiDelMyCollect({
			id
		});   
		datas.value = datas.value.filter(item => item.id !== id); 
		uni.stopPullDownRefresh();
		uni.hideLoading(); 
	}
	
	
	
	// 显示或隐藏
	function eye(e){
		e.hide = !e.hide;
	}
	function deleteFavorite(e){
		
	}
	function play(e, type){
		let eid = type + e.id; 
		
		e.isPlay = !e.isPlay;
		if(!e.isPlay){
			if(playMap != null && playMap.has(eid)){
				playMap.get(eid).stop();
			}
			return;
		}
		if(playMap != null){
			playMap.forEach((value, id) => {
			  value.stop();
			  if(type == "detail"){
				  findItemById(id).isPlay = false;
			  }else{
				  findWordById(id).isPlay = false;
			  }
			});
		}
		e.isPlay = true;
		
		let audioUrl = null; 
		if(type == "detail"){
			audioUrl = getDetailVoicePath(e.webAudioPath);
		}else{
			audioUrl = getWordVoicePath(e.webAudioPath);
		}
		
		console.log("音频地址->",audioUrl);
		const innerAudioContext = uni.createInnerAudioContext();
		innerAudioContext.autoplay = true;
		innerAudioContext.src = audioUrl;
		innerAudioContext.onPlay(() => {
		  console.log('开始播放');
		  uni.hideLoading();
		});
		innerAudioContext.onError((res) => {
			console.log(res)
		  // console.log(res.errMsg);
		  // console.log(res.errCode);
		  uni.hideLoading();
		});
		innerAudioContext.onEnded((res)=>{
			console.log("停止播放->",res);
			e.isPlay = false;
			uni.hideLoading();
		})
		
		playMap.set(eid, innerAudioContext);
	}
	 
	function findItemById(id) {
	    return datas.value.find(item => id.includes(item.id));
	}
	   
	  
</script>

<style lang="scss" scoped>
.layoutCls{
		width: 100%;
		height: 100vh;
		position: relative; 
		 .scrollCls{  
			// margin-top: 20rpx;
			width: 100%; 
			height: 100vh;
			padding: 20rpx; 
			box-sizing: border-box; 
			.contentCls{ 
				margin-top: 60rpx; 
				background: #f3f7f8; 
				border-radius: 20rpx;
				padding: 15rpx;  
				
				.favoriteTimeMainCls{
					font-size: 25rpx;
					padding: 10rpx;
					border-bottom: 1px solid #ebf1f3; 
					display: flex;  
					align-items: center;
					background: rgba(0, 0, 0, 0.1); 
					display: inline-block;
					vertical-align: middle;
					position: absolute;
					// color: #fff;
					right: 0;
					margin-top: -50rpx;
					// padding: 20rpx;
					border-radius: 20rpx;
					// top: 0rpx;
					.favoriteTextCls{
						// background: yellow;
						display: inline-block; 
						vertical-align: middle
					}
					.favoriteTimeCls{
						display: inline-block; 
						// color:$text-font-color-2;
						vertical-align: middle;
						font-family: monospace; 
						font-weight: normal; 
						// width: 19ch;
						// padding-left: 10rpx;
						// background: red;
					}
					.favoriteDelCls{
						display: inline-block; 
						vertical-align: middle;
						// right: 0;
						:deep(){
							.uni-icons{
								color:$brand-theme-color !important;
								 padding-left: 20rpx;
							}
						}	
					}
				}
				.zhCls{
					font-size: 25rpx;
					padding: 10rpx;
					border-bottom: 1px solid #ebf1f3;
				}
				.translationCls{
					display: flex;
					position: relative; 
					.engCls{
						flex: 1;
						width: 0; 
						padding: 10rpx;
					}
					.hidCls{
						width: 150rpx;	 
						// background: red;
						display: flex;
						// position: absolute; 
						justify-content: center;
						align-items: center;
						right: 0;
						:deep(){
							.uni-icons{
								color:$brand-theme-color !important;
								 padding-left: 20rpx;
							}
						}	
					}
				 }
			 }
			
		 }
		 
		 
		
	 }
</style>

