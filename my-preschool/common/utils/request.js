import {getSystemWechatUser} from "@/common/utils/common.js"
 
const BASE_URL = 'http://127.0.0.1:8080';

 
export function playAll_failVoice(){
	playVoice(BASE_URL + "/voice/all_fail.mp3");
}

export function playAll_successVoice(){
	playVoice(BASE_URL + "/voice/all_success.mp3");
}

export function playItem_failVoice(){
	playVoice(BASE_URL + "/voice/item_fail.mp3");
}

export function playItem_successVoice(){
	playVoice(BASE_URL + "/voice/item_success.mp3");
}

let innerAudioContext = null;
export function playVoice(audioUrl){ 
	if(innerAudioContext == null){
		innerAudioContext = uni.createInnerAudioContext();
		innerAudioContext.autoplay = true;
	}else{
		innerAudioContext.stop();
	}
	innerAudioContext.src = audioUrl;
	innerAudioContext.play();
	innerAudioContext.onPlay(() => { 
	  uni.hideLoading();
	});
	innerAudioContext.onError((res) => {
		console.log(res) 
	  uni.hideLoading();
	});
	innerAudioContext.onEnded((res)=>{
		console.log("停止播放->",res); 
		uni.hideLoading();
	})
}

export function request(config={}){	 
	let {
		url,
		data={},
		method="GET",
		header={}
	} = config
	
	console.log("url->",url);
	console.log("data->",data);
	
	let user = getSystemWechatUser();  
	
	url = BASE_URL+url
	header['openId'] = user.openid;
	header['thirdType'] = "wechat"; 
	header['X-WX-SERVICE'] = "springboot-ndhb"; 
	
	return new Promise((resolve,reject)=>{		
		uni.request({
			url,
			data,
			method,
			header, 
			success:res=>{ 
				if(res.data.code=="200"){
					resolve(res.data)
				}else if(res.data.code!="200"){
					uni.showModal({
						title:"错误提示",
						content:res.data.msg,
						showCancel:false
					})
					reject(res.data)
				}else{
					uni.showToast({
						title:res.data.msg,
						icon:"none"
					})
					reject(res.data)
				}				
			},
			fail:err=>{
				reject(err)
			}
		})
	})
}
