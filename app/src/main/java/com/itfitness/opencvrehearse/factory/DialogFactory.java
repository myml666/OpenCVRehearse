package com.itfitness.opencvrehearse.factory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.itfitness.opencvrehearse.R;

public class DialogFactory {
    /**
     * 创建Loading弹窗
     * @param ctx
     * @return
     */
    public static Dialog createLoadingDailog(Context ctx){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(View.inflate(ctx,R.layout.dialog_loading,null));
        return builder.create();
    }
}
