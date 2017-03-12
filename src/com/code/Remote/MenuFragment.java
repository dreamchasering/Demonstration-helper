package com.code.Remote;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.code.Remote.R;
import com.code.Remote.ppt_fragment;
import com.code.Remote.main_fragment;

public class MenuFragment extends ListFragment {
	
	private Fragment newContent;
	private ListView listView;
	//用于建立一个listview
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		
		adapter.add(new SampleItem("主界面", R.drawable.main));
		adapter.add(new SampleItem("PPT演示", R.drawable.media));
		adapter.add(new SampleItem("无线键鼠", R.drawable.mouse));
		adapter.add(new SampleItem("关于", R.drawable.set));
		
		listView=(ListView)getActivity().findViewById(android.R.id.list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ActionBar actionBar=getActivity().getActionBar();
				if(position==0){
					newContent = new main_fragment();
					if (newContent != null)
						switchFragment(newContent);
					actionBar.setTitle("安卓体感遥控");
				}
				else if(position==1){
					
					newContent=new ppt_fragment();
					if (newContent != null)
						switchFragment(newContent);
					actionBar.setTitle("PPT演示");
				}
				else if (position==2) {
					newContent=new mk_fragment();
					if (newContent != null) {
						switchFragment(newContent);
					}
					actionBar.setTitle("无线键鼠");
				}
				else if(position==3){
					newContent=new settingfragment();
					if(newContent!=null){
						switchFragment(newContent);
					}
					actionBar.setTitle("关于");
				}
				View v=parent.getChildAt(position);
				v.setBackgroundColor(Color.CYAN);
				Log.i("View","   "+ position);
				int a[]=new int[4];
				a[0]=1;
				a[1]=1;
				a[2]=1;
				a[3]=1;
				a[position]=0;
				for(int i=0;i<4;i++){
					if(a[i]==1){
						View v1=parent.getChildAt(i);
						v1.setBackgroundColor(Color.TRANSPARENT);
					}
				}
			}
		});
		setListAdapter(adapter);
		
//		String[] birds = getResources().getStringArray(R.array.birds);
//		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), 
//				android.R.layout.simple_list_item_1, android.R.id.text1, birds);
//		setListAdapter(colorAdapter);
//				
	}
	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}
	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}


	//在此处添加新界面
		// 切换上方视图    the meat of switching the above fragment
		private void switchFragment(Fragment fragment) {
			if (getActivity() == null)
				return;

			if (getActivity() instanceof MainActivity) {
				MainActivity ra = (MainActivity) getActivity();
				ra.switchContent(fragment);
			}
		}
	}	
	
	
	
