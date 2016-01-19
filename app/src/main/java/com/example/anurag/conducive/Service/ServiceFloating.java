package com.example.anurag.conducive.Service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.anurag.conducive.R;
import com.example.anurag.conducive.ViewPagerSwipe;

import java.util.ArrayList;


public class ServiceFloating extends Service{

//	public static  int ID_NOTIFICATION = 2018;

	public WindowManager windowManager;
	public ImageView chatHead;
	public PopupWindow pwindo;

	boolean mHasDoubleClicked = false;
	long lastPressTime;
	public Boolean _enable = true;

	ArrayList<String> myArray;
	String word;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override 
	public void onCreate() {
		super.onCreate();
		Log.d("Launched","Service");
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData.Item[] item = new ClipData.Item[1];
        ClipboardManager c= (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        c.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                item[0] = clipboard.getPrimaryClip().getItemAt(0);
                if(item[0].getText()!=null)
                    word= item[0].getText().toString();
                Intent intent = new Intent(getApplicationContext(), ViewPagerSwipe.class);
                intent.putExtra("word",word);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                getApplicationContext().startActivity(intent);
            }
        });

		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		chatHead = new ImageView(this);
		chatHead.setImageResource(R.drawable.floating5);
		
		if(prefs.getString("ICON", "floating2").equals("floating3")){
			chatHead.setImageResource(R.drawable.floating3);
		} else if(prefs.getString("ICON", "floating2").equals("floating4")){
			chatHead.setImageResource(R.drawable.floating4);
		} else if(prefs.getString("ICON", "floating2").equals("floating5")){
			chatHead.setImageResource(R.drawable.floating5);
		} else if(prefs.getString("ICON", "floating2").equals("floating5")){
			chatHead.setImageResource(R.drawable.floating2);
		}

		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.START;
		params.x = 0;
		params.y = 100;
        word="";
//        windowManager.addView(trial,params);
        windowManager.addView(chatHead, params);

		try {
			chatHead.setOnTouchListener(new View.OnTouchListener() {
				public WindowManager.LayoutParams paramsF = params;
				public int initialX;
				public int initialY;
				public float initialTouchX;
				public float initialTouchY;

				@Override public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

						// Get current time in nano seconds.
						long pressTime = System.currentTimeMillis();
                        Log.d("Time",String.valueOf(pressTime));

						// If double click...
						if (pressTime - lastPressTime <= 300) {
//							createNotification();
							Intent i = new Intent(getApplicationContext(),ServiceFloating.class);
							ServiceFloating.this.stopService(i);
							mHasDoubleClicked = true;
						}
						else {     // If not double click....
							mHasDoubleClicked = false;
						}
						lastPressTime = pressTime; 
						initialX = paramsF.x;
						initialY = paramsF.y;
						initialTouchX = event.getRawX();
						initialTouchY = event.getRawY();
						break;
					case MotionEvent.ACTION_UP:
						break;
					case MotionEvent.ACTION_MOVE:
						paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
						paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
						windowManager.updateViewLayout(chatHead, paramsF);
						break;
					}
					return false;
				}
			});
		} catch (Exception e) {
		}

		chatHead.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				initiatePopupWindow(chatHead);
				_enable = false;
				ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData.Item item= clipboard.getPrimaryClip().getItemAt(0);
				word=item.getText().toString();
				Intent intent = new Intent(getApplicationContext(), ViewPagerSwipe.class);
					intent.putExtra("word",word);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				getApplicationContext().startActivity(intent);
			}
		});

	}
	public boolean windowOpen=false;
	public void initiatePopupWindow(View anchor) {
//		RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_main2, null);
//		RelativeLayout container = (RelativeLayout) root.findViewById(R.id.base_popup_layout);
////		PopupWindow popupWindow = new PopupWindow(root,400,800);
////		popupWindow.showAtLocation(anchor,Gravity.BOTTOM,400,400);
//
//		WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
//				WindowManager.LayoutParams.MATCH_PARENT, 400, 10, 10,
//				WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
//						WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//				PixelFormat.TRANSLUCENT);
//		mParams.gravity = Gravity.CENTER;
//		mParams.setTitle("Window test");
//		if(!windowOpen) {
//			WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//			mWindowManager.addView(root, mParams);
//			windowOpen=true;
//		}
//		else{
//			WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//			mWindowManager.removeView();
//			windowOpen=false;
//		}

	}
//
//	public void createNotification(){
//		Intent notificationIntent = new Intent(getApplicationContext(), ServiceFloating.class);
//		PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, notificationIntent, 0);
//
//		Notification notification = new Notification(R.drawable.floating2, "Click to start launcher",System.currentTimeMillis());
////		notification.setLatestEventInfo(getApplicationContext(), "Start launcher Click to start launcher", pendingIntent);
//		notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT;
//
//		NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(ID_NOTIFICATION,notification);
//	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (chatHead != null) windowManager.removeView(chatHead);
	}

}