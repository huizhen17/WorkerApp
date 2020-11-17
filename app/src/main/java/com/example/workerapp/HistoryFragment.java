package com.example.workerapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<HistoryDetail> historyList = new ArrayList<>();
    RecyclerView mRvHistory;
    HistoryAdapter historyAdapter;
    String userID=mAuth.getCurrentUser().getUid();
    int counter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_history, container, false);

        mRvHistory = v.findViewById(R.id.rvHistory);

        //Get instant update
        CollectionReference getOrderDB =  db.collection("workerDetail").document(userID).collection("workHistory");
        getOrderDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null) {
                    if (value.isEmpty()){
                        Toast.makeText(getContext(),"No order found.",Toast.LENGTH_SHORT).show();
                    }else {
                        for (QueryDocumentSnapshot document : value) {
                            //document.getId();
                            retrieveQuery(document.toObject(HistoryDetail.class), value.size());
                        }
                    }

                }else
                    Toast.makeText(getContext(),"Fail to retrieve data.",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void retrieveQuery(HistoryDetail historyDetail, int size) {
        historyList.add(historyDetail);
        counter = size;
        if (historyList.size()==counter){
            historyAdapter = new HistoryAdapter(getContext(), historyList);
            historyAdapter.notifyDataSetChanged();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            mRvHistory.setLayoutManager(layoutManager);
            mRvHistory.setAdapter(historyAdapter);
        }
    }
}
