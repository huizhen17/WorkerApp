package com.example.workerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReceivePayment extends AppCompatActivity {

    TextView mtvOrderName, mtvOrderAmount, mtvOrderService, mtvMyBalance;
    Button mbtnReceived;
    ImageView mivQrCode;
    String startTime,mServiceTime,serviceTime,name,amount,service,customerID;
    String orderID, date,time;

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

        Bundle bundle = getIntent().getExtras();
        startTime = bundle.getString("startTime");
        serviceTime = bundle.getString("serviceTime");
        name = bundle.getString("name");
        customerID = bundle.getString("customerID");
        time = bundle.getString("time");
        amount = bundle.getString("amount");
        service = bundle.getString("service");
        date = bundle.getString("date");
        orderID = bundle.getString("orderID");
        mtvOrderName.setText(name);
        mtvOrderAmount.setText(amount);
        mtvOrderService.setText(service);

    }

    public void btnReceived_onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String endTime = simpleDateFormat.format(calendar.getTime());

        try {
            Date d1 = simpleDateFormat.parse(startTime);
            Date d2 = simpleDateFormat.parse(endTime);
            long difference_In_Time = d2.getTime() - d1.getTime();
            long difference_In_Minutes = (difference_In_Time/ (1000 * 60))% 60;
            mServiceTime = String.valueOf(difference_In_Minutes);
            Toast.makeText(ReceivePayment.this,mServiceTime,Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }

        Intent i = new Intent(ReceivePayment.this,CompletePayment.class);
        i.putExtra("serviceTime",mServiceTime);
        i.putExtra("orderID",orderID);
        i.putExtra("amount",amount);
        i.putExtra("service",service);
        i.putExtra("time",time);
        i.putExtra("customerID",customerID);
        i.putExtra("date",date);
        startActivity(i);
        finish();

    }
}
