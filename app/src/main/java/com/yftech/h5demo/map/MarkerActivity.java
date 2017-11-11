package com.yftech.h5demo.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnInfoWindowClickListener;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnMarkerClickListener;
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnMarkerDragListener;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.yftech.h5demo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MarkerActivity extends FragmentActivity {
	
	private TencentMap tencentMap;
	private boolean isMarkerAdded;

	private Marker beiJingMarker;
	private Marker chengDuMarker;
	private Marker hongkongMarker;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_marker_control);
		init();
		addMarker();
	}
	
	protected void init() {
		FragmentManager fm = getSupportFragmentManager();
		SupportMapFragment map = (SupportMapFragment)fm.findFragmentById(R.id.frag_map);
		tencentMap = map.getMap();
		tencentMap.moveCamera(CameraUpdateFactory.newCameraPosition(new 
				CameraPosition(new LatLng(34.611524,105.058565), 3, 0, 0)));
		tencentMap.getUiSettings().setZoomControlsEnabled(false);
		isMarkerAdded = false;
		Button btnScreenShot = (Button)findViewById(R.id.btn_screen_shot);
		Button btnReset = (Button)findViewById(R.id.btn_reset);
		Button btnClear = (Button)findViewById(R.id.btn_clear);
		OnClickListener onClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_reset:
					if (!isMarkerAdded) {
						addMarker();
					}
					break;
				case R.id.btn_clear:
					tencentMap.clear();
					isMarkerAdded = false;
					break;
				case R.id.btn_screen_shot:
					shotScreen();
					break;

				default:
					break;
				}
			}
		};
		btnReset.setOnClickListener(onClickListener);
		btnClear.setOnClickListener(onClickListener);
		btnScreenShot.setOnClickListener(onClickListener);
	}
	
	protected void shotScreen() {
		Config config = Config.ARGB_8888;
		Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg == null || msg.obj == null) {
					return;
				}
				Bitmap screen = (Bitmap)msg.obj;
				Toast toast = new Toast(MarkerActivity.this);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				ImageView imageView = new ImageView(MarkerActivity.this);
				imageView.setLayoutParams(lp);
				imageView.setImageBitmap(screen);
				LinearLayout ll = new LinearLayout(MarkerActivity.this);
				ll.setLayoutParams(lp);
				ll.setGravity(Gravity.CENTER);
				ll.setPadding(10, 10, 10, 10);
				ll.addView(imageView);
				toast.setMargin(20f, 20f);
				toast.setView(ll);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.show();
			}
		};
		tencentMap.snapshot(new TencentMap.SnapshotReadyCallback() {
			
			@Override
			public void onSnapshotReady(Bitmap arg0) {
				// TODO Auto-generated method stub
				Toast toast = new Toast(MarkerActivity.this);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				ImageView imageView = new ImageView(MarkerActivity.this);
				imageView.setLayoutParams(lp);
				imageView.setImageBitmap(arg0);
				LinearLayout ll = new LinearLayout(MarkerActivity.this);
				ll.setLayoutParams(lp);
				ll.setGravity(Gravity.CENTER);
				ll.setPadding(10, 10, 10, 10);
				ll.addView(imageView);
				toast.setMargin(20f, 20f);
				toast.setView(ll);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}
	
	protected void addMarker() {
		isMarkerAdded = true;
		beiJingMarker = tencentMap.addMarker(new MarkerOptions().
				position(new LatLng(39.906901,116.397972)).
				icon(BitmapDescriptorFactory.defaultMarker()).
				title("北京").
				snippet("DefaultMarker"));
		final Marker shangHaiMarker = tencentMap.addMarker(new MarkerOptions().
				position(new LatLng(31.247241,121.492696)).
				icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).
				title("上海").
				snippet("Click infowindow to change icon"));
		Marker jiNanMarker = tencentMap.addMarker(new MarkerOptions().
				position(new LatLng(36.666574,117.028908)).
				icon(BitmapDescriptorFactory.fromAsset("Performance/Galactic/textures/iron_man.ico")).
				title("济南").
				snippet("icon from assets").
				anchor(0.5f, 1f));
		Marker zhengZhouMarker = tencentMap.addMarker(new MarkerOptions().
				position(new LatLng(34.783726,113.670430)).
				icon(BitmapDescriptorFactory.fromBitmap(
						BitmapFactory.decodeResource(getResources(), R.mipmap.death_star))).
				title("郑州").
				snippet("icon from bitmap").
				anchor(0.5f, 0.5f));
		
		FileOutputStream fos;
		File file = new File(getFilesDir(), "myicon.ico");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.super_man);
		bmp.compress(CompressFormat.PNG, 100, baos);
		try {
			fos = openFileOutput(file.getName(), MODE_PRIVATE);
			fos.write(baos.toByteArray());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chengDuMarker = tencentMap.addMarker(new MarkerOptions().
				position(new LatLng(30.661821,104.066603)).
				icon(BitmapDescriptorFactory.fromFile(file.getName())).
				title("成都").
				snippet("icon from file"));
		Marker wuLuMuQiMarker = tencentMap.addMarker(new MarkerOptions().
				position(new LatLng(43.831910,87.592552)).
				icon(BitmapDescriptorFactory.
						fromPath(getFilesDir() + "/" + file.getName())).
				title("乌鲁木齐").
				snippet("icon from path"));
		hongkongMarker = tencentMap.addMarker(new MarkerOptions().
				position(new LatLng(22.318541,114.174108)).
				icon(BitmapDescriptorFactory.fromResource(R.mipmap.hulk)).
				title("香港").
				snippet("icon from resource"));
		
		final Marker marker = tencentMap.addMarker(new MarkerOptions().
				position(new LatLng(25.046083,121.513000)).
				title("cust infowindow").
				snippet("25.046083,121.513000"));
		
		tencentMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub
				if (arg0.equals(shangHaiMarker)) {
					arg0.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				}
			}

			@Override
			public void onInfoWindowClickLocation(int i, int i1, int i2, int i3) {

			}
		});
		
		tencentMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
		tencentMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onInfoWindowClickLocation(int i, int i1, int i2, int i3) {

			}
		});
		tencentMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				arg0.hideInfoWindow();
				return false;
			}
		});
		
		tencentMap.setOnMarkerDragListener(new OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker arg0) {
				// TODO Auto-generated method stub
				arg0.setSnippet("drag start");
			}
			
			@Override
			public void onMarkerDragEnd(Marker arg0) {
				// TODO Auto-generated method stub
				arg0.setSnippet("drag end");
			}
			
			@Override
			public void onMarkerDrag(Marker arg0) {
				// TODO Auto-generated method stub
				arg0.setSnippet("dragging");
			}
		});
	}

	/** Demonstrates customizing the info window and/or its contents. */
	class CustomInfoWindowAdapter implements TencentMap.InfoWindowAdapter {
		private final RadioGroup mOptions;

		// These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
		// "title" and "snippet".
		private final View mWindow;
		private final View mContents;

		CustomInfoWindowAdapter() {
			mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
			mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
			mOptions = (RadioGroup) findViewById(R.id.custom_info_window_options);
		}

		@Override
		public View getInfoWindow(Marker marker) {
			if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
				// This means that getInfoContents will be called.
				return null;
			}
			render(marker, mWindow);

			if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
				// This means that getInfoContents will be called.
				return null;
			}

			return mWindow;
		}

		@Override
		public View getInfoContents(Marker marker) {
			if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_contents) {
				// This means that the default info contents will be used.
				return null;
			}
			render(marker, mContents);
			return mContents;
		}

		/**
		 * 自定义整个气泡的InfoWindow,按下的状态。
		 *
		 * @param marker 当前要弹出InfoWindow的
		 *            {@link Marker}
		 * @return
		 */
		public View getInfoWindowPressState(Marker marker){
			return null;
		}

		private void render(Marker marker, View view) {
			int badge;
			// Use the equals() method on a Marker to check for equals.  Do not use ==.
			if (marker.equals(beiJingMarker)) {
				badge = R.mipmap.badge_qld;
			} else if (marker.equals(hongkongMarker)) {
				badge = R.mipmap.badge_nsw;
			} else if (marker.equals(chengDuMarker)) {
				badge = R.mipmap.badge_victoria;
			} else {
				badge = 0;
			}
			((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

			String title = marker.getTitle();
			TextView titleUi = ((TextView) view.findViewById(R.id.title));
			if (title != null) {
				// Spannable string allows us to edit the formatting of the text.
				SpannableString titleText = new SpannableString(title);
				titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
				titleUi.setText(titleText);
			} else {
				titleUi.setText("");
			}

			String snippet = marker.getSnippet();
			TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
			if (snippet != null && snippet.length() > 12) {
				SpannableString snippetText = new SpannableString(snippet);
				snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
				snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
				snippetUi.setText(snippetText);
			} else {
				snippetUi.setText("");
			}
		}
	}
}
