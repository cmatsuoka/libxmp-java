#include <jni.h>
#include <xmp.h>

#define METHOD(type,name) JNIEXPORT type JNICALL \
Java_org_helllabs_java_libxmp_Xmp_##name


METHOD(jlong, xmpCreateContext) (JNIEnv *env, jobject obj)
{
        return (jlong)xmp_create_context();
}

METHOD(void, xmpFreeContext) (JNIEnv *env, jobject obj, jlong ctx)
{
        xmp_free_context((xmp_context)ctx);
}

METHOD(jint, xmpLoadModule) (JNIEnv *env, jobject obj, jlong ctx, jstring path)
{
	const char *filename;
	int res;

	filename = (*env)->GetStringUTFChars(env, path, NULL);
	res = xmp_load_module((xmp_context)ctx, (char *)filename);
	(*env)->ReleaseStringUTFChars(env, path, filename);

	return res;
}

METHOD(jboolean, xmpTestModule) (JNIEnv *env, jobject obj, jstring path, jobject info)
{
	const char *filename;
	int res;
	struct xmp_test_info ti;

	filename = (*env)->GetStringUTFChars(env, path, NULL);
	res = xmp_test_module((char *)filename, &ti);
	(*env)->ReleaseStringUTFChars(env, path, filename);

	if (res == 0) {
		if (info != NULL) {
			jclass modInfoClass = (*env)->FindClass(env,
	                        	"org/helllabs/java/libxmp/TestInfo");
			jfieldID field;
	
			if (modInfoClass == NULL)
				return JNI_FALSE;
			
			field = (*env)->GetFieldID(env, modInfoClass, "name",
	                        	"Ljava/lang/String;");
			if (field == NULL)
				return JNI_FALSE;
			(*env)->SetObjectField(env, info, field,
					(*env)->NewStringUTF(env, ti.name));
	
			field = (*env)->GetFieldID(env, modInfoClass, "type",
	                        	"Ljava/lang/String;");
			if (field == NULL)
				return JNI_FALSE;
			(*env)->SetObjectField(env, info, field,
					(*env)->NewStringUTF(env, ti.type));
		}

		return JNI_TRUE;
	}

	return JNI_FALSE;
}

METHOD(void, xmpReleaseModule) (JNIEnv *env, jobject obj, jlong ctx)
{
	xmp_release_module((xmp_context)ctx);
}

METHOD(jint, xmpStartPlayer) (JNIEnv *env, jobject obj, jlong ctx, jint start, jint rate, jint flags)
{
	return xmp_start_player((xmp_context)ctx, rate, flags);
}

METHOD(void, xmpEndPlayer) (JNIEnv *env, jobject obj, jlong ctx)
{
	xmp_end_player((xmp_context)ctx);
}

METHOD(jint, xmpPlayFrame) (JNIEnv *env, jobject obj, jlong ctx)
{
	return xmp_play_frame((xmp_context)ctx);
}

