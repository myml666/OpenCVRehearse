package com.itfitness.opencvrehearse.demos.remap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.itfitness.opencvrehearse.R;
import com.itfitness.opencvrehearse.base.BaseActivity;

public class RemapActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtX,mBtY,mBtXY;
    private ImageView mImg;
    private Bitmap mBitmap;
    private static final int REMAPTYPE_X = 1;
    private static final int REMAPTYPE_Y = 2;
    private static final int REMAPTYPE_XY = 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remap);
        initView();
        initListener();
    }

    private void initListener() {
        mBtX.setOnClickListener(this);
        mBtY.setOnClickListener(this);
        mBtXY.setOnClickListener(this);
    }

    private void initView() {
        mBtX = findViewById(R.id.activity_remap_btx);
        mBtY = findViewById(R.id.activity_remap_bty);
        mBtXY = findViewById(R.id.activity_remap_btxy);
        mImg = findViewById(R.id.activity_remap_img);
    }

    /**
     * Java层的函数声明
     * @param bitmap 传入的Bitmap对象
     * @param type 映像类型，1：X轴、2：Y轴、3：XY轴
     */
    public native void remap(Object bitmap,int type);
    @Override
    public void onClick(View v) {
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_relief);
        switch (v.getId()){
            case R.id.activity_remap_btx:
//                X轴
                remap(mBitmap,REMAPTYPE_X);
                break;
            case R.id.activity_remap_bty:
//                Y轴
                remap(mBitmap,REMAPTYPE_Y);
                break;
            case R.id.activity_remap_btxy:
//                XY轴
                remap(mBitmap,REMAPTYPE_XY);
                break;

        }
        mImg.setImageBitmap(mBitmap);
    }
}
