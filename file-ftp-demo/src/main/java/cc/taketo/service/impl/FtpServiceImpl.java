package cc.taketo.service.impl;

import cc.taketo.config.FtpProperties;
import cc.taketo.ftp.FtpUtil;
import cc.taketo.service.FtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangp
 * @date 2024/04/06 23:57
 */
@Service
public class FtpServiceImpl implements FtpService {

    private Logger logger = LoggerFactory.getLogger(FtpServiceImpl.class);

    @Override
    public void uploadFile(MultipartFile multipartFile, HttpServletRequest request) {

    }

    @Override
    public void downloadFile(String fileName, String ftpFilePath, HttpServletResponse response) {

    }

    @Override
    public void deleteFile(String ftpFilePath) {

    }
}
