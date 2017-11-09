package com.sauca.ard_wifi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class Motor extends AppCompatActivity implements View.OnClickListener{

    ImageButton ib_back,ib_conf;

    ToggleButton tb_mot1;
    ImageView iv_m1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);

        // Action Bar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setCustomView(R.layout.action_bar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        ib_back=(ImageButton)findViewById(R.id.IB_Back);
        ib_conf=(ImageButton)findViewById(R.id.IB_Conf);
        tb_mot1 = (ToggleButton) findViewById(R.id.TB_Motor1);
        iv_m1 = (ImageView) findViewById(R.id.IV_M1);

        tb_mot1.setOnClickListener(this);
        ib_back.setOnClickListener(this);
        ib_conf.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==findViewById(R.id.IB_Back)) {
            finish();
        }else   if(v== findViewById(R.id.IB_Conf)){
            startActivity(new Intent(this,Confi.class));
        }else if (v == findViewById(R.id.TB_Motor1)) {
            if (tb_mot1.isChecked())
                iv_m1.setImageResource(R.mipmap.ic_green);
            else
                iv_m1.setImageResource(R.mipmap.ic_red);
        }
    }
}
