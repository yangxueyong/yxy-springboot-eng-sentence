<template>
	<view class="box">
		<!-- <button type="default" open-type="getPhoneNumber" @getphonenumber="decryptPhoneNumber">获取手机号</button> -->
		<view class="userInfo" @click="userLogin()" v-if="userinfo">
			<view class="avatar">
				<image :src="userinfo.avatarUrl" mode="aspectFill"></image>
			</view>
			<view class="ip">{{userinfo.nickName}}</view>
			<view class="address">
				{{ userinfo.province || userinfo.city}}
			</view>
		</view>
		
		<view class="section">
			 <navigator url="/pages/my/collect/collectSentence?collectType=detail&name=我收藏的句子">
				 <view class="myCls">
					 <view class="myLeft">
						 <uni-icons size="30" type="flag-filled" ></uni-icons> 
					 </view>
					 <view class="textCls">
						我收藏的句子					 
					 </view>
					 <view class="myRight">
						<uni-icons size="30" type="forward" ></uni-icons> 					 
					 </view>
				 </view>
			 </navigator>
			 <navigator url="/pages/my/collect/collectSentence?collectType=word&name=我收藏的单词">
				 <view class="myCls">
					 <view class="myLeft">
						 <uni-icons size="30" type="flag-filled" ></uni-icons> 
					 </view>
					 <view class="textCls">
						我收藏的单词					 
					 </view>
					 <view class="myRight">
						<uni-icons size="30" type="forward" ></uni-icons> 					 
					 </view>
				 </view>
			 </navigator>
			 <view class="myCls">
				 <view class="myLeft">
					 <uni-icons size="30" type="flag-filled" ></uni-icons> 
				 </view>
				 <view class="textCls">
					我已学习的课程					 
				 </view>
				 <view class="myRight">
					<uni-icons size="30" type="forward" ></uni-icons> 					 
				 </view>
			 </view>
		</view> 
	</view>
</template>

<script setup> 
import {ref} from "vue";
import {apiGetWechatMiniSessionKey} from "@/common/api/apis.js"

const userinfo = ref(uni.getStorageSync("myUser") || {"nickName":"未登录","avatarUrl":"/static/images/user/noLogin.png"})


function userLogin(type, getPhoneRes){ 
	//获取用户的基本信息，昵称等
	uni.login({
	    provider: 'weixin',
	    success: function (loginRes) {
	        // 登录成功
	        uni.getUserInfo({
	            provider: 'weixin',
	            success: function(info) {
	                // 获取用户信息成功, info.authResult保存用户信息
					// console.log("个人信息", info);
					userinfo.value = info.userInfo;
					//存储个人信息到缓存
					uni.setStorageSync("myUser", userinfo.value);
					//获取用户的openId
					getUserOpenId();
	            }
	        })
	    },
	    fail: function (err) {
	        // 登录授权失败
	        // err.code是错误码
	    }
	});
	
	
}

// 获取用户的openid
function getUserOpenId(){
	//获取token和openid
	uni.login({
		"provider": "weixin",
		"onlyAuthorize": true, // 微信登录仅请求授权认证
		success: function(event){
			const {code} = event;
			getWechatMiniSessionKey(code); 
		},
		fail: function (err) {
	        // 登录授权失败
	        // err.code是错误码
	    }
	})
}

const getWechatMiniSessionKey =async(code)=>{ 
	let res =await apiGetWechatMiniSessionKey({
		code:code,
		wechatUser:uni.getStorageSync("myUser")
	}); 
	
	let sessionKey = res.data.session_key;
	let openid = res.data.openid;
	console.log("获取到的sessionKey->", sessionKey);
	console.log("获取到的openid->", openid);
	
	userinfo.value.sessionKey = sessionKey;
	userinfo.value.openid = openid;
	console.log("获取到的userinfo->", userinfo);
	
	
	uni.setStorageSync("myUser", userinfo.value);
	
	// if(type == "getPhoneNumber"){
	// 	let phone_encryptedData = getPhoneRes.detail.encryptedData //用于解密
	// 	let phone_iv = getPhoneRes.detail.iv // 用于解密
	// 	console.log(getPhoneRes, "获取手机号需要的参数");
	// 	let pc = new WXBizDataCrypt('wx87cb4cd6a4321f5b', sessionKey);
	// 	let data = pc.decryptData(this.phone_encryptedData, phone_iv);
	// 	console.log(data);
	// 	if (data.phoneNumber != '') {
	// 		// this.phone = data.phoneNumber
	// 	} 
	// }
}
 


function decryptPhoneNumber(res){
	userLogin("getPhoneNumber", res);
}

</script>

<style lang="scss" scoped>
	.userInfo{
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;		
		padding:50rpx 0;
		.avatar{
			width: 160rpx;
			height: 160rpx;
			border-radius: 50%;
			overflow: hidden;
			image{
				width: 100%;
				height: 100%;
			}
		}
		.ip{
			font-size: 44rpx;
			color:#333;
			padding:20rpx 0 5rpx;
		}
		.address{
			font-size: 28rpx;
			color:#aaa;
		}
	}
	
	.section{
		width: 690rpx;
		margin:10rpx auto;  
		font-size: 25rpx;
		.myCls{
			border-radius: 10rpx; 
			box-shadow: 0 0 30rpx rgba(0,0,0,0.05);
			height: 80rpx;  
			justify-content: left;
			align-items: center;
			display: flex; 
			justify-content: space-between;
			margin: 20rpx 0rpx;
			padding: 10rpx;
			.myLeft{
				width: 50rpx; 
			}
			.textCls{
				padding-left: 20rpx; 
				flex: 1;
				width: 0; 
			}
			.myRight{
				display: flex;
				position: relative;
				right: 0rpx;
				width: 50rpx;
				height: 100%; 
				align-items: center;
			}
			:deep(){
				.uni-icons{
					color:$brand-theme-color !important;
				}
			}	
		}
	}
</style>

