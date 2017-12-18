package x.http;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import x.http.request.ARequest;
import x.http.result.ProgressResponseBody;
import x.http.result.ResponseResult;
import x.http.util.NetUtils;
import x.http.util.UserAgentUtil;

/**
 * className: HttpUtils
 * author: lijun
 * date: 17/6/29 15:19
 */

public class HttpUtils {

    interface Http {

        @FormUrlEncoded
        @POST
        Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> params);

        @GET
        Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> params);


    }

    interface Download {
        @GET
        Observable<ResponseBody> download(@Url String url);
    }


    private static Retrofit retrofit;
    private static Retrofit retrofit_download;
    private static Http http;
    private static Context mContext;

    public static void init(String sever, Context context) {
        mContext = context;
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
                builder.addHeader("Cache-Control", "max-age=0");
                builder.addHeader("Upgrade-Insecure-Requests", "1");
                builder.addHeader("X-Requested-With", "XMLHttpRequest");
                builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187");
                return chain.proceed(builder.build());
            }
        };
        Interceptor interceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (NetUtils.isConnected(mContext)) {//有网时
                    Response response = chain.proceed(request);
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + 60 * 60 * 24 * 7).build();
                } else {//无网时
                    request = request.newBuilder()
                            .cacheControl(new CacheControl.Builder().onlyIfCached().maxStale(60 * 60 * 24 * 7,TimeUnit.SECONDS).build())
                            .build();
                }
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached")
                        .removeHeader("Pragma")
                        .build();
            }
        };

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cache(new Cache(new File(mContext.getCacheDir(), "HttpCache"),
                        1024 * 1024 * 100));
        httpClientBuilder.addInterceptor(headerInterceptor);
        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(sever)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        http = retrofit.create(Http.class);

    }

    public static Observable<InputStream> download(final String url, final ProgressResponseBody.ProgressListener progressListener) {
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder().body(
                                new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();

        return Observable.create(new ObservableOnSubscribe<InputStream>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<InputStream> subscriber) throws Exception {
                client.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(parseError(e));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            subscriber.onNext(response.body().byteStream());
                            subscriber.onComplete();
                        } else
                            subscriber.onError(new Throwable("下载失败"));
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }


    /*异步请求*/
    public static Observable<ResponseResult> async(final Object reqObj) {



        return Observable.create(new ObservableOnSubscribe<ResponseResult>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<ResponseResult> subscriber) throws Exception {
                Logger.d("访问1：%s", reqObj instanceof ARequest);
                if (reqObj instanceof ARequest) {
                    ARequest aRequest = (ARequest) reqObj;
                    Logger.d("访问2：%s - %s", aRequest.method(), aRequest.url());
                    Observable<ResponseBody> observable = null;
                    switch (aRequest.method()) {
                        case GET:
                            observable =
                                    http.get(aRequest.url(), aRequest.params());
                            break;
                        case POST:
                            observable =
                                    http.post(aRequest.url(), aRequest.params());
                            break;
                    }

                    if (observable != null)
                        observable.subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(@NonNull ResponseBody responseBody) throws Exception {
                                ResponseResult result = new ResponseResult();
                                result.setResult(responseBody.string());
                                result.setSuccess(true);
                                subscriber.onNext(result);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                subscriber.onError(parseError(throwable));
                            }
                        });
                    else
                        subscriber.onComplete();
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }



    private static final Object responseLock = new Object();

    /*
    * int curPage = 0;
                        int totalPage = 0;
                        String result = null;
                        if (jsonObject.has("result"))
                            result = jsonObject.getString("result");
                        if (jsonObject.has("page"))
                            curPage = jsonObject.getInt("page");
                        if (jsonObject.has("totalPage"))
                            totalPage = jsonObject.getInt("totalPage");
                        if (jsonObject.has("totalPrice"))
                            responseResult.totalPrice = jsonObject.getString("totalPrice");
                        responseResult.isLastPage = curPage == totalPage;
                                                responseResult.setResult(result == null ? "" : result);

    * */
    /*解析服务器返回数据*/
    private static ResponseResult parseResponse(ResponseBody response) {
        synchronized (responseLock) {
            ResponseResult responseResult = new ResponseResult();
            try {
                Logger.json(response.string());
                JSONObject jsonObject = new JSONObject(response.string());
//                Logger.json(jsonObject.toString());
                String desription = "";
                if (jsonObject.has("error_detail"))
                    desription = jsonObject.getString("error_detail");
                responseResult.setMsg(desription);
                int state = jsonObject.getInt("error_code");
                switch (state) {
                    case 10101://失败
                        responseResult.setMsg("非法请求，仅支持json格式");
                        responseResult.setSuccess(false);
                        break;
                    case 10102://失败
                        responseResult.setMsg("非法请求，仅支持json格式");
                        responseResult.setSuccess(false);
                        break;
                    case 10105://失败
                        responseResult.setSuccess(false);
                        break;
                    case 10107://失败
                        responseResult.setSuccess(false);
                        break;
                    default:
                        responseResult.setSuccess(false);
                        break;
                }

            } catch (JSONException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseResult;
        }
    }

    /*解析返回的错误数据*/
    private static Throwable parseError(Throwable throwable) {
        synchronized (responseLock) {
            Throwable err = null;
            if (throwable != null
                    && !TextUtils.isEmpty(throwable.getMessage())
                    && throwable.getMessage().contains("time out"))
                err = new Throwable("请求超时，请检查网络");
            return err == null ? new Throwable("") : err;
        }
    }


}
