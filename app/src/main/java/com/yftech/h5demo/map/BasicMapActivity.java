package com.yftech.h5demo.map;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.yftech.h5demo.R;

public class BasicMapActivity extends Activity {
	
	private MapView mapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_map);
		mapView = (MapView)findViewById(R.id.map);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}
}
