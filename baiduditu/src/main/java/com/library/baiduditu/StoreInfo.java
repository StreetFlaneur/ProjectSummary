package com.library.baiduditu;

/**
 * Created by sam on 2018/1/23.
 */

public class StoreInfo {

/*    city":"北京","cityEn":"","cityId":"12801","id":9086,"isSelect":false,"isSelected":false," +
            ""latitude":39.8638,"
    longitude":116.481,"name":" +
            ""北京十里河商场"," +
            ""nameEn":"Beijing Shilihe","number":31,"storeId":"88","timestamp":1516610529329*/
    private String city;
    private String latitude;  //经度
    private String longitude; //纬度
    private String name;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
