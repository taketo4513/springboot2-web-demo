package cc.taketo.test;

import cc.taketo.FileFtpApplication;
import cc.taketo.ftp.FtpUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author zhangp
 * @date 2024/04/07 01:16
 */
@SpringBootTest(classes = FileFtpApplication.class)
public class FtpTest {

    @Resource
    private FtpUtil ftpUtil;

    @Test
    public void upload() throws IOException {
        FtpUtil.UploadStatus upload = ftpUtil.upload("logs/info.log", "info.log");
        System.out.println("upload = " + upload);
    }

    @Test
    public void create() throws IOException {
        boolean b = ftpUtil.createDirectory("test");
        System.out.println(b);

    }
}
