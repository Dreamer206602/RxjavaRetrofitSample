package com.mx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mx.entity.MovieEntity;
import com.mx.http.HttpMethods;
import com.mx.subscribers.ProgressSubscriber;
import com.mx.subscribers.SubscriberOnNextListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tvContent)
    TextView mTvContent;
    private Subscriber<MovieEntity> mSubscriber;

    private SubscriberOnNextListener mSubscriberOnNextListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mSubscriberOnNextListener = new SubscriberOnNextListener<List<MovieEntity>>() {


            @Override
            public void onNext(List<MovieEntity> movieEntities) {

                mTvContent.setText(movieEntities.get(1).getTitle()+"ssss");
            }
        };
    }

    @OnClick(R.id.btn)
    public void onClick() {
        getMovie();
    }

    //进行网络的请求
    public void getMovie() {

//        String baseUrl = "https://api.douban.com/v2/movie/";
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        MovieService movieService = retrofit.create(MovieService.class);
//
//        movieService.getMovie(0,10)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<MovieEntity>(){
//
//                    @Override
//                    public void onCompleted() {
//
//                        Toast.makeText(MainActivity.this,"Get Movie Completed",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        mTvContent.setText(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(MovieEntity movieEntity) {
//                            Log.d("Main2",movieEntity.toString());
//                                mTvContent.setText("title:"+movieEntity.getTitle()+"\n"
//                                        +"count:"+movieEntity.getCount()+"\n"
//                                        +"start:"+movieEntity.getStart()+"\n"
//                                        +"total:"+movieEntity.getTotal());
//                    }
//                });


//        mSubscriber = new Subscriber<MovieEntity>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(MovieEntity movieEntity) {
//
//                Log.d("Main2", movieEntity.toString());
//                mTvContent.setText("title:" + movieEntity.getTitle() + "\n"
//                        + "count:" + movieEntity.getCount() + "\n"
//                        + "start:" + movieEntity.getStart() + "\n"
//                        + "total:" + movieEntity.getTotal());
//            }
//        };
//
//        HttpMethods.getInstance().getTopMovie(mSubscriber,0,10);

        HttpMethods.getInstance().getTopMovie(new ProgressSubscriber<MovieEntity>(mSubscriberOnNextListener,MainActivity.this),0,10);


    }
}
