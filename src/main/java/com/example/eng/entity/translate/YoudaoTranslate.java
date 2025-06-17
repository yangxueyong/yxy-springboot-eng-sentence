package com.example.eng.entity.translate;


import lombok.Data;

import java.util.List;

/**
 * 有道翻译
 * {
 * 	"tSpeakUrl":"https://openapi.youdao.com/ttsapi?q=%E4%BD%A0%E4%BB%8A%E5%A4%A9%E5%A5%BD%E5%90%97%EF%BC%9F&langType=zh-CHS&sign=4B320F6B8E66A276ED31CC25F505FE57&salt=1750126172470&voice=4&format=mp3&appKey=4250569f7b9f3b38&ttsVoiceStrict=false&osType=api",
 * 	"requestId":"f545fa8e-f92e-4c6f-83a4-5d19c597f77a",
 * 	"query":"how are you today?",
 * 	"translation":[
 * 		"你今天好吗？"
 * 	],
 * 	"mTerminalDict":{
 * 		"url":"https://m.youdao.com/m/result?lang=en&word=how+are+you+today%3F"
 *        },
 * 	"errorCode":"0",
 * 	"dict":{
 * 		"url":"yddict://m.youdao.com/dict?le=eng&q=how+are+you+today%3F"
 *    },
 * 	"webdict":{
 * 		"url":"http://mobile.youdao.com/dict?le=eng&q=how+are+you+today%3F"
 *    },
 * 	"l":"en2zh-CHS",
 * 	"isWord":false,
 * 	"speakUrl":"https://openapi.youdao.com/ttsapi?q=how+are+you+today%3F&langType=en-USA&sign=C17F20AF54E50A495A0E77D1B0E12DD6&salt=1750126172470&voice=4&format=mp3&appKey=4250569f7b9f3b38&ttsVoiceStrict=false&osType=api"
 * }
 * @author yxy
 * @date 2025/06/17
 */
@Data
public class YoudaoTranslate {
    /**
     * 原始语音url
     */
    private String tSpeakUrl;
    private String requestId;
    /**
     * 原始文本
     */
    private String query;
    /**
     * 翻译之后的文本
     */
    private List<String> translation;
    private MTerminalDict mTerminalDict;
    private String errorCode;
    private Dict dict;
    private Webdict webdict;
    private String l;
    private boolean isWord;
    /**
     * 翻译之后的文本
     */
    private String speakUrl;

    public static class MTerminalDict {
        private String url;
    }

    public static class Dict {
        private String url;
    }

    public static class Webdict {
        private String url;
    }

}
