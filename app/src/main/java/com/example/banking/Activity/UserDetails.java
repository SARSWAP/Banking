package com.example.banking.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banking.DatabaseHelper.DatabaseHelper;
import com.example.banking.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserDetails extends AppCompatActivity {
    ProgressDialog progressDialog;
    String phonenum;
    Double balance;
    TextView name, phoneNumber, email, account_no, ifsc_code, balanceShow;
    Button transfer_button;
    AlertDialog dialog;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        name = findViewById(R.id.USERNAME);
        phoneNumber = findViewById(R.id.Number);
        back = findViewById(R.id.bkbtnn);
        email = findViewById(R.id.Email);
        account_no = findViewById(R.id.Acc);
        ifsc_code = findViewById(R.id.IFSC);
        balanceShow = findViewById(R.id.AMNT);
        transfer_button = findViewById(R.id.Transfers);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDetails.this, MainActivity.class));
                finish();
            }
        });
        progressDialog = new ProgressDialog(UserDetails.this);
        progressDialog.setTitle("Loading data...");
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            phonenum = bundle.getString("phonenumber");
            showData(phonenum);
        }
        transfer_button.setOnClickListener(v -> enterAmount());
    }

    private void showData(String phonenumber) {
        Cursor cursor = new DatabaseHelper(this).readparticulardata(phonenumber);
        while(cursor.moveToNext()) {
            String balancefromdb = cursor.getString(2);
            balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            progressDialog.dismiss();

            phoneNumber.setText(cursor.getString(0));
            name.setText(cursor.getString(1));
            email.setText(cursor.getString(3));
            balanceShow.setText(price);
            account_no.setText(cursor.getString(4));
            ifsc_code.setText(cursor.getString(5));
        }

    }
    private void enterAmount()
    {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserDetails.this);
        View mView = getLayoutInflater().inflate(R.layout.enter_amt, null);
        mBuilder.setTitle("Enter amount").setView(mView).setCancelable(false);

        final EditText mAmount = (EditText) mView.findViewById(R.id.EnterAmt);

        mBuilder.setPositiveButton("SEND", (dialogInterface, i) -> {
        }).setNegativeButton("CANCEL", (dialog, which) -> {
            dialog.dismiss();
            transactionCancel();
        });
        dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            if(mAmount.getText().toString().isEmpty()){
                mAmount.setError("Amount can't be empty");
            }else if(Double.parseDouble(mAmount.getText().toString()) > balance){
                mAmount.setError("Your account don't have enough balance");
            }else{
                Intent intent = new Intent(UserDetails.this, SendTo.class);
                intent.putExtra("phonenumber", phoneNumber.getText().toString());
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("currentamount", balance.toString());
                intent.putExtra("transferamount", mAmount.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    private void transactionCancel() {
        AlertDialog.Builder builder_exitbutton = new AlertDialog.Builder(UserDetails.this);
        builder_exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("yes", (dialogInterface, i) -> {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
                    String date = simpleDateFormat.format(calendar.getTime());

                    new DatabaseHelper(UserDetails.this).insertTransferData(date, name.getText().toString(), "Not selected", "0", "Failed");
                    Toast.makeText(UserDetails.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                }).setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
            enterAmount();
        });
        AlertDialog alertexit = builder_exitbutton.create();
        alertexit.show();
    }
    
}