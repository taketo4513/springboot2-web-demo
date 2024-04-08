package cc.taketo.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangp
 * @date 2024/04/06 23:49
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ftp.client")
public class FtpProperties extends GenericObjectPoolConfig {

    /**
     * ftp服务器的地址
     */
    private String host;

    /**
     * ftp服务器的端口号（连接端口号）
     */
    private int port;

    /**
     * ftp的用户名
     */
    private String username;

    /**
     * ftp的密码
     */
    private String password;

    /**
     * ftp上传的根目录
     */
    private String basePath;

    /**
     * 回显地址
     */
    private String httpPath;

    /**
     * 传输编码
     */
    private String encoding;

    /**
     * 被动模式：在这种模式下，数据连接是由客户程序发起的
     */
    private boolean passiveMode;

    /**
     * 连接超时时间
     */
    private int clientTimeout;

    /**
     * 线程数
     */
    private int threadNum;

    /**
     * 0=ASCII_FILE_TYPE(ASCII格式)，1=EBCDIC_FILE_TYPE，2=LOCAL_FILE_TYPE(二进制文件)
     */
    private int transferFileType;

    /**
     * 是否重命名
     */
    private boolean renameUploaded;

    /**
     * 重新连接时间
     */
    private int retryTimes;

    /**
     * 缓存大小
     */
    private int bufferSize;

    /**
     * 最大连接数
     */
    private int maxActive;

    /**
     * 最小空闲
     */
    private int minIdle;

    /**
     * 最大空闲
     */
    private int maxIdle;

    /**
     * 最大等待时间
     */
    private int maxWait;

    /**
     * 池对象耗尽之后是否阻塞，maxWait < 0 时一直等待
     */
    private boolean blockWhenExhausted;

    /**
     * 取对象时验证
     */
    private boolean testOnBorrow;

    /**
     * 回收验证
     */
    private boolean testOnReturn;

    /**
     * 创建时验证
     */
    private boolean testOnCreate;

    /**
     * 空闲验证
     */
    private boolean testWhileIdle;

    /**
     * 后进先出
     */
    private boolean lifo;

}
