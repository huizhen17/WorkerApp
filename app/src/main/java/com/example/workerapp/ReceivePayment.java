package com.example.workerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReceivePayment extends AppCompatActivity {

    TextView mtvOrderName, mtvOrderAmount, mtvOrderService, mtvMyBalance;
    Button mbtnReceived;
    ImageView mivQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_payment);

        mtvOrderName = findViewById(R.id.tvOrderName);
        mtvOrderAmount = findViewById(R.id.tvOrderAmount);
        mtvOrderService = findViewById(R.id.tvOrderService);
        mtvMyBalance = findViewById(R.id.tvMyBalance);
        mbtnReceived = findViewById(R.id.btnReceived);
        mivQrCode = findViewById(R.id.ivQrCode);

        //TODO::Receive bundle : order detail(name,amount,service) + start time
        Bundle bundle = getIntent().getExtras();
        String startTime = bundle.getString("startTime");
        Toast.makeText(ReceivePayment.this,"StartTime " + startTime,Toast.LENGTH_SHORT).show();

    }

    public void btnReceived_onClick(View view) {
        //TODO::Calculate End Time
        //TODO::Calculate Time Differ
        //TODO:: Pass order detail(id,date,amount,service) + service time
        Intent i = new Intent(ReceivePayment.this,CompletePayment.class);
        startActivity(i);
        finish();
    }
}
