package com.code.Remote;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class helpactivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
		  
		setContentView(R.layout.help_frame);  
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title3);
	}
}