package cc.taketo.utils;

import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Locale;

@Slf4j
public class PDFUtil {

    private final static String TEMPLATE_BASE_PATH = "src/main/resources/template";// 存放文件模板的地址
    private final static String IMG_BASE_PATH = "src/main/resources/template";// 存放图片文件的地址（logo图片、机构公章等）
    private final static String FONT_BASE_PATH = "src/main/resources/fonts/";// 存放字体资源文件的地址
    private final static String DEFAULT_FONT = "simsun.ttc";// 默认字体资源文件（[宋体][simsun.ttc]）
    private final static String ENCODING = "UTF-8";//指定编码
    public final static String PDF_DEMO_IMAGE = "demo_image.png";//图片名称
    public final static String PDF_DEMO_TEMPLATE = "demo_pdf.ftl";//模板名称
    public final static String PDF_DEMO_LIST_TEMPLATE = "list_pdf.ftl";//列表模板名称

    /**
     * 生成pdf
     *
     * @param templateCode 模板
     * @param data         传入到freemarker模板里的数据
     * @param out          生成的pdf文件流
     */
    public static void createPDF(String templateCode, Object data, OutputStream out, String imgName) {
        try {
            // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
            Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            // 指定FreeMarker模板文件的位置
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_BASE_PATH));
            ITextRenderer renderer = new ITextRenderer();
            // 设置 css中 的字体样式（暂时仅支持宋体和黑体）
            renderer.getFontResolver().addFont(FONT_BASE_PATH + DEFAULT_FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 设置模板的编码格式
            cfg.setEncoding(Locale.CHINA, ENCODING);
            // 获取模板文件 template.ftl
            Template template = cfg.getTemplate(templateCode, ENCODING);
            StringWriter writer = new StringWriter();
            // 将数据输出到html中
            template.process(data, writer);
            writer.flush();
            String html = writer.toString();
            // 把html代码传入渲染器中
            renderer.setDocumentFromString(html);
            // 解决图片的相对路径问题 ##必须在设置document后再设置图片路径，不然不起作用
            // 如果使用绝对路径依然有问题，可以在路径前面加"file:/"
            if (null != imgName && !imgName.isEmpty()) {
                renderer.getSharedContext().setBaseURL("file:" + IMG_BASE_PATH + imgName);
            }
            renderer.layout();
            renderer.createPDF(out, false);
            renderer.finishPDF();
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("PDF导出异常", e);
        }
    }
}
