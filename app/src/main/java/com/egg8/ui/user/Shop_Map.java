package com.egg8.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.ViewGroup;

import com.egg8.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class Shop_Map extends AppCompatActivity {
    public String KakaoMapAPI = "597ce31dc488b12a0330574ec1be4328";
    public MapView mapView;
    public MapPoint mapPoint;
    public LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_map);

        mapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.MapView);
        mapView.setDaumMapApiKey( KakaoMapAPI );
        mapViewContainer.addView(mapView);
        mapView.removeAllPOIItems();
        MapMarker("재일이네집",126.701382169817,37.4789963855682);
    }
    public void MapMarker(String MakerName, double startX, double startY){
        mapPoint = MapPoint.mapPointWithGeoCoord( startY, startX );
        mapView.setMapCenterPoint( mapPoint, true );
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(MakerName);   //마커 글릭시 컨테이너에 담길내용
        marker.setMapPoint(mapPoint);   //위도,경도
        marker.setMarkerType( MapPOIItem.MarkerType.RedPin );   //마커디자인
        marker.setSelectedMarkerType( MapPOIItem.MarkerType.BluePin );
        mapView.addPOIItem(marker);
    }
}