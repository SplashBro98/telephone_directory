package com.epam.mazaliuk.phones.util;

import java.util.List;

public final class CollectionUtils {

    private CollectionUtils() {

    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }
}
