package cn.gavinliu.haml;

import cn.gavinliu.haml.bean.HElement;
import cn.gavinliu.haml.util.PrintUtils;
import org.junit.Test;

import java.util.List;

public class HamlTest {

    @Test
    public void test() {
        String html = "<h1>My First Heading</h1><p><li>My first paragraph.</li></p>";
        List<HElement> elementList = Haml.parse(html);
        PrintUtils.printHElement(elementList);
    }

    @Test
    public void testTable(){
        String html = "<table ><tr><th>姓名</th><th>电话</th></tr><tr><td>Bill Gates</td><td>555 77 854</td><td>555 77 855</td></tr></table>";
        List<HElement> elementList = Haml.parse(html);
        PrintUtils.printHElement(elementList);
    }

}
