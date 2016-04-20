package com.mx.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;


/**
 * Created by boobooL on 2016/4/20 0020
 * Created 邮箱 ：boobooMX@163.com
 */
public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DAILOG=1;
    public static final int DISMISS_PROGRESS_DIALOG=2;
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private  boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, boolean cancelable, ProgressCancelListener progressCancelListener) {
        mContext = context;
        this.cancelable = cancelable;
        mProgressCancelListener = progressCancelListener;
    }

    private void initProgressDialog(){
        if(mProgressDialog==null){
            mProgressDialog=new ProgressDialog(mContext);
            mProgressDialog.setCancelable(cancelable);

            if(cancelable){
                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if(!mProgressDialog.isShowing()){
                mProgressDialog.show();
            }
        }
    }

    private void dimissProgressDialog(){
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
            mProgressDialog=null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case SHOW_PROGRESS_DAILOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dimissProgressDialog();
                break;
        }
    }

}
