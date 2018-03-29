package cn.gavinliu.haml.util;

public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isTrimEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }
}
