package com.example.baiduditu.view

import android.Manifest
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import baidumapsdk.demo.R
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(),
        SensorEventListener,
        BDLocationListener,
        EasyPermissions.PermissionCallbacks {

    private val perms = arrayOf(Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA)


    private var TAG: String = "MainActivity"

    // 定位相关
    private var mLocClient: LocationClient? = null
    private var myListener: MyLocationListenner = MyLocationListenner()
    private var mCurrentMode: MyLocationConfiguration.LocationMode? = null
    internal var mCurrentMarker: BitmapDescriptor? = null
    private val accuracyCircleFillColor = -0x55000078
    private val accuracyCircleStrokeColor = -0x55ff0100
    private var mSensorManager: SensorManager? = null
    private var currentLastX: Double = 0.0
    private var mCurrentDirection = 0
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var mCurrentAccracy: Float = 0.toFloat()

    private var mMapView: MapView? = null
    private var mBaiduMap: BaiduMap? = null


    private var isFirstLoc = true // 是否首次定位
    private var locData: MyLocationData? = null
    private val direction: Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        premissionRequired()
        mMapView = bmapView

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL

        mBaiduMap = mMapView?.map
        mBaiduMap?.isMyLocationEnabled = true
        mLocClient = LocationClient(this)
        mLocClient?.registerLocationListener(this)

        var option = LocationClientOption()
        option.openGps = true
        option.coorType = "bd0911"
        option.scanSpan = 1000
        mLocClient?.locOption = option
        mLocClient?.start()

        mBaiduMap?.setMyLocationConfigeration(MyLocationConfiguration(
                mCurrentMode, true, null))

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mBaiduMap?.setMyLocationConfiguration(MyLocationConfiguration(
                mCurrentMode, true, null))
        val builder1 = MapStatus.Builder()
        builder1.overlook(0f)
        mBaiduMap?.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()))

    }

    override fun onPause() {
        mMapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mMapView?.onResume()
        super.onResume()
        mSensorManager?.registerListener(this,
                mSensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        mSensorManager?.unregisterListener(this)
        super.onStop()
    }

    override fun onDestroy() {
        mLocClient?.stop()
        mBaiduMap?.isMyLocationEnabled = false
        mMapView?.onDestroy()
        mMapView = null
        super.onDestroy()
    }

    fun premissionRequired(){
        if (EasyPermissions.hasPermissions(this, *perms)) {


        } else {
            EasyPermissions.requestPermissions(this, "应用需要使用存储空间,否则应用将不能使用",
                   2007 , *perms)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        if (EasyPermissions.somePermissionPermanentlyDenied(MainActivity@this, perms!!.toList())) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            premissionRequired()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event!!.values[SensorManager.DATA_X].toDouble()
        var result = x - currentLastX
        if (Math.abs(result) > 1.0) {
            mCurrentDirection = x.toInt()
            locData = MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection.toFloat()).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build()
            mBaiduMap?.setMyLocationData(locData)
        }
        currentLastX = x
    }

    override fun onReceiveLocation(location: BDLocation?) {
        if (location == null || mMapView == null) {
            return
        }
        // 开启定位图层
        mCurrentLat = location.latitude
        mCurrentLon = location.longitude
        mCurrentAccracy = location.radius
        locData = MyLocationData.Builder()
                .accuracy(location.radius)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection.toFloat()).latitude(mCurrentLat)
                .longitude(mCurrentLon).build()
        mBaiduMap?.setMyLocationData(locData)
        if (isFirstLoc) {
            isFirstLoc = false
            val ll = LatLng(mCurrentLat,
                    mCurrentLon)
            val builder = MapStatus.Builder()
            builder.target(ll).zoom(18.0f)
            mBaiduMap?.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        }
    }

    /**
     * 定位SDK监听函数
     */
    inner class MyLocationListenner : BDLocationListener {

        override fun onReceiveLocation(location: BDLocation?) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return
            }
            Log.i(TAG, location.city)
            Log.i(TAG, location.cityCode)
            mCurrentLat = location.latitude
            mCurrentLon = location.longitude
            mCurrentAccracy = location.radius
            locData = MyLocationData.Builder()
                    .accuracy(location.radius)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection.toFloat()).latitude(location.latitude)
                    .longitude(location.longitude).build()
            mBaiduMap?.setMyLocationData(locData)
            if (isFirstLoc) {
                isFirstLoc = false
                val ll = LatLng(location.latitude,
                        location.longitude)
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(18.0f)
                mBaiduMap?.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }
        }
    }
}
