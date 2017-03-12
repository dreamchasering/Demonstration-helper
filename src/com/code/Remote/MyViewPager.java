package com.code.Remote;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	  
	private boolean isCanScroll = true;  
	  
    public MyViewPager(Context context) {  
        super(context);  
    }  
  
    public MyViewPager(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public void setScanScroll(boolean isCanScroll) {  
        this.isCanScroll = isCanScroll;  
    }  
  
    @Override  
    public void scrollTo(int x, int y) {  
            super.scrollTo(x, y);  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent arg0) {  
        // TODO Auto-generated method stub  
        return false;//super.onTouchEvent(arg0);  
    }  
  
    @Override  
    public void setCurrentItem(int item, boolean smoothScroll) {  
        // TODO Auto-generated method stub  
        super.setCurrentItem(item, smoothScroll);  
    }  
  
    @Override  
    public void setCurrentItem(int item) {  
        // TODO Auto-generated method stub  
        super.setCurrentItem(item);  
    } 
   

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
    return false;
    }
	 

}

