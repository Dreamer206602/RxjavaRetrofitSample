package com.mx.subscribers;

import android.content.Context;
import android.widget.Toast;

import com.mx.progress.ProgressCancelListener;
import com.mx.progress.ProgressDialogHandler;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by boobooL on 2016/4/20 0020
 * Created 邮箱 ：boobooMX@163.com
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context mContext;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(mContext, true, this);
    }

    public void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DAILOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {

        dismissProgressDialog();
        Toast.makeText(mContext,"Completed",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(Throwable e) {

        if(e instanceof SocketTimeoutException){
            Toast.makeText(mContext,"网络中断,请检查您的网络状态",Toast.LENGTH_SHORT).show();


        }else if(e instanceof ConnectException){
            Toast.makeText(mContext,"网络中断,请检查您的网络状态",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext,"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if(mSubscriberOnNextListener!=null){
            mSubscriberOnNextListener.onNext(t);
        }

    }

    @Override
    public void onCancelProgress() {

        if(!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
