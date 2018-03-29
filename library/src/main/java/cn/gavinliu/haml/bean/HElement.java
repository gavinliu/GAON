package cn.gavinliu.haml.bean;


public class HElement {

    public int type;

    public HText text;
    public HImage image;
    public HTable table;

    public HVideo video;
    public HAudio audio;

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_TABLE = 2;
    public static final int TYPE_VIDEO = 3;
    public static final int TYPE_AUDIO = 4;

}
