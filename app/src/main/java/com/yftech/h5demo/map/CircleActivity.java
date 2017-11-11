package com.yftech.h5demo.map;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.Circle;
import com.tencent.tencentmap.mapsdk.maps.model.CircleOptions;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.yftech.h5demo.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CircleActivity extends FragmentActivity {

	private TencentMap tencentMap;
	private Button btnAddCircle;
	private Button btnRemoveCircle;
	private SeekBar sbWidth;
	private SeekBar sbTransparency;
	private SeekBar sbHue;
	private Circle circle;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_circle);
		init();
	}
	
	protected void init() {
		SupportMapFragment mapFragment = (SupportMapFragment) 
				getSupportFragmentManager().findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMap();
		btnAddCircle = (Button)findViewById(R.id.btn_add_circle);
		btnRemoveCircle = (Button)findViewById(R.id.btn_remove_circle);
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
				case R.id.btn_add_circle:
					addCircle();
					break;
				case R.id.btn_remove_circle:
					if (circle != null) {
						circle.remove();
						circle = null;
					}
					break;

				default:
					break;
				}
			}
		};
		
		btnAddCircle.setOnClickListener(onClickListener);
		btnRemoveCircle.setOnClickListener(onClickListener);
		
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
				if (circle == null) {
					return;
				}
				switch (seekBar.getId()) {
				case R.id.sb_hue:
					circle.setFillColor(Color.HSVToColor(
							sbTransparency.getProgress(), 
							new float[]{progress, 1f, 1f}));
					break;
				case R.id.sb_transparency:
					circle.setFillColor(Color.HSVToColor(progress, 
							new float[]{sbHue.getProgress(), 1f, 1f}));
					break;
				case R.id.sb_width:
					circle.setStrokeWidth(progress);
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
	
	protected void addCircle() {
		if (circle != null) {
			return;
		}
		LatLng latLng = new LatLng(39.984059,116.307771);
		circle = tencentMap.addCircle(new CircleOptions().
				center(latLng).
				radius(1000d).
				fillColor(Color.HSVToColor(sbTransparency.getProgress(), 
						new float[]{sbHue.getProgress(), 1f, 1f})).
				strokeColor(0xff000000).
				strokeWidth(sbWidth.getProgress()));
	}
}
