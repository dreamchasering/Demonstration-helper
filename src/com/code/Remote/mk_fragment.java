package com.code.Remote;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.ToggleButton;

public class mk_fragment extends Fragment implements OnClickListener {
	private TextView t1, t2;// 页卡头标
	private MyViewPager mPager;// 页面内容
	private ImageView imageView01,startbutton, nextbutton, upbutton;
	private Button button01, button02;
	private ToggleButton toggleButton;
	private List<View> listViews; // Tab页面列表
	private ImageView slideImage;// 下划线图片
	private ImageView start,last,next;
	private int screenW;
	private int ivCursorWidth;// 动画图片宽度
	private int tabWidth;// 每个tab头的宽度
	private int fromX;// 滑块儿的初始位置(距屏幕边缘的距离)
	private int currIndex = 1;// 当前页卡编号

	private static float oldx;// 鼠标旧X轴坐标
	private static float oldy;// 鼠标旧Y轴坐标
	private static float nowx;// 鼠标新X轴坐标
	private static float nowy;// 鼠标新Y轴坐标
	static float sendx = 0;
	static float sendy = 0;
	static float multiplier;// 鼠标转化成电脑所需的乘数
	static int count = 1;

	private SensorManager sm;
	Oldsensoreventlistener oldsensoreventlistener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mk_frame, container, false);
		// 初始化头标及其点击事件
		t1 = (TextView) view.findViewById(R.id.text1);
		t2 = (TextView) view.findViewById(R.id.text2);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));

		// 初始化viewpaper
		mPager = (MyViewPager) view.findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		View v1 = (View) mInflater.inflate(R.layout.keyboard_frame, null);
		View v2 = (View) mInflater.inflate(R.layout.mouse_frame, null);
        
		listViews.add(v1);
		// listViews.add(mInflater.inflate(R.layout.tgcontrol_layout, null));
		listViews.add(v2);
		mPager.setAdapter(new MyPagerAdapter(listViews));
		// 设置进入系统后默认显示的页面
		mPager.setCurrentItem(1);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		/**
		 */
		imageView01 = (ImageView) v2.findViewById(R.id.imageView1);
		button01 = (Button) v2.findViewById(R.id.button1);
		button02 = (Button) v2.findViewById(R.id.button2);
		imageView01.setOnClickListener(this);
		button01.setOnClickListener(this);
		button02.setOnClickListener(this);
		
		start=(ImageView)v1.findViewById(R.id.start);
		next=(ImageView)v1.findViewById(R.id.next);
		last=(ImageView)v1.findViewById(R.id.last);
		start.setOnClickListener(this);
		next.setOnClickListener(this);
		last.setOnClickListener(this);
		
		mk_fragment.multiplier = main_fragment.computery / main_fragment.phonex;
		
		imageView01.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.i("点击", "点下去");
					mk_fragment.oldx = event.getX();
					mk_fragment.oldy = event.getY();

				}
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					mk_fragment.nowx = event.getX();
					mk_fragment.nowy = event.getY();
					sendx = (mk_fragment.nowx - mk_fragment.oldx) * multiplier;
					sendy = (mk_fragment.nowy - mk_fragment.oldy) * multiplier;
					new Thread(new Runnable() {

						@Override
						public void run() {
							new androidsocket(main_fragment.IP,
									main_fragment.port, 10);
						}
					}).start();

					mk_fragment.oldx = mk_fragment.nowx;
					mk_fragment.oldy = mk_fragment.nowy;

				}

				if (event.getAction() == MotionEvent.ACTION_UP) {
					Log.i("点击", "点上来");
				}
				return false;

			}
		});
		/**
		 */
		oldsensoreventlistener = new Oldsensoreventlistener();
		// myListener=new mySensorEventListener();
		sm = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);

		/**
          */
		toggleButton = (ToggleButton) v2.findViewById(R.id.toggleButton);
		toggleButton
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {

							if (ppt_fragment.buttonnotpressed) {
								count = 1;
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
										while (count == 1) {
											try {
												Thread.sleep(10);
											} catch (InterruptedException e1) {
												e1.printStackTrace();
											}
											new androidsocket(main_fragment.IP,
													main_fragment.port, 1);

										}
										ppt_fragment.buttonnotpressed = true;
										// v.setClickable(true);
									}
								}).start();
							}
						} else {

							count = 2;
							sm.unregisterListener(oldsensoreventlistener);
						}
					}
				});


		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView1:
			new Thread(new Runnable() {

				@Override
				public void run() {
					new androidsocket(main_fragment.IP, main_fragment.port, 5);

				}
			}).start();
			break;
		case R.id.button1:
			new Thread(new Runnable() {

				@Override
				public void run() {
					new androidsocket(main_fragment.IP, main_fragment.port, 5);

				}
			}).start();
			break;
		case R.id.button2:
			new Thread(new Runnable() {

				@Override
				public void run() {
					new androidsocket(main_fragment.IP, main_fragment.port, 8);
				}
			}).start();
			break;
		case R.id.start:
			// 添加媒体开始播放功能代码
			new Thread(new Runnable() {
				@Override
				public void run() {
					new androidsocket(main_fragment.IP, main_fragment.port, 13);
				}
			}).start();
			break;
		case R.id.next:
			// 添加前进功能代码
			new Thread(new Runnable() {
				@Override
				public void run() {
					new androidsocket(main_fragment.IP, main_fragment.port, 14);
				}
			}).start();
			break;
		case R.id.last:
			// 添加后退功能代码
			new Thread(new Runnable() {
				@Override
				public void run() {
					new androidsocket(main_fragment.IP, main_fragment.port, 15);
				}
			}).start();
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getActivity().setContentView(R.layout.ppt_frame);
		// InitTextView();
		// InitViewPager();
		InitImageView();
	}

	@Override
	public void onPause() {
		super.onPause();
		count = 2;
		sm.unregisterListener(oldsensoreventlistener);
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * 适配器
	 * 
	 * @author baiyuliang
	 */
	class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		// 获取滑块儿控件
		slideImage = (ImageView) getActivity().findViewById(R.id.cursor);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenW = dm.widthPixels;
		// 获取滑块儿图片宽度
		ivCursorWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.a).getWidth();
		// 每个tab宽度为屏幕宽度/tab数量
		tabWidth = screenW / listViews.size();
		// 如果滑块儿图片宽度超过每个tab的宽度时，将滑块儿宽度设置为与tab宽度相同
		if (ivCursorWidth >= tabWidth) {
			slideImage.getLayoutParams().width = tabWidth;
			ivCursorWidth = tabWidth;
			fromX = 0;
		} else {
			// fromX = (tabWidth - ivCursorWidth) / 2;
			fromX = 0;
		}
		// 设置动画初始位置
		Matrix matrix = new Matrix();

		matrix.postTranslate(fromX + tabWidth, 0);
		// matrix.postTranslate(0, 0);
		// matrix.postTranslate(fromX , 0);
		slideImage.setImageMatrix(matrix);
	}

	/**
	 * 页面切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			// 设置页面切换时动画偏移量
			Animation animation = new TranslateAnimation(tabWidth * currIndex
					- tabWidth, tabWidth * arg0 - tabWidth, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// 图片停在动画结束位置
			animation.setDuration(0);// 设置动画移动毫秒数
			slideImage.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

	}

}
