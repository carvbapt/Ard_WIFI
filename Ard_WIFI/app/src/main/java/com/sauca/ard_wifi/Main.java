package com.sauca.ard_wifi;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Main extends AppCompatActivity implements View.OnClickListener{

    Button btSair;
    SwitchCompat stWF;
    TextView txt;
    WifiManager wf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setCustomView(R.layout.action_bar);

        btSair=(Button)findViewById(R.id.BT_Sair);
        stWF=(SwitchCompat) findViewById(R.id.STC_WIFI);

        txt=(TextView) findViewById(R.id.T_Tex);

        btSair.setOnClickListener(this);
        stWF.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        wf = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

       if(v==findViewById(R.id.STC_WIFI)){
            if(stWF.isChecked()) {
                wf.setWifiEnabled(true);
                //txt.setText(R.string.txt_on);
                stWF.setText(R.string.bt_on);
                //Toast.makeText(this, stWF.getTextOn().toString(),Toast.LENGTH_LONG).show();
            }else{
                wf.setWifiEnabled(false);
                //txt.setText(R.string.txt_off);
                stWF.setText(R.string.bt_off);
                //Toast.makeText(this, stWF.getTextOff().toString(),Toast.LENGTH_LONG).show();
            }
        }else if(v==findViewById(R.id.BT_Sair)){
            moveTaskToBack(true);
            finish();
            System.exit(0);
        }
    }
}