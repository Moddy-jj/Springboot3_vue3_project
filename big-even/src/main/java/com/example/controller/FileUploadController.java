package com.example.controller;
import com.example.pojo.Result;
import com.example.utils.AliOssUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        // 文件存储到本地
        String originalfileName = file.getOriginalFilename();
        // 文件名唯一
        String fileName = UUID.randomUUID().toString() + "_" + originalfileName.substring(originalfileName.lastIndexOf("."));
        // file.transferTo(new File("C:\\Users\\95120\\Desktop\\项目\\file\\"+fileName));
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }

}
