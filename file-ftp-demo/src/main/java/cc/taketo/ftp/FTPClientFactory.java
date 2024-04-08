package cc.taketo.ftp;

import cc.taketo.config.FtpProperties;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhangp
 * @date 2024/04/07 10:01
 */
@Component
public class FTPClientFactory implements PooledObjectFactory<FTPClient> {

    private Logger logger = LoggerFactory.getLogger(FTPClientFactory.class);

    /**
     * 注入 ftp 连接配置
     */
    @Resource
    private FtpProperties ftpProperties;

    /**
     * 获取 FTP 连接配置
     *
     * @return
     */
    public FtpProperties getFtpProperties() {
        return ftpProperties;
    }

    /**
     * 创建连接到池中
     *
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(ftpProperties.getClientTimeout());
        ftpClient.connect(ftpProperties.getHost(), ftpProperties.getPort());
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            return null;
        }
        boolean success;
        if (StringUtils.isBlank(ftpProperties.getUsername())) {
            success = ftpClient.login("anonymous", "anonymous");
        } else {
            success = ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
        }
        if (!success) {
            return null;
        }
        ftpClient.setFileType(ftpProperties.getTransferFileType());
        ftpClient.setBufferSize(1024);
        ftpClient.setControlEncoding(ftpProperties.getEncoding());
        if (ftpProperties.isPassiveMode()) {
            ftpClient.enterLocalPassiveMode();
        }
        logger.debug("创建ftp连接");
        return new DefaultPooledObject<>(ftpClient);
    }

    /**
     * 链接状态检查
     *
     * @param pool
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> pool) {
        FTPClient ftpClient = pool.getObject();
        try {
            return ftpClient != null && ftpClient.sendNoOp();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 销毁连接，当连接池空闲数量达到上限时，调用此方法销毁连接
     *
     * @param pool
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> pool) throws Exception {
        FTPClient ftpClient = pool.getObject();
        if (ftpClient != null) {
            try {
                ftpClient.disconnect();
                logger.debug("销毁ftp连接");
            } catch (Exception e) {
                logger.error("销毁ftpClient异常，error：", e.getMessage());
            }
        }
    }

    /**
     * 钝化连接，是连接变为可用状态
     *
     * @param p
     * @throws Exception
     */
    @Override
    public void passivateObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        try {
            ftpClient.changeWorkingDirectory(ftpProperties.getBasePath());
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not disconnect from server.", e);
        }
    }

    /**
     * 初始化连接
     *
     * @param pool
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<FTPClient> pool) throws Exception {
        FTPClient ftpClient = pool.getObject();
        ftpClient.connect(ftpProperties.getHost(), ftpProperties.getPort());
        ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
        ftpClient.setControlEncoding(ftpProperties.getEncoding());
        ftpClient.changeWorkingDirectory(ftpProperties.getBasePath());
        //设置上传文件类型为二进制，否则将无法打开文件
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }


}
