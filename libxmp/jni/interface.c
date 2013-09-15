#include <string.h>
#include <errno.h>
#include <jni.h>
#include <xmp.h>

#define METHOD(type,name) JNIEXPORT type JNICALL \
Java_org_helllabs_java_libxmp_Xmp_##name


/*
 * Native API methods
 */

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
	jclass frameInfoClass;
	jfieldID field;

	xmp_get_frame_info((xmp_context)ctx, &fi);

	frameInfoClass = (*env)->FindClass(env,
                       	"org/helllabs/java/libxmp/FrameInfo");

	field = (*env)->GetFieldID(env, frameInfoClass, "pos", "I");
	(*env)->SetIntField(env, info, field, fi.pos);

	field = (*env)->GetFieldID(env, frameInfoClass, "pattern", "I");
	(*env)->SetIntField(env, info, field, fi.pattern);

	field = (*env)->GetFieldID(env, frameInfoClass, "row", "I");
	(*env)->SetIntField(env, info, field, fi.row);

	field = (*env)->GetFieldID(env, frameInfoClass, "numRows", "I");
	(*env)->SetIntField(env, info, field, fi.num_rows);

	field = (*env)->GetFieldID(env, frameInfoClass, "frame", "I");
	(*env)->SetIntField(env, info, field, fi.frame);

	field = (*env)->GetFieldID(env, frameInfoClass, "speed", "I");
	(*env)->SetIntField(env, info, field, fi.speed);

	field = (*env)->GetFieldID(env, frameInfoClass, "bpm", "I");
	(*env)->SetIntField(env, info, field, fi.bpm);

	field = (*env)->GetFieldID(env, frameInfoClass, "time", "I");
	(*env)->SetIntField(env, info, field, fi.time);

	field = (*env)->GetFieldID(env, frameInfoClass, "totalTime", "I");
	(*env)->SetIntField(env, info, field, fi.total_time);

	field = (*env)->GetFieldID(env, frameInfoClass, "frameTime", "I");
	(*env)->SetIntField(env, info, field, fi.frame_time);

	field = (*env)->GetFieldID(env, frameInfoClass, "bufferSize", "I");
	(*env)->SetIntField(env, info, field, fi.buffer_size);

	field = (*env)->GetFieldID(env, frameInfoClass, "totalSize", "I");
	(*env)->SetIntField(env, info, field, fi.total_size);

	field = (*env)->GetFieldID(env, frameInfoClass, "volume", "I");
	(*env)->SetIntField(env, info, field, fi.volume);

	field = (*env)->GetFieldID(env, frameInfoClass, "loopCount", "I");
	(*env)->SetIntField(env, info, field, fi.loop_count);

	field = (*env)->GetFieldID(env, frameInfoClass, "virtChannels", "I");
	(*env)->SetIntField(env, info, field, fi.virt_channels);

	field = (*env)->GetFieldID(env, frameInfoClass, "virtUsed", "I");
	(*env)->SetIntField(env, info, field, fi.virt_used);

	field = (*env)->GetFieldID(env, frameInfoClass, "sequence", "I");
	(*env)->SetIntField(env, info, field, fi.sequence);

	return info;
}

METHOD(jint, xmpPlayFrame) (JNIEnv *env, jobject obj, jlong ctx)
{
	return xmp_play_frame((xmp_context)ctx);
}

METHOD(void, xmpEndPlayer) (JNIEnv *env, jobject obj, jlong ctx)
{
	xmp_end_player((xmp_context)ctx);
}

METHOD(jobject, xmpGetModuleInfo) (JNIEnv *env, jobject obj, jlong ctx, jobject info)
{
	struct xmp_module_info mi;
	jclass modInfoClass;
	jfieldID field;

	xmp_get_module_info((xmp_context)ctx, &mi);

	modInfoClass = (*env)->FindClass(env,
                       		"org/helllabs/java/libxmp/ModuleInfo");

	field = (*env)->GetFieldID(env, modInfoClass, "md5", "[B");
	//(*env)->SetIntField(env, info, field, mi.pos);

	field = (*env)->GetFieldID(env, modInfoClass, "volBase", "I");
	(*env)->SetIntField(env, info, field, mi.vol_base);

	field = (*env)->GetFieldID(env, modInfoClass,
				"comment", "Ljava/lang/String;");
	(*env)->SetObjectField(env, info, field,
				(*env)->NewStringUTF(env, mi.comment));

	field = (*env)->GetFieldID(env, modInfoClass, "numSequences", "I");
	(*env)->SetIntField(env, info, field, mi.num_sequences);

	return info;
}

/*
 * Helpers
 */

METHOD(int, getErrno) (JNIEnv *env, jobject obj)
{
	return errno;
}

METHOD(jobject, getStrError) (JNIEnv *env, jobject obj, jint err)
{
	char c[128];
	strerror_r(err, c, 128);
	return (*env)->NewStringUTF(env, c);
}

METHOD(void, getModData) (JNIEnv *env, jobject obj, jlong ctx, jobject mod)
{
	struct xmp_module_info mi;
	jclass modInfoClass;
	jfieldID field;

	xmp_get_module_info((xmp_context)ctx, &mi);

	modInfoClass = (*env)->FindClass(env,
                       		"org/helllabs/java/libxmp/Module");

	field = (*env)->GetFieldID(env, modInfoClass, "name",
				"Ljava/lang/String;");
	(*env)->SetObjectField(env, mod, field,
				(*env)->NewStringUTF(env, mi.mod->name));

	field = (*env)->GetFieldID(env, modInfoClass, "type",
				"Ljava/lang/String;");
	(*env)->SetObjectField(env, mod, field,
				(*env)->NewStringUTF(env, mi.mod->type));

	field = (*env)->GetFieldID(env, modInfoClass, "pat", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->pat);

	field = (*env)->GetFieldID(env, modInfoClass, "trk", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->trk);

	field = (*env)->GetFieldID(env, modInfoClass, "chn", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->chn);

	field = (*env)->GetFieldID(env, modInfoClass, "ins", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->ins);

	field = (*env)->GetFieldID(env, modInfoClass, "smp", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->smp);

	field = (*env)->GetFieldID(env, modInfoClass, "spd", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->spd);

	field = (*env)->GetFieldID(env, modInfoClass, "bpm", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->bpm);

	field = (*env)->GetFieldID(env, modInfoClass, "len", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->len);

	field = (*env)->GetFieldID(env, modInfoClass, "rst", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->rst);

	field = (*env)->GetFieldID(env, modInfoClass, "gvl", "I");
	(*env)->SetIntField(env, mod, field, mi.mod->gvl);
}
