
package com.example.banking.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.banking.DatabaseHelper.DatabaseHelper;
import com.example.banking.Adapter.TransactionHistoryAdapter;
import com.example.banking.Model.Model;
import com.example.banking.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistory extends AppCompatActivity
{
    List<Model> modelList_historylist = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    TransactionHistoryAdapter adapter;
    TextView history_empty;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        back = findViewById(R.id.backbtn);
        mRecyclerView = findViewById(R.id.RecycleHistory);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionHistory.this, MainActivity.class));
                finish();
            }
        });
        showData();
    }

    private void showData() {
        modelList_historylist.clear();
        Cursor cursor = new DatabaseHelper(this).readtransferdata();

        while (cursor.moveToNext()) {
            String balancefromdb = cursor.getString(4);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            Model model = new Model(cursor.getString(2), cursor.getString(3), price, cursor.getString(1), cursor.getString(5));
            modelList_historylist.add(model);
        }

        adapter = new TransactionHistoryAdapter(TransactionHistory.this, modelList_historylist);
        mRecyclerView.setAdapter(adapter);

        if(modelList_historylist.size() == 0){
           // history_empty.setVisibility(View.VISIBLE);
        }


    }

}
