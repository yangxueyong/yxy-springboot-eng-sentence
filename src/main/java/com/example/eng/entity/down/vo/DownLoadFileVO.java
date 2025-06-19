package com.example.eng.entity.down.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;

import java.io.FileInputStream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownLoadFileVO {
    private FileInputStream fileInputStream ;
    private InputStreamResource resource ;
}
