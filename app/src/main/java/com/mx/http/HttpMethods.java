package com.mx.http;

import com.mx.entity.HttpResult;
import com.mx.entity.MovieEntity;
import com.mx.service.MovieService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by boobooL on 2016/4/20 0020
 * Created 邮箱 ：boobooMX@163.com
 */
public class HttpMethods {

    public static HttpMethods mHttpMethods;
    public static final String BASE_URL="http://api.douban.com/v2/movie/";
    public static final int DEFAULT_TIMEOUT=5;
    private Retrofit mRetrofit;
    private MovieService mMovieService;
    //构造方法私有化
    private HttpMethods(){
        //手动创建一个OkhttpClient并且设置超时的时间
        OkHttpClient.Builder httpClientBuilder=new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mMovieService=mRetrofit.create(MovieService.class);

    }

    //获取单例
    public static HttpMethods getInstance(){
        mHttpMethods=new HttpMethods();
         return  mHttpMethods;

    }

    public void getTopMovie(Subscriber<MovieEntity>subscriber,int start,int count){





//        mMovieService.getMovie(start,count)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();

        Observable observable=mMovieService.getMovie(start,count)
                .map(new HttpResultFunc<List<MovieEntity>>());
        toSubscrible(observable,subscriber);
    }

    private <T> void  toSubscrible(Observable<T> o, Subscriber<T> s) {

        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);

    }


    private class HttpResultFunc<T> implements Func1<HttpResult<T>,T>{

        @Override
        public T call(HttpResult<T> httpResult) {
            if(httpResult.getCount()==0){
                throw  new ApiException(100);
            }
            return httpResult.getSubjects();
        }
    }



}
