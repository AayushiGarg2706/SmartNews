package com.logischtech.smartnews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class ArticleScanner extends AppCompatActivity {
    SurfaceView cameraview;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    Button btnarticle;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {

                        cameraSource.start(cameraview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_scanner);
        cameraview = (SurfaceView) findViewById(R.id.articlescan);
        textView = (TextView) findViewById(R.id.articletxt);

      final  Button btnscanarticle = (Button)findViewById(R.id.btncameraon);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");

        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                   // .setRequestedPreviewSize(1280, 1024)
                    .setRequestedPreviewSize(1280,720)
                    .setRequestedFps(1.0f)



                    .setAutoFocusEnabled(true)
                    .build();
            cameraview.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                      ActivityCompat.requestPermissions(ArticleScanner.this,
                              new String[]{android.Manifest.permission.CAMERA},RequestCameraPermissionID);
                        return;
                    }
                    try {

                        cameraSource.start(cameraview.getHolder());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                 @Override
                 public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    // setCameraDisplayOrientationAPI8();

                 }
                 public void surfaceDestroyed(SurfaceHolder holder) {
                     cameraSource.stop();

                 }
             });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                    cameraSource.stop();

                }

                 @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size()!= 0){
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                btnscanarticle.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        if (InternetStatus.getInstance(getApplicationContext()).isOnline()){
                                            StringBuilder stringBuilder = new StringBuilder();
                                            for(int i = 0; i<items.size();++i){
                                                TextBlock item = items.valueAt(i);
                                                stringBuilder.append(item.getValue());
                                                stringBuilder.append("\n");
                                                textView.setText(stringBuilder.toString());
                                                cameraSource.stop();
                                                String Title=((TextView) findViewById(R.id.articletxt)).getText().toString();
                                                Intent in  = new Intent(ArticleScanner.this,ArticleDescription.class);
                                                in.putExtra("Title",Title.toString());
                                                startActivity(in);

                                                // new ArticlescanRequest(ArticleScanner.this,getApplicationContext()).execute(Title,Language);
                                            }

                                        }
                                        else{
                                            new AlertDialog.Builder(ArticleScanner.this)

                                                    .setTitle("Error")
                                                    .setMessage("Please check your internet connection .")
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
                        });
                    }

                }
            });
         }
         }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(ArticleScanner.this,MainActivity.class);
        startActivity(in);
    }


}