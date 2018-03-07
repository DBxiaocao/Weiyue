package x.http.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 上传文件字段
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface MultipartParam {
    String key() default "";
}
