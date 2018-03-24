package x.lib.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json处理工具类
 */

public class JsonUtil {

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;

        gsonString = new Gson().toJson(object);
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        t = new Gson().fromJson(gsonString, cls);
        return t;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        list = new Gson().fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static  <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        list = new Gson().fromJson(gsonString,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        map = new Gson().fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

    /**
     * 根据key值从json获取对应的数据.
     *
     * @param jsonStr json Object数据
     * @param key     键值
     * @return
     */
    public static String getString(String jsonStr, String key) {
        return getJsonElement(jsonStr, key).getAsString();
    }

    /**
     * 根据key值从json获取对应的数据.
     *
     * @param jsonStr json Object数据
     * @param key     键值
     * @return
     */
    public static JsonElement getJsonElement(String jsonStr, String key) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonStr).getAsJsonObject();
        return jsonObject.get(key);
    }

    /**
     * json字符串转化为map
     *
     * @param s
     * @return
     */
    public static Map getJsonMap(String s) {
        Map m = JSONObject.parseObject(s);
        return m;
    }
}
