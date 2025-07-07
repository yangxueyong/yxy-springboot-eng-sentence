export function compareTimestamp(timestamp) {
  const currentTime = new Date().getTime();
  const timeDiff = currentTime - timestamp;

  if (timeDiff < 60000) {  
    return '1分钟内';
  } else if (timeDiff < 3600000) {
    return Math.floor(timeDiff / 60000) + '分钟';
  } else if (timeDiff < 86400000) {
    return Math.floor(timeDiff / 3600000) + '小时';
  } else if (timeDiff < 2592000000) {
    return Math.floor(timeDiff / 86400000) + '天';
  } else if (timeDiff < 7776000000) {
    return Math.floor(timeDiff / 2592000000) + '月';
  } else {
    return null;
  }
}

export function showHint(content){
	uni.showModal({
		title: "温馨提示",
		content: content,
		showCancel: false
	});
}

// 获取缓存中的用户信息
export function getSystemWechatUser(){
	return uni.getStorageSync("myUser");
}


// 检查缓存中的用户信息是否为空
export function getSystemWechatUserForward(){
	let wechatUser = getSystemWechatUser();
	if(wechatUser == null || wechatUser.openid == null){
		uni.showToast({
			title:"跳转登录中..."
		});
		uni.reLaunch({
			url:"/pages/my/my"
		})
	}
}

export function gotoHome(){
	uni.showModal({
		title:"提示",
		content:"页面有误将返回首页",
		showCancel:false,
		success: (res) => {
			if(res.confirm){
				uni.reLaunch({
					url:"/pages/index/index"
				})
			}
		}
	})
}

export const getRandomItems = (changeGridList, count) => {
  const result = new Set();
  // 边界条件处理
  if (!changeGridList.length || count <= 0) {
	  return result;
  }
  if (count >= changeGridList.length) {
	  count = changeGridList.length;
  }
  
  // 使用Set确保不重复 
  const maxIndex = changeGridList.length; 
  
  while (result.size < count) {
	  console.log("正在找数据：",count);
    const randomIndex = Math.floor(Math.random() * maxIndex);
	// console.log("随机randomIndex->",randomIndex)
	result.add(randomIndex);
  }
  
  return result;
};

export const getRandomItem = (changeGridList,processedSet) => { 
  // 使用Set确保不重复 
  const maxIndex = changeGridList.length; 
  let randomIndex = -1;
  do{
	 randomIndex = Math.floor(Math.random() * maxIndex); 
	 console.log("正在随机颜色-->", randomIndex);
  }while(processedSet.has(randomIndex) && processedSet.size < changeGridList.length);
  processedSet.add(randomIndex);
  return randomIndex;
};

export const getRandomItemValue = (changeGridList) => {  
  const maxIndex = changeGridList.length; 
  let randomIndex = -1; 
  randomIndex = Math.floor(Math.random() * maxIndex);  
  return changeGridList[randomIndex];
};

/**
 * 生成指定范围内的随机整数
 * @param {number} a - 最小值(包含)
 * @param {number} b - 最大值(包含)
 * @returns {number} 随机整数
 */
function getRandomInRange(a, b) {
  // 参数验证
  if (typeof a !== 'number' || typeof b !== 'number' || 
      !Number.isInteger(a) || !Number.isInteger(b)) {
    throw new Error('参数必须是整数');
  }
  
  if (a < 0 || b < 0) {
    throw new Error('参数必须是正整数');
  }
  
  if (a > b) {
    // 如果a比b大，交换两个值
    [a, b] = [b, a];
  }
  
  // 生成随机数
  return Math.floor(Math.random() * (b - a + 1)) + a;
}

/**
 * 生成指定范围内且不等于排除值的随机整数
 * @param {number} a - 最小值(包含)
 * @param {number} b - 最大值(包含)
 * @param {number} c - 要排除的值
 * @returns {number} 随机整数
 * @throws {Error} 当参数无效或无法生成有效随机数时抛出错误
 */
export function getRandomInRangeExcluding(a, b, c) {
  // 参数验证
  if (!Number.isInteger(a) || !Number.isInteger(b) || !Number.isInteger(c)) {
    throw new Error('所有参数都必须是整数');
  }
  
  if (a < 0 || b < 0 || c < 0) {
    throw new Error('所有参数都必须是正整数');
  }
  
  if (a > b) {
    // 如果a比b大，交换两个值
    [a, b] = [b, a];
  }
  
  // 检查排除值是否在范围内
  if (c < a || c > b) {
    // 如果排除值不在范围内，直接生成普通随机数
    return Math.floor(Math.random() * (b - a + 1)) + a;
  }
  
  // 计算有效数字的数量
  const validNumbersCount = b - a; // 因为排除了一个数
  
  // 如果范围内只有一个数且这个数被排除了
  if (validNumbersCount === 0) {
    throw new Error('没有有效的数字可供选择');
  }
  
  // 生成随机数
  let randomNum;
  do {
    randomNum = Math.floor(Math.random() * (b - a + 1)) + a;
  } while (randomNum === c);
  
  return randomNum;
}


// 替换特殊字符
export function repstr(name){
	return name.replace(",","")
	.replace(".","")
	.replace("，","")
	.replace("。","")
	.replace("！","")
	.replace("!","")
	.replace("、","")
	.replace("  "," "); 
}
	
// originalText 正常的字符串
// compareText 输入的字符串
export function highlightedText(originalText,compareText) { 
	// 将字符串分割为单词数组（保留空格和标点）
	  const tokenize = (str) => {
	    return str.split(/(\s+|'\w+|\w+'\w+|\w+|[^\w\s])/).filter(t => t.trim().length > 0);
	  };
	
	  const originalTokens = tokenize(originalText.toLowerCase());
	  const compareTokens = tokenize(compareText.toLowerCase());
	  const compareOriginalTokens = tokenize(compareText); // 保留原始大小写
	
	  let result = [];
	  let originalIndex = 0;
	  let compareIndex = 0;
	
	  while (compareIndex < compareTokens.length || originalIndex < originalTokens.length) {
	    // 比较文本有额外token
	    if (originalIndex >= originalTokens.length) {
	      result.push(`<span class="diff-added">${compareOriginalTokens[compareIndex]}</span>`);
	      compareIndex++;
	      continue;
	    }
	
	    // 原始文本有额外token
	    if (compareIndex >= compareTokens.length) {
	      originalIndex++;
	      continue;
	    }
	
	    // token匹配
	    if (compareTokens[compareIndex] === originalTokens[originalIndex]) {
	      result.push(compareOriginalTokens[compareIndex]);
	      originalIndex++;
	      compareIndex++;
	    } 
	    // token不匹配
	    else {
	      // 检查是否是拼写错误（相似单词）
	      if (isTypo(compareTokens[compareIndex], originalTokens[originalIndex])) {
	        result.push(`<span style="background-color: #ff0000;font-weight: bold;color: #fff;">${compareOriginalTokens[compareIndex]}</span>`);
	        originalIndex++;
	        compareIndex++;
	      }
	      // 检查是否是新增内容
	      else if (isAddedContent(compareTokens, originalTokens, compareIndex, originalIndex)) {
	        result.push(`<span style="background-color: #ffeb3b;font-weight: bold;">${compareOriginalTokens[compareIndex]}</span>`);
	        compareIndex++;
	      }
	      // 默认处理为修改
	      else {
	        result.push(`<span class="diff-modified">${compareOriginalTokens[compareIndex]}</span>`);
	        originalIndex++;
	        compareIndex++;
	      }
	    }
	  }
	
	  return result.join(' ');
}

// 判断是否是拼写错误（相似单词）
function isTypo(word1, word2) {
  if (Math.abs(word1.length - word2.length) > 1) return false;
  
  let diff = 0;
  const maxLength = Math.max(word1.length, word2.length);
  for (let i = 0; i < maxLength; i++) {
    if (word1[i] !== word2[i]) diff++;
    if (diff > 1) return false;
  }
  return true;
}

// 判断是否是新增内容
function isAddedContent(compareTokens, originalTokens, compareIndex, originalIndex) {
  // 检查接下来的几个token是否都不匹配
  for (let i = 0; i < 3; i++) {
    if (compareIndex + i < compareTokens.length && 
        originalIndex + i < originalTokens.length &&
        compareTokens[compareIndex + i] === originalTokens[originalIndex + i]) {
      return false;
    }
  }
  return true;
}

function updateComparison() {
  // 触发重新计算
  highlightedResult.value;
}
		
