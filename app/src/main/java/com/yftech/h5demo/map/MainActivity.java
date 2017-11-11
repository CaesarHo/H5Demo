package com.yftech.h5demo.map;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yftech.h5demo.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

	private static final int PERMISSIONS_REQUEST_CODE = 0;

	private final DemoInfo[] demos = {
			new DemoInfo(R.string.demo_label_base_map,
					R.string.demo_desc_base_map, BasicMapActivity.class),
			new DemoInfo(R.string.demo_label_map_options, 
					R.string.demo_desc_map_options, MapOptionsActivity.class),
			new DemoInfo(R.string.demo_label_map_fragment, 
					R.string.demo_desc_map_fragment, MapFragmentActivity.class),
			new DemoInfo(R.string.demo_label_map_type, 
					R.string.demo_desc_map_type, MapTypeActivity.class),
			new DemoInfo(R.string.demo_label_ui_setting, 
					R.string.demo_desc_ui_setting, UiSettingActivity.class),
			new DemoInfo(R.string.demo_label_map_control, 
					R.string.demo_desc_map_control, MapControlActivity.class),
			new DemoInfo(R.string.demo_label_map_polyline, 
					R.string.demo_desc_map_polyline, PolylineActivity.class),
			new DemoInfo(R.string.demo_label_map_polygon, 
					R.string.demo_desc_map_polygon, PolygonActivity.class),
			new DemoInfo(R.string.demo_label_map_circle, 
					R.string.demo_desc_map_circle, CircleActivity.class),
			new DemoInfo(R.string.demo_label_marker, 
					R.string.demo_desc_marker, MarkerActivity.class),
			new DemoInfo(R.string.demo_label_map_location, 
					R.string.demo_desc_map_location, MapLocationActivity.class),
			new DemoInfo(R.string.demo_label_map_projection, 
					R.string.demo_desc_map_projection, MapProjectionActivity.class),
			new DemoInfo(R.string.demo_label_heat_map, 
					R.string.demo_desc_heat_map, HeatMapActivity.class)
					
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setBackgroundColor(0xffffffff);
		DemoListAdapter adapter = new DemoListAdapter();
		setListAdapter(adapter);

		if (Build.VERSION.SDK_INT >= 23) {
			List<String> permissions = new ArrayList<>();
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			}
			if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
					!= PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.READ_PHONE_STATE);
			}
			if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
					!= PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
			}
			if (permissions.size() > 0) {
				requestPermissions(permissions.toArray(
						new String[permissions.size()]), PERMISSIONS_REQUEST_CODE);
			}
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(this, demos[position].demoActivityClass);
		startActivity(intent);
	}

	class DemoListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return demos.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return demos[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(MainActivity.this, 
						R.layout.demo_list_item, null);
				holder = new ViewHolder();
				holder.tvLable = (TextView)convertView.findViewById(R.id.label);
				holder.tvDesc = (TextView)convertView.findViewById(R.id.desc);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}

			holder.tvLable.setText(demos[position].lable);
			holder.tvDesc.setText(demos[position].desc);

			return convertView;
		}


		class ViewHolder {
			TextView tvLable;
			TextView tvDesc;
		}
	}

	class DemoInfo {
		private final int lable;
		private final int desc;
		private final Class<? extends Activity> demoActivityClass;

		public DemoInfo(int lable, int desc, Class<? extends Activity> demoActivityClass) {
			// TODO Auto-generated constructor stub
			this.lable = lable;
			this.desc = desc;
			this.demoActivityClass = demoActivityClass;
		};
	}
}
