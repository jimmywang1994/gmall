package com.ww.gmall.util;

import com.ww.gmall.common.CommonConstant;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
    public static String uploadUtil(MultipartFile multipartFile) {
        String imgUrl = CommonConstant.FDFS_IP;
        String tracker = UploadUtil.class.getResource("/tracker.conf").getPath();
        try {
            ClientGlobal.init(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        try {
            trackerClient.getTrackerServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StorageClient storageClient = new StorageClient(trackerServer, null);
        try {
            byte[] bytes = multipartFile.getBytes();
            String originalFilename = multipartFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String[] uploadInfos = storageClient.upload_file(bytes, extName, null);
            for (String uploadInfo : uploadInfos) {
                imgUrl += "/" + uploadInfo;
            }
            System.out.println(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgUrl;
    }
}
