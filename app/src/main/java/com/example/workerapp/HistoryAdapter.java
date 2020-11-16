package com.example.workerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    Context context;
    private ArrayList<HistoryDetail> historyList;

    //Create constructor for cart adapter
    public HistoryAdapter(Context context, ArrayList<HistoryDetail> historyList){
        this.context = context;
        this.historyList = historyList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mreceiptID;
        TextView mreceiptDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mreceiptID = itemView.findViewById(R.id.receiptID);
            mreceiptDateTime = itemView.findViewById(R.id.receiptDateTime);

        }

    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_history,parent,false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.mreceiptID.setText(historyList.get(position).getHistoryID());
        holder.mreceiptDateTime.setText(historyList.get(position).getHistoryDateTime());

        //When time is chosen
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "History Clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
