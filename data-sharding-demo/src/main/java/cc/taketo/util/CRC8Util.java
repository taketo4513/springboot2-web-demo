package cc.taketo.util;

import java.nio.ByteBuffer;

/**
 * @author Zhangp
 * @date 2024/3/22 16:34
 */
public class CRC8Util {

    private static final int POLYNOMIAL = 0x07; // CRC8多项式

    private static final int INITIAL_VALUE = 0x00; // 初始值

    /**
     * 计算CRC8校验码
     *
     * @param data 要计算校验码的字节数组
     * @return CRC8校验码
     */
    public static int calculateCRC8(byte[] data) {
        int crc = INITIAL_VALUE;
        for (byte b : data) {
            crc ^= (b & 0xFF);
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0) {
                    crc = (crc << 1) ^ POLYNOMIAL;
                } else {
                    crc <<= 1;
                }
            }
        }
        return crc & 0xFF;
    }

    public static byte[] longToBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        return buffer.array();
    }
}
