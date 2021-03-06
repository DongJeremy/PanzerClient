package org.cloud.core.net;

import android.os.Build;

import org.cloud.core.net.interceptor.ParameterInterceptor;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseRetrofit {
    private static OkHttpClient client;
    private static volatile Retrofit retrofit;

    /**
     * 配置网络请求头
     */
    public static HashMap<String, Object> getRequestHeader() {
        HashMap<String, Object> parameters = new HashMap<>();
        // 为接口统一添加 key 参数
        parameters.put("token", "");
        return parameters;
    }

    /**
     * 配置网络请求公共参数
     */
    public static HashMap<String, Object> getRequestParams() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("_appversion", "");
        parameters.put("_systemtype", "" );
        parameters.put("_systemversion", "" );
        parameters.put("_deviceid", "" );
        parameters.put("_memberid", "" );
        return parameters;
    }
    public static Retrofit getRetrofit(String urlString) {

        if (retrofit == null) {
            synchronized (BaseRetrofit.class) {
                if (retrofit == null) {
                    //添加一个log拦截器,打印所有的log
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    //可以设置请求过滤的水平,body,basic,headers
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient.Builder builder = new OkHttpClient
                            .Builder()
//                          .cookieJar(new CookieJarImpl(new PersistentCookieStore(App.getContext()))); //cookie 相关
                            .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应
                            .addInterceptor(new ParameterInterceptor(getRequestHeader())) // token过滤
                            //不加以下两行代码,https请求不到自签名的服务器
                            //.sslSocketFactory(createSSLSocketFactory())//创建一个证书对象
                            .hostnameVerifier(new TrustAllHostnameVerifier())//校验名称,这个对象就是信任所有的主机,也就是信任所有https的请求
                            .connectTimeout(15, TimeUnit.SECONDS)//连接超时时间
                            .readTimeout(15, TimeUnit.SECONDS)//读取超时时间
                            .writeTimeout(15, TimeUnit.SECONDS)//写入超时时间
                            .retryOnConnectionFailure(false);//连接不上是否重连,false不重连

                    //忽略ssl证书,android10及以上的版本就不用了
                    if (Build.VERSION.SDK_INT < 29) {
                        builder.sslSocketFactory(createSSLSocketFactory()); //创建一个证书对象
                    }
                    client = builder.build();

                    // 获取retrofit的实例
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(urlString)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static Retrofit getRawRetrofit(String urlString) {

        if (retrofit == null) {
            synchronized (BaseRetrofit.class) {
                if (retrofit == null) {
                    //添加一个log拦截器,打印所有的log
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    //可以设置请求过滤的水平,body,basic,headers
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient.Builder builder = new OkHttpClient
                            .Builder()
                            .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应
                            .addInterceptor(new ParameterInterceptor(getRequestHeader())) // token过滤
                            //不加以下两行代码,https请求不到自签名的服务器
                            //.sslSocketFactory(createSSLSocketFactory())//创建一个证书对象
                            .hostnameVerifier(new TrustAllHostnameVerifier())//校验名称,这个对象就是信任所有的主机,也就是信任所有https的请求
                            .connectTimeout(15, TimeUnit.SECONDS)//连接超时时间
                            .readTimeout(15, TimeUnit.SECONDS)//读取超时时间
                            .writeTimeout(15, TimeUnit.SECONDS)//写入超时时间
                            .retryOnConnectionFailure(true);//连接不上是否重连,false不重连

                    //忽略ssl证书,android10及以上的版本就不用了
                    if (Build.VERSION.SDK_INT < 29) {
                        builder.sslSocketFactory(createSSLSocketFactory()); //创建一个证书对象
                    }
                    client = builder.build();

                    // 获取retrofit的实例
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(urlString)
                            .client(client)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * 实现https请求
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * 信任所有的服务器,返回true
     */
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
