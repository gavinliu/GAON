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
    public void testText() {
        String html = "<p>记得以前我们使用类似“<code>快牙</code>”这些文件分享工具的时候，一开始就是先在 <strong>手机A</strong> 上创建一个“房间”，<i><a href=\"xx.com\">然后连接上</a></i> 手机A <strong>WiFi</strong> 热点的其他手机（即这些手机处于一个<i><em>局域网</em></i>内）就可以发现到这个房间并加入到这个房间里面，然后就可以互相分享文件了。</p>";
        List<HElement> elementList = Haml.parse(html);
        PrintUtils.printHElement(elementList);
    }

    @Test
    public void testImage() {
        String html = "<img src=\"abc.png\"/><p>abc</p><a href=\"/content\"><img src=\"/static/content.png\"/></a>";
        List<HElement> elementList = Haml.parse(html);
        PrintUtils.printHElement(elementList);
    }

    @Test
    public void testTable() {
        String html = "<table ><tr><th>姓名</th><th>电话</th></tr><tr><td>Bill Gates</td><td>555 77 854</td><td>555 77 855</td></tr></table>";
        List<HElement> elementList = Haml.parse(html);
        PrintUtils.printHElement(elementList);
    }

}
