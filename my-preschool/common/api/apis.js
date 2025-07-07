import {request} from "@/common/utils/request.js"


export function apiGetWechatMiniSessionKey(data={}){
	console.log("查询数据->",data);
	return request({
		url:"/user/getWechatMiniSessionKey",
		data,
		method:"post"
	})
}
 
export function apiGetGameTypeList(data={}){
	console.log("查询数据->",data);
	return request({
		url:"/game/gameTypeList",
		data,
		method:"post"
	})
}
 
export function apiGetGameColorList(data={}){
	console.log("查询数据->",data);
	return request({
		url:"/game/gameColorList",
		data,
		method:"post"
	})
}
 
 export function apiGetNumLevelList(data={}){
 	console.log("查询数据->",data);
 	return request({
 		url:"/game/numLevelList",
 		data,
 		method:"post"
 	})
 }
  
  
export function apiGetGameAnimalList(data={}){
	console.log("查询数据->",data);
	return request({
		url:"/game/gameAnimalList",
		data,
		method:"post"
	})
}

export function apiSaveGameScore(data={}){
	console.log("保存数据->",data);
	return request({
		url:"/game/saveGameScore",
		data,
		method:"post"
	})
}



