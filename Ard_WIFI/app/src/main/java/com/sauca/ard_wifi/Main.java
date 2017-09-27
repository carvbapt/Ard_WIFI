package com.sauca.ard_wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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


public class Main extends AppCompatActivity  implements View.OnClickListener{

    WifiManager wf;
    BroadcastReceiver wf_receiver;
    WifiInfo wf_info;

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

        btSair=(Button)findViewById(R.id.BT_Sair);
        stWF=(SwitchCompat) findViewById(R.id.STC_WIFI);
        txt=(TextView) findViewById(R.id.T_Tex);
        rl=(RelativeLayout)findViewById(R.id.RL_Frame);
        tb_led1=(ToggleButton)findViewById(R.id.TB_Led1);
        iv_led1=(ImageView)findViewById(R.id.IV_Led1);

        rl.setVisibility(View.INVISIBLE);

        btSair.setOnClickListener(this);
        stWF.setOnClickListener(this);
        tb_led1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        wf = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

       if(v==findViewById(R.id.STC_WIFI)){
            if(stWF.isChecked()) {
                wf.setWifiEnabled(true);
                //txt.setText(R.string.txt_on);
                stWF.setText(R.string.bt_on);
                rl.setVisibility(View.VISIBLE);
                //Toast.makeText(this, stWF.getTextOn().toString(),Toast.LENGTH_LONG).show();

              /*  wf_info=wf.getConnectionInfo();
              //  txt.append("\n "+ wf_info.toString());

                if(wf.startScan()){
                    // List available APs
                    List<ScanResult> scans = wf.getScanResults();
                    if(scans != null && !scans.isEmpty()) {
                        for (ScanResult scan : scans) {
                            int level = WifiManager.calculateSignalLevel(scan.level, 20);
                            txt.append("\n  "+scans.toString()+ " "+level);
                        }
                    }
                 }*/
            }else{
                wf.setWifiEnabled(false);
                //txt.setText(R.string.txt_off);
                rl.setVisibility(View.INVISIBLE);
                stWF.setText(R.string.bt_off);
                //Toast.makeText(this, stWF.getTextOff().toString(),Toast.LENGTH_LONG).show();
            }
        }else if (v==findViewById(R.id.TB_Led1)){
            if(tb_led1.isChecked())
               iv_led1.setImageResource(R.mipmap.ic_green);
            else
               iv_led1.setImageResource(R.mipmap.ic_red);
        }else if(v==findViewById(R.id.BT_Sair)){
            moveTaskToBack(true);
            finish();
            System.exit(0);
        }
    }
}