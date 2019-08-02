package com.itfitness.opencvrehearse.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.itfitness.opencvrehearse.factory.DialogFactory;

public class BaseActivity extends AppCompatActivity {
    protected Dialog mLoadingDialog;
    private int mScreenWidth;
    private WindowManager mWindowManager;
    protected String TAG = "";
    /**
     * 跳转Activity
     * @param clazz
     */
    protected void gotoActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 打印函数
     * @param msg
     */
    protected void logE(String msg){
        Log.e(TAG,msg);
    }
    /**
     * 显示Loading加载框
     */
    protected void showLoadingDialog(){
        mLoadingDialog = DialogFactory.createLoadingDailog(this);
        mLoadingDialog.show();
        mLoadingDialog.getWindow().setLayout( mScreenWidth/2, LinearLayout.LayoutParams.WRAP_CONTENT);
//设置背景透明
        mLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
