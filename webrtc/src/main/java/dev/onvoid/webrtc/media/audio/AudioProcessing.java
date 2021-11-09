/*
 * Copyright 2021 Alex Andres
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

package dev.onvoid.webrtc.media.audio;

import dev.onvoid.webrtc.internal.DisposableNativeObject;
import dev.onvoid.webrtc.internal.NativeLoader;

/**
 * Audio Processing provides a collection of voice processing components
 * designed for real-time communications software.
 * <br/>
 * Audio Processing
 * accepts only linear PCM audio data in chunks of 10 ms. The int16 interfaces
 * use interleaved data, while the float interfaces use deinterleaved data.
 *
 * @author Alex Andres
 */
public class AudioProcessing extends DisposableNativeObject {

	static {
		try {
			NativeLoader.loadLibrary("webrtc-java");
		}
		catch (Exception e) {
			throw new RuntimeException("Load library 'webrtc-java' failed", e);
		}
	}


	private final AudioProcessingStats stats = new AudioProcessingStats();


	public AudioProcessing() {
		initialize();
	}

	public native void applyConfig(AudioProcessingConfig config);

	public AudioProcessingStats getStatistics() {
		updateStats();

		return stats;
	}

	public native int ProcessStream(short[] src,
			AudioProcessingStreamConfig inputConfig,
			AudioProcessingStreamConfig outputConfig, short[] dest);

	public native int ProcessStream(float[] src,
			AudioProcessingStreamConfig inputConfig,
			AudioProcessingStreamConfig outputConfig, float[] dest);

	public native int ProcessReverseStream(short[] src,
			AudioProcessingStreamConfig inputConfig,
			AudioProcessingStreamConfig outputConfig, short[] dest);

	public native int ProcessReverseStream(float[] src,
			AudioProcessingStreamConfig inputConfig,
			AudioProcessingStreamConfig outputConfig, float[] dest);

	@Override
	public native void dispose();

	private native void initialize();

	private native void updateStats();

}