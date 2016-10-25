package com.example.saxion.nl.projectpersistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

public class MainActivity extends AppCompatActivity {
    private AVLoadingIndicatorView avi;
    private boolean stillLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startAnim();
//        while(stillLoading){
//
//        }
    public void launchLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void startAnim(){
   //     avi.show();
    }
//
//    private void foutMetServer(){
//        new BottomDialog.Builder(this)
//                .setTitle("serverfout!")
//                .setContent("App kan server niet bereiken. Probeer het later opnieuw")
//                .setPositiveText("OK")
//                .setPositiveBackgroundColorResource(R.color.colorPrimary)
//                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
//                .setPositiveTextColorResource(android.R.color.white)
//                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
//                .onPositive(new BottomDialog.ButtonCallback() {
//                    @Override
//                    public void onClick(BottomDialog dialog) {
//                        Log.d("BottomDialogs", "Do something!");
//                    }
//                }).show();
//    }



    private void hasInternetAcces(){

    }
}
