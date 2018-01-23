package com.example.baiduditu.view;

import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.baiduditu.Constants;
import com.example.baiduditu.StoreInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sam.library.Constant;

import java.util.ArrayList;
import java.util.List;

import baidumapsdk.demo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.baiduditu.Constants.DEF_2PI;
import static com.example.baiduditu.Constants.DEF_PI;
import static com.example.baiduditu.Constants.DEF_PI180;
import static com.example.baiduditu.Constants.DEF_R;

/**
 * Created by sam on 2017/12/13.
 */

public class LocationActivity extends AppCompatActivity
        implements SensorEventListener,
        EasyPermissions.PermissionCallbacks {


    //    @BindView(R.id.textview_storename)
    TextView textViewStoreName;
    //    @BindView(R.id.textview_distance)
    TextView textViewDistance;

    private String[] perms = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    MapView mMapView;
    BaiduMap mBaiduMap;

    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;

    private List<StoreInfo> storeInfos = new ArrayList<>();
    private boolean isLocation = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textViewStoreName = (TextView) findViewById(R.id.textview_storename);
        textViewDistance = (TextView) findViewById(R.id.textview_distance);
        requiredPremission();
        Gson gson = new Gson();
        storeInfos = gson.fromJson(Constants.Citys,
                new TypeToken<List<StoreInfo>>() {
                }.getType());
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;


        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化

        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        final MapStatus.Builder builder1 = new MapStatus.Builder();
        builder1.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));

        mCurrentMarker = null;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        location();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                String storeName = bundle.getString("storename");
                String lat = bundle.getString("lat");
                String lon = bundle.getString("lon");
                Toast.makeText(getApplicationContext(), storeName, Toast.LENGTH_SHORT).show();
                textViewStoreName.setText(storeName);
                double distance = storeDistance(Double.parseDouble(lat),
                        Double.parseDouble(lon));
                textViewDistance.setText(String.valueOf(distance) + " m");
                return false;
            }
        });
    }

    //添加城市商城標記
    private void initMark(String cityName) {
        mBaiduMap.clear();
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        int j = 0;
        for (int i = 0; i < storeInfos.size(); i++) {
            StoreInfo storeInfo = storeInfos.get(i);
            if (storeInfo.getCity().equalsIgnoreCase(cityName)) {
                if (j == 0) {
                    textViewStoreName.setText(storeInfo.getName());
                    double distance = storeDistance(
                            Double.parseDouble(storeInfo.getLatitude()),
                            Double.parseDouble(storeInfo.getLongitude()));
                    textViewDistance.setText(String.valueOf(distance) + " m");
                }
                ++j;
                LatLng point = new LatLng(
                        Double.parseDouble(storeInfo.getLatitude()),
                        Double.parseDouble(storeInfo.getLongitude()));
//构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_arrow);
//构建MarkerOption，用于在地图上添加Marker
                Bundle bundle = new Bundle();
                bundle.putString("city", storeInfo.getCity());
                bundle.putString("lat", storeInfo.getLatitude());
                bundle.putString("lon", storeInfo.getLongitude());
                bundle.putString("storename", storeInfo.getName());
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap).extraInfo(bundle);
                options.add(option);
                Marker marker = (Marker) (mBaiduMap.addOverlay(option));
            }
        }
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlays(options);
    }

    private double storeDistance(double desLat, double desLon) {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = mCurrentLon * DEF_PI180;
        ns1 = mCurrentLat * DEF_PI180;
        ew2 = desLon * DEF_PI180;
        ns2 = desLat * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    private void addMarkToMap() {
        LatLng point = new LatLng(39.963175, 116.400244);

//构建Marker图标

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_arrow);

//构建MarkerOption，用于在地图上添加Marker

        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);

//在地图上添加Marker，并显示

        mBaiduMap.addOverlay(option);
    }

    //162 .so文件放置不對
    //161 option配置錯誤
    private void location() {
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @AfterPermissionGranted(3002)
    private void requiredPremission() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            location();
        } else {
            EasyPermissions.requestPermissions(this, "应用需要使用存储空间,否则应用将不能使用",
                    2007, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            location();
        } else {
            requiredPremission();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        requiredPremission();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            Log.i("location", String.valueOf(location.getLocType()));
            if (location.hasAddr() && !isLocation) {
                isLocation = true;
                mCurrentLat = location.getLatitude();
                mCurrentLon = location.getLongitude();
                mCurrentAccracy = location.getRadius();
                locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentDirection).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    LatLng ll = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                String city = location.getCity();
                city = city.substring(0, city.length() - 1);
                initMark(city);
                mLocClient.stop();
            }
        }

    }

   /* mLocationClient = new LocationClient(getActivity()); // 声明LocationClient类
    //注册监听函数
        mLocationClient.registerLocationListener(new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            currentLat = location.getLatitude();
            currentLng = location.getLongitude();
            Log.d(TAG, "获取用户当前位置。Lat：" + currentLat + "，lng：" + currentLng);
            loadBaiduMapNav();
        }
    });

    LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");
        mLocationClient.setLocOption(option);*/

}
