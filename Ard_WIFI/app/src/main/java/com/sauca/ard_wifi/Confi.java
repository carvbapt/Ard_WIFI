package com.sauca.ard_wifi;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Confi extends AppCompatActivity implements View.OnClickListener{

    ImageButton ib_back,ib_conf;
    Button bt_chng,bt_scan;
    EditText et_ssid,et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confi);

        // Action Bar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setCustomView(R.layout.action_bar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        ib_back=(ImageButton)findViewById(R.id.IB_Back);
        ib_conf=(ImageButton)findViewById(R.id.IB_Conf);
        bt_chng = (Button)findViewById(R.id.BT_Ssid);
        bt_scan=(Button)findViewById(R.id.BT_Scan);

        ib_conf.setVisibility(View.INVISIBLE);
        ib_back.setOnClickListener(this);
        bt_chng.setOnClickListener(this);
        bt_scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.IB_Back))
            finish();
        else if(v== findViewById(R.id.BT_Ssid)){
            alertView(getString(R.string.Ssid));
        }else if(v== findViewById(R.id.BT_Scan)){
            Toast.makeText(this, " Call WIFI", Toast.LENGTH_SHORT).show();
        }
    }

    ///////////////////  ALERT DIALOG OK ///////////////////////////////////////////////////////////

    private void alertView( String message ) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(message);
        // Setting Dialog Message
        //alertDialog.setMessage(message);
        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_arduino);

        RelativeLayout ad_rlayout = (RelativeLayout) findViewById(R.id.CL_Dialog);
        View viewInflated = getLayoutInflater().inflate(R.layout.action_dialog,ad_rlayout,false);
        // Set up the input
        et_ssid = (EditText) viewInflated.findViewById(R.id.ET_SSID);
        et_pass = (EditText) viewInflated.findViewById(R.id.ET_Pass);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        alertDialog.setView(viewInflated);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), et_ssid.getText()+ "  "+et_pass.getText()+"\n  You clicked on YES", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
