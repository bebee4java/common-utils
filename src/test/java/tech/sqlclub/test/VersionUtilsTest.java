package tech.sqlclub.test;

import static tech.sqlclub.common.utils.VersionUtils.compareVersion;

/**
 * VersionUtils 测试
 * Created by songgr on 2022/05/31.
 */
public class VersionUtilsTest {
    public static void main(String[] args) {
        System.out.println(compareVersion("1.0", "1.0.0"));

    }
}
