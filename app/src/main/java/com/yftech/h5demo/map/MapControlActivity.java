package com.yftech.h5demo.map;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.CancelableCallback;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnCameraChangeListener;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnCompassClickedListener;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnMapClickListener;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnMapLongClickListener;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.yftech.h5demo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class MapControlActivity extends FragmentActivity implements 
OnCameraChangeListener, OnCompassClickedListener, OnMapClickListener, 
OnMapLongClickListener, CancelableCallback {

	private TencentMap tencentMap;
	private LinearLayout llLog;
	private RelativeLayout rlScrollControl;
	private LinearLayout llCustZoom;
	//	private SlidingDrawer slidingDrawer;
	private CheckBox cbScrollBy;
	private CheckBox cbCustZoom;
	private CheckBox cbLog;
	private CheckBox cbMapAnimation;
	private Spinner spMarginRateType;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_map_control);
		init();
		bindListener();
	}

	protected void init() {
		llLog = (LinearLayout) findViewById(R.id.ll_log);
		rlScrollControl = (RelativeLayout) findViewById(R.id.rl_scroll_control);
		llCustZoom = (LinearLayout) findViewById(R.id.ll_cust_zoom);
		//slidingDrawer = (SlidingDrawer)findViewById(R.id.sliding_drawer);
		cbScrollBy = (CheckBox) findViewById(R.id.cb_scroll_by);
		cbCustZoom = (CheckBox) findViewById(R.id.cb_cust_zoom);
		cbLog = (CheckBox) findViewById(R.id.cb_log);
		cbMapAnimation = (CheckBox) findViewById(R.id.cb_map_animation);

		FragmentManager fm = getSupportFragmentManager();
		SupportMapFragment mapFragment = 
				(SupportMapFragment) fm.findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMap();
		tencentMap.getUiSettings().setZoomControlsEnabled(false);
		
//		spMarginRateType = (Spinner)findViewById(R.id.sp_margin_rate_type);
//		List<String> list = new ArrayList<String>();
//		list.add("左边");
//		list.add("右边");
//		list.add("底边");
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
//				android.R.layout.simple_spinner_item, list);
//		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//		spMarginRateType.setAdapter(adapter);
	}

	protected void bindListener() {
		tencentMap.setOnCameraChangeListener(this);
		tencentMap.setOnCompassClickedListener(this);
		tencentMap.setOnMapClickListener(this);
		tencentMap.setOnMapLongClickListener(this);

		OnCheckedChangeListener onCheckedChangeListener = 
				new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				switch (buttonView.getId()) {
				case R.id.cb_scroll_by:
					if (isChecked) {
						rlScrollControl.setVisibility(View.VISIBLE);
					} else {
						rlScrollControl.setVisibility(View.GONE);
					}
					break;
				case R.id.cb_cust_zoom:
					if (isChecked) {
						llCustZoom.setVisibility(View.VISIBLE);
					} else {
						llCustZoom.setVisibility(View.GONE);
					}
					break;
				case R.id.cb_log:
					if (isChecked) {
						llLog.setVisibility(View.VISIBLE);
					} else {
						llLog.setVisibility(View.GONE);
					}
					break;

				default:
					break;
				}
			}
		};
		cbCustZoom.setOnCheckedChangeListener(onCheckedChangeListener);
		cbScrollBy.setOnCheckedChangeListener(onCheckedChangeListener);
		cbLog.setOnCheckedChangeListener(onCheckedChangeListener);
	}

	public void onUpClicked(View view) {
		tencentMap.moveCamera(CameraUpdateFactory.scrollBy(0, -100));
	}

	public void onDownClicked(View view) {
		tencentMap.moveCamera(CameraUpdateFactory.scrollBy(0, 100));
	}

	public void onRightClicked(View view) {
		tencentMap.moveCamera(CameraUpdateFactory.scrollBy(100, 0));
	}

	public void onLeftClicked(View view) {
		tencentMap.moveCamera(CameraUpdateFactory.scrollBy(-100, 0));
	}

	public void onZoomOutClicked(View view) {
		tencentMap.moveCamera(CameraUpdateFactory.zoomOut());
	}

	public void onZoomInClicked(View view) {
		tencentMap.moveCamera(CameraUpdateFactory.zoomIn());
	}

	public void onAnimatToYinkeClicked(View view) {
		//		LatLng yinke = new LatLng(39.981840,116.306420);
		CameraUpdate cameraYinke = 
				CameraUpdateFactory.newCameraPosition(new CameraPosition(
				new LatLng(39.981840,116.306420), 19, 0f, 0f));
		if (cbMapAnimation.isChecked()) {
			tencentMap.animateCamera(cameraYinke, this);
		} else {
			tencentMap.moveCamera(cameraYinke);
		}
	}

	public void onAnimatToSigemaClicked(View view) {
		//		LatLng sigma = new LatLng(39.977290,116.337000);
		CameraUpdate cameraSigma = 
				CameraUpdateFactory.newCameraPosition(new CameraPosition(
						new LatLng(39.977290,116.337000), 19, 45f, 45f));
		if (cbMapAnimation.isChecked()) {
			tencentMap.animateCamera(cameraSigma, this);
		} else {
			tencentMap.moveCamera(cameraSigma);
		}
	}

	public void onStopAnimationClicked(View view) {
		tencentMap.stopAnimation();
	}

//	public void onSetLogoMarginClicked(View view) {
//		EditText etBottomMargin = (EditText)findViewById(R.id.et_bottom_margin);
//		EditText etLeftMargin = (EditText)findViewById(R.id.et_letf_margin);
//		if (etBottomMargin.getText().toString().trim().length() != 0) {
//			tencentMap.set.setLogoBottomMargin(
//					Integer.parseInt(etBottomMargin.getText().toString()));
//		}
//		if (etLeftMargin.getText().toString().trim().length() != 0) {
//			tencentMap.setLogoLeftMargin(
//					Integer.parseInt(etLeftMargin.getText().toString()));
//		}
//	}
	
//	public void onSetLogoMarginRateClicked(View view) {
//		int type = spMarginRateType.getSelectedItemPosition();
//		EditText etMarginRate = (EditText)findViewById(R.id.et_margin_rate);
//		if (etMarginRate.getText().toString().trim().length() == 0) {
//			return;
//		}
//		float rate = Float.valueOf(etMarginRate.getText().toString());
//		if (rate < 0) {
//			rate = 0;
//		}
//		if (rate > 1) {
//			rate = 1;
//		}
//		tencentMap.setLogoMarginRate(type, rate);
//	}

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO Auto-generated method stub
		MapTypeActivity.logToView(this, llLog, "On Camera Change!");
	}

	@Override
	public void onCompassClicked() {
		// TODO Auto-generated method stub
		MapTypeActivity.logToView(this, llLog, "On Compass Clicked!");
	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub
		MapTypeActivity.logToView(this, llLog, "On Map Long Click!");
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		MapTypeActivity.logToView(this, llLog, "On Map Click!");
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		MapTypeActivity.logToView(this, llLog, "动画取消");
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		MapTypeActivity.logToView(this, llLog, "动画结束");
	}

	@Override
	public void onCameraChangeFinished(CameraPosition arg0) {
		// TODO Auto-generated method stub
		MapTypeActivity.logToView(this, llLog, "camera change finished");
	}
}
