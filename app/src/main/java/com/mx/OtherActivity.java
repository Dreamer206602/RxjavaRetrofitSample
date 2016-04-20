package com.mx;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class OtherActivity extends AppCompatActivity {


    @Bind(R.id.tvContent1)
    TextView mTvContent1;
    @Bind(R.id.tvContent2)
    TextView mTvContent2;
    @Bind(R.id.tvContent3)
    TextView mTvContent3;
    @Bind(R.id.tvContent4)
    TextView mTvContent4;
    @Bind(R.id.image1)
    ImageView mImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);

        testOne();



    }

    private void testOne() {
        String[] names = {"赵", "钱", "孙", "李"};
        Observable.from(names)//将字符串names中的所有字符一次打印出来
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("Other", s);
                        //TODO 为什么会只显示最后一个字符呢
                        mTvContent1.setText(s);

                    }
                });

        final int drawablesRes=R.mipmap.ic_launcher;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable=getTheme().getDrawable(drawablesRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(OtherActivity.this,"error",Toast.LENGTH_SHORT).show();;
            }

            @Override
            public void onNext(Drawable drawable) {
                    mImage1.setImageDrawable(drawable);
            }
        });
    }
}
