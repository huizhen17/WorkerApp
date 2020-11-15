package com.example.workerapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.workerapp.FCM.SendNotification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static com.example.workerapp.MainActivity.getPhoneNo;
import static com.example.workerapp.MainActivity.getUserName;
import static com.example.workerapp.MainActivity.userName;


public class HomeFragment extends Fragment {

    TextView mtvCredit, mtvTaskNo, mtvTaskTime, mtvTaskDate, mtvTaskName, mtvTaskPhone, mtvAddress;
    TextView mtaskId, mtaskDate, mtaskTime, mtaskName, mtaskPhone, mtaskAddress;
    Button mbtnCheck, mbtnAccept;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID=mAuth.getCurrentUser().getUid(),status="",orderID="",customerID="";
    String name, phone, time,address,amount, service,date;
    Dialog taskAssignDialog;
    RequestQueue requestQueue;
    String userToken;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        //String strtext = getArguments().getString("completePay");
        //TODO::Dialog box to accept the task
        //TODO::Check db whether gt task + status
        //TODO::If no task jiu invisible all

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        mtvCredit = v.findViewById(R.id.tvCredit);
        mtvTaskNo = v.findViewById(R.id.taskId);
        mtvTaskTime = v.findViewById(R.id.taskTime);
        mtvTaskDate = v.findViewById(R.id.taskDate);
        mtvTaskName = v.findViewById(R.id.taskName);
        mtvTaskPhone = v.findViewById(R.id.taskPhone);
        mtvAddress = v.findViewById(R.id.taskAddress);
        mbtnCheck = v.findViewById(R.id.btnCheck);
        taskAssignDialog = new Dialog(getContext());

        //Check worker status is free display task assign
        DocumentReference workerCurrentStatus = db.collection("workerDetail").document(userID);
        workerCurrentStatus.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error==null){
                    if(value.exists()){
                        status = value.getString("currentStatus");
                        if(status.equalsIgnoreCase("free")){
                            displayDialog();
                        }else{
                            checkCurrentOrder();
                        }
                    }
                }
            }
        });

        mbtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO::Pass customer info
                //Toast.makeText(getContext(),orderID+"123"+test,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(),NavigateTask.class);
                i.putExtra("orderID",orderID);
                i.putExtra("address",address);
                i.putExtra("time",time);
                i.putExtra("amount",amount);
                i.putExtra("service",service);
                i.putExtra("name",name);
                i.putExtra("date",date);
                i.putExtra("phone",phone);
                getActivity().startActivity(i);
            }
        });

        return v;
    }

    private void checkCurrentOrder() {
        //Check current order
        DocumentReference currentOrder = db.collection("workerDetail").document(userID).collection("currentOrderDetail").document("currentOrder");
        currentOrder.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                orderID = documentSnapshot.getString("orderID");
                customerID = documentSnapshot.getString("userID");
                displayOrderDetail(orderID,customerID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"No task assigned.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayOrderDetail(final String orderID, final String customerID) {
        DocumentReference userRef = db.collection("userDetail").document(customerID);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name = documentSnapshot.getString("name");
                mtvTaskName.setText(name);
                phone = documentSnapshot.getString("phoneNo");
                mtvTaskPhone.setText(phone);
                address = documentSnapshot.getString("address");
                mtvAddress.setText(address);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"User details failed.",Toast.LENGTH_SHORT).show();
            }
        });

        DocumentReference orderRef = db.collection("userDetail").document(customerID).collection("currentOrder").document("currentOrder");
        orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mtvTaskNo.setText( documentSnapshot.getString("orderID"));
                mtvTaskDate.setText(documentSnapshot.getString("orderDate"));
                time = documentSnapshot.getString("orderTime");
                mtvTaskTime.setText(time);
                date = documentSnapshot.getString("orderDate");
                amount = documentSnapshot.getString("orderPrice");
                service = documentSnapshot.getString("orderService");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"User details failed.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayDialog(){
        taskAssignDialog.setContentView(R.layout.accept_order_dialog);
        taskAssignDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        taskAssignDialog.show();
        taskAssignDialog.setCancelable(false);

        mtaskId = taskAssignDialog.findViewById(R.id.taskId);
        mtaskDate = taskAssignDialog.findViewById(R.id.taskDate);
        mtaskTime = taskAssignDialog.findViewById(R.id.taskTime);
        mtaskName = taskAssignDialog.findViewById(R.id.taskName);
        mtaskPhone = taskAssignDialog.findViewById(R.id.taskPhone);
        mtaskAddress = taskAssignDialog.findViewById(R.id.taskAddress);
        mbtnAccept = taskAssignDialog.findViewById(R.id.btnAccept);

        DocumentReference currentOrder = db.collection("workerDetail").document(userID).collection("currentOrderDetail").document("currentOrder");
        currentOrder.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                orderID = documentSnapshot.getString("orderID");
                customerID = documentSnapshot.getString("userID");
                displayDialogDetail(customerID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"No task assigned.",Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void displayDialogDetail(String customerID){
        DocumentReference userRef = db.collection("userDetail").document(customerID);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mtaskName.setText( documentSnapshot.getString("name"));
                mtaskPhone.setText(documentSnapshot.getString("phoneNo"));
                mtaskAddress.setText(documentSnapshot.getString("address"));
                saveToken(documentSnapshot.getString("token"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"User details failed.",Toast.LENGTH_SHORT).show();
            }
        });

        DocumentReference orderRef = db.collection("userDetail").document(customerID).collection("currentOrder").document("currentOrder");
        orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mtaskId.setText( documentSnapshot.getString("orderID"));
                mtaskDate.setText(documentSnapshot.getString("orderDate"));
                mtaskTime.setText(documentSnapshot.getString("orderTime"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"User details failed.",Toast.LENGTH_SHORT).show();
            }
        });

        mbtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= "Appointment Accepted";
                String body ="Your stylist will be " + getUserName() +
                            ".\nTelno: " + getPhoneNo() +
                            "\nTime: " + mtaskTime.getText().toString();
                SendNotification sendNotification= new SendNotification();
                requestQueue.add(sendNotification.specifUser(userToken,title,body));

                DocumentReference workerCurrentStatus = db.collection("workerDetail").document(userID);
                workerCurrentStatus.update("currentStatus","Busy").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
                //store in db
                taskAssignDialog.dismiss();
            }
        });

    }

    private void saveToken(String token) {
        userToken = token;
    }
}
