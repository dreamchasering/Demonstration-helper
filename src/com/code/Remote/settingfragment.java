package com.code.Remote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.code.Remote.R;


public class settingfragment extends ListFragment {
	
//	private Fragment newContent;
	private ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.setting_frame, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		adapter.add(new SampleItem("  版本更新", R.drawable.main));
		adapter.add(new SampleItem("  功能介绍", R.drawable.main));
		adapter.add(new SampleItem("  团队介绍", R.drawable.main));
		adapter.add(new SampleItem("  帮助与反馈", R.drawable.main));
		
		listView=(ListView)getActivity().findViewById(android.R.id.list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		
		setListAdapter(adapter);
		

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
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.raw, null);
			}

			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
//			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}

	
	public void onListItemClick(ListView lv, View v, int position, long id) {
		if(position==0){
			Toast.makeText(getActivity(), "已是最新版本！", 1).show();
		}
		else if(position==1){
			Intent intent =new Intent(getActivity(),AboutActivity.class);
        	startActivity(intent); 
			
		}
		else if(position==2){
			Intent intent =new Intent(getActivity(),teamactivity.class);
        	startActivity(intent); 
			
		}
		else if(position==3){
			Intent intent =new Intent(getActivity(),helpactivity.class);
        	startActivity(intent); 
			
		}
		
	
		}

}
		
	
	
	
