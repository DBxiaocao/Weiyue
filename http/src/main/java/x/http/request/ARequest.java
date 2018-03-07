package x.http.request;

import java.util.Map;

import x.http.util.RequestUtil;


/**
 * 网络请求抽象父类
 * Created by Rico on 16/11/1.
 */

public abstract class ARequest {

    /*请求方法：get、post。。。*/
    public abstract RequestUtil.RequestMethod method();
    public abstract Map<String,String> params();
    public abstract String url();

}
