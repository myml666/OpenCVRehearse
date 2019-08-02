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

}