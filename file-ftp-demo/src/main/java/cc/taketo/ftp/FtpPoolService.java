package cc.taketo.ftp;

import cc.taketo.config.FtpProperties;
import org.apache.commons.net.ftp.FTPClient;

/**
 * @author zhangp
 * @date 2024/04/07 10:05
 */
public interface FtpPoolService {

    /**
     * 获取ftpClient
     */
    FTPClient borrowObject();

    /**
     * 归还ftpClient
     */
    void returnObject(FTPClient ftpClient);

    /**
     * 获取 ftp 配置信息
     */
    FtpProperties getFtpProperties();
}
