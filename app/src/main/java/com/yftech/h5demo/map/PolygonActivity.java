package com.yftech.h5demo.map;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Polygon;
import com.tencent.tencentmap.mapsdk.maps.model.PolygonOptions;
import com.yftech.h5demo.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PolygonActivity extends FragmentActivity {
	
	private TencentMap tencentMap;
	private Polygon polygon;
	private Button btnAddPolygon;
	private Button btnRemovePolygon;
	private SeekBar sbWidth;
	private SeekBar sbTransparency;
	private SeekBar sbHue;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_polygon);
		init();
	}
	
	protected void init() {
		SupportMapFragment mapFragment = (SupportMapFragment)
				getSupportFragmentManager().findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMap();
		tencentMap.moveCamera(CameraUpdateFactory.zoomTo(11f));
		btnAddPolygon = (Button)findViewById(R.id.btn_add_polygon);
		btnRemovePolygon = (Button)findViewById(R.id.btn_remove_polygon);
		sbWidth = (SeekBar)findViewById(R.id.sb_width);
		sbTransparency = (SeekBar)findViewById(R.id.sb_transparency);
		sbHue = (SeekBar)findViewById(R.id.sb_hue);
		bindListener();
	}
	
	protected void bindListener() {
		OnClickListener onClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_add_polygon:
					addPolygon();
					break;
				case R.id.btn_remove_polygon:
					if (polygon != null) {
						polygon.remove();
						polygon = null;
					}
					break;

				default:
					break;
				}
			}
		};
		
		btnAddPolygon.setOnClickListener(onClickListener);
		btnRemovePolygon.setOnClickListener(onClickListener);
		
		OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				if (polygon == null) {
					return;
				}
				switch (seekBar.getId()) {
				case R.id.sb_hue:
					polygon.setFillColor(Color.HSVToColor(
							sbTransparency.getProgress(), 
							new float[]{progress, 1f, 1f}));
					break;
				case R.id.sb_transparency:
					polygon.setFillColor(Color.HSVToColor(progress, 
							new float[]{sbHue.getProgress(), 1f, 1f}));
					break;
				case R.id.sb_width:
					polygon.setStrokeWidth(progress);
					break;

				default:
					break;
				}
			}
		};
		
		sbHue.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbTransparency.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbWidth.setOnSeekBarChangeListener(onSeekBarChangeListener);
	}
	
	protected void addPolygon() {
		if (polygon != null) {
			return;
		}
		LatLng[] latLngs = {new LatLng(39.980277,116.305390), 
				new LatLng(39.946595,116.387788), 
				new LatLng(39.985538,116.448212), 
				new LatLng(39.873911,116.379548)};
		polygon = tencentMap.addPolygon(new PolygonOptions().
				add(latLngs).
				fillColor(Color.HSVToColor(sbTransparency.getProgress(), 
						new float[]{sbHue.getProgress(), 1f, 1f})).
				strokeColor(0xff000000).
				strokeWidth(sbWidth.getProgress()));
	}
}
