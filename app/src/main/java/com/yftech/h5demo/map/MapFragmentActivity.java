package com.yftech.h5demo.map;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.yftech.h5demo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MapFragmentActivity extends FragmentActivity {

	private SupportMapFragment supportMapFragment;
	private CustMapFragment custMapFragment;
	FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_in_fragment);

		initView();
	}

	private void initView() {
		fragmentManager = getSupportFragmentManager();
		
		supportMapFragment = SupportMapFragment.newInstance(this);
		Marker marker1 = supportMapFragment.getMap()
				.addMarker(new MarkerOptions()
				.position(new LatLng(39.984129,116.307696))
				.title("SupportMapFragment"));
		marker1.showInfoWindow();
//		fragmentManager.beginTransaction().add(R.id.ll_frag_root, supportMapFragment).commit();

		custMapFragment = CustMapFragment.newInstance(this);
		Marker marker2 = custMapFragment.getMap()
				.addMarker(new MarkerOptions()
				.position(new LatLng(39.984129,116.307696))
				.title("CustMapFragment"));
		marker2.showInfoWindow();
		fragmentManager.beginTransaction().add(R.id.ll_frag_root, custMapFragment).commit();
	}
}
