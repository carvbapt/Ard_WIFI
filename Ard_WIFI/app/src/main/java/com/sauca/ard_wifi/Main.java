package com.sauca.ard_wifi;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Main extends AppCompatActivity implements View.OnClickListener{

    WifiManager wf;
    ConnectivityManager wf_Conect;
    NetworkInfo wf_Active;

    Button btSair,btScan;
    ImageButton biConf;
    SwitchCompat stWF;
    TextView txt;
    RelativeLayout rl,rl2;
    ToggleButton tb_led1;
    ImageView iv_led1;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action Bar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setCustomView(R.layout.action_bar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        // Permissions
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

        // Buttons, Images & Text
        btSair = (Button) findViewById(R.id.BT_Sair);
        btScan = (Button) findViewById(R.id.BT_Scan);
        biConf=(ImageButton)findViewById(R.id.BI_Conf);
        stWF = (SwitchCompat) findViewById(R.id.STC_WIFI);
        txt = (TextView) findViewById(R.id.T_Tex);
        rl = (RelativeLayout) findViewById(R.id.RL_Frame);
        rl2 = (RelativeLayout) findViewById(R.id.RL_Frame2);
        tb_led1 = (ToggleButton) findViewById(R.id.TB_Led1);
        iv_led1 = (ImageView) findViewById(R.id.IV_Led1);
        wf = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wf_Conect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Action
        wf.setWifiEnabled(false);
        str=getString(R.string.Scan) +"WIFIARD";
        btScan.setText(str);
        rl.setVisibility(View.INVISIBLE);
        rl2.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);
        btSair.setOnClickListener(this);
        btScan.setOnClickListener(this);
        biConf.setOnClickListener(this);
        stWF.setOnClickListener(this);
        tb_led1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // WIFI
        if (v == findViewById(R.id.STC_WIFI)){
            if (stWF.isChecked()) {
                wf.setWifiEnabled(true);
                stWF.setText(getString(R.string.bt_on));
                rl2.setVisibility(View.VISIBLE);
            }else {
                wf.setWifiEnabled(false);
                stWF.setText(getString(R.string.bt_off));
                rl.setVisibility(View.INVISIBLE);
                rl2.setVisibility(View.INVISIBLE);
            }
        // Scan
        }else if (v == findViewById(R.id.BT_Scan)) {
            if (btScan.getText().toString().contains(getString(R.string.Scan))) {
                chk_WIFI();
            } else {

                btScan.setText(str);
                btScan.setBackgroundColor(ContextCompat.getColor(this, R.color.LightRed));
                rl.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.INVISIBLE);
            }
        // Configuration WIFI
        }else if(v== findViewById(R.id.BI_Conf)){
            alertView(getString(R.string.txt));
        // LED´S
        } else if (v == findViewById(R.id.TB_Led1)) {
                if (tb_led1.isChecked())
                    iv_led1.setImageResource(R.mipmap.ic_green);
                else
                    iv_led1.setImageResource(R.mipmap.ic_red);
        }
        // Sair Aplicação
        else if (v == findViewById(R.id.BT_Sair))
        {
                moveTaskToBack(true);
                finish();
                System.exit(0);
        }
    }

    private void chk_WIFI() {

        // Connectivity Manager object to check connection
        wf_Active = wf_Conect.getActiveNetworkInfo();
       // wf.getWifiState();

        if(!wf_Active.getExtraInfo().contains(getString(R.string.Sssid))) {
            btScan.setText(R.string.Conect);
            btScan.setBackgroundColor(ContextCompat.getColor(this, R.color.LightGreen));
            rl.setVisibility(View.VISIBLE);
            txt.setText(R.string.Leds);
            txt.setVisibility(View.VISIBLE);
            Toast.makeText(this, wf_Active.getExtraInfo(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, R.string.Conect, Toast.LENGTH_SHORT).show();
        }
    }


    ///////////////////  ALERT DIALOG OK ///////////////////////////////////////////////////////////

    private void alertView( String message ) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.app_name));
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_arduino);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.action_dialog, null);
        // Set up the input
        //final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        alertDialog.setView(viewInflated);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
