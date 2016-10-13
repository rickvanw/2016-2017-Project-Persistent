package com.example.saxion.nl.projectpersistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

public class MainActivity extends AppCompatActivity {
private AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    void startAnim(){
        avi.show();
        // or avi.smoothToShow();
    }
}
