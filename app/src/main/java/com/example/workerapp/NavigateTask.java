package com.example.workerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NavigateTask extends AppCompatActivity implements OnMapReadyCallback {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID = mAuth.getCurrentUser().getUid();
    String orderStatus;
    String time1, time2, serviceTime;
    String orderID = "", name, phone, time, address, amount, service, date;
    String latitude, longitude;
    MapView mvMapView;
    TextView mtvOrderNo, mtvOrderTime, mtvOrderName, mtvOrderPhone, mbtnShareLink,
            metShareableLink, mbtnSendLink, mtvReSubmit, mtvAddress;
    Dialog sendLinkDialog;

    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_task);

        mvMapView = findViewById(R.id.mapView);
        mtvOrderNo = findViewById(R.id.tvOrderNo);
        mtvOrderTime = findViewById(R.id.tvOrderTime);
        mtvOrderName = findViewById(R.id.tvOrderName);
        mtvOrderPhone = findViewById(R.id.tvOrderPhone);
        mtvReSubmit = findViewById(R.id.tvReSubmit);
        mtvAddress = findViewById(R.id.tvAddress);
        mbtnShareLink = findViewById(R.id.btnShareLink);
        sendLinkDialog = new Dialog(this);
        mvMapView.setClickable(true);

        //TODO::Get customer name, phone, address, service, amount
        //TODO::Get booking id, date, time
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            phone = bundle.getString("phone");
            address = bundle.getString("address");
            service = bundle.getString("service");
            amount = bundle.getString("amount");
            time = bundle.getString("time");
            orderID = bundle.getString("orderID");
            date = bundle.getString("date");
        }

        mtvOrderNo.setText(orderID);
        mtvOrderTime.setText(time);
        mtvOrderName.setText(name);
        mtvOrderPhone.setText(phone);
        mtvAddress.setText(address);


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
        latitude = String.valueOf(5.3338433);
        longitude = String.valueOf(100.2771833);
        //TODO::Retreive latitude & longitude from firebase
        LatLng ny = new LatLng(5.3338433, 100.2771833);
        gmap.addMarker(new MarkerOptions().position(ny).title("Customer's House"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(ny, 20F));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void btnShareLink_onClick(View view) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

        displayDialog();

        mtvReSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });

        mbtnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                String startTime = simpleDateFormat.format(calendar.getTime());
                orderStatus = "arrived";
                Toast.makeText(NavigateTask.this, amount + "123", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NavigateTask.this, ReceivePayment.class);
                i.putExtra("startTime", startTime);
                i.putExtra("name", name);
                i.putExtra("amount", amount);
                i.putExtra("service", service);
                i.putExtra("orderID", orderID);
                i.putExtra("date", date);
                startActivity(i);
                finish();
            }
        });
    }

    private void displayDialog() {
        sendLinkDialog.setContentView(R.layout.submit_link_dialog);
        sendLinkDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sendLinkDialog.show();
        sendLinkDialog.setCancelable(false);

        metShareableLink = sendLinkDialog.findViewById(R.id.etShareableLink);
        mbtnSendLink = sendLinkDialog.findViewById(R.id.btnSendLink);

        mbtnSendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (metShareableLink.getText().toString().isEmpty()) {
                    metShareableLink.setError("Please paste the link here");
                    metShareableLink.requestFocus();
                } else {
                    String fullLink = metShareableLink.getText().toString();
                    String link = fullLink.substring(fullLink.indexOf("https"));
                    //TODO::Store link to db
                    Toast.makeText(NavigateTask.this, link, Toast.LENGTH_SHORT).show();
                    mbtnShareLink.setText("I'm Arrived");
                    sendLinkDialog.dismiss();
                    mtvReSubmit.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void ivPhone_onClick(View view) {
        Toast.makeText(NavigateTask.this,"Hihi",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0164587592"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
