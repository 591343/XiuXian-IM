/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.xiuxian.thirdparty.controller;


import com.xiuxian.thirdparty.annotation.Login;
import com.xiuxian.thirdparty.service.AliyunCloudStorageService;
import com.xiuxian.common.exception.ErrorCode;
import com.xiuxian.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api/oss")
@Api(tags = "文件上传")
public class OssController {
    @Autowired
    AliyunCloudStorageService aliyunCloudStorageService;

    @Login
    @PostMapping("upload")
    @ApiOperation(value = "上传文件")
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file,
                                              @RequestParam("prefix") String prefix) throws Exception {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }
        aliyunCloudStorageService.setPrefix(prefix);
        //上传文件
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String url = aliyunCloudStorageService.uploadSuffix(file.getBytes(), extension);

        Map<String, Object> data = new HashMap<>(1);
        data.put("src", url);

        return new Result<Map<String, Object>>().ok(data);
    }

}