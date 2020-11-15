package com.example.workerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CompletePayment extends AppCompatActivity {

    String status;
    FragmentManager fragmentManager;
    TextView mtvMyOrderNo, mtvMyOrderDate, mtvMyServiceTime, mtvMyOrderPrice, mtvMyOrderService;
    String serviceTime,orderID,amount,service,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_payment);

        mtvMyOrderNo = findViewById(R.id.tvMyOrderNo);
        mtvMyOrderDate = findViewById(R.id.tvMyOrderDate);
        mtvMyServiceTime = findViewById(R.id.tvMyServiceTime);
        mtvMyOrderPrice = findViewById(R.id.tvMyOrderPrice);
        mtvMyOrderService = findViewById(R.id.tvMyOrderService);

        Bundle bundle = getIntent().getExtras();
        serviceTime = bundle.getString("serviceTime");
        orderID = bundle.getString("orderID");
        amount = bundle.getString("amount");
        service = bundle.getString("service");
        date = bundle.getString("date");

        mtvMyServiceTime.setText(serviceTime);
        mtvMyOrderNo.setText(orderID);
        mtvMyOrderPrice.setText(amount);
        mtvMyOrderService.setText(service);
        mtvMyOrderDate.setText(date);

    }

    public void btnHome_onClick(View view) {
        status = "complete";

        finish();

        //TODO::Update status in orderDetail

    }
}
