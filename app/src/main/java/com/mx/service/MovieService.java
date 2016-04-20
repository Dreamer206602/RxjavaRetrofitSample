package com.mx.service;



import com.mx.entity.HttpResult;
import com.mx.entity.MovieEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by boobooL on 2016/4/20 0020
 * Created 邮箱 ：boobooMX@163.com
 */
public interface MovieService {
    @GET("top250")
    Observable<HttpResult<List<MovieEntity>>> getMovie(@Query("start") int start, @Query("count") int count);
}
