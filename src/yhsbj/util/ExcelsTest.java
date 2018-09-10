package yhsbj.util;

import java.io.IOException;

import org.junit.Test;

public class ExcelsTest {

    @Test
    public void test() {
        try {
            var wb = Excels.load("D:\\Downloads\\2017年居保代缴人员名单200180904.xlsx");
            var sheet = wb.getSheetAt(0);
            var row = sheet.getRow(1);
            for (var cell : row) {
                System.out.println(cell);
            }
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
