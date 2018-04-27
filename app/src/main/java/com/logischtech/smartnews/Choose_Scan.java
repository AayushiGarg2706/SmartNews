package com.logischtech.smartnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose_Scan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__scan);

        Button scanarticle = (Button)findViewById(R.id.scanarticle);
        Button scanad= (Button)findViewById(R.id.scanad);
        scanad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choose_Scan.this,ScanAdd.class);
                startActivity(i);
                finish();

            }
        });

        scanarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Choose_Scan.this, ArticleScanner.class);
                startActivity(in);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

          super.onBackPressed();
        finish();

        //  return;

    }
}
