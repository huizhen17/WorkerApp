package com.example.workerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CompletePayment extends AppCompatActivity {

    String status;
    FragmentManager fragmentManager;
    TextView mtvMyOrderNo, mtvMyOrderDate, mtvMyServiceTime, mtvMyOrderPrice, mtvMyOrderService;
    String serviceTime,orderID,amount,service,date,customerID,time;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID=mAuth.getCurrentUser().getUid();

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
        customerID = bundle.getString("customerID");
        orderID = bundle.getString("orderID");
        time = bundle.getString("time");
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
        String orderDateTime = date + " " + time;

        DocumentReference updateReference = db.collection("workerDetail").document(userID);
        updateReference.update("currentStatus","Free").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        //Store data to historyDetail
        DocumentReference documentReference = db.collection("workerDetail").document(userID).collection("workHistory").document(orderID);
        Map<String,Object> historyData = new HashMap<>();
        historyData.put("orderID",orderID);
        historyData.put("customerID",customerID);
        historyData.put("orderDateTime",orderDateTime);
        documentReference.set(historyData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        //Delete in orderDetail
        DocumentReference delOrderReference = db.collection("orderDetail").document(orderID);
        delOrderReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        //Delete from worker current task
        DocumentReference delReference = db.collection("workerDetail").document(userID).collection("currentOrderDetail").document("currentOrder");
        delReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CompletePayment.this,"Service done",Toast.LENGTH_SHORT).show();
            }
        });

        Intent i = new Intent(CompletePayment.this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }
}
