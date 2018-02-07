#include <jni.h>
#include <string>

extern "C"{
    JNIEXPORT jstring JNICALL
    Java_cn_hbu_cs_ndktest_MainActivity_stringFromJNI(
            JNIEnv *env,
            jobject /* this */) {
        std::string hello = "Hello from C++";
        return env->NewStringUTF(hello.c_str());
    }
    JNIEXPORT jstring JNICALL
    Java_cn_hbu_cs_ndktest_MainActivity_stringFromJNI1(
            JNIEnv *env,
    jobject /* this */) {
    std::string hiNdk = "Learning Ndk";
    return env->NewStringUTF(hiNdk.c_str());
    }
}