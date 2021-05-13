package com.example.banking.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.example.banking.R;

public class Splash extends AppCompatActivity {
    ProgressBar pbr;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pbr=findViewById(R.id.progressBar);
        Handler hand = new Handler();
        hand.postDelayed(() -> {

            Intent intent = new Intent(Splash.this,Start.class);
            startActivity(intent);
            pbr.setVisibility(View.GONE);
            finish();
    },3000);
}
}