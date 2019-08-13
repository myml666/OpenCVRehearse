#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <android/bitmap.h>
#include <android/log.h>
using namespace cv;
extern "C"
JNIEXPORT void JNICALL
Java_com_itfitness_opencvrehearse_beauty_BeautyActivity_beauty(JNIEnv *env, jobject instance,
                                                               jobject bitmap) {

    AndroidBitmapInfo info;
    void *pixels;

    CV_Assert(AndroidBitmap_getInfo(env, bitmap, &info) >= 0);
    CV_Assert(info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
              info.format == ANDROID_BITMAP_FORMAT_RGB_565);
    CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
    CV_Assert(pixels);
    if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        Mat tempSrc(info.height, info.width, CV_8UC4, pixels);
        cvtColor(tempSrc,tempSrc,CV_RGBA2RGB);
        Mat tempSrcClone = tempSrc.clone();
        __android_log_print(ANDROID_LOG_ERROR,"美颜","图像类型为：%d",tempSrcClone.type());
        medianBlur(tempSrcClone,tempSrc,15);
        bilateralFilter(tempSrcClone,tempSrc,9,30,30);
        //tempSrcClone.release();
    }
    AndroidBitmap_unlockPixels(env, bitmap);

}extern "C"
JNIEXPORT void JNICALL
Java_com_itfitness_opencvrehearse_demos_codeextra_CodeExtraActivity_codeExtra(JNIEnv *env,
                                                                              jobject instance,
                                                                              jobject bitmap) {

    AndroidBitmapInfo info;
    void *pixels;

    CV_Assert(AndroidBitmap_getInfo(env, bitmap, &info) >= 0);
    CV_Assert(info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
              info.format == ANDROID_BITMAP_FORMAT_RGB_565);
    CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
    CV_Assert(pixels);
    if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        Mat temp(info.height, info.width, CV_8UC4, pixels);
        Mat gray;
        cvtColor(temp, gray,CV_RGBA2GRAY);//转为灰度图像
        threshold(gray, gray, 170, 255, CV_THRESH_BINARY);//图像二值化
        Mat kernel = getStructuringElement(MORPH_RECT, Size(5, 5));//创建结构元素大小为3*3
        morphologyEx(gray, gray, MORPH_CLOSE, kernel);//闭操作
        cvtColor(gray,temp,CV_GRAY2RGBA);//转为RGBA图像
        gray.release();//释放资源
    }
    AndroidBitmap_unlockPixels(env, bitmap);

}extern "C"
JNIEXPORT void JNICALL
Java_com_itfitness_opencvrehearse_demos_stickfigure_StickFigureActivity_stickFigure__Ljava_lang_Object_2(
        JNIEnv *env, jobject instance, jobject bitmap) {

    AndroidBitmapInfo info;
    void *pixels;

    CV_Assert(AndroidBitmap_getInfo(env, bitmap, &info) >= 0);
    CV_Assert(info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
              info.format == ANDROID_BITMAP_FORMAT_RGB_565);
    CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
    CV_Assert(pixels);
    if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        Mat temp(info.height, info.width, CV_8UC4, pixels);
        GaussianBlur(temp,temp,Size(3,3),0,0);//高斯去噪
        Mat gray;
        cvtColor(temp,gray,CV_RGBA2GRAY);//转换为灰度图
        Mat mask;
        Canny(gray,mask,20,200);//边缘提取
        Mat backGround = Mat(temp.size(),temp.type(),Scalar(255,255,255));//背景为白色
        Mat foreGround = Mat(temp.size(),temp.type(),Scalar(255,0,0));//前景色
        foreGround.copyTo(backGround,mask);
        backGround.copyTo(temp);//将最终图像赋给bitmap
        //释放资源
        gray.release();
        mask.release();
        backGround.release();
        foreGround.release();
    }
    AndroidBitmap_unlockPixels(env, bitmap);

}extern "C"
JNIEXPORT void JNICALL
Java_com_itfitness_opencvrehearse_demos_remap_RemapActivity_remap(JNIEnv *env, jobject instance,
                                                                  jobject bitmap, jint type) {

    AndroidBitmapInfo info;
    void *pixels;

    CV_Assert(AndroidBitmap_getInfo(env, bitmap, &info) >= 0);
    CV_Assert(info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
              info.format == ANDROID_BITMAP_FORMAT_RGB_565);
    CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
    CV_Assert(pixels);
    if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        Mat temp(info.height, info.width, CV_8UC4, pixels);
        Mat dst = Mat(temp.size(),temp.type());
        int width = temp.cols;
        int height = temp.rows;
        for (int row = 0; row < height;row++) {
            for (int col = 0; col < width;col++) {
                int b = temp.at<Vec4b>(row, col)[0];
                int g = temp.at<Vec4b>(row, col)[1];
                int r = temp.at<Vec4b>(row, col)[2];
                int a = temp.at<Vec4b>(row, col)[3];
                switch (type){
                    case 1:
                        //X轴
                        dst.at<Vec4b>(row, width-1 - col)[0] = b;
                        dst.at<Vec4b>(row, width-1 - col)[1] = g;
                        dst.at<Vec4b>(row, width-1 - col)[2] = r;
                        dst.at<Vec4b>(row, width-1 - col)[3] = a;
                        break;
                    case 2:
                        //Y轴
                        dst.at<Vec4b>(height - 1 - row, col)[0] = b;
                        dst.at<Vec4b>(height - 1 - row, col)[1] = g;
                        dst.at<Vec4b>(height - 1 - row, col)[2] = r;
                        dst.at<Vec4b>(height - 1 - row, col)[3] = a;
                        break;
                    case 3:
                        //XY轴
                        dst.at<Vec4b>(height - 1 - row, width - 1 - col)[0] = b;
                        dst.at<Vec4b>(height - 1 - row, width - 1 - col)[1] = g;
                        dst.at<Vec4b>(height - 1 - row, width - 1 - col)[2] = r;
                        dst.at<Vec4b>(height - 1 - row, width - 1 - col)[3] = a;
                        break;
                }
            }

        }
        dst.copyTo(temp);
        dst.release();
    }
    AndroidBitmap_unlockPixels(env, bitmap);

}