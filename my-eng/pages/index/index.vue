<template>
	<view class="layoutCls">
		<view class="changeCls">
			<view class="changeBtnCls" @click="getIndexEngUpMain"><uni-icons type="back" size="20"></uni-icons></view>
			<view class="changeBtnCls" @click="getIndexEngNextMain"><uni-icons type="forward" size="20"></uni-icons></view>
			<view class="changeBtnCls" @click="openSearch"><uni-icons type="search" size="20"></uni-icons></view>
		</view>
		<view class="titleCls">
			<view>{{mainData.enName}}</view>
		</view>
		<scroll-view class="scrollCls" scroll-y="true" scroll-x="false"> 
			<view class="contentCls" v-for="item in datas" :key="item.id">
				<view class="zhCls" @click="showDetail(item)">
					{{item.znName}}
				</view>
				<view class="translationCls">
					<view class="engCls" >
						{{item.hide==true?'***':item.enName}}
					</view>
					<view class="hidCls">
						<view @click="eye(item)">
							<uni-icons :type="item.hide==true?'eye-slash':'eye'" size="25"></uni-icons>
						</view>
						<!-- <uni-icons type="sound" size="30"  @click="play(item)"></uni-icons> -->
						<view @click="play(item,'detail')"> 
							<a-trumpet :isPlay="item.isPlay" size="25" color="#28B389" class="myPlay"></a-trumpet>
						</view>
					</view>
				</view>
			</view>
		</scroll-view> 
		
		<search-bar 
		@funSearchData="onFunSearchData" 
		@funSearchChangeItem="onFunSearchChangeItem" 
		ref="mySearchBar"></search-bar>
				
		<uni-popup ref="flagPopup" :is-mask-click="true"  >
			<view class="flagPopup">
				<view class="popHeader">
					收藏
				</view>
		
				<view class="content">
					<view 
					class="contentText"
					:style="currentItem.flag==true?'background-color:#beecd8':'background-color:#fff'"
					@click="changeSentence" v-if="currentItem">{{currentItem.enName}}</view>
					
					<view 
					v-for="item in wordsDetails" 
					:key="item.id" 
					class="contentWord" 
					:style="item.flag==true?'background-color:#beecd8':'background-color:#fff'"
					@click="changeWord(item)"> 
						<view class="text">{{item.enName}}</view>
						<view class="soundmark">{{item.znName}}</view>
						<view class="play" @click.stop="play(item,'word')">
							<a-trumpet :isPlay="item.isPlay" size="25" color="#28B389"></a-trumpet>
							<!-- <uni-icons type="sound" size="30"  @click="play(item)"></uni-icons> -->
						</view>
					</view>
				</view>
		
				<view class="footer">
					<button @click="submitFlag"  
					type="default" 
					size="mini"
						plain>确定收藏</button> 
				</view>
			</view>
			<!-- <view class="safe-area-inset-bottom"></view> -->
		</uni-popup>
		
	</view>
</template>

<script setup>
	import {ref} from "vue";
	import {onReachBottom,onPullDownRefresh} from "@dcloudio/uni-app";
	import {getSystemWechatUserForward} from "@/common/utils/common.js";
	import {apiGetEngMain,
	apiOperData,
	apiGetEngWordByDetail,
	apiGetIndexEngNextMain,
	apiGetIndexEngUpMain,
	apiSearchEngMain,
	apiSearchChangeEngMain} from "@/common/api/apis.js";
	import {getDetailVoicePath,getWordVoicePath} from "@/common/utils/request.js";
	//检查用户是否登录
	getSystemWechatUserForward(); 
	
	const mainData = ref({});
	const flagPopup = ref(null);
	const searchPopup = ref(null);
	const currentItem = ref(null);
	let playMap = new Map();
	//当前单元所有的数据
	const datas=ref([]); 
	
	//当前数据所有的单词
	const wordsDetails=ref([]); 
	
	const mySearchBar = ref(null);
	
	const getEngMain =async(code)=>{ 
		uni.showLoading({
			title:"加载中.."
		})
		let res =await apiGetEngMain({ 
			pageType:"study"
		}); 
		console.log("获取到的主页数据->",res);
		mainData.value = res.data;
		datas.value = res.data.details;
		uni.stopPullDownRefresh();
		uni.hideLoading();
		playMap = new Map();
	}
	getEngMain(); 
	
	//打开搜索框
	const openSearch=async()=>{
		// searchPopup.value.open(); 
		mySearchBar.value.open(); 
	}
	
	//被组件调用
	const onFunSearchData=async(e)=>{  
		 let res =await apiSearchEngMain({
		 	searchText:e
		 });  
		 mySearchBar.value.loadData(res.data);
	}
	
	//被组件调用
	const onFunSearchChangeItem=async(e)=>{ 
		console.log("页面得到了->",e);
		mySearchBar.value.close(); 
		 let res =await apiSearchChangeEngMain({
		 	id:e,
			pageType:"study"
		 }); 
		 getEngMain(); 
	}
	
	const getIndexEngNextMain =async()=>{
		let res =await apiGetIndexEngNextMain({ 
			"type":"next",
			pageType:"study"
		}); 
		console.log("获取到的主页数据->",res);
		mainData.value = res.data;
		datas.value = res.data.details;
		uni.stopPullDownRefresh();
		uni.hideLoading();
		playMap = new Map();
	} 
	
	const getIndexEngUpMain=async()=>{
		let res =await apiGetIndexEngUpMain({ 
			"type":"up",
			pageType:"study"
		}); 
		console.log("获取到的主页数据->",res);
		mainData.value = res.data;
		datas.value = res.data.details;
		uni.stopPullDownRefresh();
		uni.hideLoading();
		playMap = new Map();
	} 
	
	 
	//下拉刷新
	onPullDownRefresh(()=>{
		getEngMain();
	})
	
	const getEngWord =async(id)=>{ 
		let res =await apiGetEngWordByDetail({"detailId":id});
		wordsDetails.value = res.data;
		console.log("查询到的单词-》",res);
	}
	
	function showDetail(e){
		currentItem.value = e; 
		//todo 查询单词详情
		flagPopup.value.open();
		getEngWord(e.id); 
	}
	
	//选中句子
	function changeSentence(){
		currentItem.value.flag = !currentItem.value.flag; 
	}
	//选中句子中的单词
	function changeWord(e){
		e.flag = !e.flag; 
	}
	
	// 提交所选择的
	function submitFlag(){
		let opers = [{}];
		
		wordsDetails.value.forEach((word)=>{
			let operType = "noflag";
			if(word.flag){
				operType = "flag";
			}
			let oper = {
				operType,
				dataType:"word",
				dataId:word.id 
			}
			opers.push(oper);
		});
		let operType = "noflag"; 
		if(currentItem.value.flag){
			operType = "flag";
		}
		let oper = {
			operType,
			dataType:"detail",
			dataId:currentItem.value.id 
		}
		opers.push(oper);
		// 操作
		apiOperData(opers);
		// 
		flagPopup.value.close(); 
	}
	
   
	
	// 显示或隐藏
	function eye(e){
		e.hide = !e.hide;
		let operType = "nohide";
		if(e.hide){
			operType = "hide";
		}
		//当前单元所有句子的操作
		const detailDataOper=[{
			operType,
			dataType:"detail",
			dataId:e.id 
		}];
		console.log("参数->",detailDataOper);
		//操作
		apiOperData(detailDataOper);
	}
	
	

	function play(e, type){ 
		let eid = type + e.id;
		console.log("进入到播放区")
		// uni.showLoading({
		// 	title:"加载中..."
		// })
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
			  let itemm = null;
			  if(type == "detail"){
				 itemm = findItemById(id);
			  }else{
				 itemm = findWordById(id);
			  }
			  if(itemm != null){
				itemm.isPlay = false;
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
	 
	
	// 收藏
	function flag(e){
		e.flag = !e.flag; 
	}
	
	function findItemById(id) {
	    return datas.value.find(item => id.includes(item.id));
	}
	  
	function findWordById(id) {
	  return wordsDetails.value.find(item => id.includes(item.id));
	}
</script>

<style lang="scss" scoped>
	 .layoutCls{
		width: 100%;
		height: 100vh;
		position: relative; 
		.changeCls{ 
			position: fixed;
			display: flex;
			justify-content: right;
			align-items: right;
			padding: 20rpx; 
			right: 0;   
			z-index: 10; 
			margin-top: 50rpx;
			.changeBtnCls{ 
				display: inline-block; 
				background: rgba(0, 0, 0, 0.2); 
				display: flex;
				padding: 17rpx 20rpx;
				margin-left: 20rpx;
				justify-content: center;
				align-items: center;
				border-radius: 50%;
			}
		}
		.titleCls{   
			 font-size: 35rpx;
			 box-shadow: 0 0 30rpx rgba(0,0,0,0.05);
			 left:0;
			 top:0;
			 display: flex;
			 justify-content: left;
			 align-items: center;
			 padding-left: 30rpx;
			 padding: 15rpx;
			 // background-color: #ff9966;
			 margin: 20rpx; 
			 color: #082567;
			 background:
			 		linear-gradient(to bottom,transparent,#fff 400rpx),
			 		linear-gradient(to right,#beecd8 20%,#F4E2D8);
		 }
		 .scrollCls{  
			margin-top: 20rpx;
			width: 100%; 
			padding: 20rpx; 
			box-sizing: border-box; 
			.contentCls{ 
				margin-bottom: 20rpx; 
				background: #f3f7f8; 
				border-radius: 20rpx;
				padding: 15rpx;
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
						font-size: 25rpx;
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
							.myPlay{
								// color:$brand-theme-color !important;
								 padding-left: 20rpx; 
							}
						}	
					}
				 }
			 }
			
		 }
		 .searchPopup{
			 background: #fff;
			 padding: 30rpx;
			 border-radius: 30rpx 30rpx 30rpx 30rpx;
			 width: 80vw;
			 max-height: 80vh;
		 }
		 .flagPopup {
		 	background: #fff;
			padding: 30rpx;
			border-radius: 30rpx 30rpx 30rpx 30rpx;
			width: 80vw;
			overflow: hidden; 
		 
		 	.content {
		 		padding: 30rpx 0;
				.contentText{
					padding: 15rpx;
					font-size: 30rpx;
				}
				 .contentWord{ 
					 display: flex;
					 justify-content: left;
					 align-items: center;
					 border: 1px solid #f3f7f8;
					 border-bottom: 0; 
					.text {
						padding: 15rpx;
						width: 180rpx;
						line-height: 1em;
						text-align: left; 
						font-size: 28rpx;
						white-space: nowrap; /* 禁止换行 */
						overflow: hidden; /* 隐藏溢出内容 */
						text-overflow: ellipsis; /* 显示省略号 */
					} 
					.play{
						margin-left: 10rpx;
						:deep(){
							.uni-icons{
								color:$brand-theme-color !important;
								 padding-left: 20rpx;
							}
						}	
					}
				 }
				 .contentWord:last-child{border-bottom: 1px solid #f3f7f8;}
		 		
		 	}
		 
		 	.footer {
		 		padding: 10rpx 0;
		 		display: flex;
		 		align-items: center;
		 		justify-content: center;
		 	}
		 }
		
	 }
</style>
