package tech.sqlclub.common.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import org.apache.commons.lang.StringUtils;
import tech.sqlclub.common.exception.AssertException;

/**
 * 断言工具
 * Created by songgr on 2022/02/23.
 */
public class Assert {

    /**
     * 断言不为空，为空时抛出异常
     *
     * @param obj      被判断对象
     * @param messasge 断言失败时异常信息
     */
    public static void notNull(Object obj, String messasge) {
        if (obj == null) {
            throw new AssertException(messasge);
        }
    }

    /**
     * 断言不为空，为空时抛出异常
     *
     * @param obj      被判断对象
     * @param msgSupplier 断言失败时异常信息
     */
    public static void notNull(Object obj, Supplier<String> msgSupplier) {
        if (obj == null) {
            throw new AssertException(msgSupplier.get());
        }
    }

    /**
     * 断言为空，不为空时抛出异常
     *
     * @param obj      被判断对象
     * @param messasge 断言失败时异常信息
     */
    public static void isNull(Object obj, String messasge) {
        if (obj != null) {
            throw new AssertException(messasge);
        }
    }

    /**
     * 断言为空，不为空时抛出异常
     *
     * @param obj      被判断对象
     * @param msgSupplier 断言失败时异常信息
     */
    public static void isNull(Object obj, Supplier<String> msgSupplier) {
        if (obj != null) {
            throw new AssertException(msgSupplier.get());
        }
    }

    /**
     * 断言为真，为null或false，抛出异常
     *
     * @param value    被断言对象
     * @param messasge 断言失败时异常信息
     */
    public static void isTrue(Boolean value, String messasge) {
        // 为null或未false，抛出异常
        if (value == null || !value) {
            throw new AssertException(messasge);
        }
    }

    /**
     * 断言为真，为null或false，抛出异常
     *
     * @param value    被断言对象
     * @param msgSupplier 断言失败时异常信息
     */
    public static void isTrue(Boolean value, Supplier<String> msgSupplier) {
        // 为null或未false，抛出异常
        if (value == null || !value) {
            throw new AssertException(msgSupplier.get());
        }
    }

    /**
     * 断言为假
     *
     * @param value    被断言对象
     * @param messasge 断言失败时异常信息
     */
    public static void isFalse(Boolean value, String messasge) {
        // 不为null并且为真时，抛出异常
        if (value != null && value) {
            throw new AssertException(messasge);
        }
    }

    /**
     * 断言为假
     *
     * @param value    被断言对象
     * @param msgSupplier 断言失败时异常信息
     */
    public static void isFalse(Boolean value, Supplier<String> msgSupplier) {
        // 不为null并且为真时，抛出异常
        if (value != null && value) {
            throw new AssertException(msgSupplier.get());
        }
    }

    public static void isNotEmpty(Collection<?> collection, String errorMsg) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void isNotEmpty(Map<?, ?> map, String errorMsg) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void isNotEmpty(Object[] array, String errorMsg) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void isNotEmpty(String str, String errorMsg) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void isNotEmpty(int[] array, String errorMsg) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(errorMsg);
        }
    }
}

