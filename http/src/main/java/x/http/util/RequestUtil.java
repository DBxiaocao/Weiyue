package x.http.util;

import android.text.TextUtils;
import android.util.ArrayMap;


import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import x.http.interfaces.MultipartParam;
import x.http.interfaces.NotRequestParam;
import x.http.interfaces.RequestBody;
import x.http.interfaces.RequestHeader;
import x.http.interfaces.RequestParam;

/**
 * 处理网络请求参数之类的工具类
 */
public class RequestUtil {
    private static RequestUtil util = new RequestUtil();

    private String serverUrl;

    private RequestUtil() {

    }


    public static enum RequestMethod {

        GET("GET"),

        POST("POST"),

        PUT("PUT"),

        MOVE("MOVE"),

        COPY("COPY"),

        DELETE("DELETE"),

        HEAD("HEAD"),

        PATCH("PATCH"),

        OPTIONS("OPTIONS"),

        TRACE("TRACE"),

        CONNECT("CONNECT");


        private final String value;

        RequestMethod(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public boolean allowRequestBody() {
            switch (this) {
                case POST:
                case PUT:
                case PATCH:
                case DELETE:
                    return true;
                default:
                    return false;
            }
        }

    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        Logger.d("newurl-" + this.serverUrl);
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public static RequestUtil getUtil() {
//        if (util == null)
//            util = new RequestUtil();
        return util;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public Map<String, String> getHeader(Object requestEntity) {
        Map<String, String> result = new ArrayMap<>();
        if (requestEntity != null) {
            Field[] fields = requestEntity.getClass().getDeclaredFields();
            try {
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (field.getName().startsWith("this$"))
                        continue;

                    int annonCount = field.getAnnotations().length;
                    Annotation headerAnnon = annonCount == 0 ? null : field.getAnnotation(RequestHeader.class);
                    if (headerAnnon == null)
                        continue;

                    String key = null;
                    if (headerAnnon != null) {
                        key = ((RequestHeader) headerAnnon).key();
                    }
                    key = TextUtils.isEmpty(key) ? field.getName() : key;

                    result.put(key, parseValue(field.get(requestEntity), field.getType()));

//                    Logg.d("请求-" + key + "-" + parseValue(field.get(requestEntity), field.getType()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Map<String, String> genMapParams(Object requestEntity) {
        Map<String, String> result = new ArrayMap<>();
        if (requestEntity != null) {
            parseMapParams(requestEntity, requestEntity.getClass(), result);
        }
        return result;
    }

    private void parseMapParams(Object requestEntity, Class<?> clz, Map<String, String> result) {
        Field[] fields = clz.getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.getName().startsWith("this$"))
                    continue;

                field.setAccessible(true);
                int annonCount = field.getAnnotations().length;
                Annotation headerAnnon = annonCount == 0 ? null : field.getAnnotation(RequestHeader.class);
                Annotation passAnnon = annonCount == 0 ? null : field.getAnnotation(NotRequestParam.class);
                Annotation bodyAnnon = annonCount == 0 ? null : field.getAnnotation(RequestBody.class);
                if (headerAnnon != null || (annonCount == 0 && Modifier.isTransient(field.getModifiers())) || passAnnon != null || bodyAnnon != null)//是请求头或者没有注解并被transient修饰或者被标注不是请求参数或是添加请求体则跳过该字段
                    continue;

                RequestParam paramAnnon = annonCount == 0 ? null : field.getAnnotation(RequestParam.class);
                String key = null;
                if (paramAnnon != null) {
                    key = paramAnnon.key();
                }
                key = TextUtils.isEmpty(key) ? field.getName() : key;

                result.put(key, parseValue(field.get(requestEntity), field.getType()));
//                Logg.d("请求-" + key + "-" + parseValue(field.get(requestEntity), field.getType()));

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Class<?> superClz = clz.getSuperclass();
        if (superClz != null)
            parseMapParams(requestEntity, superClz, result);
    }


    /**
     * 用于OkHttp三方库
     *
     * @param requestEntity
     * @return
     */
    public okhttp3.RequestBody getMultipartOkRequestBody(Object requestEntity) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (requestEntity != null) {
            parseMultipartOkRequestBody(requestEntity, requestEntity.getClass(), requestBodyBuilder);
        }
        return requestBodyBuilder.build();
    }

    private void parseMultipartOkRequestBody(Object requestEntity, Class<?> clz, MultipartBody.Builder requestBodyBuilder) {
        Field[] fields = clz.getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.getName().startsWith("this$"))
                    continue;

                field.setAccessible(true);
                int annonCount = field.getAnnotations().length;
                Annotation headerAnnon = annonCount == 0 ? null : field.getAnnotation(RequestHeader.class);
                Annotation passAnnon = annonCount == 0 ? null : field.getAnnotation(NotRequestParam.class);
                Annotation bodyAnnon = annonCount == 0 ? null : field.getAnnotation(RequestBody.class);
                if (headerAnnon != null || (annonCount == 0 && Modifier.isTransient(field.getModifiers())) || passAnnon != null || bodyAnnon != null)//是请求头或者没有注解并被transient修饰或者被标注不是请求参数或是添加请求体则跳过该字段
                    continue;

                MultipartParam multipartParam = annonCount == 0 ? null : field.getAnnotation(MultipartParam.class);
                if (field.getType().isAssignableFrom(File.class) && multipartParam != null) {
                    Object fieldVal = field.get(requestEntity);
                    if (fieldVal != null) {
                        String multiKey = null;
                        multiKey = multipartParam.key();
                        multiKey = TextUtils.isEmpty(multiKey) ? field.getName() : multiKey;
                        File file = (File) fieldVal;
                        requestBodyBuilder.addFormDataPart(multiKey, file.getName(), okhttp3.RequestBody.create(null, file));

                        requestBodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""), okhttp3.RequestBody.create(MediaType.parse("application/octet-stream"), file));
                    }
                } else {
                    RequestParam paramAnnon = annonCount == 0 ? null : field.getAnnotation(RequestParam.class);
                    String key = null;
                    if (paramAnnon != null) {
                        key = paramAnnon.key();
                    }
                    key = TextUtils.isEmpty(key) ? field.getName() : key;

                    requestBodyBuilder.addFormDataPart(key, parseValue(field.get(requestEntity), field.getType()));
//                    Logg.d("请求-" + key + "-" + parseValue(field.get(requestEntity), field.getType()));

                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Class<?> superClz = clz.getSuperclass();
        if (superClz != null)
            parseMultipartOkRequestBody(requestEntity, superClz, requestBodyBuilder);
    }

    public okhttp3.RequestBody getOkRequestBody(Object requestEntity) {
        okhttp3.FormBody.Builder requestBodyBuilder = new okhttp3.FormBody.Builder();
        if (requestEntity != null) {
            parseOkRequestBody(requestEntity, requestEntity.getClass(), requestBodyBuilder);
        }
        return requestBodyBuilder.build();
    }

    private void parseOkRequestBody(Object requestEntity, Class<?> clz, okhttp3.FormBody.Builder requestBodyBuilder) {
        Field[] fields = clz.getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.getName().startsWith("this$"))
                    continue;

                field.setAccessible(true);
                int annonCount = field.getAnnotations().length;
                Annotation headerAnnon = annonCount == 0 ? null : field.getAnnotation(RequestHeader.class);
                Annotation passAnnon = annonCount == 0 ? null : field.getAnnotation(NotRequestParam.class);
                Annotation bodyAnnon = annonCount == 0 ? null : field.getAnnotation(RequestBody.class);
                if (headerAnnon != null || (annonCount == 0 && Modifier.isTransient(field.getModifiers())) || passAnnon != null || bodyAnnon != null)//是请求头或者没有注解并被transient修饰或者被标注不是请求参数或是添加请求体则跳过该字段
                    continue;

                RequestParam paramAnnon = annonCount == 0 ? null : field.getAnnotation(RequestParam.class);
                String key = null;
                if (paramAnnon != null) {
                    key = paramAnnon.key();
                }
                key = TextUtils.isEmpty(key) ? field.getName() : key;
                requestBodyBuilder.add(key, parseValue(field.get(requestEntity), field.getType()));

//                Logg.d("请求-" + key + "-" + parseValue(field.get(requestEntity), field.getType()));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Class<?> superClz = clz.getSuperclass();
        if (superClz != null)
            parseOkRequestBody(requestEntity, superClz, requestBodyBuilder);
    }


    public CharSequence genGetterParams(Object requestEntity) {
        StringBuilder result = new StringBuilder();
        if (requestEntity != null) {
            parseGetterParams(requestEntity, requestEntity.getClass(), result, true);
        }
        return result;
    }

    private void parseGetterParams(Object requestEntity, Class<?> clz, StringBuilder result, boolean first) {
        Field[] fields = clz.getDeclaredFields();
        try {
            for (Field field :
                    fields) {

                if (field.getName().startsWith("this$"))
                    continue;

//                    Field field = fields[i];
                field.setAccessible(true);
                int annonCount = field.getAnnotations().length;
                Annotation headerAnnon = annonCount == 0 ? null : field.getAnnotation(RequestHeader.class);
                Annotation passAnnon = annonCount == 0 ? null : field.getAnnotation(NotRequestParam.class);
                Annotation bodyAnnon = annonCount == 0 ? null : field.getAnnotation(RequestBody.class);
                if (headerAnnon != null || (annonCount == 0 && Modifier.isTransient(field.getModifiers())) || passAnnon != null || bodyAnnon != null)//是请求头或者没有注解并被transient修饰或者被标注不是请求参数或是添加请求体则跳过该字段
                    continue;

                RequestParam paramAnnon = annonCount == 0 ? null : field.getAnnotation(RequestParam.class);
                String key = null;
                if (paramAnnon != null) {
                    key = paramAnnon.key();
                }
                key = TextUtils.isEmpty(key) ? field.getName() : key;


                if (first) {
                    first = false;
                    result.append(getGetter(key, parseValue(field.get(requestEntity), field.getType()), "?"));
                } else {
                    result.append(getGetter(key, parseValue(field.get(requestEntity), field.getType()), "&"));
                }
//                Logg.d("请求-"+key+"-"+parseValue(field.get(requestEntity), field.getType()));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Class<?> superClz = clz.getSuperclass();
        if (superClz != null)
            parseGetterParams(requestEntity, superClz, result, first);
    }


    public CharSequence getGetter(String key, Object value, String prefix) {
        return new StringBuilder(prefix).append(key).append("=").append(value);
    }

    public String getUrl(CharSequence uri) {
//        if (TextUtils.isEmpty(this.serverUrl))
//            this.serverUrl = Constants.SERVER;
        return new StringBuilder(this.serverUrl).append(uri).toString();
    }

    public String getHtmlUrl(CharSequence url) {
        return new StringBuffer(url).toString();
    }

    private String parseValue(Object value, Class<?> type) {
//        Logg.d("解析值－"+value);
        String result = null;
        if (Character.TYPE.isAssignableFrom(type))
            result = value == null ? "" : Character.toString((Character) value);
        else if (Integer.TYPE.isAssignableFrom(type))
            result = value == null ? "0" : Integer.toString((Integer) value);
        else if (Boolean.TYPE.isAssignableFrom(type))
            result = value == null ? "false" : Boolean.toString((Boolean) value);
        else if (String.class.isAssignableFrom(type))
            result = value == null ? "" : value.toString();
        else if (Long.TYPE.isAssignableFrom(type))
            result = value == null ? "0" : Long.toString((Long) value);
        else if (Double.TYPE.isAssignableFrom(type))
            result = value == null ? "0.0" : Double.toString((Double) value);
        else if (Float.TYPE.isAssignableFrom(type))
            result = value == null ? "0.0" : Float.toString((Float) value);
        else if (Short.TYPE.isAssignableFrom(type))
            result = value == null ? "0" : Short.toString((Short) value);
        else
            result = value == null ? null : value.toString();
        return result;
    }
}
