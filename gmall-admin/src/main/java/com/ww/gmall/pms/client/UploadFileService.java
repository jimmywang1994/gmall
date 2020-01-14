package com.ww.gmall.pms.client;

import com.ww.gmall.config.FeignMultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "gmall-pms",configuration = FeignMultipartSupportConfig.class)
public interface UploadFileService {
    @RequestMapping(value = "/pms/product-info/fileUpload",produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile);
}
