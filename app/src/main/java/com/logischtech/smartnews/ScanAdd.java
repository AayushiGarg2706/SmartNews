package com.logischtech.smartnews;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanAdd extends AppCompatActivity {
    final Activity activity = this;
    TextView contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_add);
        contentTxt = (TextView) findViewById(R.id.scan_content);

        IntentIntegrator integrator = new IntentIntegrator(activity);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);

        integrator.setPrompt("Scan a barcode");

        integrator.setCameraId(0);  // Use a specific camera of the device

        integrator.setBeepEnabled(false);

        integrator.setBarcodeImageEnabled(true);

        integrator.initiateScan();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(ScanAdd.this,MainActivity.class);
        startActivity(in);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("ScanAdd", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("ScanAdd", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                contentTxt.setText(result.getContents());

            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

}
