package cc.taekto.utils;

import cc.taekto.entity.login.AuthCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class ImageCodeUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageCodeUtil.class);

    /**
     * Image authentication code length
     */
    public static final int Image_Auth_Code_Char_Number = 4;

    private static SecureRandom generator = new SecureRandom();

    /**
     *
     */
    private static final char[] CHARS = {
            '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };

    /**
     * Gets a numeric and alphanumeric string for the specified number of digits
     *
     * @param length
     */
    public static String randomString(int length) {
        if (length > CHARS.length) {
            return null;
        }
        Random random = new Random();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }

    /**
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static AuthCode createAuthCode() throws IOException {
        String authCode = randomString(Image_Auth_Code_Char_Number);
        System.out.println(authCode);
        return AuthCode.builder().authCode(authCode).base64Image(getBase64Image(authCode)).build();
    }

    /**
     * get buffer image.
     */
    private static BufferedImage getBufferedImage(String s) {
        int i = 155;
        byte byte0 = 60;
        BufferedImage bufferedimage = new BufferedImage(i, byte0, 1);
        Graphics g = bufferedimage.getGraphics();
        SecureRandom random = new SecureRandom();
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, i, byte0);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.setColor(getRandColor(160, 200));
        for (int j = 0; j < 10; j++) {
            int l = random.nextInt(i);
            int i1 = random.nextInt(byte0);
            int j1 = random.nextInt(12);
            int k1 = random.nextInt(12);
            g.drawOval(l, i1, l + j1, i1 + k1);
        }

        for (int k = 0; k < s.length(); k++) {
            char c = s.charAt(k);
            String s1 = String.valueOf(c);
            g.setColor(
                    new Color(
                            20 + random.nextInt(110),
                            20 + random.nextInt(110),
                            20 + random.nextInt(110)));
            g.drawString(s1, ((i - 36) / s.length()) * k + 18, 42);
        }
        shear(g, bufferedimage.getWidth(), bufferedimage.getHeight(), new Color(240, 248, 255));

        g.dispose();

        return bufferedimage;
    }

    public static Color getRandColor(int i, int j) {
        SecureRandom random = new SecureRandom();
        if (i > 255) {
            i = 255;
        }
        if (j > 255) {
            j = 255;
        }
        int k = i + random.nextInt(j - i);
        int l = i + random.nextInt(j - i);
        int i1 = i + random.nextInt(j - i);
        return new Color(k, l, i1);
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    public static void shearX(Graphics g, int w1, int h1, Color color) {
        //		int period = generator.nextInt(2);
        int period = generator.nextInt(200) + 10;

        int frames = 1;
        //		int phase = generator.nextInt(2);
        int phase = generator.nextInt(200);

        for (int i = 0; i < h1; i++) {
            double d =
                    (double) (period >> 1)
                            * Math.sin(
                            (double) i / (double) period
                                    + (6.2831853071795862D * (double) phase)
                                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
        }
    }

    public static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = generator.nextInt(5) + 2; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d =
                    (double) (period >> 1)
                            * Math.sin(
                            (double) i / (double) period
                                    + (6.2831853071795862D * (double) phase)
                                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    /**
     * response base64.
     */
    public static String getBase64Image(String msg) throws IOException {
        BufferedImage bufferedimage = getBufferedImage(msg);
        ByteArrayOutputStream bs = null;
        try {
            bs = new ByteArrayOutputStream();
            ImageIO.write(bufferedimage, "png", bs); // 将绘制得图片输出到流
            return Base64.getEncoder().encodeToString(bs.toByteArray());
        } catch (Exception e) {
            logger.error("fail createPic.", e);
            return null;
        } finally {
            if (bs != null) {
                bs.close();
                bs.flush();
            }
        }
    }
}
