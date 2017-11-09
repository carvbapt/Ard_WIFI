package com.sauca.ard_wifi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Main extends AppCompatActivity implements View.OnClickListener{

    Button btTLeds,btSair;
    ImageButton ibConf;
    SwitchCompat stWF;
    TextView t_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action Bar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setCustomView(R.layout.action_bar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        // Buttons, Images & Text
        btTLeds=(Button)findViewById(R.id.BT_TLeds);
        btSair = (Button) findViewById(R.id.BT_Sair);
        ibConf=(ImageButton)findViewById(R.id.IB_Conf);
        stWF = (SwitchCompat) findViewById(R.id.STC_WIFI);
        t_text=(TextView)findViewById(R.id.T_Tex);

        ibConf.setVisibility(View.VISIBLE);
        // Action
        stWF.setOnClickListener(this);
        ibConf.setOnClickListener(this);
        btTLeds.setOnClickListener(this);
        btSair.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // WIFI
        if (v == findViewById(R.id.STC_WIFI)) {
            if (stWF.isChecked()) {
                stWF.setText(getString(R.string.bt_on));
                t_text.setText(getText(R.string.Conect));
            } else {
                stWF.setText(getString(R.string.bt_off));
                t_text.setText(getText(R.string.NConect));
            }
        }else   if(v== findViewById(R.id.IB_Conf)){
            startActivity(new Intent(this,Confi.class));
        }else if(v== findViewById(R.id.BT_TLeds)){
            startActivity(new Intent(this,Leds.class));
        }else if (v == findViewById(R.id.BT_Sair)){
            System.exit(0);
        }
    }
}
