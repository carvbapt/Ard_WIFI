package com.sauca.ard_wifi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity implements View.OnClickListener{

    Button btTLeds,btTMotor,btSair;
    ImageButton ibConf;
    SwitchCompat stWF;
    TextView t_text;

    public static WifiManager wfm;
    public static ConnectivityManager wfm_Conect;
    public static String st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action Bar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setCustomView(R.layout.action_bar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        // WIFI Permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "Permission to get BLE location data is required", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }else{
            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }

        // WIFI
        wfm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //wfm_info= wfm.getConnectionInfo();
        wfm_Conect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Buttons, Images & Text
        btTLeds=(Button)findViewById(R.id.BT_TLeds);
        btTMotor=(Button)findViewById(R.id.BT_TMotor);
        btSair = (Button) findViewById(R.id.BT_Sair);
        ibConf=(ImageButton)findViewById(R.id.IB_Conf);
        stWF = (SwitchCompat) findViewById(R.id.STC_WIFI);
        t_text=(TextView)findViewById(R.id.T_Tex);

        ibConf.setVisibility(View.VISIBLE);

        // Action
        if(wfm.getWifiState()==3) {
            // Connectivity Manager object to check connection
            chk(getString(R.string.SsidDef),1);
        }

        stWF.setOnClickListener(this);
        ibConf.setOnClickListener(this);
        btTLeds.setOnClickListener(this);
        btTMotor.setOnClickListener(this);
        btSair.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // WIFI
        if (v == findViewById(R.id.STC_WIFI)) {
            wfm.setWifiEnabled(true);
            if (stWF.isChecked()) {
                // WIFI - DISABLED
                chk("\"SAPA\"",1);

            }else {
                chk("",0);
            }
        }else   if(v== findViewById(R.id.IB_Conf)){
            startActivity(new Intent(this,Confi.class));
        }else if(v== findViewById(R.id.BT_TLeds)){
            startActivity(new Intent(this,Leds.class));
        }else if(v== findViewById(R.id.BT_TMotor)){
            startActivity(new Intent(this,Motor.class));
        }else if (v == findViewById(R.id.BT_Sair)){
            System.exit(0);
        }
     }

    //////////////// WIFI CONNECT

    public  void chk(String Ssi, int i) {

        st = "";

        if (wfm_Conect.getActiveNetworkInfo().getState().name().equals("CONNECTED") && i!=0){
        try {
            Thread.sleep(2100);
            if(wfm_Conect.getActiveNetworkInfo().getExtraInfo().equals(Ssi)) {
                st = getString(R.string.Conect) + " to " + wfm.getConnectionInfo().getSSID() + " -";
                stWF.setText(getString(R.string.bt_on));
                stWF.setChecked(true);
                t_text.setText(st);
            }else{
                wfm.setWifiEnabled(false);
                Toast.makeText(getBaseContext(), "REDE ERRADA", Toast.LENGTH_SHORT).show();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        } else {
            wfm.setWifiEnabled(false);
            stWF.setText(getString(R.string.bt_off));
            stWF.setChecked(false);
            t_text.setText(getText(R.string.NConect));
        }
    }
}
