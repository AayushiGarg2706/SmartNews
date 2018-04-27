package com.logischtech.smartnews;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.logischtech.smartnews.ApiControllers.HttpQrrequest;
import com.logischtech.smartnews.Helpers.InternalStorage;

import java.io.IOException;

import Models.User;

public class CastDevicefromArticleDetail extends AppCompatActivity {
    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    boolean dataReceived = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_devicefrom_article_detail);


        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview1);
        txtResult = (TextView) findViewById(R.id.txtresult1);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


                        ActivityCompat.requestPermissions(CastDevicefromArticleDetail.this,
                                new String[]{android.Manifest.permission.CAMERA},RequestCameraPermissionID);

                        return;
                    }
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                cameraSource.stop();

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0){

                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {

                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            txtResult.setText(qrcodes.valueAt(0).displayValue);
                            //cameraSource.stop();
                            barcodeDetector.release();
                            ///cameraPreview.cancelPendingInputEvents();
                            if (!dataReceived)
                            {
                                dataReceived = true;
//                                Intent intent = new Intent(CastDevicefromArticleDetail.this,QRRequest.class);
//                                intent.putExtra("castid",txtResult.getText().toString());
//                                startActivity(intent);
                                Object fromStorage = null;

                                try {
                                    fromStorage = InternalStorage.readObject(getApplicationContext(),"User");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                User u = (User) fromStorage;





                                Long userid= u.Id;

                                String txt = txtResult.getText().toString();
                                new HttpQrrequest(CastDevicefromArticleDetail.this,getApplicationContext()).execute(userid,txt);



                            }



                        }
                    });
                }

            }
        });



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();


    }


}
