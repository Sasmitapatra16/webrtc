/*
 * Copyright 2019 Alex Andres
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "media/video/VideoDevice.h"
#include "JavaClasses.h"
#include "JavaString.h"
#include "JavaObject.h"
#include "JNI_WebRTC.h"

namespace jni
{
	namespace avdev
	{
		VideoDevice::VideoDevice(std::string name, std::string descriptor) :
			Device(name, descriptor)
		{
		}
	}

	namespace VideoDevice
	{
		JavaLocalRef<jobject> toJavaVideoDevice(JNIEnv * env, const avdev::VideoDevice & device)
		{
			const auto javaClass = JavaClasses::get<JavaVideoDeviceClass>(env);

			jobject obj = env->NewObject(javaClass->cls, javaClass->ctor,
				JavaString::toJava(env, device.getName()).get(),
				JavaString::toJava(env, device.getDescriptor()).get());

			return JavaLocalRef<jobject>(env, obj);
		}

		avdev::VideoDevice toNativeVideoDevice(JNIEnv * env, const JavaRef<jobject> & javaType)
		{
			const auto javaClass = JavaClasses::get<JavaVideoDeviceClass>(env);

			JavaObject obj(env, javaType);

			return avdev::VideoDevice(
				JavaString::toNative(env, obj.getString(javaClass->name)),
				JavaString::toNative(env, obj.getString(javaClass->descriptor))
			);
		}

		JavaVideoDeviceClass::JavaVideoDeviceClass(JNIEnv * env)
		{
			cls = FindClass(env, PKG_VIDEO"VideoDevice");

			ctor = GetMethod(env, cls, "<init>", "(" STRING_SIG STRING_SIG ")V");

			name = GetFieldID(env, cls, "name", STRING_SIG);
			descriptor = GetFieldID(env, cls, "descriptor", STRING_SIG);
		}
	}
}