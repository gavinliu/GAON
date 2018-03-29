package cn.gavinliu.haml.ve;

import org.jsoup.parser.Tag;

public class VTag {

    public Tag tag;

    public VTag(Tag tag) {
        this.tag = tag;
    }

    public String attr;
}
