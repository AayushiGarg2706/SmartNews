package com.logischtech.smartnews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class Secret_News_Choice extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret__news__choice);
        final Button btnnext = (Button)findViewById(R.id.btnnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Secret_News_Choice.this,Secret_News.class);
                startActivity(in);
            }
        });
        btnnext.setEnabled(false);

     //  ImageView  btncollapsed = (ImageView)findViewById(R.id.btnmenu);
//        btncollapsed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               DrawerLayout drawable = (DrawerLayout) findViewById(R.id.activity_secret__news__choice);
//                if (drawable.isDrawerOpen(GravityCompat.START)) {
//
//                  //  btncollapse.setImageResource(R.drawable.menubar);
//                    drawable.closeDrawer(GravityCompat.START);
//                } else {
//                  //  btncollapse.setImageResource(R.drawable.navclick);
//
//                    drawable.openDrawer(GravityCompat.START);
//                }
//
//            }
//        });
//        setNavigationViewListner();



        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.radioButton1){
                    btnnext.setEnabled(true);

                }

                     else if(checkedId == R.id.radioButton2){
                    btnnext.setEnabled(true);

                }
                else {
                    btnnext.setEnabled(false);
                    new AlertDialog.Builder(Secret_News_Choice.this)

                            .setTitle("Error")
                            .setMessage("You must select radio option to move forward .")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();                        }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();



                }

            }
        });

    }

    private void setNavigationViewListner() {
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_drawersecret) ;
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(Secret_News_Choice.this,MainActivity.class);
        startActivity(in);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.connectcastdv:
                Intent i = new Intent(Secret_News_Choice.this, QRcodescanner.class);
                startActivity(i);
                break;
            case R.id.scanarticle:
                Intent in = new Intent(Secret_News_Choice.this, ArticleScanner.class);
                startActivity(in);
                break;

            case R.id.scanadd:

                Intent inn = new Intent(Secret_News_Choice.this, ScanAdd.class);
                startActivity(inn);
                break;


            case R.id.dash :

                Intent ind = new Intent(Secret_News_Choice.this, Dashboard.class);
                startActivity(ind);
                break;






        }






            return false;
    }
}
