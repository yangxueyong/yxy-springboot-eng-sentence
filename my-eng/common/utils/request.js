import {getSystemWechatUser} from "@/common/utils/common.js"

const BASE_URL = 'http://172.20.10.10:8080';

export function getDetailVoicePath(webPath){ 
	let user = getSystemWechatUser(); 
	return BASE_URL  + webPath + "?openId=" + user.openid + "&thirdType=wechat";
}

export function getWordVoicePath(webPath){ 
	let user = getSystemWechatUser(); 
	return BASE_URL  + webPath + "?openId=" + user.openid + "&thirdType=wechat";
}

export function request(config={}){	 
	let {
		url,
		data={},
		method="GET",
		header={}
	} = config
	
	let user = getSystemWechatUser();  
	
	url = BASE_URL+url
	header['openId'] = user.openid;
	header['thirdType'] = "wechat"; 
	
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
