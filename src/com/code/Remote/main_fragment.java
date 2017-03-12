package com.code.Remote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class main_fragment extends Fragment {
	public static float Zangle;
	public static float Xangle;
	public static float Yangle;
	private ImageView imagebutton = null;
	private ImageView image;
	private TextView textView1, textView4;
	static Boolean wifiservenotopen = true;
	static Boolean hasreceive = false;
	// static int computerrun=0;
	static String myIP;
	static String IP = "0.0.0.2";// 如果为"0.0.0.0"则无法发出
	static int port = 8686;
	static float computerx = 1366;
	static float computery = 768;
	static float phonex;
	static float phoney;

	Handler handler1;

	// private FrameLayout framelayout=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	private String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.main_frame, container,
				false);
		imagebutton = (ImageView) view.findViewById(R.id.imageView1);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView4 = (TextView) view.findViewById(R.id.textView4);
		image = (ImageView) view.findViewById(R.id.imageView3);

		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) getActivity().getSystemService(
				Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip1 = intToIp(ipAddress);
		myIP = ip1;

		handler1 = new Handler() {
			public void handleMessage(Message message) {
				if (message.what == 1) {
					image = (ImageView) view.findViewById(R.id.imageView3);
					image.setImageResource(R.drawable.succeed);

				} else {
					image = (ImageView) view.findViewById(R.id.imageView3);
					image.setImageResource(R.drawable.fail);

				}
				super.handleMessage(message);
			}

		};
		if (image != null && IP != "0.0.0.2") {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message message1 = new Message();
					message1.what = 1;
					handler1.sendMessage(message1);
				}
			}).start();

		}
		// if (image!=null&& IP != "0.0.0.0") {
		// //image.setImageDrawable(getResources().getDrawable(R.drawable.succeed));
		// //image.invalidate();
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// Message message1 = new Message();
		// message1.what = 1;
		// handler1.sendMessage(message1);
		// }
		// }).start();
		// }
		/**
		 */
		// 以下这五行代码是得到手机的分辨率
		Display display = getActivity().getWindowManager().getDefaultDisplay(); // Activity#getWindowManager()
		Point size = new Point();
		display.getSize(size);
		// 得到手机的宽度
		phonex = size.x;
		// 得到手机的高度
		phoney = size.y;
		/**
		 * 
		 */
		imagebutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 跳到wifi管理界面
				Intent intent = new Intent(
						android.provider.Settings.ACTION_WIFI_SETTINGS);
				startActivity(intent);
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (wifiservenotopen) {
							try {
								wifiservenotopen = false;
								Thread.sleep(2000);
								// 先创建一个DatagramSocket对象，并指定监听的端口
								DatagramSocket phonesoket = new DatagramSocket(
										22221);
								// 定义接收到的byte数组类型
								byte receivebyte[] = new byte[35];
								// 指定将最多收到含35个byte的byte数组，并将其放在phonepacket中
								// 是最多收到35个byte
								DatagramPacket phonepacket = new DatagramPacket(
										receivebyte, receivebyte.length);
								// 端口接受phonepacket包
								phonesoket.receive(phonepacket);
								byte receive[] = null;
								receive = new byte[30];
								// 从phonepacket包中的到byte数组
								receive = phonepacket.getData();
								String receiveString = new String(receive,
										"utf-8");
								String judgeString = receiveString.substring(0,
										5);
								// 判断
								if (judgeString.equals("@cfm#")) {
									hasreceive = true;
								}

								String b = receiveString.substring(5,
										receiveString.length() - 1);
								String c[] = b.split("%");
								String d[] = c[0].split("#");
								IP = d[0];
								computerx = Float.parseFloat(d[1]);
								computery = Float.parseFloat(d[2]);
								new Thread(new Runnable() {

									@Override
									public void run() {
										new androidsocket(main_fragment.IP,
												8686, 9);
										Message message1 = new Message();
										message1.what = 1;
										handler1.sendMessage(message1);
									}
								}).start();
								phonesoket.close();
								wifiservenotopen = true;
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (SocketException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}

						}

					}
				}).start();

			}
		});
		return view;
	}
}
