package com.example.eng.web;

import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.EngSentenceWord;
import com.example.eng.entity.eng.io.EngSentenceDetailIO;
import com.example.eng.entity.eng.io.EngSentenceMainIO;
import com.example.eng.entity.eng.io.EngSentenceWordIO;
import com.example.eng.entity.res.R;
import com.example.eng.service.EngSentenceDetailService;
import com.example.eng.service.EngSentenceMainService;
import com.example.eng.service.EngSentenceWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("/engMain")
@Tag(name = "句型-主表")
public class EngMainController {

    @Autowired
    private EngSentenceMainService engSentenceMainService;

    @Autowired
    private EngSentenceDetailService engSentenceDetailService;

    @Autowired
    private EngSentenceWordService engSentenceWordService;


    @PostMapping("/getEngMain")
    @Operation(summary = "获取句子主表数据")
    public R<List<EngSentenceMain>> getEngMain(@RequestBody @Validated EngSentenceMainIO io){
        return R.data(engSentenceMainService.selectOrderBySort(io));
    }


    @PostMapping("/getEngDetailList")
    @Operation(summary = "获取句子详情数据")
    public R<List<EngSentenceDetail>> getEngDetailList(@RequestBody @Validated EngSentenceDetailIO io){
        return R.data(engSentenceDetailService.selectListByIO(io));
    }

    @PostMapping("/getEngDetail")
    @Operation(summary = "获取句子详情数据")
    public R<EngSentenceDetail> getEngDetail(@RequestBody @Validated EngSentenceDetailIO io){
        return R.data(engSentenceDetailService.selectDetailByIO(io));
    }

    @PostMapping("/getEngWordByDetail")
    @Operation(summary = "获取句子中单词详情数据")
    public R<List<EngSentenceWord>> getEngWordByDetail(@RequestBody @Validated EngSentenceWordIO io){
        return R.data(engSentenceWordService.selectByIO(io));
    }
}
