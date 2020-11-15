package com.example.workerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    RecyclerView mRvHistory;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryDetail> historyDetails;
    String historyID, historyDateTime;
    String userID, date, time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_history, container, false);

        historyDetails = new ArrayList<>();
        historyDetails.add(new HistoryDetail("#123445567","11/11/2020 01:30pm"));
        historyDetails.add(new HistoryDetail("#123445566","9/11/2020 9:00am"));

        mRvHistory = v.findViewById(R.id.rvHistory);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),LinearLayoutManager.VERTICAL,false);
        mRvHistory.setLayoutManager(layoutManager);

        //TODO::History Adapter
        //Set adapter for recycler view
        historyAdapter = new HistoryAdapter(getContext(), historyDetails);
        mRvHistory.setAdapter(historyAdapter);

        return v;
    }
}
