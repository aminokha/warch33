package com.example.myapplication2;

public class NumberUtils {
    public static String normalize(String str) {
        if (str == null)
            return null;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch >= '\u0660' && ch <= '\u0669') { // Arabic digits
                chars[i] = (char) (ch - '\u0660' + '0');
            } else if (ch >= '\u06f0' && ch <= '\u06f9') { // Persian digits
                chars[i] = (char) (ch - '\u06f0' + '0');
            }
        }
        return new String(chars);
    }

    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(normalize(str));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
