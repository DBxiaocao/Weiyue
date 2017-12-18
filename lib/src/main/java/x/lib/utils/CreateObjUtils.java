package x.lib.utils;

import java.lang.reflect.ParameterizedType;

/**
 * description: CreateObjUtils
 * author: lijun
 * date: 17/12/4 14:04
 */

public class CreateObjUtils {
    /**
     * 获取T类型然后自动new出对象
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
