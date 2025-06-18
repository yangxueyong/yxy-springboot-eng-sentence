<template>
	<view class="layoutCls">
		<view class="changeCls">
			<view class="changeBtnCls" @click="randomLoad"><uni-icons type="refreshempty" size="20"></uni-icons></view>
			<view class="changeBtnCls" @click="getIndexEngUpMain"><uni-icons type="back" size="20"></uni-icons></view>
			<view class="changeBtnCls" @click="getIndexEngNextMain"><uni-icons type="forward" size="20"></uni-icons></view>
			<view class="changeBtnCls" @click="openSearch"><uni-icons type="search" size="20"></uni-icons></view>
		</view>
		<view class="titleCls">
			<view>{{mainData.enName}}</view>
		</view>
		<scroll-view class="scrollCls" scroll-y="true" scroll-x="false"> 
			<view class="contentCls" v-for="item in datas" :key="item.id">
				<view class="zhCls">{{item.znName}}</view>
				
				<view class="translationCls">
					<view class="engCls">
						<input class="uni-input" placeholder="请输入英文" v-model="item.myInput" />
					</view> 
					<view class="hidCls">
						<uni-icons type="auth" size="25" @click="verify(item)"></uni-icons>
					</view>
				</view>
				
				<view class="translationCls">
					<view class="engCls">
						<view class="textCls" 
						 v-if="!item.hide"
						>
							正确答案:{{item.enName}}
						</view> 
						<view class="textCls"
						 v-if="item.hide"
						>
							点击右侧'眼睛'显示答案
						</view> 
						<view class="textCls" >
							<rich-text :nodes="item.compareMyInput"></rich-text>
							<!-- <mp-html :content="item.compareMyInput" /> -->
						</view>
					</view>
					<view class="hidCls">
						<uni-icons size="25" :type="item.hide?'eye-slash':'eye'" @click="eye(item)"></uni-icons> 
					</view>
				</view>
			</view>
		</scroll-view>
		<search-bar
		@funSearchData="onFunSearchData" 
		@funSearchChangeItem="onFunSearchChangeItem" 
		ref="mySearchBar"></search-bar>
	</view>
</template>

<script setup> 
	import {ref} from "vue";
	import {onReachBottom,onPullDownRefresh} from "@dcloudio/uni-app";
	import {getSystemWechatUserForward,repstr,highlightedText} from "@/common/utils/common.js";
	import {apiGetEngMain,
	apiOperData,
	apiGetEngWordByDetail,
	apiGetIndexEngNextMain,
	apiGetIndexEngUpMain,
	apiSearchEngMain,
	apiSearchChangeEngMain} from "@/common/api/apis.js";
	import {getDetailVoicePath,getWordVoicePath} from "@/common/utils/request.js";
		
	getSystemWechatUserForward();
	
	const mySearchBar = ref(null);
	
	const mainData = ref({});
	const datas=ref([{}]); 
	
	const getEngMain =async(loadType)=>{
		uni.showLoading({
			title:"加载中..."
		});
		let res =await apiGetEngMain({ 
			pageType:"practice",
			loadType
		}); 
		console.log("获取到的主页数据->",res);
		mainData.value = res.data;
		datas.value = res.data.details;
		uni.stopPullDownRefresh();
		uni.hideLoading()
	}
	getEngMain("");
	
	// 随机加载
	function randomLoad(){
		getEngMain("random");
	}
	
	// 打开搜索框
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
			pageType:"practice"
		 }); 
		 getEngMain(); 
	}
	
	const getIndexEngNextMain =async()=>{
		let res =await apiGetIndexEngNextMain({ 
			"type":"next",
			pageType:"practice"
		}); 
		console.log("获取到的主页数据->",res);
		mainData.value = res.data;
		datas.value = res.data.details;
		uni.stopPullDownRefresh();
		uni.hideLoading()
	} 
	
	const getIndexEngUpMain=async()=>{
		let res =await apiGetIndexEngUpMain({ 
			"type":"up",
			pageType:"practice"
		}); 
		console.log("获取到的主页数据->",res);
		mainData.value = res.data;
		datas.value = res.data.details;
		uni.stopPullDownRefresh();
		uni.hideLoading()
	} 
	

	 
	function verify(e){
		const item = findItemById(e.id);
		const enName = item.enName;
		const myInput = item.myInput;
		if(!myInput){
			uni.showToast({
				title:"请输入答案",
				icon:"error"
			});
			return;
		}
		
		const newEnName = repstr(enName);
		const newMyInput = repstr(myInput); 
		
		const htext =  highlightedText(newEnName, newMyInput);
		item.compareMyInput = "你的答案:" + htext;
		console.log("比较后的数据->", htext);
		
		item.hide = false;
		
		if(newEnName == newMyInput){
			uni.showToast({
				title:"恭喜，回答正确"
			});
		}else{
			uni.showToast({
				title:"回答错误", 
				duration: 800,
				icon:"error"
			});
		}
	}
	
	function findItemById(id) {
	    return datas.value.find(item => item.id === id);
	}
	  
	  function eye(e){
		  e.hide = !e.hide;
	  } 
</script>

<style lang="scss" scoped>
	.diff-modified {
	  background-color: #ff0000; /* 黄色高亮修改的单词 */
	  font-weight: bold;
	  color: #fff;
	}
	.diff-added {
	  background-color: #ffeb3b; /* 绿色高亮新增的单词 */
	  font-weight: bold;
	}
	
	 .layoutCls{
		width: 100%; 
		box-sizing: border-box; 
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
				border-radius: 50rpx;
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
			padding: 10rpx; 
			box-sizing: border-box; 
			.contentCls{ 
				margin-bottom: 20rpx; 
				background: #f3f7f8; 
				border-radius: 20rpx;
				padding: 10rpx;
				.zhCls{
					font-size: 25rpx;
					padding: 10rpx;
					border-bottom: 1px solid #ebf1f3;
				}
				.translationCls{
					display: flex;
					position: relative;
					box-sizing: border-box; 
					.engCls{
						flex: 1;
						width: 0; 
						padding: 10rpx;
						box-sizing: border-box; 
						font-size: 25rpx;
						.textCls{
							padding: 5rpx 10rpx 5rpx 10rpx;
							// min-height: 50rpx;
						}
						input{
							padding: 2px 5px;
							border: 1px solid #e0e0e0;
							border-radius: 4px;
							background-color: #f3f7f8;
							transition: all 0.3s ease;
							outline: none;  
						}
						input:hover {
						  background-color: #f5f5f5;
						  border-color: #d0d0d0;
						}
						
						input:focus {
						  background-color: #fff;
						  border-color: #a0c4ff;
						  box-shadow: 0 0 0 2px rgba(160, 196, 255, 0.2);
						}
					}
					.hidCls{
						width: 50rpx;	
						display: flex;
						// position: absolute; 
						justify-content: center;
						align-items: center;
						right: 0;
						:deep(){
							.uni-icons{
								color:$brand-theme-color !important;
							}
						}	
					}
				 }
			 }
			
		 }
		
	 }
	 
	 
</style>
