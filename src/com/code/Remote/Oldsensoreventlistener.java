package com.code.Remote;

import android.hardware.SensorListener;
import android.hardware.SensorManager;

public class Oldsensoreventlistener implements  SensorListener{

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		if (sensor == SensorManager.SENSOR_ORIENTATION) {// 只检查姿态的变化
			 main_fragment.Zangle = values[0];
			 main_fragment.Xangle = values[1];
			 main_fragment.Yangle = values[2];
		}
	}

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
		
	}

}
