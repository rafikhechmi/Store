package com.example.rafik.store;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;


public class LogIn extends AppCompatActivity {
    EditText txtun,txtpw;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (isConnected(this)==true) {
            txtun = (EditText) findViewById(R.id.txtun);
            txtpw = (EditText) findViewById(R.id.txtpw);
            login = (Button) findViewById(R.id.login);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txtun.getText().toString().equals("admin")) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);


                    }


                }
            });

        }else
        {
            Intent i = new Intent (this,NoInternetActivity.class);
            startActivity(i);

        }



    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }
}
