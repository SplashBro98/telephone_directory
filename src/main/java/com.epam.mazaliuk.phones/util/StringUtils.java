package com.epam.mazaliuk.phones.util;

public final class StringUtils {

    private StringUtils() {

    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }
}
