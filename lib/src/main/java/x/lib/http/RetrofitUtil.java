package x.lib.http;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import x.lib.utils.LogUtil;

/**
 * description: RetrofitUtil
 * author: lijun
 * date: 17/11/30 11:02
 */

public class RetrofitUtil {

    private static Retrofit retrofit;

    /**
     * 获取retrofit对象
     * <p>
     * retrofit对象默认使用我们自己的URL：DatasConfig.HOST
     * 如果想要指定具体的url,请调用build(baseUrl)方法
     * 设置了Rxjava的请求回调机制
     * 设置了Gson：json-Java bean的自动解析（序列化和反序列化）
     */
    public static Retrofit build() {
        return retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(RetrofitUtil.genericClient())
                .build();
    }

    /**
     * 获取retrofit对象
     *
     * @param baseUrl
     */
    public static Retrofit build(String baseUrl) {
        return retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(RetrofitUtil.genericClient())// 添加自定义的头信息
                .build();
    }

    /**
     * 定制client
     */
    public static OkHttpClient genericClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.info("请求" + message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置HTTP请求通用的协议头
        Interceptor proheader = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept", "*/*")
//                        .addHeader("Cookie", "add cookies here")
//                        .build();
//                return chain.proceed(request);
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
                builder.addHeader("Cache-Control", "max-age=0");
                builder.addHeader("Upgrade-Insecure-Requests", "1");
                builder.addHeader("X-Requested-With", "XMLHttpRequest");
                builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187");
                return chain.proceed(builder.build());
            }
        };

        //设置OkHttpClient
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)//添加日志打印功能
                .addInterceptor(proheader)//自定义http headers设置
//                .addInterceptor(new RspCheckInterceptor())
                .readTimeout(30, TimeUnit.SECONDS)//设置超时
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }


}
