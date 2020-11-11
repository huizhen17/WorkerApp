package com.example.workerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
        mvMapView.setClickable(true);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mvMapView.onCreate(mapViewBundle);
        mvMapView.getMapAsync(this);

    }

    @Override
    public void onResume() {
        mvMapView.onResume();
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(20);
        LatLng ny = new LatLng(5.3338433, 100.2771833);
        gmap.addMarker(new MarkerOptions().position(ny).title("Customer's House"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(ny,20F));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void btnShareLink_onClick(View view) {
        //TODO::Display dialog box to shareable link
    }

    public void ivPhone_onClick(View view) {
    }
}
