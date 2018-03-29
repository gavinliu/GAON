package cn.gavinliu.haml.parser;

import cn.gavinliu.haml.bean.*;
import cn.gavinliu.haml.util.StringUtils;
import cn.gavinliu.haml.ve.*;

import java.util.ArrayList;
import java.util.List;

public class HParser {

    public static List<HElement> parse(List<IVirtualElement> elements) {
        List<HElement> elementList = new ArrayList<>();

        HText hText = null;
        TextLineType lineType = null;
        StringBuilder stringBuilder = new StringBuilder();
        int from = 0;

        for (IVirtualElement ivElement : elements) {

            if (ivElement instanceof VTextElement) {
                String text = ((VTextElement) ivElement).text;

                if (StringUtils.isEmpty(text.trim())) continue;

                if (hText == null) {
                    hText = new HText();
                }

                stringBuilder.append(((VTextElement) ivElement).text);
                hText.text = stringBuilder.toString();
                int index = hText.text.indexOf(((VTextElement) ivElement).text, from);
                from = index + ((VTextElement) ivElement).text.length();

                for (VTag tag : ((VTextElement) ivElement).parentTags) {
                    switch (tag.tag.getName()) {
                        case "code":
                        case "strong":
                        case "em":
                        case "b":
                        case "i":
                        case "u":
                        case "a":
                            Markup markup = new Markup();
                            markup.tag = tag.tag.getName();
                            markup.start = index;
                            markup.end = from;

                            if (hText.markups == null) {
                                hText.markups = new ArrayList<>();
                            }

                            hText.markups.add(markup);

                            if (markup.tag.equals("a")) {
                                markup.source = tag.attr;
                            }
                    }
                }
                lineType = TextLineType.parse(((VTextElement) ivElement).parentTags);
            } else if (ivElement instanceof VBrElement) {
                if (hText != null) {
                    HElement hElement = new HElement();
                    hElement.type = HElement.TYPE_TEXT;
                    hElement.text = hText;
                    hElement.text.lineType = lineType;
                    elementList.add(hElement);
                }

                hText = null;
                stringBuilder.delete(0, stringBuilder.length());
                from = 0;
            } else if (ivElement instanceof VImageElement) {
                HElement hElement = new HElement();
                hElement.type = HElement.TYPE_IMAGE;
                hElement.image = new HImage();
                hElement.image.source = ((VImageElement) ivElement).source;
                elementList.add(hElement);

                for (VTag tag : ((VImageElement) ivElement).parentTags) {
                    switch (tag.tag.getName()) {
                        case "a":
                            if (hElement.image.markups == null) {
                                hElement.image.markups = new ArrayList<>();
                            }
                            Markup markup = new Markup();
                            markup.tag = tag.tag.getName();
                            markup.source = tag.attr;
                            hElement.image.markups.add(markup);
                            break;
                    }
                }
            } else if (ivElement instanceof VTableElement) {
                HElement hElement = new HElement();
                hElement.type = HElement.TYPE_TABLE;
                hElement.table = new HTable();
                hElement.table.col = ((VTableElement) ivElement).col;
                hElement.table.row = ((VTableElement) ivElement).row;
                hElement.table.items = new ArrayList<>();

                for (List<IVirtualElement> ive : ((VTableElement) ivElement).items) {
                    List<HElement> e = parse(ive);
                    hElement.table.items.add(e);
                }
                elementList.add(hElement);
            }
        }

        if (hText != null) {
            HElement hElement = new HElement();
            hElement.type = HElement.TYPE_TEXT;
            hElement.text = hText;
            hElement.text.lineType = lineType;
            elementList.add(hElement);
        }

        return elementList;
    }
}
