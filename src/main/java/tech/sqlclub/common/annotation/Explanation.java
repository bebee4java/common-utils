package tech.sqlclub.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法说明注解
 * @author songgr
 * @since 1.0.4
 * Created by songgr on 2020/10/25.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Explanation {

    /**
     * 描述信息
     * @return String
     */
    String description() default "";

    /**
     * 注意事项
     * @return String
     */
    String attention() default "";
}