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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Main extends AppCompatActivity implements View.OnClickListener{

    Button btTLeds,btTMotor,btSair;
    ToggleButton tbWF;
    ImageButton ibConf;
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

        // Buttons, Images & Text
        btTLeds=(Button)findViewById(R.id.BT_TLeds);
        btTMotor=(Button)findViewById(R.id.BT_TMotor);
        btSair = (Button) findViewById(R.id.BT_Sair);
        ibConf=(ImageButton)findViewById(R.id.IB_Conf);
        tbWF=(ToggleButton)findViewById(R.id.TB_Wifi);
        t_text=(TextView)findViewById(R.id.T_Tex);

        ibConf.setVisibility(View.VISIBLE);

        // Action
        if(wfm.getWifiState()==3) {
            // Connectivity Manager object to check connection
            chk(getString(R.string.SsidDef),1);
        }

        tbWF.setOnClickListener(this);
        ibConf.setOnClickListener(this);
        btTLeds.setOnClickListener(this);
        btTMotor.setOnClickListener(this);
        btSair.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // WIFI
        if (v == findViewById(R.id.TB_Wifi)) {
            wfm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (tbWF.isChecked()) {
                wfm.setWifiEnabled(true);
                chk("\"SAPA\"",1);
            }else {
                wfm.setWifiEnabled(false);
                tbWF.setText(getString(R.string.bt_off));
                tbWF.setBackgroundColor(ContextCompat.getColor(this, R.color.LightRed));
                tbWF.setChecked(false);
                t_text.setText(getText(R.string.NConect));
            }
        }else   if(v== findViewById(R.id.IB_Conf)){
            startActivity(new Intent(this,Confi.class));
        }else if(v== findViewById(R.id.BT_TLeds)){
            startActivity(new Intent(this,Leds.class));
        }else if(v== findViewById(R.id.BT_TMotor)){
            startActivity(new Intent(this,Motor.class));
        }else if (v == findViewById(R.id.BT_Sair)){
            finish();
        }
     }

    //////////////// WIFI CONNECT //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public  void chk(String Ssi, int i) {

        st = "";
        wfm_Conect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            Thread.sleep(2500);
            if (wfm_Conect.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI && i != 0) {
                if (wfm_Conect.getActiveNetworkInfo().getState().name().equals("CONNECTED") && wfm_Conect.getActiveNetworkInfo().getExtraInfo().equals(Ssi)) {
                    st = getString(R.string.Conect) + " to " + wfm.getConnectionInfo().getSSID() + " -";
                    tbWF.setText(getString(R.string.bt_on));
                    tbWF.setBackgroundColor(ContextCompat.getColor(this, R.color.LightGreen));
                    tbWF.setChecked(true);
                    t_text.setText(st);
                } else {
                    wfm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wfm.setWifiEnabled(false);
                    Toast.makeText(getBaseContext(), "REDE ERRADA", Toast.LENGTH_SHORT).show();
                }
            } else {
                wfm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }
}
