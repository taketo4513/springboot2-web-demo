package cc.taketo.main;

import cc.taketo.entity.DrawText;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Zhangp
 * @date 2024/4/23 23:42
 */
public class Main {

    private final static String TEMPLATE_PATH = "classpath:template.jpg";

    // 交易编号
    private final static String TRANSACTION_NUMBER = "交易编号:".concat("N00124010000027944");
    // 交易编号XY坐标
    private final static int[] TRANSACTION_NUMBER_XY = {1100, 205};
    // 交易编号字体大小
    private final static int TRANSACTION_NUMBER_SIZE = 30;

    // 购买方
    private final static String BUYER_TEXT = "购买方";
    // 购买方XY坐标
    private final static int[] BUYER_TEXT_XY = {800, 956};
    // 购买方字体大小
    private final static int BUYER_TEXT_SIZE = 40;

    // 名称
    private final static String NAME_TEXT = "个人 : ".concat("张鹏");
    // 名称XY坐标
    private final static int[] NAME_TEXT_XY = {774, 1060};
    // 名称字体大小
    private final static int NAME_TEXT_SIZE = 35;

    // 购买绿证数量
    private final static String BUYER_GEC_NUMBER = "购买绿证数量 : ".concat("1个");
    // 购买绿证数量坐标
    private final static int[] BUYER_GEC_NUMBER_XY = {774, 1118};
    // 购买绿证数量字体大小
    private final static int BUYER_GEC_NUMBER_SIZE = 35;

    // 电量
    private final static String ELECTRICITY_TEXT = "1";
    // 电量XY坐标
    private final static int[] ELECTRICITY_TEXT_XY = {848, 1281};
    // 电量字体大小
    private final static int ELECTRICITY_TEXT_SIZE = 85;

    // 兆瓦时
    private final static String MEGAWATT_TEXT = "兆瓦时";
    // 兆瓦时XY坐标
    private final static int[] MEGAWATT_TEXT_XY = {912, 1285};
    // 电量字体大小
    private final static int MEGAWATT_TEXT_SIZE = 35;

    // 生产方
    private final static String PRODUCTION_TEXT = "生产方";
    // 生产方坐标
    private final static int[] PRODUCTION_TEXT_XY = {800, 1407};
    // 电量字体大小
    private final static int PRODUCTION_TEXT_SIZE = 40;

    // 项目名称
    private final static String PROJECT_NAME_TEXT = "项目名称 : ".concat("临泽北滩50兆瓦风电场项目");
    // 项目名称坐标
    private final static int[] PROJECT_NAME_TEXT_XY = {250, 1505};
    // 项目名称文字大小
    private final static int PROJECT_SIZE = 35;

    // 项目代码
    private final static String PROJECT_CODE_TEXT = "项目代码 : ".concat("WWC1601620723001T");
    // 项目名称坐标
    private final static int[] PROJECT_CODE_TEXT_XY = {250, 1565};

    // 项目类型
    private final static String PROJECT_TYPE_TEXT = "项目类型 : ".concat("风力发电");
    // 项目类型坐标
    private final static int[] PROJECT_TYPE_TEXT_XY = {250, 1625};

    // 项目所在地
    private final static String PROJECT_ADDRESS_TEXT = "项目所在地 : ".concat("甘肃省");
    // 项目所在地坐标
    private final static int[] PROJECT_ADDRESS_TEXT_XY = {250, 1685};

    // 电量生产日期
    private final static String PRODUCTION_DATE_TEXT = "电量生产日期 : ".concat("2022年04月");
    // 电量生产日期坐标
    private final static int[] PRODUCTION_DATE_TEXT_XY = {250, 1745};

    // 交易平台
    private final static String TRANS_PLATFORM_TEXT = "交易平台 : ".concat("中国绿色电力证书交易平台");
    // 交易平台坐标
    private final static int[] TRANS_PLATFORM_TEXT_XY = {250, 1805};

    // 日期
    private final static String DATE_TEXT = "2024年3月7日";
    // 日期坐标
    private final static int[] DATE_TEXT_XY = {1202, 2132};
    // 日期字体大小
    private final static int DATE_TEXT_SIZE = 25;

    private static List<DrawText> getDataDrawTextList() {
        return Arrays.asList(
                new DrawText(TRANSACTION_NUMBER, TRANSACTION_NUMBER_XY, null, null, null, TRANSACTION_NUMBER_SIZE),
                new DrawText(BUYER_TEXT, BUYER_TEXT_XY, null, null, null, BUYER_TEXT_SIZE),
                new DrawText(NAME_TEXT, NAME_TEXT_XY, null, null, null, NAME_TEXT_SIZE),
                new DrawText(BUYER_GEC_NUMBER, BUYER_GEC_NUMBER_XY, null, null, null, BUYER_GEC_NUMBER_SIZE),
                new DrawText(ELECTRICITY_TEXT, ELECTRICITY_TEXT_XY, null, null, Color.green, ELECTRICITY_TEXT_SIZE),
                new DrawText(MEGAWATT_TEXT, MEGAWATT_TEXT_XY, null, null, null, MEGAWATT_TEXT_SIZE),
                new DrawText(PRODUCTION_TEXT, PRODUCTION_TEXT_XY, null, null, null, PRODUCTION_TEXT_SIZE),
                new DrawText(PROJECT_NAME_TEXT, PROJECT_NAME_TEXT_XY, null, null, null, PROJECT_SIZE),
                new DrawText(PROJECT_CODE_TEXT, PROJECT_CODE_TEXT_XY, null, null, null, PROJECT_SIZE),
                new DrawText(PROJECT_TYPE_TEXT, PROJECT_TYPE_TEXT_XY, null, null, null, PROJECT_SIZE),
                new DrawText(PROJECT_ADDRESS_TEXT, PROJECT_ADDRESS_TEXT_XY, null, null, null, PROJECT_SIZE),
                new DrawText(PRODUCTION_DATE_TEXT, PRODUCTION_DATE_TEXT_XY, null, null, null, PROJECT_SIZE),
                new DrawText(TRANS_PLATFORM_TEXT, TRANS_PLATFORM_TEXT_XY, null, null, null, PROJECT_SIZE),
                new DrawText(DATE_TEXT, DATE_TEXT_XY, null, null, null, DATE_TEXT_SIZE)
        );
    }

    public static void main(String[] args) throws IOException {
        File file = ResourceUtils.getFile(TEMPLATE_PATH);
        try {
            BufferedImage image = ImageIO.read(file);

            Graphics2D g2d = image.createGraphics();
            getDataDrawTextList().forEach(drawText -> {
                // 设置文字颜色
                g2d.setColor(drawText.getColour());
                // 设置文字字体和大小
                g2d.setFont(new Font("SimSun", Font.BOLD, drawText.getSize()));
                // 绘制文字
                g2d.drawString(drawText.getText(), drawText.getXy()[0], drawText.getXy()[1]);
            });

            g2d.dispose();

            ImageIO.write(image, "jpg", new File("draw-2d-demo/src/main/resources/output.jpg"));

            System.out.println("图片生成成功！");
        } catch (IOException e) {
            System.out.println("图片生成失败：" + e.getMessage());
        }

    }
}
