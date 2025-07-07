<template>
  <view class="container">
	  <my-firework ref="myFireWorkComponent"></my-firework> 
  </view>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
 
</script>

<style>
.container { 
  width: 100%;
  height: 100%; 
  /* background-color: red; */
} 
</style>