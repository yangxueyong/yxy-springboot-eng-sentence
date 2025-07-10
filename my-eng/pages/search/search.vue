<!-- 搜索组件 -->
<template>
	<view class="box"> 
		<view>
			<uni-search-bar class="uni-mt-10" radius="5" placeholder="请输入你想搜索的内容" clearButton="auto" cancelButton="none" @confirm="search" />
		</view>
		<view class="searchHistoryCls" v-if="searchHisDatas && searchHisDatas.length">
		  <view class="searchHistoryItemCls">
		    <!-- 历史记录容器改为可换行的flex容器 -->
		    <view class="historyItemsContainer">
		      <view 
		        class="searchHistoryDataCls" 
		        v-for="item in searchHisDatas" 
		        @click="historySearch(item)" 
		        :key="item"
		      >
		        {{item}}
		      </view>
		    </view>
		    <!-- 删除按钮 -->
		    <view class="deleteAllHistoryCls" @click="deleteAllHistory(item)">
		      <uni-icons type="trash" size="25"></uni-icons>
		    </view>
		  </view>
		</view>
		<scroll-view  class="searchResDataCls" scroll-y  @scrolltolower="loadMoreData">
			<!-- <view class="dataMain1Cls">  -->
				<view  class="dataMain2Cls" @click="changeItem(item)" v-for="item in searchResDatas" :key="item.id">
					<view class="dataCls">
						<view class="typeCls">
							<view :class="item.type == 'main' ? 'dataTypeMainCls' : 'dataTypeDetailCls'">
								[{{item.type == 'main' ? '句型' : '句子' }}]
							</view>
						</view>
						<view class="dataNameCls">
							<view class="dataZhTypeCls"> 
								<view class="dataZhNameCls">
									{{removeLeadingZeros(item)}}
								</view>
							</view>
							<view class="dataEnNameCls">
								{{item.hide ? '***':item.enName}}
							</view> 
						</view>
						<view class="hidCls" v-if="item.type == 'main'">
							<view @click="openStudy(item)">
								<image src="/static/images/search/xuexi.png" class="mainImgCls"></image> 
							</view>
							<view @click="openPractice(item)"> 
								<image src="/static/images/search/lianxi.png" class="mainImgCls"></image>
							</view>
						</view>
						<view class="hidCls" v-if="item.type == 'detail'">
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
			<!-- </view> -->
			<view class="loadingLayout" v-if="noData || searchResDatas.length">
				<uni-load-more :status="noData?'noMore':'loading'"/>
			</view>
		</scroll-view>   
	</view>
</template>

<script setup> 
	import {ref,onMounted} from "vue"; 
	import {onLoad,onUnload,onReachBottom,onShareAppMessage,onShareTimeline,onPullDownRefresh} from "@dcloudio/uni-app"
	import {apiNewSearchEngMain,apiSearchChangeEngMain} from "@/common/api/apis.js"
	import {playVoice, getDetailVoicePath} from "@/common/utils/request.js"
	import { showHint } from "@/common/utils/common.js";
	
	//没有更多
	const noData = ref(false);
	const searchResDatas = ref([]);
	
	//历史搜索数据
	const searchHisDatas = ref([]);
	
	let searchHisDataKey = "searchHisDataKey";
	
	const queryParams = {
		pageNum:1,
		pageSize:20
	}
	
	// 当为句子时，组装所属课程
	function removeLeadingZeros(item) {
		let znName = item.znName;
		let sort = item.sort;
		let type = item.type;
		if(type == "main"){
			return znName;
		}
	  return "[课" + sort.replace(/^0+/, '') + "] " + znName;
	}
	
	onMounted(()=>{
		searchHisDatas.value = uni.getStorageSync(searchHisDataKey) || [];
	});
	
	function storeCacheToHistory(v){
		let datas = uni.getStorageSync(searchHisDataKey) || []; 
		let datass = [...new Set([v, ...datas])].slice(0,10); 
		searchHisDatas.value = datass;
		uni.setStorageSync(searchHisDataKey, datass);
	}
	// 删除所有历史
	function deleteAllHistory(){
		uni.removeStorageSync(searchHisDataKey);
		searchHisDatas.value = [];
	}
	
	function loadData(res){
		console.log("组件得到的数据->",res)
		searchResDatas.value = [...searchResDatas.value, ...res];
	}
	 
	 let oldPlayItem = null;
	 function play(item){
		 item.isPlay = true;
		 if(oldPlayItem){ 
			 oldPlayItem.isPlay = false;
		 }
		 oldPlayItem = item;
		 playVoice(item, getDetailVoicePath(item));
		
	 }
	 //getDetailVoicePath(e)
	 
	 //被组件调用
	const onFunSearchData=async(e)=>{
		 queryParams.searchText = e;
		 let res =await apiNewSearchEngMain(queryParams);  
		 loadData(res.data);
		 if(queryParams.pageSize > res.data.length){
		 	noData.value = true;
		 }else{
			 noData.value = false;
		 }
	}
	
	function search(e, type='search'){
		let data = null;
		if(type === 'search'){
			if(e.value == null || e.value.trim() == ''){
				showHint("请输入你要搜索的内容");
				return;
			}
			if(e.value.length > 20){
				showHint("搜索的内容不能超过20个字");
				return;
			}
			data = e.value; 
		}else{
			data = e;
		}
		console.log("准备存储->", data);
		storeCacheToHistory(data);
		noData.value = false;
		queryParams.pageNum = 1;
		searchResDatas.value = [];
		onFunSearchData(data);
		// emit("funSearchData", e.value);
	}
	
	
	function historySearch(data){
		search(data,'history');
	}
	
	
	function changeItem(item){
		// console.log("正在选择其中一项",item.id);
		// emit("funSearchChangeItem", item.id);
	}
	function eye(item){
		item.hide = !item.hide;
	}
	// //触底加载更多
	// onReachBottom(()=>{
	// 	console.log("触地了-->");
	// 	if(noData.value) return;
	// 	queryParams.pageNum++
	// 	onFunSearchData(queryParams.searchText);
	// })
	
	function loadMoreData(){
		// console.log("are you sure");
		if(noData.value) return;
		queryParams.pageNum++
		onFunSearchData(queryParams.searchText);
	}
	
	function getSystemWith(){
		uni.getSystemInfo({
			success:(res)=>{
				// console.log("获取到的系统信息",res);
				// showLeftWidth.value = res.windowWidth - 100;
			}
		})
	}
	getSystemWith();	
	
	//被组件调用
	const onFunSearchChangeItem=async(e, pageType)=>{  
		// mySearchBar.value.close(); 
		 let res =await apiSearchChangeEngMain({
		 	id:e,
			pageType:pageType
		 });  
	}
	
	// //被组件调用
	// const onFunSearchChangeItem=async(e)=>{ 
	// 	console.log("页面得到了->",e);
	// 	mySearchBar.value.close(); 
	// 	 let res =await apiSearchChangeEngMain({
	// 	 	id:e,
	// 		pageType:"practice"
	// 	 }); 
	// 	 getEngMain(); 
	// }
	
	
	//打开首页
	async function openStudy(item){
		await onFunSearchChangeItem(item.id, 'study');
		uni.setStorageSync("onLoadStudyId", item.id);
		uni.switchTab({
			url:"/pages/index/index"
		})
	}
	async function openPractice(item){
		await onFunSearchChangeItem(item.id, 'practice');
		uni.setStorageSync("onLoadPracticeId", item.id);
		uni.switchTab({
			url:"/pages/practice/practice"
		})
	}
</script>

<style lang="scss" scoped>
	.box{
		width: 100vw;
		height: 100vh;
		overflow: hidden; 
		font-size: 25rpx;
	}
	.searchHistoryCls {
	  padding: 20rpx; 
	  margin-left: 20rpx;
	  margin-right: 20rpx;
	  background-color: #f3f3f3;
	}
	
	/* 关键修改：父容器设置垂直居中 */
	.searchHistoryItemCls {
	  display: flex;
	  align-items: center; /* 垂直居中 */
	  width: 100%;
	  // background-color: red;
	}
	
	/* 历史记录容器 */
	.historyItemsContainer {
	  flex: 1;
	  display: flex;
	  flex-wrap: wrap;
	  gap: 10rpx;
	  margin-right: 10rpx;
	  align-items: center; /* 新增：内部项目垂直居中 */
	  min-height: 60rpx; /* 确保有最小高度 */
	}
	
	/* 历史记录项 */
	.searchHistoryDataCls {
	  background-color: #ccc;
	  padding: 10rpx; 
	  border-radius: 10rpx;
	  max-width: 100%;
	  word-break: break-all;
	  /* 确保单个项目也能垂直居中 */
	  display: inline-flex; /* 或 inline-block */
	  align-items: center;
	}
	
	/* 删除按钮 */
	.deleteAllHistoryCls {
	  // background-color: red;
	  width: 60rpx;
	  height: 60rpx; /* 与容器高度匹配 */
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  flex-shrink: 0;
	  // background-color: #80ffd9;
	}
	
	
	.searchResDataCls{
		  height: calc(100vh - 120rpx - var(--window-bottom)) !important;
		  padding: 20rpx;
		  margin-bottom: 10rpx;
		  display: flex;
		  flex-direction: column;
		  /* 添加以下两行 */
		  box-sizing: border-box;
		  width: 100%;
		   /* 添加底部安全区域 */
		    padding-bottom: constant(safe-area-inset-bottom);
		    padding-bottom: env(safe-area-inset-bottom);
	}
	// .dataMain1Cls{
	// 	padding: 20rpx;
	// 	margin-bottom: 10rpx;
	// 	// background-color: #ffcccc;
	// }
	.dataMain2Cls{ 
		 margin-bottom: 20rpx;
		  background: #f3f7f8;
		  border-radius: 20rpx;
		  padding: 15rpx;
		  /* 修改为以下内容 */
		  box-sizing: border-box;
		  width: 100%;
		  max-width: 100%;
		
	}
	
	.dataCls {
	  display: flex; // 使用flex布局
	  align-items: center; // 垂直居中
	  width: 100%; // 确保父容器宽度填满
	  
	  .dataNameCls {
	    flex: 1; // 占据剩余所有空间
	    // overflow: hidden; // 防止内容溢出
	    // text-overflow: ellipsis; // 文字超出显示省略号
	    // white-space: nowrap; // 不换行
	    padding-right: 20rpx; // 和右边保持一点间距
	  }
	  
	  .typeCls {
	    // width: 70rpx; // 固定宽度
	    display: flex; // 子元素水平排列
	    justify-content: space-between; // 子元素平均分布
	    align-items: center; // 子元素垂直居中
	  		margin-right: 20rpx;
	  }
	  
	  .hidCls {
	    width: 150rpx; // 固定宽度
	    display: flex; // 子元素水平排列
	    justify-content: space-between; // 子元素平均分布
	    align-items: center; // 子元素垂直居中
		margin-right: 20rpx;
		// background-color: red;
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
	    > view { // 针对hidCls下的直接view子元素
	      display: flex;
	      align-items: center;
	      justify-content: center;
	    }
	  }
	}
	
	.dataZhTypeCls {
	  display: flex;          // 启用flex布局
	  flex-direction: row;    // 子元素横向排列（默认值，可省略）
	  align-items: center;   // 垂直居中（可选） 
	  
	  border-bottom: 1px solid #ebf1f3;
	  
	}
	
	.dataTypeCls {
	  padding: 10rpx;
	  margin-right: 10rpx;  // 可选：添加右边距分隔两个元素
	  background-color: pink;
	  color: red;
	  border-radius: 10rpx;
	}
	
	.dataTypeMainCls {
	  padding: 5rpx;
	  margin-right: 10rpx;  // 可选：添加右边距分隔两个元素
	  background-color: pink;
	  color: red;
	   border-radius: 10rpx;
	}
	
	.dataTypeDetailCls {
	  padding: 5rpx;
	  margin-right: 10rpx;  // 可选：添加右边距分隔两个元素
	  background-color: #80ffd9;
	  color: #43655b;
	   border-radius: 10rpx;
	}
	
	.dataZhNameCls {
	  // 可以添加其他样式
	  margin-bottom: 10rpx;
	}
	.dataEnNameCls{
	  margin-top: 10rpx;
	}
	
	.mainImgCls{
		width: 45rpx;
		height: 45rpx;
		padding-left: 20rpx;
	}
	
	// .hidCls{
	// 	width: 150rpx;	 
	// 	// background: red;
	// 	display: flex;
	// 	// position: absolute; 
	// 	justify-content: center;
	// 	align-items: center;
	// 	right: 0;
	// 	:deep(){
	// 		.uni-icons{
	// 			color:$brand-theme-color !important;
	// 			 padding-left: 20rpx;
	// 		}
	// 		.myPlay{
	// 			// color:$brand-theme-color !important;
	// 			 padding-left: 20rpx; 
	// 		}
	// 	}	
	// }
</style>

