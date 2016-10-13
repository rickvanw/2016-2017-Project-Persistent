package com.example.saxion.nl.projectpersistant.AdminActivitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.Networking.Post;
import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class AddUserActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etPasswordCheck;
    private RadioButton rbTypeNormal, rbTypePower, rbTypeAdmin;
    private Button buttonNewUser;
    private int type;
    private Singleton singleton = Singleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));

        etUsername = (EditText)findViewById(R.id.etNewUserUsername);
        etPassword = (EditText)findViewById(R.id.etNewUserPassword);
        etPasswordCheck = (EditText)findViewById(R.id.etNewUserPasswordCheck);
        rbTypeNormal = (RadioButton)findViewById(R.id.rbNewUserNormal);
        rbTypePower = (RadioButton)findViewById(R.id.rbNewUserPower);
        rbTypeAdmin = (RadioButton)findViewById(R.id.rbNewUserAdmin);
        buttonNewUser = (Button)findViewById(R.id.buttonNewUser);

        // Normal is the standard selected user
        rbTypeNormal.setChecked(true);

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if all fields are filled in
                if(etUsername == null || etUsername.getText().toString().isEmpty() || etPassword == null || etPassword.getText().toString().isEmpty() || etPasswordCheck == null || etPasswordCheck.getText().toString().isEmpty()) {
                    showAlert("Foutmelding", "Vul alle velden in.");
                }else {

                    // Check if password equels the check password field
                    if (!etPassword.getText().toString().equals(etPasswordCheck.getText().toString())) {

                        // Alert user if passwords aren't the same
                        showAlert("Foutmelding", "Vul tweemaal hetzelfde wachtwoord in");

                        // Clear both fields for re-entry of passwords
                        etPassword.setText("");
                        etPasswordCheck.setText("");

                        // Set focus to first password field
                        etPassword.requestFocus();

                    } else {

                        // TEST
                        System.out.println("PASSWORDS THE SAME = " + etPassword.getText().toString());

                        // Determine type
                        if (rbTypePower.isChecked()) {
                            // Type Power user
                            type = 1;
                        } else if (rbTypeAdmin.isChecked()) {
                            // Type Administrator
                            type = 2;
                        } else {
                            // Type Normal user
                            type = 0;
                        }
                        // TEST
                        System.out.println("TYPE = " + type);

                        String username = etUsername.getText().toString();
                        String password = "";

                        try {
                            password = singleton.hashPassword(etPassword.getText().toString());
                        }catch(Exception e){}


                        // TEST
                        System.out.println("USERNAME = " + username);

                        // TODO gebruiker in database zetten, hiervoor plaatste system outs weghalen (api gebruikers database nog niet bestaand)
                        try{
                            //URL url = new URL(singleton.REST_URL + "/api/users?username="+username+"&password="+password+"&type="+type);

                           // new Post().execute(url).get();

                           // succesAlert("Gebruiker aangemaakt", "Nog een gebruiker aanmaken?");


                                String output =
                                        new Post().execute(
                                                new URL(singleton.REST_URL + "/api/users?username="+username+"&password="+password+"&type="+type)
                                        ).get();

                                JSONObject object = new JSONObject(output);

                                if(object.has("http_status")) {
                                    int status = object.getInt("http_status");

                                    System.out.println(""+status);

                                    if (status >= 200 && status <= 299) {
                                        //Server response ophalen

                                        succesAlert("Gebruiker aangemaakt", "Nog een gebruiker aanmaken?");
                                    }else{
                                        HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                                        showAlert(error_map.get("titel"), error_map.get("bericht"));}
                                }


                        }catch (Exception e){}
                    }
                }
            }
        });
    }

    public void showAlert(String titel, String bericht) {
        new AlertDialog.Builder(AddUserActivity.this)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /* niks doen */
                    }
                })
                .show();
    }

    public void succesAlert(String titel, String bericht) {
        new AlertDialog.Builder(AddUserActivity.this)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        etUsername.setText("");
                        etUsername.requestFocus();
                        etPassword.setText("");
                        etPasswordCheck.setText("");
                        rbTypeNormal.setChecked(true);
                    }
                })
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

}
