package com.isport.sportpool.service;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.Scroller;


public class MyGalleryView extends Gallery {

	private Context context;
	private Gallery gallery;
	public MyGalleryView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public MyGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	
	private void init() {
		gallery = new Gallery(context);

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		//int screenWidth = disp.getWidth();

		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);


	}
	
	@Override
	  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	    setAnimationDuration(600);
	    return super.onScroll(e1, e2, distanceX, distanceY);
	  }

	  @Override
	  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	    float velMax = 2500f;
	    float velMin = 1000f;
	    float velX = Math.abs(velocityX);
	    if (velX > velMax) {
	      velX = velMax;
	    } else if (velX < velMin) {
	      velX = velMin;
	    }
	    velX -= 600;
	    int k = 500000;
	    int speed = (int) Math.floor(1f / velX * k);
	    setAnimationDuration(speed);

	    int kEvent;
	    if (isScrollingLeft(e1, e2)) {
	      // Check if scrolling left
	      kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
	    } else {
	      // Otherwise scrolling right
	      kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
	    }
	    onKeyDown(kEvent, null);

	    return true;
	  }

	  private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2){
		  return e2.getX() > e1.getX();
		}
}
