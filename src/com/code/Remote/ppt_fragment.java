package com.code.Remote;

//import javax.security.auth.callback.Callback;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.code.Remote.R;

public class ppt_fragment extends Fragment {
    static Boolean buttonnotpressed = true;
	static float Zangle;
	static float Xangle;
	static float Yangle;
	static int count = 1;
	private SensorManager sm;

	Oldsensoreventlistener oldsensoreventlistener;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ppt_frame, container, false);
		view.findViewById(R.id.threecolor_red_view).setOnClickListener(
				mListener);
		view.findViewById(R.id.threecolor_green_view).setOnClickListener(
				mListener);
		view.findViewById(R.id.threecolor_blue_view).setOnClickListener(
				mListener);

		view.findViewById(R.id.threecolor_yellow_view).setOnClickListener(
				mListener);
		oldsensoreventlistener = new Oldsensoreventlistener();
		// myListener=new mySensorEventListener();
		sm = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);

		return view;
	}

	private OnClickListener mListener = new OnClickListener() {

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(final View v) {
			if (v.getId() == R.id.threecolor_red_view) {
				new Thread(new Runnable() {
					public void run() {
							new androidsocket(main_fragment.IP,
									main_fragment.port, 15);
					}
				}).start();
				
			}
			else if (v.getId() == R.id.threecolor_green_view) {
				new Thread(new Runnable() {
					public void run() {
							new androidsocket(main_fragment.IP,
									main_fragment.port, 14);
					}
				}).start();
			}
			else if (v.getId() == R.id.threecolor_yellow_view) {
				Toast.makeText(getActivity(), "停止控制", Toast.LENGTH_SHORT)
						.show();
				/**
				 * 停止控制的代码
				 */
				ppt_fragment.count = 2;
				sm.unregisterListener(oldsensoreventlistener);

			}
			else if (v.getId() == R.id.threecolor_blue_view) {
				Toast.makeText(getActivity(), "开始控制", Toast.LENGTH_SHORT)
						.show();
				/**
				 */
				if (buttonnotpressed) {
					buttonnotpressed = false;
					v.setClickable(false);
					ppt_fragment.count = 1;
					sm.registerListener(oldsensoreventlistener,
							SensorManager.SENSOR_ORIENTATION,
							SensorManager.SENSOR_DELAY_FASTEST);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(450);
							} catch (Exception e) {
								// TODO: handle exception
							}
							new androidsocket(main_fragment.IP,
									main_fragment.port, 2);
							while (ppt_fragment.count == 1) {
								try {
									Thread.sleep(10);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								new androidsocket(main_fragment.IP,
										main_fragment.port, 1);

							}
							ppt_fragment.buttonnotpressed = true;
							v.setClickable(true);
						}
					}).start();
			}  

		}

		}

	};
	public void onPause() {
		super.onPause();
		count = 2;
		sm.unregisterListener(oldsensoreventlistener);
	};
	
 
	

}
