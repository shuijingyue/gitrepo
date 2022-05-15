#include <jni.h>
#include <string>
#include <android/log.h>

#define TAG "NativeLib-IndexActivity"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_pro_ply_ui_screen_IndexActivity_stringFromJNI(JNIEnv* env, jobject thiz /* this */) {
    std::string hello = "Hello from C++";

    jclass idxActKlass = env->GetObjectClass(thiz);

    jfieldID valueFieldId = env->GetFieldID(idxActKlass, "value", "I");

    int value = env->GetIntField(thiz, valueFieldId);

    jmethodID jmethodId = env->GetMethodID(idxActKlass, "hello", "()Ljava/lang/String;");

    auto helloString = (jstring) env->CallObjectMethod(thiz, jmethodId);

    LOGI("value: %d", value);

    LOGI("hello: %s", env->GetStringUTFChars(helloString, nullptr));

    jclass javaObjectKlass = env->FindClass("pro/ply/data/bean/JavaObject");
    jobject instance = env->AllocObject(javaObjectKlass);
    jfieldID valueId = env->GetFieldID(javaObjectKlass, "value", "I");
    int v = env->GetIntField(instance, valueId);
    LOGI("instance value: %d", v);

    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jobject JNICALL
Java_pro_ply_ui_screen_IndexActivity_objectFromJNI(JNIEnv *env, jobject thiz) {
    jclass javaObjectKlass = env->FindClass("pro/ply/data/bean/JavaObject");
    return env->AllocObject(javaObjectKlass);
}