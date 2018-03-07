package x.http.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 请求头字段
 */

@Target(FIELD)
@Retention(RUNTIME)
public @interface RequestHeader {
    String key();
}
