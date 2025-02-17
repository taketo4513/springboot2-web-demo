package cc.taketo.test;

import cc.taketo.utils.PDFUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class Html2PdfTest {

    @Test
    public void testHtml2Pdf() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("data", "PDF导出测试");
        map.put("image", "暂无");
        map.put("date", LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth());
        PDFUtil.createPDF(PDFUtil.PDF_DEMO_TEMPLATE, map, Files.newOutputStream(Paths.get("src/main/resources/demo.pdf")), null);
    }

    @Test
    public void listHtml2Pdf() throws IOException {
        // 创建一个List<Map>来存储测试数据
        List<Map<String, String>> queryList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Map<String, String> data = new HashMap<>();
            data.put("demo", "Test Data ,这是一条测试数据！这是一条测试数据！这是一条测试数据！这是一条测试数据！这是一条测试数据！这是一条测试数据！这是一条测试数据！这是一条测试数据！" + i);
            queryList.add(data);
        }

        // 创建数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("queryList", queryList);

        PDFUtil.createPDF(PDFUtil.PDF_DEMO_LIST_TEMPLATE, dataModel, Files.newOutputStream(Paths.get("src/main/resources/list.pdf")), null);
    }

}
