package com.example.eng.myenum;

import lombok.Data;
import lombok.Getter;

@Getter
public enum GameTypeEnum {
    MEMORY("memory", "综合"),
    SIMPLE("simple", "基础"),
    ;
    private String code;
    private String zhName;
    GameTypeEnum(String code, String zhName) {
        this.code = code;
        this.zhName = zhName;
    }
    public static GameTypeEnum getByCode(String code) {
        for (GameTypeEnum gameTypeEnum : GameTypeEnum.values()) {
            if (gameTypeEnum.getCode().equals(code)) {
                return gameTypeEnum;
            }
        }
        return null;
    }
}
