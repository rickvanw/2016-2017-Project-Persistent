package com.example.saxion.nl.projectpersistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.saxion.nl.projectpersistant.model.Singleton;

public class LoginActivity extends AppCompatActivity {
    private Singleton singleton = Singleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doLogin(View v) {
        EditText edtUsername = (EditText)findViewById(R.id.edtUsername);
        EditText edtPassword = (EditText)findViewById(R.id.edtPassword);

        if(singleton.doAuthUser( edtUsername.getText().toString(), edtPassword.getText().toString() ) ) {
            Log.d("LOGINACTIVITY-AUTH", "TRUE");
        }
        else {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Fout")
                    .setMessage("De ingevoerde inloggegevens zijn onjuist.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { /* niks doen */ }
                    })
                    .show();
        }
    }
}
