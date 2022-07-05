package tech.sqlclub.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static tech.sqlclub.common.utils.StringEscapeUtils.*;

/**
 * 字符串转义工具测试
 * Created by songgr on 2020/11/19.
 */
public class StringEscapeUtilsTest {

    @Test
    public void testEscapeJava(){
        assertEquals("\\n",escapeJava("\n"));
        assertEquals("\\t", escapeJava("\t"));
        assertEquals("\\\"", escapeJava("\""));
    }

    @Test
    public void testUnEscapeJava(){
        assertEquals("\n",unescapeJava("\\n"));
        assertEquals("\t", unescapeJava("\\t"));
        assertEquals("\"", unescapeJava("\\\""));
    }

    @Test
    public void testEscapeJavaScript(){
        assertEquals("\\n",escapeJavaScript("\n"));
        assertEquals("\\t", escapeJavaScript("\t"));
        assertEquals("\\\'", escapeJavaScript("\'"));
    }

    @Test
    public void testUnEscapeJavaScript(){
        assertEquals("\n",unescapeJavaScript("\\n"));
        assertEquals("\t", unescapeJavaScript("\\t"));
        assertEquals("\'", unescapeJavaScript("\\\'"));
    }


}
