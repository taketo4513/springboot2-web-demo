package cc.taketo.algorithm;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author Zhangp
 * @date 2024/3/10 17:25
 */
public class CRC8Test {

    // 多项式值，用于CRC8计算
    private static final int POLYNOMIAL = 0x07;
    // CRC8初始值
    private static final int INITIAL_VALUE = 0x00;

    public static byte calculateCRC8(long snowflakeId) {
        // 初始化CRC8值
        byte crc = INITIAL_VALUE;
        // 将long型分布式ID转换为字节数组
        byte[] bytes = longToBytes(snowflakeId);
        // 遍历字节数组，计算CRC8值
        for (byte b : bytes) {
            crc = updateCRC8(crc, b);
        }
        return crc;
    }

    /**
     * 将long型数据转换为字节数组
     * @param value 要转换的long型数据
     * @return 转换后的字节数组
     */
    private static byte[] longToBytes(long value) {
        // 创建ByteBuffer对象，分配内存空间
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        // 将long型数据写入ByteBuffer
        buffer.putLong(value);
        // 返回ByteBuffer中的字节数组
        return buffer.array();
    }

    /**
     * 更新CRC8值
     * @param crc 当前CRC8值
     * @param data 需要更新CRC8的数据字节
     * @return 更新后的CRC8值
     */
    private static byte updateCRC8(byte crc, byte data) {
        // 异或操作，对CRC8值和数据字节进行异或操作
        crc ^= data;
        // 循环8次，进行CRC8计算
        for (int i = 0; i < 8; i++) {
            // 如果CRC8值的最高位为1
            if ((crc & 0x80) != 0) {
                // 左移一位并与多项式值进行异或操作
                crc = (byte) ((crc << 1) ^ POLYNOMIAL);
            } else {
                // 否则，只进行左移操作
                crc <<= 1;
            }
        }
        // 返回更新后的CRC8值
        return crc;
    }

    @Test
    public void testCRC8() {
        // 分布式ID
        long snowflakeId = 1766762715877081088L;
        // 计算CRC8值
        byte crc8 = calculateCRC8(snowflakeId);
        // 打印CRC8值
        System.out.println("CRC8 of snowflake ID " + snowflakeId + ": " + crc8);
    }
}
