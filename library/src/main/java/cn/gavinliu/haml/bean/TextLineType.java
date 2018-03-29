package cn.gavinliu.haml.bean;

import cn.gavinliu.haml.ve.VTag;
import org.jsoup.parser.Tag;

import java.util.List;

public enum TextLineType {
    code,
    pre,

    p,
    h1,
    h2,
    h3,
    h4,
    h5,
    h6,
    li,
    ol,
    blockquote,

    th,
    td;

    private static TextLineType parse(String tag) {
        try {
            return TextLineType.valueOf(tag);
        } catch (Exception e) {
            return p;
        }
    }

    public static TextLineType parse(List<VTag> tags) {
        for (int i = tags.size() - 1; i >= 0; i--) {
            Tag tag = tags.get(i).tag;
            if (tag.isBlock()) {
                return parse(tag.getName());
            }
        }

        return null;
    }
}