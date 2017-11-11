package com.yftech.h5demo.map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Polyline;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;
import com.yftech.h5demo.R;

import java.util.ArrayList;
import java.util.List;

public class PolylineActivity extends FragmentActivity {

	private TencentMap tencentMap;
	private Polyline polyline;
	private Button btnAddPolyline;
	private Button btnRemovePolyline;
	private SeekBar sbWidth;
	private SeekBar sbAlpha;
	private SeekBar sbHue;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_polyline);
		init();
		bindListener();
	}

	protected void init() {
		FragmentManager fm = getSupportFragmentManager();
		SupportMapFragment mapFragment = 
				(SupportMapFragment)fm.findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMap();
		tencentMap.moveCamera(CameraUpdateFactory.zoomTo(10f));
		btnAddPolyline = (Button)findViewById(R.id.btn_add_polyline);
		btnRemovePolyline = (Button)findViewById(R.id.btn_remove_polyline);
		sbWidth = (SeekBar)findViewById(R.id.sb_width);
		sbAlpha = (SeekBar)findViewById(R.id.sb_transparency);
		sbHue = (SeekBar)findViewById(R.id.sb_hue);
	}

	protected void bindListener() {
		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_add_polyline:
					addPolyLine();
					break;
				case R.id.btn_remove_polyline:
					if (polyline != null) {
						polyline.remove();
						polyline = null;
					}
					break;

				default:
					break;
				}
			}
		};

		btnAddPolyline.setOnClickListener(onClickListener);
		btnRemovePolyline.setOnClickListener(onClickListener);

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
				if (polyline == null) {
					return;
				}
				switch (seekBar.getId()) {
					case R.id.sb_width:
						polyline.setWidth(progress);
						break;
					case R.id.sb_transparency:
						polyline.setColor(Color.HSVToColor(
								progress,
								new float[]{sbHue.getProgress(), 1f, 1f}));
						break;
					case R.id.sb_hue:
						polyline.setColor(Color.HSVToColor(
								sbAlpha.getProgress(),
								new float[]{progress, 1f, 1f}));
						break;
					default:
						break;
				}
			}
		};

		sbAlpha.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbWidth.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbHue.setOnSeekBarChangeListener(onSeekBarChangeListener);
	}

	protected void addPolyLine() {
		if (polyline != null) {
			return;
		}
		List<LatLng> latLngs = new ArrayList<LatLng>();
		latLngs.add(new LatLng(39.999391,116.135972));
		latLngs.add(new LatLng(39.898323,116.057694));
		latLngs.add(new LatLng(39.900430,116.265061));
		latLngs.add(new LatLng(39.955192,116.140092));
		polyline = tencentMap.addPolyline(new PolylineOptions().
				addAll(latLngs).
				color(Color.HSVToColor(
						sbAlpha.getProgress(),
						new float[]{sbHue.getProgress(), 1f, 1f})).
				//是否在线上显示箭头，默认不显示。多用于导航线绘制
				arrow(false).
				width(5f));
	}
}
