package com.logischtech.smartnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

public class ArticleDetail extends AppCompatActivity {
    int color2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Bundle bundle = getIntent().getExtras();
        String text= bundle.getString("url");
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(text);



        ImageView floatingbtn = (ImageView)findViewById(R.id.floatingbtn);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 int color=1;

                Intent gotoqrcode = new Intent(ArticleDetail.this, CastDevicefromArticleDetail.class);
                gotoqrcode.putExtra("color", color);
                startActivity(gotoqrcode);




            }
        });


//        color2= getIntent().getIntExtra("color", color2);
//        if (color2 == 1){
//            floatingbtn.setImageResource(R.drawable.on);
//            floatingbtn.setEnabled(false);
//        }
//        else {
//            floatingbtn.setImageResource(R.drawable.off);
//        }

    }


    @Override
    public void onBackPressed() {

          super.onBackPressed();
        // Do as you please
        Intent i = new Intent(getApplicationContext(),Dashboard.class);
        startActivity(i);
        //  return;

    }


}
