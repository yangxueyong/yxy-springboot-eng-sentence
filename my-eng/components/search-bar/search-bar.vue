<!-- 搜索组件 -->
<template>
	<view class="box">
		<uni-section title="左侧滑出" type="line">
				<view class="example-body"> 
					<uni-drawer ref="showLeft" mode="left" :width="showLeftWidth">
						<view>
							<uni-search-bar class="uni-mt-10" radius="5" placeholder="请输入你想搜索的内容" clearButton="auto" cancelButton="none" @confirm="search" />
						</view>
						<scroll-view scroll-y>
						<view class="searchResData"> 
							<uni-list v-if="searchResDatas">
								<view @click="changeItem(item)" v-for="item in searchResDatas" :key="item.id">
									<uni-list-item showArrow :title="item.enName" /> 
								</view>
							</uni-list> 
						</view>
						</scroll-view>
					</uni-drawer>
				</view>
		</uni-section>
	</view>
</template>

<script setup> 
	import {ref} from "vue"; 
	
	const emit = defineEmits(["funSearchData","funSearchChangeItem"]);
	
	const showLeft = ref(null);
	const showLeftWidth = ref(null); 
	// 搜素得到的数据
	const searchResDatas = ref(null);
	
	function open(){
		showLeft.value.open();
	}
	function close(){
		showLeft.value.close();
	}
	function loadData(res){
		console.log("组件得到的数据->",res)
		searchResDatas.value = res;
	}
	
	//向外暴露方法
	defineExpose({ 
		open,
		close,
		loadData
	});
	
	function search(e){
		emit("funSearchData", e.value);
	}
	function changeItem(item){
		console.log("正在选择其中一项",item.id);
		emit("funSearchChangeItem", item.id);
	}
	
	function getSystemWith(){
		uni.getSystemInfo({
			success:(res)=>{
				console.log("获取到的系统信息",res);
				showLeftWidth.value = res.windowWidth - 100;
			}
		})
	}
	getSystemWith();	
</script>

<style lang="scss" scoped>
	.searchResData{
		padding: 20rpx;
		height: calc(100vh - 100rpx);
	}
</style>

