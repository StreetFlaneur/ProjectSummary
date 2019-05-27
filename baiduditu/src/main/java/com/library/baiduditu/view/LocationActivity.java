package com.library.baiduditu.view;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.library.baiduditu.Constants;
import com.library.baiduditu.StoreInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techidea.library.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import baidumapsdk.demo.R;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by sam on 2017/12/13.
 */

public class LocationActivity extends AppCompatActivity
        implements SensorEventListener,
        EasyPermissions.PermissionCallbacks,
        OnGetRoutePlanResultListener {

    BikingRouteResult bikingRouteResult;
    DrivingRouteResult drivingRouteResult;
    WalkingRouteResult walkingRouteResult;

    //    @BindView(R.id.textview_storename)
    TextView textViewStoreName;
    //    @BindView(R.id.textview_distance)
    TextView textViewDistance;

    //    @BindView(R.id.textview_distance_other)
    TextView textViewDistanceOther;

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
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private int mCurrentDirection = 0;

    MapView mMapView;
    BaiduMap mBaiduMap;
    RoutePlanSearch routePlanSearch;
    DrivingRouteOverlay currentDrivingRoute;

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
        String content = "123";


        textViewStoreName = (TextView) findViewById(R.id.textview_storename);
        textViewDistance = (TextView) findViewById(R.id.textview_distance);
        textViewDistanceOther = (TextView) findViewById(R.id.textview_distance_other);
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
                double desLat = Double.parseDouble(lat);
                double desLon = Double.parseDouble(lon);
                double distance = storeDistance(desLat,
                        desLon);
                textViewDistance.setText(StringUtil.roundTwo(String.valueOf(distance)) + " KM");
                loadNav(desLat, desLon);
                return false;
            }
        });

//        mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(enNode))
    }

    //添加城市商城標記
    private void initMark(String cityName) {
        mBaiduMap.clear();
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        int j = 0;
        for (int i = 0; i < storeInfos.size(); i++) {
            StoreInfo storeInfo = storeInfos.get(i);
            if (j == 0) {
                textViewStoreName.setText(storeInfo.getName());
                double desLat = Double.parseDouble(storeInfo.getLatitude());
                double desLon = Double.parseDouble(storeInfo.getLongitude());
                double distance = storeDistance(
                        desLat,
                        desLon);
                textViewDistance.setText(String.valueOf(distance) + " m");
                loadNav(desLat, desLon);
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
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlays(options);
    }


    private void loadNav(double desLat, double desLon) {
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);

        LatLng startPointTest = new LatLng(mCurrentLat, mCurrentLon);
        LatLng endPointTest = new LatLng(desLat, desLon);

        // 设置起终点信息
        PlanNode stNode = PlanNode.withLocation(startPointTest);
        PlanNode enNode = PlanNode.withLocation(endPointTest);

//        if (!NetworkHelper.isNetworkConnected(getContext().getApplicationContext())) {
//            locationError();
//        } else {
        // 实际使用中请对起点终点城市进行正确的设定
        routePlanSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(enNode));
//        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    //路线规划结果
    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            if (currentDrivingRoute != null) {
                currentDrivingRoute.removeFromMap();
            }
            double distance = drivingRouteResult.getRouteLines().get(0).getDistance();
            textViewDistanceOther.setText(String.valueOf(distance / 1000));
            currentDrivingRoute = new DrivingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(currentDrivingRoute);
            currentDrivingRoute.setData(drivingRouteResult.getRouteLines().get(0));
            currentDrivingRoute.addToMap();
//            currentDrivingRoute.zoomToSpan();
        } else {
            Toast.makeText(getApplicationContext(), "路线规划失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    private double storeDistance(double desLat, double desLon) {
        double distance = DistanceUtil.getDistance(new LatLng(mCurrentLat, mCurrentLon), new LatLng(desLat, desLon));
        distance = distance / 1000;
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
