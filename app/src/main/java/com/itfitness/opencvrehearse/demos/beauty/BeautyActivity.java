package com.itfitness.opencvrehearse.demos.beauty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.itfitness.opencvrehearse.R;
import com.itfitness.opencvrehearse.base.BaseActivity;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class BeautyActivity extends BaseActivity {
    private Button mBtBeauty;
    private ImageView mImgBeauty;
    private Bitmap mBitmap;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImgBeauty.setImageBitmap(mBitmap);
            if(mLoadingDialog!=null&&mLoadingDialog.isShowing()){
                mLoadingDialog.dismiss();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);
        TAG = "美颜";
        initView();
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_beauty);


        mBtBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Mat src = new Mat();
                        Mat dst = new Mat();
                        //将Bitmap转换为Mat
                        Utils.bitmapToMat(mBitmap,src);
                        //将图像转换为三通道（CV_8UC3），因为双边滤波的操作不支持原图像的4通道（CV_8UC4）
                        Imgproc.cvtColor(src,src,Imgproc.COLOR_RGBA2RGB);
                        //中值滤波，去除图像的噪点（或是脸部小斑点）
                        Imgproc.medianBlur(src,src,3);
                        //双边滤波，保留图像边缘
                        Imgproc.bilateralFilter(src,dst,40,50,30);
                        //========锐化图像，让边缘更加明显========
                        Mat kx = new Mat(3, 3, CvType.CV_32FC1);
                        float[] robert_x = new float[]{0, -1, 0, -1, 5, -1, 0, -1, 0};
                        kx.put(0, 0, robert_x);
                        Imgproc.filter2D(dst,dst,-1,kx );
                        //=========================================
                        //将Mat转换为Bitmap
                        Utils.matToBitmap(dst,mBitmap);
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });
    }

    private void initView() {
        mBtBeauty = findViewById(R.id.activity_beauty_bt);
        mImgBeauty = findViewById(R.id.activity_beauty_img);
    }
    /**
     * 人像美颜Native方法
     * @param bitmap
     */
    public native void beauty(Object bitmap);
}
