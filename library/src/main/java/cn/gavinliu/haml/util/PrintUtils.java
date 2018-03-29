package cn.gavinliu.haml.util;


import cn.gavinliu.haml.bean.HElement;
import cn.gavinliu.haml.bean.Markup;

import java.util.List;

public class PrintUtils {

    public static void printHElement(List<HElement> elementList) {
        for (HElement element : elementList) {
            if (element.type == HElement.TYPE_TEXT) {
                System.out.println(element.text.lineType + " " + element.text.text);
                if (element.text.markups != null) {
                    for (Markup markup : element.text.markups) {
                        System.out.println(" > " + markup.tag + " " + markup.start + " - " + markup.end + " " + element.text.text.substring(markup.start, markup.end) + " " + markup.source);
                    }
                }
            }

            if (element.type == HElement.TYPE_IMAGE) {
                System.out.println("image " + element.image.source);
                if (element.image.markups != null) {
                    for (Markup markup : element.image.markups) {
                        System.out.println(" > " + markup.tag + " " + markup.source);
                    }
                }
            }

            if (element.type == HElement.TYPE_TABLE) {
                System.out.println("=> table " + element.table.row + " * " + element.table.col);
                for (List<HElement> e : element.table.items) {
                    printHElement(e);
                    System.out.println("---");
                }
                System.out.println("<= table " + element.table.row + " * " + element.table.col);
            }
        }
    }
}
