package com.example.workerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CompletePayment extends AppCompatActivity {

    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_payment);
    }

    public void btnHome_onClick(View view) {
        status = "complete";

        // set Fragmentclass Arguments
        HomeFragment fragobj = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", "From Activity");
        fragobj.setArguments(bundle);
        //return fragobj;

        Intent intent = new Intent(CompletePayment.this,MainActivity.class);
        intent.putExtra("staus","from activity");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
}
