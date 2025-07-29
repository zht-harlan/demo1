package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.management.ObjectName;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
      log.info("文件上传：{}", file);

      try {
          String OrginalName =file .getOriginalFilename();
            log.info("上传的文件名：{}", OrginalName);

            String filename=OrginalName.substring(0, OrginalName.lastIndexOf("."));
          String s = UUID.randomUUID().toString() + filename;
          aliOssUtil.upload(file.getBytes(), s);
            return Result.success(s);
      }catch (Exception e) {
          log.error("文件上传失败：", e);
          return Result.error("文件上传失败，请稍后重试");
      }
        // 检查文件是否为空
    }
}
