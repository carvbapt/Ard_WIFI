package com.sauca.ard_wifi;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
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

    public String str_ssid = "WIFIARD";
    public String str_pass = "123456768";
    public String str;

    public static WifiManager wf;
    public static NetworkInfo wf_Active;
    public static ConnectivityManager wf_Conect;

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
        if (v == findViewById(R.id.IB_Back)) {
            finish();
        }else if(v== findViewById(R.id.BT_Ssid)){
            alertView(getString(R.string.Ssid));
        }else if(v== findViewById(R.id.BT_Scan)){
            //Toast.makeText(this, " Call WIFI", Toast.LENGTH_SHORT).show();
            str=getString(R.string.Scan) +str_ssid;
            bt_scan.setText(str);
            bt_scan.setBackgroundColor(ContextCompat.getColor(this, R.color.LightRed));
            chk_WIFI("C");
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
        str_ssid=et_ssid.getHint().toString();
        str_pass=et_pass.getHint().toString();
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        alertDialog.setView(viewInflated);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke YES event
                if(!et_ssid.getText().toString().equals("")) {
                    str_ssid = et_ssid.getText().toString();
                    str_pass = et_pass.getText().toString();
                }
                Toast.makeText(getApplicationContext(), str_ssid+ "  "+str_pass+"\n  You clicked on YES", Toast.LENGTH_SHORT).show();
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

    ///////////////////  WIFI SCAN ///////////////////////////////////////////////////////////

    public void chk_WIFI(String act) {

        String stg,netw="";

        // WIFI - DISABLED
        wf = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(wf.getWifiState()== 3) {
            wf.setWifiEnabled(false);
        }else
            wf.setWifiEnabled(true);

        // WIFI
        wf_Conect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Connectivity Manager object to check connection
        wf_Active = wf_Conect.getActiveNetworkInfo();
       // wf.getWifiState();

        for(int i = 0; i < wf.getScanResults().size(); i++){
            stg=wf.getScanResults().get(i).toString();
            if(stg.substring(stg.indexOf(":")+2,stg.indexOf(",")).equals(str_ssid)) {
                netw = (stg.substring(stg.indexOf(":") + 2, stg.indexOf(",")));
                break;
            }
        }

        if(netw.equals(str_ssid)){
            str=getString(R.string.Wifi) +wf_Active.getExtraInfo() + getString(R.string.Conect);
            if (act.equals("M"))
                Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
               // Main.st=str;
            else {
                bt_scan.setText(str);
                bt_scan.setBackgroundColor(ContextCompat.getColor(this, R.color.LightGreen));
            }
        }else { // WIFI FOUND AND CONNECTED
            str = getString(R.string.Wifi) + str_ssid + getString(R.string.NConect2);
                if (act.equals("M"))
                    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                    //Main.st=str;
                else {
                    bt_scan.setText(str);
                    bt_scan.setBackgroundColor(ContextCompat.getColor(this, R.color.LightRed));
                }
        }
        //Toast.makeText(this, wf_Active.getExtraInfo(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, R.string.Conect, Toast.LENGTH_SHORT).show();*/
    }
}
