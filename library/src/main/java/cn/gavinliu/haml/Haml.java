package cn.gavinliu.haml;

import cn.gavinliu.haml.bean.HElement;
import cn.gavinliu.haml.parser.HParser;
import cn.gavinliu.haml.parser.VParser;
import cn.gavinliu.haml.ve.IVirtualElement;
import cn.gavinliu.haml.ve.VTag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class Haml {

    public static List<HElement> parse(String html) {
        Document document = Jsoup.parse(html);
        Element body = document.body();

        List<VTag> tags = new ArrayList<>();
        tags.add(new VTag(body.tag()));
        List<IVirtualElement> elements = VParser.parse(body.childNodes(), tags);

        return HParser.parse(elements);
    }

}
