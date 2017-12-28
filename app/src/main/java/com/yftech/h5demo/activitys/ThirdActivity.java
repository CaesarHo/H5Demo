package com.yftech.h5demo.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yftech.h5demo.R;
import com.yftech.h5demo.utils.GPSLocationListener;
import com.yftech.h5demo.utils.GPSLocationManager;
import com.yftech.h5demo.utils.GPSProviderStatus;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lizhenya on 2016/9/12.
 */
public class ThirdActivity extends Activity {
    private TextView text_gps_3;
    private Button btn_gps_3;

    private GPSLocationManager gpsLocationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initData();
    }

    private void initData() {
        gpsLocationManager = GPSLocationManager.getInstances(ThirdActivity.this);

        text_gps_3 = (TextView) findViewById(R.id.text_gps_3);
        btn_gps_3 = (Button) findViewById(R.id.btn_gps_3);
        btn_gps_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启定位
                gpsLocationManager.start(new MyListener());
            }
        });
    }

    class MyListener implements GPSLocationListener {

        @Override
        public void UpdateLocation(Location location) {
            if (location != null) {
                text_gps_3.setText("经度：" + location.getLongitude() + "\n纬度：" + location.getLatitude());
            }
        }

        @Override
        public void UpdateStatus(String provider, int status, Bundle extras) {
            if ("gps" == provider) {
                Toast.makeText(ThirdActivity.this, "定位类型：" + provider, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void UpdateGPSProviderStatus(int gpsStatus) {
            switch (gpsStatus) {
                case GPSProviderStatus.GPS_ENABLED:
                    Toast.makeText(ThirdActivity.this, "GPS开启", Toast.LENGTH_SHORT).show();
                    break;
                case GPSProviderStatus.GPS_DISABLED:
                    Toast.makeText(ThirdActivity.this, "GPS关闭", Toast.LENGTH_SHORT).show();
                    break;
                case GPSProviderStatus.GPS_OUT_OF_SERVICE:
                    Toast.makeText(ThirdActivity.this, "GPS不可用", Toast.LENGTH_SHORT).show();
                    break;
                case GPSProviderStatus.GPS_TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(ThirdActivity.this, "GPS暂时不可用", Toast.LENGTH_SHORT).show();
                    break;
                case GPSProviderStatus.GPS_AVAILABLE:
                    Toast.makeText(ThirdActivity.this, "GPS可用啦", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在onPause()方法终止定位
        gpsLocationManager.stop();
    }
}