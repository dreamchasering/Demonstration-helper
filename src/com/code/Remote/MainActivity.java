package com.code.Remote;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	private Fragment mContent;
	private CanvasTransformer mTransformer;
	private AudioManager mAudioManager;

	@Override
	//
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ReadData()) {
			WriteData();
			Intent intent = new Intent(MainActivity.this, new_client.class);
			this.finish();
			startActivity(intent);
		}
		// 设置标题
		setTitle("安卓体感遥控");
		// 初始化动画
		initAnimation();
		// 设置主视图界面
		setContentView(R.layout.responsive_content_frame);
		// 初始化滑动菜单
		initSlidingMenu(savedInstanceState);
		// 获取AudioManager对象
		mAudioManager = (AudioManager) this
				.getSystemService(Context.AUDIO_SERVICE);
		// 静音处理
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

	}

	// 采用共享参数来判断程序是否第一次启动
	public void WriteData() {
		SharedPreferences mySharedPreferences = getSharedPreferences("client",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("write", "true");
		editor.commit();

	}

	// 采用共享参数来写入数据
	public boolean ReadData() {
		Boolean read = false;
		SharedPreferences sharedPreferences = getSharedPreferences("client",
				Activity.MODE_PRIVATE);
		String name = sharedPreferences.getString("write", "");
		if (name != null && name.equals("true")) {
			read = true;
		}
		return read;
	}

	/**
	 */
	private void initAnimation() {
		mTransformer = new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}

		};
	}

	/**
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 检查当前视图是否包含滑动菜单？？？？？？？？？？
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			// 设置是否从边缘划开
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			// 设置home键
			getActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		// 在这里设置主界面Fragment视图内容
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new main_fragment();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();

		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setBehindScrollScale(0.0f);
		sm.setBehindCanvasTransformer(mTransformer);
	}

	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			// 静音处理
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			new Thread(new Runnable() {
				public void run() {
					new androidsocket(main_fragment.IP, main_fragment.port, 15);
				}
			}).start();
			Toast.makeText(MainActivity.this, "上一页", Toast.LENGTH_SHORT).show();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			// 静音处理
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			new Thread(new Runnable() {
				public void run() {
					new androidsocket(main_fragment.IP, main_fragment.port, 14);
				}
			}).start();
			Toast.makeText(MainActivity.this, "下一页", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

	/**
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true;
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					isExit = false;
				}
			}, 2000);

		} else {
			// 程序结束的时候再把音量调到正常
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			finish();
			System.exit(0);
		}
	}
}
