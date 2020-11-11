package com.example.workerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

public class NavigateTask extends AppCompatActivity implements OnMapReadyCallback {

    MapView mvMapView;
    private GoogleMap gmap;
    TextView mtvOrderNo, mtvOrderTime, mtvOrderName, mtvOrderPhone;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_task);

        mvMapView = findViewById(R.id.mapView);
        mtvOrderNo = findViewById(R.id.tvOrderNo);
        mtvOrderTime = findViewById(R.id.tvMyOrderTime);
        mtvOrderName = findViewById(R.id.tvOrderName);
        mtvOrderPhone = findViewById(R.id.tvOrderPhone);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mvMapView.onCreate(mapViewBundle);
        mvMapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
