package com.itfitness.opencvrehearse.demos.codeextra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.itfitness.opencvrehearse.R;
import com.itfitness.opencvrehearse.base.BaseActivity;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class CodeExtraActivity extends BaseActivity {
    private Button mBtnCodeExtra;
    private ImageView mImgCode;
    private Bitmap mBitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeextra);
        initView();



        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_codeextra);
        mBtnCodeExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeExtra(mBitmap);
                mImgCode.setImageBitmap(mBitmap);
            }
        });
    }

    private void initView() {
        mBtnCodeExtra = findViewById(R.id.activity_codeextra_bt);
        mImgCode = findViewById(R.id.activity_codeextra_img);
    }

    /**
     * 提取验证码
     * @param bitmap
     */
    public native void codeExtra(Object bitmap);
}
