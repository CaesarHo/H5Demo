package com.yftech.h5demo.map;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.yftech.h5demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapTypeActivity extends FragmentActivity {

	private SupportMapFragment mapFragment;
	private TencentMap	tencentMap;
	private LinearLayout llLog;
	private CheckBox cbSatellite;
	private CheckBox cbTraffic;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_map_type);
		init();
		bindListener();
	}

	protected void init() {
		FragmentManager fm = getSupportFragmentManager();
		mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMap();
		llLog = (LinearLayout)findViewById(R.id.ll_log);
		cbSatellite = (CheckBox)findViewById(R.id.cb_satellite);
		cbTraffic = (CheckBox)findViewById(R.id.cb_traffic);
		if (tencentMap.getMapType() == TencentMap.MAP_TYPE_SATELLITE) {
			cbSatellite.setChecked(true);
		} else {
			cbSatellite.setChecked(false);
		}
		cbTraffic.setChecked(tencentMap.isTrafficEnabled());
	}
	
	protected void bindListener() {
		OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				switch (buttonView.getId()) {
				case R.id.cb_satellite:
					if (isChecked) {
						tencentMap.setMapType(TencentMap.MAP_TYPE_SATELLITE);
					} else {
						tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
					}
					break;
				case R.id.cb_traffic:
					tencentMap.setTrafficEnabled(isChecked);
					break;

				default:
					break;
				}
			}
		};
		
		cbSatellite.setOnCheckedChangeListener(checkedChangeListener);
		cbTraffic.setOnCheckedChangeListener(checkedChangeListener);
	}
	
	public static void logToView(final Activity activity, final LinearLayout linearLayout, final String log) {
		if (log == null || log.trim().length() == 0) {
			return;
		}
		activity.runOnUiThread(new Runnable() {
			public void run() {
				TextView textView = new TextView(activity);
				textView.setText(log);
				textView.setTextColor(0xff000000);
				linearLayout.addView(textView);
				if (linearLayout.getChildCount() > 8) {
					linearLayout.removeViewAt(0);
				}
			}
		});
	}
}
