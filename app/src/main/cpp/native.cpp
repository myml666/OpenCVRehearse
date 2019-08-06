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

}