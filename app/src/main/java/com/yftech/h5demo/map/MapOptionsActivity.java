package com.yftech.h5demo.map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.yftech.h5demo.R;

//options功能暂未完成
public class MapOptionsActivity extends Activity {
	private static MapView mMapView;
	private LinearLayout layoutRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_options);
		init();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (mMapView != null) {
			mMapView.onStart();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mMapView != null) {
			mMapView.onResume();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mMapView != null) {
			mMapView.onPause();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mMapView != null) {
			mMapView.onStop();
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (mMapView != null) {
			mMapView.onRestart();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mMapView != null) {
			mMapView.onDestroy();
			mMapView = null;
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		layoutRoot = (LinearLayout)findViewById(R.id.layout_root);
		Button btnAddMap = (Button)findViewById(R.id.btn_add_map);
		Button btnRemoveMap = (Button)findViewById(R.id.btn_remove_map);
		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
					case R.id.btn_add_map:
						addMap();
						break;
					case R.id.btn_remove_map:
						removeMap();
						break;

					default:
						break;
				}
			}
		};

		btnAddMap.setOnClickListener(onClickListener);
		btnRemoveMap.setOnClickListener(onClickListener);
	}

	protected void addMap() {
		mMapView = new MapView(this);
		mMapView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layoutRoot.addView(mMapView);
		mMapView.onResume();
	}

	protected void removeMap() {
		if (mMapView != null) {
			layoutRoot.removeView(mMapView);
			mMapView.onDestroy();
			mMapView = null;
		}
	}
}
