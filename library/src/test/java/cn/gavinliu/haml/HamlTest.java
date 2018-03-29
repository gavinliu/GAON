package cn.gavinliu.haml;

import cn.gavinliu.haml.bean.HElement;
import cn.gavinliu.haml.util.PrintUtils;
import org.junit.Test;

import java.util.List;

public class HamlTest {

    @Test
    public void test() {
        String str = "<h1>My First Heading</h1><p><li>My first paragraph.</li></p>";

        List<HElement> elementList = Haml.parse(str);
        PrintUtils.printHElement(elementList);
    }

}
