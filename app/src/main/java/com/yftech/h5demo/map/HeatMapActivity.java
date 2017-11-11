package com.yftech.h5demo.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.HeatDataNode;
import com.tencent.tencentmap.mapsdk.maps.model.HeatOverlay;
import com.tencent.tencentmap.mapsdk.maps.model.HeatOverlayOptions;
import com.tencent.tencentmap.mapsdk.maps.model.HeatOverlayOptions.IColorMapper;
import com.tencent.tencentmap.mapsdk.maps.model.HeatOverlayOptions.OnHeatMapReadyListener;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.yftech.h5demo.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HeatMapActivity extends FragmentActivity {

	private TencentMap tencentMap;
	private HeatOverlay heatOverlay;
	private Button btnAddHeatOverlay;
	private Button btnRemoveHeatOverlay;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_heat_map);
		init();
	}

	protected void init() {
		FragmentManager fm = getSupportFragmentManager();
		SupportMapFragment mapFragment = 
				(SupportMapFragment) fm.findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMap();
		btnAddHeatOverlay = (Button)findViewById(R.id.btn_add_heat_overlay);
		btnRemoveHeatOverlay = (Button)findViewById(R.id.btn_remove_heat_overlay);
		btnAddHeatOverlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (heatOverlay == null) {
					initHeatMapOverlay();
				} else {
					Toast.makeText(HeatMapActivity.this, 
							"热力图已添加，请等待热力图初始化完成", Toast.LENGTH_SHORT).show();
				}
			}
		});
		btnRemoveHeatOverlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				heatOverlay.remove();
				heatOverlay = null;
				Toast.makeText(HeatMapActivity.this, 
						"热力图移除成功", Toast.LENGTH_SHORT).show();
			}
		});
	}

	protected void initHeatMapOverlay() {
		try {
			ArrayList<HeatDataNode> nodes = new ArrayList<HeatDataNode>();
			BufferedReader reader = new BufferedReader(new 
					InputStreamReader((getResources().getAssets().open("datac"))));
			String line;
			String[] lines;
			while ((line = reader.readLine()) != null) {
				lines = line.split("\t");
				LatLng latlng = new LatLng(Double.parseDouble(lines[1]), 
						Double.parseDouble(lines[0]));
				nodes.add(new HeatDataNode(latlng, Double.parseDouble(lines[2])));
			}
			
			HeatOverlayOptions heatOverlayOptions = new HeatOverlayOptions();
			heatOverlayOptions.nodes(nodes).
				radius(18). // 半径，单位是像素，这个数值越大运算量越大，默认值为18，建议设置在18-30之间)
				colorMapper(new DidiColorMapper()).
				onHeatMapReadyListener(new OnHeatMapReadyListener() {
					
					@Override
					public void onHeatMapReady() {
						// TODO Auto-generated method stub
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(HeatMapActivity.this, 
										"热力图数据准备完毕", Toast.LENGTH_SHORT).show();
							}
						});
						
					}
				});
			
			heatOverlay = tencentMap.addHeatOverlay(heatOverlayOptions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//滴滴配色方案
	class DidiColorMapper implements IColorMapper {

		@Override
		public int colorForValue(double arg0) {
			// TODO Auto-generated method stub
			int alpha, red, green, blue;
			if (arg0 > 1) {
				arg0 = 1;
			}
			arg0 = Math.sqrt(arg0);
			float a = 20000;
			red = 255;
			green = 119;
			blue = 3;
			if (arg0 > 0.7) {
				green = 78;
				blue = 1;
			} 
			if (arg0 > 0.6) {
				alpha = (int)(a * Math.pow(arg0 - 0.7, 3) + 240);
			} else if (arg0 > 0.4) {
				alpha = (int)(a * Math.pow(arg0 - 0.5, 3) + 200);
			} else if (arg0 > 0.2) {
				alpha = (int)(a * Math.pow(arg0 - 0.3, 3) + 160);
			} else {
				alpha = (int)(700 * arg0);
			}
			if (alpha > 255) {
				alpha = 255;
			}
			return Color.argb(alpha, red, green, blue);
		}
		
	}
}
