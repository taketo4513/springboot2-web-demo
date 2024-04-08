package cc.taketo.ftp.impl;

import cc.taketo.config.FtpProperties;
import cc.taketo.ftp.FTPClientFactory;
import cc.taketo.ftp.FtpPoolService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author zhangp
 * @date 2024/04/07 10:07
 */
@Service
public class FTPPoolServiceImpl implements FtpPoolService {

    private Logger logger = LoggerFactory.getLogger(FTPClientFactory.class);

    /**
     * ftp 连接池生成
     */
    private GenericObjectPool<FTPClient> pool;

    /**
     * ftp 客户端配置文件
     */
    @Resource
    private FtpProperties ftpProperties;

    /**
     * ftp 客户端工厂
     */
    @Resource
    private FTPClientFactory factory;

    /**
     * 初始化pool
     */
    @PostConstruct
    private void initPool() {
        this.pool = new GenericObjectPool<FTPClient>(this.factory, this.ftpProperties);
    }

    /**
     * 获取ftpClient
     */
    @Override
    public FTPClient borrowObject() {
        if (this.pool != null) {
            try {
                return this.pool.borrowObject();
            } catch (Exception e) {
                logger.error("获取 FTPClient 失败 ", e);
            }
        }
        return null;
    }

    /**
     * 归还 ftpClient
     */
    @Override
    public void returnObject(FTPClient ftpClient) {
        if (this.pool != null && ftpClient != null) {
            this.pool.returnObject(ftpClient);
        }
    }

    @Override
    public FtpProperties getFtpProperties() {
        return ftpProperties;
    }

}
