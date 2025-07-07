package com.example.eng.entity.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqBaseData {
    private String reqUrl;

    private String thirdType;

    private String appid;
}
