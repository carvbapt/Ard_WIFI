package com.sauca.ard_wifi;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Main extends AppCompatActivity implements View.OnClickListener {

    Button btOn,btOff,btSair;
    TextView txt;
    ImageView iv_wifi;
    WifiManager wf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        btOn=(Button)findViewById(R.id.BT_WFOn);
        btOff=(Button)findViewById(R.id.BT_WFOff);
        btSair=(Button)findViewById(R.id.BT_Sair);

        txt=(TextView) findViewById(R.id.T_Tex);
        iv_wifi=(ImageView)findViewById(R.id.wifi_image);

        btOn.setOnClickListener(this);
        btOff.setOnClickListener(this);
        btSair.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v==findViewById( R.id.BT_WFOn)){
            wf=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wf.setWifiEnabled(true);
            txt.setText("WIFI Turned ON");
            iv_wifi.setImageResource(R.drawable.bt_green);
            //txt.setVisibility(View.VISIBLE);
            //Toast.makeText(this,"Hello ",Toast.LENGTH_SHORT).show();
        }else if(v==findViewById(R.id.BT_WFOff)){
            wf=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wf.setWifiEnabled(false);
            txt.setText("WIFI Turned OFF");
            iv_wifi.setImageResource(R.drawable.bt_red);
            //txt.setVisibility(View.INVISIBLE);
            //Toast.makeText(this,"Goodbye ",Toast.LENGTH_SHORT).show();
        }else if(v==findViewById(R.id.BT_Sair)){
            moveTaskToBack(true);
            finish();
            System.exit(0);
        }
    }
}