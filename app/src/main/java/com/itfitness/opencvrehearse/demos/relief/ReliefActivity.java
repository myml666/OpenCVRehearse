package com.itfitness.opencvrehearse.demos.relief;

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
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ReliefActivity extends BaseActivity {
    private Button mBtRelief;
    private ImageView mImgRelief;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relief);
        initView();
        mBtRelief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImgRelief.setImageBitmap(relief());
            }
        });
    }

    private void initView() {
        mBtRelief = findViewById(R.id.activity_relief_bt);
        mImgRelief = findViewById(R.id.activity_relief_img);
    }

    /**
     * 利用OpenCV自带的JNI函数库进行操作
     */
    private Bitmap relief(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_relief);
        Mat src = new Mat();
        Mat dst = new Mat();
        Mat scharrX = new Mat();
        Mat scharrY = new Mat();
        Utils.bitmapToMat(bitmap,src);//将Bitmap对象转换为Mat对象
        Imgproc.GaussianBlur(src,src,new Size(3,3),0,0);
        Imgproc.cvtColor(src,src,Imgproc.COLOR_RGBA2GRAY);//将图像转换为灰度图像
        Imgproc.Laplacian(src,dst,-1,3);
//        Imgproc.Scharr(src,scharrX, -1,1,1);//提取X方向的边缘
//        Imgproc.Sobel(src,scharrY, -1,0,1);//提取Y方向的边缘
//        Core.add(scharrX,scharrY,dst);//将XY方向提取的边缘合并到一起
        //dst.convertTo(dst,CvType.CV_8UC1);//注意如果上面的ddepth参数填写了其他类型如CV_16S则需要此操作将类型转换为CV_8UC1或者CV_8UC3或CV_8UC4才能转换为Bitmap
        Utils.matToBitmap(dst,bitmap);//将Mat对象转换为Bitmap对象
        return bitmap;
    }
}
