package cn.gavinliu.haml.parser;

import cn.gavinliu.haml.bean.ITags;
import cn.gavinliu.haml.util.StringUtils;
import cn.gavinliu.haml.ve.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class VParser {

    public static List<IVirtualElement> parse(List<Node> nodes, List<VTag> parentTags) {

        List<IVirtualElement> elements = new ArrayList<>();

        for (Node node : nodes) {
            if (node instanceof Element) {
                if (!((Element) node).tag().isEmpty()) {

                    // Block Element 换行
                    if (((Element) node).tag().isBlock()) {
                        elements.add(new VBrElement());
                    }

                    // Pre Code 统一处理成 Pre
                    if (((Element) node).tagName().equals("code") && parentTags.get(parentTags.size() - 1).tag.getName().equals("pre")) {
                        String text = ((Element) node).text();
                        if (text.length() > 0) {
                            VTextElement t = new VTextElement();
                            t.text = text;
                            t.parentTags = parentTags;
                            elements.add(t);
                        }
                        continue;
                    }

                    // Table
                    if (((Element) node).tagName().equals("table")) {
                        VTableElement vTable = parseTable((Element) node);
                        elements.add(vTable);
                        continue;
                    }

                    // Recursion
                    List<VTag> tempTags = new ArrayList<>();
                    tempTags.addAll(parentTags);
                    VTag supperTag = new VTag(((Element) node).tag());
                    if (supperTag.tag.getName().equals("a")) {
                        supperTag.attr = node.attr("href");
                    }
                    tempTags.add(supperTag);

                    List<IVirtualElement> e = parse(node.childNodes(), tempTags);
                    elements.addAll(e);
                } else {
                    switch (((Element) node).tagName()) {
                        case ITags.img:
                            elements.add(new VBrElement());
                            VImageElement image = new VImageElement();
                            image.parentTags = parentTags;
                            image.source = node.attr("src");
                            if (StringUtils.isEmpty(image.source)) {
                                image.source = node.attr("data-src");
                            }
                            elements.add(image);
                            break;

                        case "br": {
                            elements.add(new VBrElement());
                            break;
                        }
                    }
                }
            } else if (node instanceof TextNode) {
                String text = ((TextNode) node).text();
                if (text.length() > 0) {
                    VTextElement t = new VTextElement();
                    t.text = text;
                    t.parentTags = parentTags;
                    elements.add(t);
                }
            }
        }

        return elements;
    }

    private static VTableElement parseTable(Element table) {
        Elements trs = table.getElementsByTag("tr");

        List<List<IVirtualElement>> items = new ArrayList<>();

        int row = trs.size();
        int col = 0;

        for (Element tr : trs) {
            List<IVirtualElement> data = new ArrayList<>();

            Elements ths = tr.getElementsByTag("th");
            parseTableData(ths, data);

            Elements tds = tr.getElementsByTag("td");
            parseTableData(tds, data);

            items.add(data);

            if (col < ths.size() + tds.size()) {
                col = data.size();
            }
        }

        return new VTableElement(row, col, items);
    }

    private static void parseTableData(List<Element> tds, List<IVirtualElement> data) {
        for (Element td : tds) {
            List<VTag> tags = new ArrayList<>();
            tags.add(new VTag(td.tag()));
            List<IVirtualElement> e = parse(td.childNodes(), tags);
            e.add(new VBrElement());
            data.addAll(e);
        }
    }

    public static void main(String[] args) {
        String html = "<table border=\"1\"><tr><th>姓名</th><th colspan=\"2\">电话</th></tr><tr><td>Bill Gates</td><td>555 77 854</td><td>555 77 855</td></tr></table>";

        Document document = Jsoup.parse(html);
        Element body = document.body();

        List<VTag> tags = new ArrayList<>();
        tags.add(new VTag(body.tag()));
        List<IVirtualElement> elements = VParser.parse(body.childNodes(), tags);

        System.out.println(elements.size());
    }
}
