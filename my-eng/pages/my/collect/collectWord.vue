<template>
	<view class="layoutCls">
		<a-trumpet isPlay="false"></a-trumpet>
		<a-trumpet isPlay></a-trumpet>
		<a-trumpet :isPlay="isplay" color="#1100ff"></a-trumpet>
		<a-trumpet :isPlay="isplay" :size="50"></a-trumpet>
		<a-trumpet :isPlay="isplay" direction="right"></a-trumpet>
	</view>
</template>

<script setup> 
import {ref} from "vue"; 
</script>

<style lang="scss" scoped> 
</style>

