package com.example.eng.entity.down.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownLoadFileIO {
    private String filePath;
    private String fileName;

    private String fileWebUrl;
    private String fileWebLocalPath;
}
