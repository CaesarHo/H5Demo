package com.yftech.h5demo.map;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.yftech.h5demo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class UiSettingActivity extends FragmentActivity {
	
	private TencentMap tencentMap;
	private UiSettings mapUiSettings;
	
	private CheckBox cbAllGesture;
	private CheckBox cbCompass;
	private CheckBox cbZoomWidget;
	private CheckBox cbLocationWidget;
	private CheckBox cbRotateGesture;
	private CheckBox cbScrollGesture;
	private CheckBox cbTiltGesture;
	private CheckBox cbZoomGesture;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_map_setting);
		init();
		bindListener();
	}
	
	protected void init() {
		FragmentManager fm = getSupportFragmentManager();
		SupportMapFragment mapFragment = 
				(SupportMapFragment)fm.findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMap();
		mapUiSettings = tencentMap.getUiSettings();
		cbAllGesture = (CheckBox)findViewById(R.id.cb_all_gesture);
		cbCompass = (CheckBox)findViewById(R.id.cb_compass);
		cbZoomWidget = (CheckBox)findViewById(R.id.cb_zoom_widget);
		cbLocationWidget = (CheckBox)findViewById(R.id.cb_location_button);
		cbRotateGesture = (CheckBox)findViewById(R.id.cb_rotate_gesture);
		cbScrollGesture = (CheckBox)findViewById(R.id.cb_scroll_gesture);
		cbTiltGesture = (CheckBox)findViewById(R.id.cb_tilt_gesture);
		cbZoomGesture = (CheckBox)findViewById(R.id.cb_zoom_gesture);
		
		cbAllGesture.setChecked(mapUiSettings.isRotateGesturesEnabled() && 
				mapUiSettings.isScrollGesturesEnabled() && 
				mapUiSettings.isTiltGesturesEnabled() &&
				mapUiSettings.isZoomGesturesEnabled());
		cbCompass.setChecked(mapUiSettings.isCompassEnabled());
		cbZoomWidget.setChecked(mapUiSettings.isZoomControlsEnabled());
		cbLocationWidget.setChecked(mapUiSettings.isMyLocationButtonEnabled());
		cbRotateGesture.setChecked(mapUiSettings.isRotateGesturesEnabled());
		cbScrollGesture.setChecked(mapUiSettings.isScrollGesturesEnabled());
		cbTiltGesture.setChecked(mapUiSettings.isTiltGesturesEnabled());
		cbZoomGesture.setChecked(mapUiSettings.isZoomGesturesEnabled());
	}

	protected void bindListener() {
		OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				switch (buttonView.getId()) {
				case R.id.cb_all_gesture:
					mapUiSettings.setAllGesturesEnabled(isChecked);
					cbRotateGesture.setChecked(isChecked);
					cbScrollGesture.setChecked(isChecked);
					cbTiltGesture.setChecked(isChecked);
					cbZoomGesture.setChecked(isChecked);
					break;
				case R.id.cb_compass:
					mapUiSettings.setCompassEnabled(isChecked);
					break;
				case R.id.cb_zoom_widget:
					mapUiSettings.setZoomControlsEnabled(isChecked);
					break;
				case R.id.cb_location_button:
					mapUiSettings.setMyLocationButtonEnabled(isChecked);
					break;
				case R.id.cb_rotate_gesture:
					mapUiSettings.setRotateGesturesEnabled(isChecked);
					break;
				case R.id.cb_scroll_gesture:
					mapUiSettings.setScrollGesturesEnabled(isChecked);
					break;
				case R.id.cb_tilt_gesture:
					mapUiSettings.setTiltGesturesEnabled(isChecked);
					break;
				case R.id.cb_zoom_gesture:
					mapUiSettings.setZoomGesturesEnabled(isChecked);
					break;

				default:
					break;
				}
			}
		};
		
		cbAllGesture.setOnCheckedChangeListener(onCheckedChangeListener);
		cbCompass.setOnCheckedChangeListener(onCheckedChangeListener);
		cbZoomWidget.setOnCheckedChangeListener(onCheckedChangeListener);
		cbLocationWidget.setOnCheckedChangeListener(onCheckedChangeListener);
		cbRotateGesture.setOnCheckedChangeListener(onCheckedChangeListener);
		cbScrollGesture.setOnCheckedChangeListener(onCheckedChangeListener);
		cbTiltGesture.setOnCheckedChangeListener(onCheckedChangeListener);
		cbZoomGesture.setOnCheckedChangeListener(onCheckedChangeListener);
		
	}
}
