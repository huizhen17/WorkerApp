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
    String serviceTime;

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
        Toast.makeText(this,serviceTime,Toast.LENGTH_SHORT).show();
        mtvMyServiceTime.setText(serviceTime);
    }

    public void btnHome_onClick(View view) {
        status = "complete";

        // set Fragmentclass Arguments
        HomeFragment fragobj = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", "From Activity");
        fragobj.setArguments(bundle);

        //TODO::PROBLEM
       Intent intent = new Intent(CompletePayment.this,MainActivity.class);
       intent.putExtra("status","from activity");
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       finish();
    }
}
