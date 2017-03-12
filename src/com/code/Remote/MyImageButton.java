package com.code.Remote;
//
//import android.content.Context;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class MyImageButton extends LinearLayout{
//	
//	 private ImageView mButtonImage = null;   
//	    private TextView mButtonText = null;  
//	    private int textSize = 18; 
//	    public MyImageButton(Context context, int intArray){}
//
//
//}


  
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.code.Remote.R;
  
/** 
 * @author jackie 
 * 
 */  
public class MyImageButton extends LinearLayout {  
  
    private ImageView mButtonImage = null;   
    private TextView mButtonText = null;  
    private int textSize = 18;  
      
    /** 
     *  
     * @param context 
     * @param intArray intArray[0] : ImageResourceId;  
     * intArray[1] : textResourceId; intArray[2] : textSize. Other intArray is useless 
     */  
    public MyImageButton(Context context, int... intArray) {   
        super(context);   
       
        // Init instance  
        mButtonImage = new ImageView(context);  
        mButtonText = new TextView(context);  
          
        int len = intArray.length;  
        if (len >= 1) {  
            // Set Image Resource  
            setImageResource(intArray[0]);  
        }  
        if (len >= 2) {  
            // Set Text  
            setText(intArray[1]);  
        }  
        if (len >= 3) {  
            // Change text size  
            textSize = intArray[2];  
        }  
          
        /** Set Child View(ImageView/TextView) properties */  
        // Set Text Size : default 18  
        setTextSize(textSize);  
            // Set ImageView ScaleType : default CENTER_INSIDE(located center, without resize)  
        setImgScaleType(ScaleType.CENTER_INSIDE);  
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 0.5f);  
        // Set ImageView LayoutParams : default half  
        setImgLayoutParams(layoutParams);  
        // Set TextView LayoutParams : default half  
        setTextLayoutParams(layoutParams);  
        // Set Text Gravity : default center  
        setTextGravity(Gravity.CENTER);  
        // Set Text Color : default white  
        setTextColor(0xFFFFFFFF);  
          
        /** Set Father View(LinearLayout) properties */  
        setClickable(true);  
        setFocusable(true);  
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,  
                LayoutParams.MATCH_PARENT, 1);  
//      params.gravity = Gravity.CENTER;  
        // Set Father View Orientation : default fulfill  
        setFatherViewLayoutParams(params);  
        // Set Father View Orientation : default my_img_btn_default  
        setFatherViewBgResource(R.drawable.qq);  
        // Set Father View Orientation : default VERTICAL  
        setFatherViewOrientation(LinearLayout.VERTICAL);  
          
        addView(mButtonImage);   
        addView(mButtonText);   
      }  
  
      /*  
       * setImageResource 
       */   
      public void setImageResource(int resId) {   
        mButtonImage.setImageResource(resId);   
      }   
       
      /*  
       * setText 
       */   
      public void setText(int resId) {   
        mButtonText.setText(resId);   
      }   
       
      public void setText(CharSequence buttonText) {   
        mButtonText.setText(buttonText);   
      }   
       
      /*  
       * setTextColor 
       */   
      public void setTextColor(int color) {   
        mButtonText.setTextColor(color);   
      }  
        
      /*  
       * setTextSize 
       */   
      public void setTextSize(int textSize) {   
        mButtonText.setTextSize(textSize);   
      }  
  
    /** 
     * @param layoutParams the layoutParams to set 
     */  
    public void setImgLayoutParams(LayoutParams layoutParams) {  
        mButtonImage.setLayoutParams(layoutParams);  
    }  
  
        /** 
     * Controls how the image should be resized or moved to match the size of this ImageView 
     *  
     * @param layoutParams the layoutParams to set 
     */  
    public void setImgScaleType(ScaleType scaleType) {  
        mButtonImage.setScaleType(scaleType);  
    }  
      
    /** 
     * @param layoutParams the layoutParams to set 
     */  
    public void setTextLayoutParams(LayoutParams layoutParams) {  
        mButtonText.setLayoutParams(layoutParams);  
    }  
      
    /** 
     * @param gravity the gravity to set 
     */  
    public void setTextGravity(int gravity) {  
        mButtonText.setGravity(gravity);  
    }  
      
    /** 
     * Set Father View LayoutParams. Notice that this method should not be used generally. 
     *  
     * @param params 
     */  
    public void setFatherViewLayoutParams(LayoutParams params) {  
        super.setLayoutParams(params);  
    }  
      
    public void setFatherViewBgResource(int resId) {  
        super.setBackgroundResource(resId);  
    }  
      
    /** 
     * Set orientation of this Linearlayout. Notice that since the default orientation is vertical,  
     * when you use this method to modify the orientation to horizontal , make sure that you  
     * also use {@link #setImgLayoutParams(LayoutParams layoutParams)} and  
     * {@link #setTextLayoutParams(LayoutParams layoutParams)} together, otherwise,  
     * the button can not be displayed normally 
     *  
     * @param orientation 
     */  
    public void setFatherViewOrientation(int orientation) {  
        super.setOrientation(orientation);  
    }  
      
}
