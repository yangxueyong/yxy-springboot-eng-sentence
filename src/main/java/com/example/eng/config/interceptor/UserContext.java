package com.example.eng.config.interceptor;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.eng.entity.user.User;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * UserContext
 * @author senfel
 * @version 1.0
 * @date 2025/2/18 17:01
 */
public class UserContext {
    // 创建一个 ThreadLocal 实例来存储 User 对象
    private static final ThreadLocal<Map<String, Object>> userThreadLocal = new TransmittableThreadLocal<>();

    public static void setUser(User user){
        Map<String, Object> stringObjectMap = userThreadLocal.get();
        if (stringObjectMap == null) {
            stringObjectMap = new HashMap<>();
            userThreadLocal.set(stringObjectMap);
        }
        stringObjectMap.put("user", user);
    }

    // 获取用户数据
    public static User getUser() {
        Map<String, Object> stringObjectMap = userThreadLocal.get();
        if(CollectionUtils.isEmpty(stringObjectMap) || !stringObjectMap.containsKey("user")){
            return null;
        }
        return (User) stringObjectMap.get("user");
    }

    // 删除用户数据
    public static void clearUser() {
        userThreadLocal.remove();
    }
}
