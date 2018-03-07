package x.lib.http.retrofit;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import x.lib.BaseApplication;
import x.lib.http.retrofit.download.DownloadProgressResponseBody;
import x.lib.utils.LogUtil;
import x.lib.utils.NetUtils;

/**
 * description: RetrofitUtil
 * author: lijun
 * date: 17/11/30 11:02
 */

public class RetrofitUtil {

    public static final String BASE_API = "http://47.92.119.85/";
    private static RetrofitUtil mInstance = null;

    private RetrofitUtil() {
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }

    public static OkHttpClient okHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.d("请求" + message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //增加头部信息
        Interceptor proheader = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
                return chain.proceed(builder.build());
            }
        };
        Interceptor interceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (NetUtils.isConnected(BaseApplication.getInstance())) {//有网时
                    Response response = chain.proceed(request);
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + 60 * 60 * 24 * 7).build();
                } else {//无网时
                    request = request.newBuilder()
                            .cacheControl(new CacheControl.Builder().onlyIfCached().maxStale(60 * 60 * 24 * 7, TimeUnit.SECONDS).build())
                            .build();
                }
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached")
                        .removeHeader("Pragma")
                        .build();
            }
        };

        //设置OkHttpClient
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)//添加日志打印功能
                .addInterceptor(proheader)//自定义http headers设置
                .addNetworkInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)//设置超时
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

    public Retrofit retrofit(String api) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public Retrofit dowRetrofit(final DownloadProgressResponseBody.DownloadProgressListener listener) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new DownloadProgressResponseBody(originalResponse.body(), listener))
                                .build();
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

}
