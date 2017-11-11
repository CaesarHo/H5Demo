package com.yftech.h5demo.map;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.Projection;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnCameraChangeListener;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnMapClickListener;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.yftech.h5demo.R;

public class MapProjectionActivity extends FragmentActivity 
implements OnMapClickListener, OnCameraChangeListener {

	private TencentMap tencentMap;
	private Projection projection;
	private Button btnGetVisibleRegion;
	private CheckBox cbCenterCoordinate;
	private TextView tvCenterCoordinate;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_map_projection);
		init();
		bindListener();
	}

	protected void init() {
		FragmentManager fm = getSupportFragmentManager();
		SupportMapFragment mapFragment = 
				(SupportMapFragment)fm.findFragmentById(R.id.frag_map);
		btnGetVisibleRegion = (Button)findViewById(R.id.btn_get_visible_region);
		cbCenterCoordinate = (CheckBox)findViewById(R.id.cb_center_coor);
		tencentMap = mapFragment.getMap();
		tencentMap.getUiSettings().setZoomControlsEnabled(false);
		projection = tencentMap.getProjection();
	}

	protected void bindListener() {
		tencentMap.setOnMapClickListener(this);
		
		tencentMap.setOnCameraChangeListener(this);
		
		btnGetVisibleRegion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast toast = Toast.makeText(MapProjectionActivity.this, 
						"screen visible farLeft: " + projection.getVisibleRegion().farLeft.toString() 
						+ "\nscreen visible farLeft: " + projection.getVisibleRegion().farRight.toString() 
						+ "\nscreen visible nearLeft: " + projection.getVisibleRegion().nearLeft.toString()
						+ "\nscreen visible nearRight: " + projection.getVisibleRegion().nearRight.toString(),
						Toast.LENGTH_LONG);
				toast.show();
			}
		});
		
		cbCenterCoordinate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					showCenterText();
				} else {
					tvCenterCoordinate.setVisibility(View.GONE);
				}
			}
		});
	}
	
	protected void showCenterText() {
		if (tvCenterCoordinate == null) {
			MapView mapView = tencentMap.getMapView();
			createTextView(mapView);
			setText2Center(mapView);
		} else {
			tvCenterCoordinate.setVisibility(View.VISIBLE);
		}
	}
	
	protected void createTextView(MapView mapView) {
		tvCenterCoordinate = new TextView(this);
		tvCenterCoordinate.setTextSize(16);
		tvCenterCoordinate.setTextColor(Color.BLACK);
		tvCenterCoordinate.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		Drawable drawable = getResources().getDrawable(R.mipmap.red_location);
		drawable.setBounds(0, 0, 
				drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tvCenterCoordinate.setCompoundDrawables(null, null, null, drawable);
		mapView.addView(tvCenterCoordinate);
	}
	
	protected void setText2Center(MapView mapView) {
		int mapWidth = mapView.getMeasuredWidth();
		int mapHeight = mapView.getMeasuredHeight();
		tvCenterCoordinate.setText(
				tencentMap.getCameraPosition().target.toString());
		tvCenterCoordinate.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int tvWidth = tvCenterCoordinate.getMeasuredWidth();
		int tvHeight = tvCenterCoordinate.getMeasuredHeight();
		if (Build.VERSION.SDK_INT > 10) {
			tvCenterCoordinate.setX((mapWidth - tvWidth) / 2);
			tvCenterCoordinate.setY(mapHeight / 2 - tvHeight);
		} else {
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.leftMargin = (mapWidth - tvWidth) / 2;
			lp.topMargin = mapHeight / 2 - tvHeight;
			tvCenterCoordinate.setLayoutParams(lp);
		}
	}

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO Auto-generated method stub
		if (tvCenterCoordinate != null && 
				tvCenterCoordinate.getVisibility() == View.VISIBLE) {
			setText2Center(tencentMap.getMapView());
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		Point point = projection.toScreenLocation(arg0);
		Toast toast = Toast.makeText(MapProjectionActivity.this, 
				"screen location: " + point.toString(), Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public void onCameraChangeFinished(CameraPosition arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
