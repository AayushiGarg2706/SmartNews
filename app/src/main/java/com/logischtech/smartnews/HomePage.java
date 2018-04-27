package com.logischtech.smartnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        String username = getIntent().getStringExtra("username");
        String userid = getIntent().getStringExtra("id");
        TextView name = (TextView)findViewById(R.id.tname);
        TextView id = (TextView)findViewById(R.id.tid);
        name.setText(username);
        id.setText(userid);

    }
}
