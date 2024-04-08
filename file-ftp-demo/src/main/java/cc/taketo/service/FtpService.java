package cc.taketo.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangp
 * @date 2024/04/06 23:57
 */
public interface FtpService {

    /**
     * 上传文件到ftp
     *
     * @param multipartFile
     * @param request
     * @return
     */
    void uploadFile(MultipartFile multipartFile, HttpServletRequest request);

    /**
     * 下载ftp文件，直接转到输出流
     *
     * @param fileName
     * @param ftpFilePath
     * @param response
     */
    void downloadFile(String fileName, String ftpFilePath, HttpServletResponse response);

    /**
     * 删除ftp文件
     *
     * @param ftpFilePath ftp下文件路径，根目录开始
     * @return
     */
    void deleteFile(String ftpFilePath);


}
