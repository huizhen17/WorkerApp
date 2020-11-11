package com.example.workerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {

    TextView mtvCredit, mtvTaskNo, mtvTaskTime, mtvTaskDate, mtvTaskName, mtvTaskPhone, mtvAddress;
    Button mbtnCheck;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        //TODO::Dialog box to accept the task
        //TODO::Check db whether gt task + status

        mtvCredit = v.findViewById(R.id.tvCredit);
        mtvTaskNo = v.findViewById(R.id.taskId);
        mtvTaskTime = v.findViewById(R.id.taskTime);
        mtvTaskDate = v.findViewById(R.id.taskDate);
        mtvTaskName = v.findViewById(R.id.taskName);
        mtvTaskPhone = v.findViewById(R.id.taskPhone);
        mtvAddress = v.findViewById(R.id.taskAddress);
        mbtnCheck = v.findViewById(R.id.btnCheck);

        mbtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Hi",Toast.LENGTH_SHORT).show();
                //TODO::Pass customer info
                Intent i = new Intent(getContext(),NavigateTask.class);
                startActivity(i);
            }
        });

        return v;
    }
}
