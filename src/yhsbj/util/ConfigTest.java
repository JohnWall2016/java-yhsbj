package yhsbj.util;

import org.junit.Test;

public class ConfigTest {

    @Test
    public void test() {
        System.out.println(Config.getValue("user002_id"));
        System.out.println(Config.getMapValue("jbzt_map", "3", "1"));
        System.out.println(Config.getMapValue("jbzt_map", "3", "0"));
        System.out.println(Config.getMapValue("jbzt_map", "0", "1"));
    }

}
