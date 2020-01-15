package com.gmall.pms;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = GmallTestApplication.class)
public class GmallTestApplication {
    @Test
    public void contextloads() throws IOException, MyException {
        String path = GmallTestApplication.class.getResource("/tracker.conf").getPath();
        ClientGlobal.init(path);
        TrackerClient client = new TrackerClient();
        TrackerServer server = client.getTrackerServer();
        StorageClient storageClient = new StorageClient(server, null);
        String[] uploadInfos = storageClient.upload_file("d:/timg.jpg", "jpg", null);
        for (String uploadInfo : uploadInfos) {
            System.out.println(uploadInfo);
        }
    }
}
