package com.example.eng.web;

import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.EngSentenceWord;
import com.example.eng.entity.eng.io.EngSentenceDetailIO;
import com.example.eng.entity.eng.io.EngSentenceMainIO;
import com.example.eng.entity.eng.io.EngSentenceWordIO;
import com.example.eng.entity.res.R;
import com.example.eng.entity.user.wechat.WechatMiniAppSessionKey;
import com.example.eng.entity.user.wechat.WechatUser;
import com.example.eng.entity.user.wechat.io.WechatMiniAppSessionIO;
import com.example.eng.entity.user.wechat.io.WechatTokenIO;
import com.example.eng.service.EngSentenceDetailService;
import com.example.eng.service.EngSentenceMainService;
import com.example.eng.service.EngSentenceWordService;
import com.example.eng.service.WechatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("/user")
@Tag(name = "用户相关")
public class UserController {

    @Autowired
    private WechatService wechatService;

    @RequestMapping("/getWechatMiniSessionKey")
    @Operation(summary = "获取微信小程序的sessionKey用户信息")
    public R<WechatMiniAppSessionKey> getWechatMiniSessionKey(@RequestBody WechatTokenIO io){
        return R.data(wechatService.getWechatMiniSessionKey(io));
    }

//    @RequestMapping("/saveWechatMiniSessionKey")
//    @Operation(summary = "获取微信小程序的sessionKey用户信息")
//    public R saveWechatMiniSessionKey(WechatMiniAppSessionIO io){
//        wechatService.saveWechatMiniSessionKey(io);
//        return R.success("保存成功");
//    }
}
