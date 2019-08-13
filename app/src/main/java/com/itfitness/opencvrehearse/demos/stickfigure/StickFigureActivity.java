package com.itfitness.opencvrehearse.demos.stickfigure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itfitness.opencvrehearse.R;
import com.itfitness.opencvrehearse.base.BaseActivity;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class StickFigureActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnSF;
    private ImageView mImgSF;
    private TextView mTvBlue, mTvBlack, mTvYellow, mTvRed, mTvGreen;
    private Scalar mSelectscalar = new Scalar(0, 0, 0);//默认黑色

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickfigure);
        initView();
        initListener();
    }

    private void initListener() {
        mTvYellow.setOnClickListener(this);
        mTvBlack.setOnClickListener(this);
        mTvBlue.setOnClickListener(this);
        mTvGreen.setOnClickListener(this);
        mTvRed.setOnClickListener(this);
        mBtnSF.setOnClickListener(this);
    }

    private void initView() {
        mBtnSF = findViewById(R.id.activity_stickfigure_bt);
        mImgSF = findViewById(R.id.activity_stickfigure_img);
        mTvBlack = findViewById(R.id.activity_stickfigure_tv_black);
        mTvBlue = findViewById(R.id.activity_stickfigure_tv_blue);
        mTvGreen = findViewById(R.id.activity_stickfigure_tv_green);
        mTvRed = findViewById(R.id.activity_stickfigure_tv_red);
        mTvYellow = findViewById(R.id.activity_stickfigure_tv_yellow);
    }

    public native void stickFigure(Object bitmap);

    /**
     * 获取图像的简笔画
     *
     * @return
     */
    private Bitmap stickFigure() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_relief);
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap, src);//将Bitmap对象转换为Mat对象
        Imgproc.GaussianBlur(src, src, new Size(3, 3), 0, 0);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGBA2GRAY);//将图像转换为灰度图像
        Mat mask = new Mat();//边缘蒙版
        Imgproc.Canny(src, mask, 20, 200);//边缘提取
        Mat dst = new Mat(src.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));//背景色为白色CV_8UC3表示为3通道即R、G、B
        Mat fground = new Mat(src.size(), CvType.CV_8UC3, mSelectscalar);//前景色
        fground.copyTo(dst, mask);//将蒙版区域拷贝到背景为白色的图像上
        Utils.matToBitmap(dst, bitmap);//将Mat对象转换为Bitmap对象
        return bitmap;
    }

    /**
     * 清空选择的颜色
     */
    private void clearSelectColor() {
        mTvYellow.setText("");
        mTvRed.setText("");
        mTvGreen.setText("");
        mTvBlue.setText("");
        mTvBlack.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_stickfigure_bt:
                //mImgSF.setImageBitmap(stickFigure());
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_relief);
//                stickFigure(bitmap);
                mImgSF.setImageBitmap(stickFigure());
                break;
            case R.id.activity_stickfigure_tv_black:
                clearSelectColor();
                mTvBlack.setText("√");
                mSelectscalar = new Scalar(0, 0, 0);
                break;
            case R.id.activity_stickfigure_tv_blue:
                clearSelectColor();
                mTvBlue.setText("√");
                mSelectscalar = new Scalar(0, 0, 255);
                break;
            case R.id.activity_stickfigure_tv_green:
                clearSelectColor();
                mTvGreen.setText("√");
                mSelectscalar = new Scalar(0, 255, 0);
                break;
            case R.id.activity_stickfigure_tv_red:
                clearSelectColor();
                mTvRed.setText("√");
                mSelectscalar = new Scalar(255, 0, 0);
                break;
            case R.id.activity_stickfigure_tv_yellow:
                clearSelectColor();
                mTvYellow.setText("√");
                mSelectscalar = new Scalar(255, 255, 0);
                break;
        }
    }
}
