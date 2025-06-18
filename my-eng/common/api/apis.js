import {request} from "@/common/utils/request.js"


export function apiGetWechatMiniSessionKey(data={}){
	console.log("查询数据->",data);
	return request({
		url:"/user/getWechatMiniSessionKey",
		data,
		method:"post"
	})
}

export function apiGetEngMain(data={}){ 
	return request({
		url:"/engMain/getIndexEngMain",
		data,
		method:"post"
	})
}

export function apiOperData(data=[{}]){ 
	return request({
		url:"/engMain/operData",
		data,
		method:"post"
	})
}

export function apiGetEngWordByDetail(data={}){ 
	return request({
		url:"/engMain/getEngWordByDetail",
		data,
		method:"post"
	})
}

export function apiGetIndexEngNextMain(data={}){ 
	return request({
		url:"/engMain/getIndexEngNextMain",
		data,
		method:"post"
	})
}

export function apiGetIndexEngUpMain(data={}){ 
	return request({
		url:"/engMain/getIndexEngUpMain",
		data,
		method:"post"
	})
}

export function apiSearchEngMain(data={}){ 
	return request({
		url:"/engMain/getEngMain",
		data,
		method:"post"
	})
}

export function apiSearchChangeEngMain(data={}){ 
	return request({
		url:"/engMain/searchChangeEngMain",
		data,
		method:"post"
	})
}

export function apiGetMyCollect(data={}){ 
	return request({
		url:"/engMain/getMyCollect",
		data,
		method:"post"
	})
}

export function apiDelMyCollect(data={}){ 
	return request({
		url:"/engMain/delMyCollect",
		data,
		method:"post"
	})
}

