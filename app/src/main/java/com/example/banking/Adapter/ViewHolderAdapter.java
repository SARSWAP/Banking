package com.example.banking.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.banking.R;

public class ViewHolderAdapter extends RecyclerView.ViewHolder
{

    TextView mName, mPhoneno, mBalance, mRupee, mslash, mName1, mName2, mDate, mTransc_status;
    ImageView mPhone,mArrow;
    View mView;

    public ViewHolderAdapter(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        mName = itemView.findViewById(R.id.USERNAME);
        mPhoneno = itemView.findViewById(R.id.Phoneno);
        mBalance = itemView.findViewById(R.id.Balance);
        mRupee = itemView.findViewById(R.id.Rs);
        mslash = itemView.findViewById(R.id.Slash);
        mPhone = itemView.findViewById(R.id.phone);
        mName1 = itemView.findViewById(R.id.name1);
        mName2 = itemView.findViewById(R.id.name2);
        mDate = itemView.findViewById(R.id.date);
        mTransc_status = itemView.findViewById(R.id.transaction_status);
        itemView.setOnClickListener(view -> mClickListener.onItemClick(view, getAdapterPosition()));

    }
    private ClickListener mClickListener;
    public interface  ClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(ClickListener clickListener){
        mClickListener = clickListener;
    }
}
