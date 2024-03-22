package cc.taketo.algorithm;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author Zhangp
 * @date 2024/3/22 15:51
 */
public class DateTest {

    @Test
    public void test() {
        int currentMonth = LocalDateTime.now().getMonthValue();
        String templateMonth = String.format("%02d", currentMonth);
        System.out.println(templateMonth);
    }
}
