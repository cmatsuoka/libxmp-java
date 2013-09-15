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
		jclass modInfoClass = (*env)->FindClass(env,
	                        	"org/helllabs/java/libxmp/TestInfo");
		jfieldID field;
	
		if (modInfoClass == NULL)
			return JNI_FALSE;
			
		field = (*env)->GetFieldID(env, modInfoClass, "name",
					"Ljava/lang/String;");
		(*env)->SetObjectField(env, info, field,
					(*env)->NewStringUTF(env, ti.name));

		field = (*env)->GetFieldID(env, modInfoClass, "type",
					"Ljava/lang/String;");
		(*env)->SetObjectField(env, info, field,
					(*env)->NewStringUTF(env, ti.type));

		return JNI_TRUE;
	}

	return JNI_FALSE;
}

METHOD(void, xmpReleaseModule) (JNIEnv *env, jobject obj, jlong ctx)
{
	xmp_release_module((xmp_context)ctx);
}

METHOD(jint, xmpStartPlayer) (JNIEnv *env, jobject obj, jlong ctx, jint rate, jint flags)
{
	return xmp_start_player((xmp_context)ctx, rate, flags);
}

METHOD(jobject, xmpGetFrameInfo) (JNIEnv *env, jobject obj, jlong ctx, jobject info)
{
	struct xmp_frame_info fi;
	jclass modInfoClass;
	jfieldID field;

	xmp_get_frame_info((xmp_context)ctx, &fi);

	modInfoClass = (*env)->FindClass(env,
                       	"org/helllabs/java/libxmp/FrameInfo");

	if (modInfoClass == NULL)
		return JNI_FALSE;
	
	field = (*env)->GetFieldID(env, modInfoClass, "pos", "I");
	(*env)->SetIntField(env, info, field, fi.pos);

	field = (*env)->GetFieldID(env, modInfoClass, "pattern", "I");
	(*env)->SetIntField(env, info, field, fi.pattern);

	field = (*env)->GetFieldID(env, modInfoClass, "row", "I");
	(*env)->SetIntField(env, info, field, fi.row);

	field = (*env)->GetFieldID(env, modInfoClass, "numRows", "I");
	(*env)->SetIntField(env, info, field, fi.num_rows);

	field = (*env)->GetFieldID(env, modInfoClass, "frame", "I");
	(*env)->SetIntField(env, info, field, fi.frame);

	field = (*env)->GetFieldID(env, modInfoClass, "speed", "I");
	(*env)->SetIntField(env, info, field, fi.speed);

	field = (*env)->GetFieldID(env, modInfoClass, "bpm", "I");
	(*env)->SetIntField(env, info, field, fi.bpm);

	field = (*env)->GetFieldID(env, modInfoClass, "time", "I");
	(*env)->SetIntField(env, info, field, fi.time);

	field = (*env)->GetFieldID(env, modInfoClass, "totalTime", "I");
	(*env)->SetIntField(env, info, field, fi.total_time);

	field = (*env)->GetFieldID(env, modInfoClass, "frameTime", "I");
	(*env)->SetIntField(env, info, field, fi.frame_time);

	field = (*env)->GetFieldID(env, modInfoClass, "bufferSize", "I");
	(*env)->SetIntField(env, info, field, fi.buffer_size);

	field = (*env)->GetFieldID(env, modInfoClass, "totalSize", "I");
	(*env)->SetIntField(env, info, field, fi.total_size);

	field = (*env)->GetFieldID(env, modInfoClass, "volume", "I");
	(*env)->SetIntField(env, info, field, fi.volume);

	field = (*env)->GetFieldID(env, modInfoClass, "loopCount", "I");
	(*env)->SetIntField(env, info, field, fi.loop_count);

	field = (*env)->GetFieldID(env, modInfoClass, "virtChannels", "I");
	(*env)->SetIntField(env, info, field, fi.virt_channels);

	field = (*env)->GetFieldID(env, modInfoClass, "virtUsed", "I");
	(*env)->SetIntField(env, info, field, fi.virt_used);

	field = (*env)->GetFieldID(env, modInfoClass, "sequence", "I");
	(*env)->SetIntField(env, info, field, fi.sequence);

	return info;
}

METHOD(void, xmpEndPlayer) (JNIEnv *env, jobject obj, jlong ctx)
{
	xmp_end_player((xmp_context)ctx);
}

METHOD(jint, xmpPlayFrame) (JNIEnv *env, jobject obj, jlong ctx)
{
	return xmp_play_frame((xmp_context)ctx);
}

