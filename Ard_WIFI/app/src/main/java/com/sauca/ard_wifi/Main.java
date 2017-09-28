package com.sauca.ard_wifi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class Main extends AppCompatActivity implements View.OnClickListener {

    WifiManager wf;
    ConnectivityManager wf_Conect;
    NetworkInfo wf_Active;

    List<ScanResult> wifiList;
    StringBuilder sb = new StringBuilder();


    Button btSair;
    SwitchCompat stWF;
    TextView txt;
    RelativeLayout rl;
    ToggleButton tb_led1;
    ImageView iv_led1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "The permission to get BLE location data is required", Toast.LENGTH_SHORT).show();
            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }else{
            Toast.makeText(this, "Location permissions already granted", Toast.LENGTH_SHORT).show();
        }

        btSair = (Button) findViewById(R.id.BT_Sair);
        stWF = (SwitchCompat) findViewById(R.id.STC_WIFI);
        txt = (TextView) findViewById(R.id.T_Tex);
        rl = (RelativeLayout) findViewById(R.id.RL_Frame);
        tb_led1 = (ToggleButton) findViewById(R.id.TB_Led1);
        iv_led1 = (ImageView) findViewById(R.id.IV_Led1);

        wf = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // get Connectivity Manager object to check connection
        wf_Conect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wf_Active = wf_Conect.getActiveNetworkInfo();

        //wf.setWifiEnabled(false);
        rl.setVisibility(View.INVISIBLE);

        btSair.setOnClickListener(this);
        stWF.setOnClickListener(this);
        tb_led1.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.STC_WIFI)) {
            if (stWF.isChecked()) {
                wf.setWifiEnabled(true);
                stWF.setText(R.string.bt_on);
                rl.setVisibility(View.VISIBLE);
                // connected to wifi
                chk_WIFI();
            } else {
                wf.setWifiEnabled(false);
                rl.setVisibility(View.INVISIBLE);
                stWF.setText(R.string.bt_off);
                Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
                //Toast.makeText(this, stWF.getTextOff().toString(),Toast.LENGTH_LONG).show();
            }
            //checkInternetConetion();
        } else if (v == findViewById(R.id.TB_Led1)) {
            if (tb_led1.isChecked())
                iv_led1.setImageResource(R.mipmap.ic_green);
            else
                iv_led1.setImageResource(R.mipmap.ic_red);
        } else if (v == findViewById(R.id.BT_Sair)) {
            moveTaskToBack(true);
            finish();
            System.exit(0);
        }
    }

    public void chk_WIFI() {

        // Register broadcast receiver
        // Broacast receiver will automatically call when number of wifi connections changed
        //registerReceiver(wf_Receive, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wf.startScan();
        txt.setText("Starting Scan...");

        sb = new StringBuilder();

        sb.append("\n Number Of Wifi connections :"+wf.getScanResults().size()+"\n\n");

        for(int i = 0; i <wf.getScanResults().size(); i++){
            if ((wf.getScanResults().get(i).SSID.equals("WIFIARD"))) {
                sb.append(new Integer(i+1).toString() + ". ");
                sb.append(wf.getScanResults().get(i).toString());
                sb.append("\n\n");
            }
        }

        if(sb.equals("\n Number Of Wifi connections :"+wf.getScanResults().size()+"\n\n"))
            Toast.makeText(getBaseContext(), "WIFI - Errada \n  " + sb, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getBaseContext(), "WIFI - Ligada" + wf_Active.getExtraInfo(), Toast.LENGTH_SHORT).show();
        txt.setText(sb);
    }
}
