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

public class MyAccFragment extends Fragment {

    TextView mtvUsername, mtvUserPhone, mtvUserEmail;
    ImageView mivRating1,mivRating2,mivRating3,mivRating4,mivRating5;
    Button mbtnLogOut;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_acc,container,false);

        mtvUsername = v.findViewById(R.id.tvUserName);
        mtvUserEmail = v.findViewById(R.id.tvUserEmail);
        mtvUserPhone = v.findViewById(R.id.tvUserPhone);
        mivRating1 = v.findViewById(R.id.rating1);
        mivRating2 = v.findViewById(R.id.rating2);
        mivRating3 = v.findViewById(R.id.rating3);
        mivRating4 = v.findViewById(R.id.rating4);
        mivRating5 = v.findViewById(R.id.rating5);
        mbtnLogOut = v.findViewById(R.id.btnLogout);

        //Retrieve data from firebase
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference user = db.collection("workerDetail").document(userID);
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name = documentSnapshot.getString("name");
                String email = documentSnapshot.getString("email");
                String phone = documentSnapshot.getString("phone");

                mtvUsername.setText(name);
                mtvUserEmail.setText(email);
                mtvUserPhone.setText(phone);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Problem on retrieving data. Please try again later.",Toast.LENGTH_SHORT).show();
            }
        });

        //Log out
        mbtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i= new Intent(getContext(),LoginActivity.class);
                startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
            }
        });

        return v;

    }

}
