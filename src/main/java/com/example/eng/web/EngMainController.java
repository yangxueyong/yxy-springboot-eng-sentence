package com.example.eng.web;

import com.example.eng.config.param.AudioParam;
import com.example.eng.config.param.oss.OssConfig;
import com.example.eng.constant.MyConstant;
import com.example.eng.entity.down.io.DownLoadFileIO;
import com.example.eng.entity.down.vo.DownLoadFileVO;
import com.example.eng.entity.eng.EngSentenceDetail;
import com.example.eng.entity.eng.EngSentenceMain;
import com.example.eng.entity.eng.EngSentenceWord;
import com.example.eng.entity.eng.EngUserOper;
import com.example.eng.entity.eng.io.*;
import com.example.eng.entity.eng.vo.EngCollectVO;
import com.example.eng.entity.eng.vo.EngSentenceMainVO;
import com.example.eng.entity.eng.vo.EngSentenceWordVO;
import com.example.eng.entity.eng.vo.SearchEngSentenceVO;
import com.example.eng.entity.res.R;
import com.example.eng.entity.user.wechat.WechatUser;
import com.example.eng.entity.user.wechat.io.WechatTokenIO;
import com.example.eng.service.*;
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
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Autowired
    private EngUserOperService operService;

    @Autowired
    private AudioParam audioParam;

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private DownLoadService downLoadService;

    @PostMapping("/getIndexEngMain")
    @Operation(summary = "获取首页数据")
    public R<EngSentenceMainVO> getIndexEngMain(@RequestBody @Validated EngSentenceMainIO io){
        return R.data(engSentenceMainService.getIndexEngMain(io));
    }

    @PostMapping("/operData")
    @Operation(summary = "收藏或隐藏数据")
    public R operData(@RequestBody List<EngUserOper> operList){
        operService.operData(operList);
        return R.success("操作成功");
    }

    @PostMapping("/getEngWordByDetail")
    @Operation(summary = "获取句子中单词详情数据")
    public R<List<EngSentenceWordVO>> getEngWordByDetail(@RequestBody @Validated EngSentenceWordIO io){
        return R.data(engSentenceWordService.selectByIO(io));
    }

    @PostMapping("/getIndexEngNextMain")
    @Operation(summary = "下一页数据")
    public R<EngSentenceMainVO> getIndexEngNextMain(@RequestBody @Validated EngSentenceMainNextIO ioo){
        return R.data(engSentenceMainService.getIndexEngNextMain(ioo));
    }

    @PostMapping("/getIndexEngUpMain")
    @Operation(summary = "上一页数据")
    public R<EngSentenceMainVO> getIndexEngUpMain(@RequestBody @Validated EngSentenceMainNextIO ioo){
        return R.data(engSentenceMainService.getIndexEngNextMain(ioo));
    }

    @PostMapping("/getEngMain")
    @Operation(summary = "搜索句子主表数据")
    public R<List<EngSentenceMain>> getEngMain(@RequestBody @Validated EngSentenceMainIO io){
        return R.data(engSentenceMainService.selectOrderBySort(io));
    }

    @PostMapping("/searchEng")
    @Operation(summary = "新的搜索句子数据")
    public R<List<SearchEngSentenceVO>> searchEng(@RequestBody @Validated EngSentenceMainIO io){
        return R.data(engSentenceMainService.searchEng(io));
    }

    @PostMapping("/searchChangeEngMain")
    @Operation(summary = "选择某一条搜索出来的句子主表数据")
    public R searchChangeEngMain(@RequestBody @Validated EngSentenceMainIO io){
        engSentenceMainService.searchChangeEngMain(io);
        return R.success("选择成功");
    }

    @PostMapping("/getMyCollect")
    @Operation(summary = "获取我的收藏")
    public R getMyCollect(@RequestBody @Validated EngSentenceCollectIO io){
        List<EngCollectVO> datas = engSentenceMainService.selectCollectByIO(io);
        return R.data(datas);
    }

    @PostMapping("/delMyCollect")
    @Operation(summary = "删除我的收藏")
    public R delMyCollect(@RequestBody @Validated EngSentenceCollectDelIO io){
        engSentenceMainService.delMyCollect(io);
        return R.success("删除成功");
    }

    @GetMapping("/files/detail/{filename:.+}/{detailId}")
    public ResponseEntity<InputStreamResource> getDetailVoice(@PathVariable String filename,
                                                              @PathVariable String detailId) throws IOException {
        DownLoadFileVO downLoadFileVO = downLoadService.downloadFile(DownLoadFileIO.builder()
                .fileName(filename)
                .filePath(audioParam.getLocalPath())
                .fileWebUrl(ossConfig.getWxBackEngUrl())
                .fileWebLocalPath(ossConfig.getWxBackEngLocalPath())
                .dataType(MyConstant.DATA_TYPE_DETAIL)
                .detailId(detailId)
                .build());
        InputStreamResource resource = downLoadFileVO.getResource();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(downLoadFileVO.getFileInputStream().available())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/files/word/{filename:.+}")
    public ResponseEntity<InputStreamResource> getWordVoice(@PathVariable String filename) throws IOException {
        DownLoadFileVO downLoadFileVO = downLoadService.downloadFile(DownLoadFileIO.builder()
                .fileName(filename)
                .filePath(audioParam.getWordLocalPath())
                .fileWebUrl(ossConfig.getWxBackEngWordUrl())
                .fileWebLocalPath(ossConfig.getWxBackEngWordLocalPath())
                .dataType(MyConstant.DATA_TYPE_WORD)
//                .detailId(detailId)
//                .wordId(wordId)
                .build());
        InputStreamResource resource = downLoadFileVO.getResource();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(downLoadFileVO.getFileInputStream().available())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


}
