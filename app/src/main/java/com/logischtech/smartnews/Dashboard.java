package com.logischtech.smartnews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.linkedin.platform.LISessionManager;
import com.logischtech.smartnews.Helpers.InternalStorage;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Models.DashboardArticle;
import Models.SocialLoginType;
import Models.User;

import static com.logischtech.smartnews.InternetStatus.context;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,AdapterView.OnItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {
    private DrawerLayout drawable;
    ImageView btncollapse;


    private Activity dashactivity;

    Button connect;
    Object fromStorage;
    GoogleApiClient googleApiClient;
    private ListView articlelist1;
    private ListView articlelist2;
    private ListView articlelist3;

    private ListView articlelist4;
    private RecyclerView horizontal_recycler_view;
    private RecyclerView horizontal_recycler_view2;
    private RecyclerView horizontal_recycler_view3;
    private RecyclerView horizontal_recycler_view4;

    private CustomAdapter horizontalAdapter;
    private CustomAdapter2 horizontalAdapter2;
    private LinearLayout toplinear;


    private String TWITTER_KEY = "12AYe7MQrFeioLQw7FWFrxFZx";
    private String TWITTER_SECRET = "VrMrhOcIvcLU1QOA0X6eeZk5CnhVEWrFCCeZQ7oqvduofBMYlN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        // DashboardSection section = DashboardSection.TopStories;
        dashactivity = this;
        // toplinear = (LinearLayout)findViewById(R.id.activity_main1);
      //  toplinear.setVisibility(View.VISIBLE);







        FacebookSdk.sdkInitialize(getApplicationContext());
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);



        horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontalview);
        horizontal_recycler_view2= (RecyclerView) findViewById(R.id.horizontalview2);
        horizontal_recycler_view3 = (RecyclerView)findViewById(R.id.horizontalview3);
        horizontal_recycler_view4 = (RecyclerView)findViewById(R.id.horizontalview4);

//        horizontal_recycler_view.addOnItemTouchListener(
//                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        // TODO Handle item click
//                    }
//                })
//        );









      //  spinner.setVisibility(View.VISIBLE);



        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);



        String section = "1";

        new HttpDashboardSection().execute(section);

        String section2 = "2";
        new HttpWorldArticles().execute(section2);



        String section3 = "3";
        new HttpIndiaArticles().execute(section3);
        String section4 = "4";
        new HttpBusinessArticles().execute(section4);

        RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.dashframe1);
        actionbar.bringToFront();
        RelativeLayout actionbar2 = (RelativeLayout) findViewById(R.id.dashframe2);
        actionbar2.bringToFront();
        View view = (View) findViewById(R.id.view);
        view.bringToFront();
     //   spinner = (ProgressBar)findViewById(R.id.progressBar1);


        Button btnscan = (Button) findViewById(R.id.selectscan);
        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Dashboard.this, Choose_Scan.class);
                startActivity(in);

            }
        });


        Button btnconnect = (Button) findViewById(R.id.btnconnect);
        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoqrcode = new Intent(Dashboard.this, QRcodescanner.class);
                startActivity(gotoqrcode);

            }
        });
//        Spinner spinner = (Spinner)findViewById(R.id.selectscan);
//
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.organization_type, R.layout.spinner_item);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        btncollapse = (ImageView) findViewById(R.id.btncollapse);
        btncollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable = (DrawerLayout) findViewById(R.id.activity_dashboard);
                if (drawable.isDrawerOpen(GravityCompat.START)) {

                    //  btncollapse.setImageResource(R.drawable.menubar);
                    drawable.closeDrawer(GravityCompat.START);
                } else {
                    //   btncollapse.setImageResource(R.drawable.navclick);

                    drawable.openDrawer(GravityCompat.START);
                }

            }
        });


        setNavigationViewListner();


    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem userprofile = menu.findItem(R.id.profile);


        try {
            fromStorage = InternalStorage.readObject(getApplicationContext(), "User");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        User user = (User) fromStorage;
        if (user.getLoginType() == SocialLoginType.Facebook) {
            userprofile.setTitle("Facebook");
            userprofile.setIcon(R.drawable.ficon);

        } else if (user.getLoginType() == SocialLoginType.Twitter) {
            // userprofile.setIcon(R.drawable.ticon);
            userprofile.setTitle("Twitter");

        } else if (user.getLoginType() == SocialLoginType.LinkedIn) {
            // userprofile.setIcon(R.drawable.lincon);
            userprofile.setTitle("Linkedin");


        } else if (user.getLoginType() == SocialLoginType.GooglePlus) {
            //  userprofile.setIcon(R.drawable.google);
            userprofile.setTitle("GooglePlus");

        }


    }

    @Override
    public void onBackPressed() {

        //   super.onBackPressed();
        // Do as you please

        //  return;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.connectcastdv:
                Intent i = new Intent(Dashboard.this, QRcodescanner.class);
                startActivity(i);
                break;
            case R.id.testConnectDevice:
                Intent tcd = new Intent(Dashboard.this,TestCasting.class);
                startActivity(tcd);
                break;

            case R.id.scanarticle:
                Intent in = new Intent(Dashboard.this, ArticleScanner.class);
                startActivity(in);
                break;

            case R.id.scanadd:

                Intent inn = new Intent(Dashboard.this, ScanAdd.class);
                startActivity(inn);
                break;


            case R.id.secret:
                Intent secret = new Intent(Dashboard.this, Secret_News_Choice.class);
                startActivity(secret);
                break;


            case R.id.about:

                try {
                    fromStorage = InternalStorage.readObject(getApplicationContext(), "User");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                User result = (User) fromStorage;

                if (result.getLoginType() == SocialLoginType.Facebook) {

                    if (InternetStatus.getInstance(getApplicationContext()).isOnline()) {
                        LoginManager.getInstance().logOut();
                        AccessToken.setCurrentAccessToken(null);
                        try {
                            InternalStorage.removeObject(getApplicationContext(), "User");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        Intent gotologin = new Intent(Dashboard.this, Login.class);
                        startActivity(gotologin);

                    } else {

                        new AlertDialog.Builder(Dashboard.this)

                                .setTitle("Error")
                                .setMessage("Please check your internet connection .")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }


                } else if (result.getLoginType() == SocialLoginType.LinkedIn) {


                    if (InternetStatus.getInstance(getApplicationContext()).isOnline()) {
                        LISessionManager.getInstance(getApplicationContext()).clearSession();
                        try {
                            InternalStorage.removeObject(getApplicationContext(), "User");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        Intent gotologin = new Intent(Dashboard.this, Login.class);
                        startActivity(gotologin);


                    } else {
                        new AlertDialog.Builder(Dashboard.this)

                                .setTitle("Error")
                                .setMessage("Please check your internet connection .")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }


                } else if (result.getLoginType() == SocialLoginType.GooglePlus) {

                    if (InternetStatus.getInstance(getApplicationContext()).isOnline()) {
                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                try {
                                    InternalStorage.removeObject(getApplicationContext(), "User");
                                    Intent gotologin = new Intent(Dashboard.this, Login.class);
                                    startActivity(gotologin);

                                    //  updateUI(false);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                    } else {
                        new AlertDialog.Builder(Dashboard.this)

                                .setTitle("Error")
                                .setMessage("Please check your internet connection .")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }


//                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
//          @Override
//           public void onResult(@NonNull Status status) {
//
//               updateUI(false);
//              try {
//                  InternalStorage.removeObject(getApplicationContext(),"User");
//                  Intent  gotologin= new Intent(Dashboard.this,Login.class);
//                  startActivity(gotologin);
//
//                  updateUI(false);
//
//              } catch (IOException e) {
//                  e.printStackTrace();
//              } catch (ClassNotFoundException e) {
//                  e.printStackTrace();
//              }
//
//
//
//
//          }
//       });
//
//                    Plus.AccountApi.clearDefaultAccount(googleApiClient);
//                    googleApiClient.disconnect();
//                    googleApiClient.connect();
//                    try {
//                  InternalStorage.removeObject(getApplicationContext(),"User");
//                  Intent  gotologin= new Intent(Dashboard.this,Login.class);
//                  startActivity(gotologin);
//
//                  updateUI(false);
//
//              } catch (IOException e) {
//                  e.printStackTrace();
//              } catch (ClassNotFoundException e) {
//                  e.printStackTrace();
//              }


                } else if (result.getLoginType() == SocialLoginType.Twitter) {

                    if (InternetStatus.getInstance(getApplicationContext()).isOnline()) {
                        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                        if (twitterSession != null) {
                            TwitterCore.getInstance().getSessionManager().clearActiveSession();
                        }
                        try {
                            InternalStorage.removeObject(getApplicationContext(), "User");
                            Intent gotologint = new Intent(Dashboard.this, Login.class);
                            startActivity(gotologint);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else {
                        new AlertDialog.Builder(Dashboard.this)

                                .setTitle("Error")
                                .setMessage("Please check your internet connection .")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }

//

                }


        }

        return false;
    }

    private void updateUI(boolean isLogin) {
        if (isLogin) {
            //  profile_section.setVisibility(View.VISIBLE);
//            glogout.setVisibility(View.VISIBLE);
//            gsignin.setVisibility(View.GONE);


        } else {

            //  profile_section.setVisibility(View.GONE);
//            glogout.setVisibility(View.GONE);
//            gsignin.setVisibility(View.VISIBLE);


        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class HttpDashboardSection extends AsyncTask<String, String, DashboardArticle[]> {
        private ProgressDialog progress = new ProgressDialog(Dashboard.this);

        @Override
        protected DashboardArticle[] doInBackground(String... params) {

            String section = params[0];
            String url = "http://52.187.186.166/smartnews/api/SmartNewApi/GetDashboardArticles?" + "section=" + section;

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new CustomResponseErrorHandler());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            // DashboardArticle[] response = restTemplate.getForObject(url, DashboardArticle[].class);
            List<DashboardArticle> answerList = new ArrayList<DashboardArticle>();
            DashboardArticle[] response = restTemplate.getForObject(url, DashboardArticle[].class);

            try {
                InternalStorage.writeObject(Dashboard.this,"Articledetail",response);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return response;
        }

        @Override
        protected void onPreExecute() {
            this.progress.setMessage("Please Wait");
            this.progress.show();


        }

        @Override
        protected void onPostExecute(DashboardArticle[] articledesc) {
            super.onPostExecute(articledesc);

            horizontalAdapter=new CustomAdapter(dashactivity,getApplicationContext(), articledesc);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
            horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
            horizontal_recycler_view.setAdapter(horizontalAdapter);


//            spinner.setVisibility(View.GONE);

            if (progress.isShowing()) {
                progress.dismiss();
//
          }


//            articlelist1 = (ListView) findViewById(R.id.articlelist1);
//            Utility.setListViewHeightBasedOnChildren(articlelist1);
//            ArticleAdapter adapters = new ArticleAdapter(dashactivity, articledesc);
//            articlelist1.setAdapter(adapters);

//            ArticleAdapter adapters = new ArticleAdapter(dashactivity, articledesc);
//            com.logischtech.smartnews.HorizontalListView hListView = ( com.logischtech.smartnews.HorizontalListView) findViewById(R.id.hlistview);
//            hListView.setAdapter(adapters);


        }


        private class CustomResponseErrorHandler implements ResponseErrorHandler {
            @Override

            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        }
    }

    private class HttpWorldArticles extends AsyncTask<String, String, DashboardArticle[]> {
        private ProgressDialog progress = new ProgressDialog(Dashboard.this);

        @Override
        protected DashboardArticle[] doInBackground(String... params) {
            String section2 = params[0];
            String url = "http://52.187.186.166/smartnews/api/SmartNewApi/GetDashboardArticles?" + "section=" + section2;

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new CustomResponseErrorHandler());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            List<DashboardArticle> answerList = new ArrayList<DashboardArticle>();
            DashboardArticle[] response = restTemplate.getForObject(url, DashboardArticle[].class);


            return response;
        }

        @Override
        protected void onPreExecute() {
            this.progress.setMessage("Please Wait");
            this.progress.show();

        }

        @Override
        protected void onPostExecute(DashboardArticle[] articledesc) {
            super.onPostExecute(articledesc);


             horizontalAdapter2=new CustomAdapter2( dashactivity,  articledesc);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
            horizontal_recycler_view2.setLayoutManager(horizontalLayoutManagaer);
            horizontal_recycler_view2.setAdapter(horizontalAdapter2);
            if (progress.isShowing()) {
                progress.dismiss();

            }




//             articlelist2 = (ListView) findViewById(R.id.articlelist2);
//            Utility.setListViewHeightBasedOnChildren(articlelist2);
//            ArticleAdapter2 articleAdapter2 = new ArticleAdapter2(dashactivity, articledesc);
//            articlelist2.setAdapter(articleAdapter2);


//            HLVAdapter adapters = new HLVAdapter(dashactivity, articledesc);
//            mRecyclerView.setAdapter(adapters);


        }


        private class CustomResponseErrorHandler implements ResponseErrorHandler {
            @Override

            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        }
    }

    private class HttpIndiaArticles extends AsyncTask<String, String, DashboardArticle[]> {
        private ProgressDialog progress = new ProgressDialog(Dashboard.this);


        @Override
        protected DashboardArticle[] doInBackground(String... params) {

            String section3 = params[0];
            String url = "http://52.187.186.166/smartnews/api/SmartNewApi/GetDashboardArticles?" + "section=" + section3;

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new CustomResponseErrorHandler());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            List<DashboardArticle> answerList = new ArrayList<DashboardArticle>();
            DashboardArticle[] response = restTemplate.getForObject(url, DashboardArticle[].class);


            return response;
        }

        @Override
        protected void onPreExecute() {
            this.progress.setMessage("Please Wait");
            this.progress.show();

        }

        @Override
        protected void onPostExecute(DashboardArticle[] articledesc) {
            super.onPostExecute(articledesc);

//            articlelist3 = (ListView) findViewById(R.id.articlelist3);
//            Utility.setListViewHeightBasedOnChildren(articlelist3);
//            ArticleAdapter3 adapters = new ArticleAdapter3(dashactivity, articledesc);
//            articlelist3.setAdapter(adapters);
//

            horizontalAdapter2=new CustomAdapter2(dashactivity,articledesc);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
            horizontal_recycler_view3.setLayoutManager(horizontalLayoutManagaer);
            horizontal_recycler_view3.setAdapter(horizontalAdapter2);
            if (progress.isShowing()) {
                progress.dismiss();

            }


        }


        private class CustomResponseErrorHandler implements ResponseErrorHandler {
            @Override

            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        }
    }

    private class HttpBusinessArticles extends AsyncTask<String,String,DashboardArticle[]> {
        private ProgressDialog progress = new ProgressDialog(Dashboard.this);

        @Override
        protected DashboardArticle[] doInBackground(String... params) {
            String section4 = params[0];
            String url = "http://52.187.186.166/smartnews/api/SmartNewApi/GetDashboardArticles?" + "section=" + section4;

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new CustomResponseErrorHandler());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            List<DashboardArticle> answerList = new ArrayList<DashboardArticle>();
            DashboardArticle[] response = restTemplate.getForObject(url, DashboardArticle[].class);


            return response;
        }

        @Override
        protected void onPreExecute() {
            this.progress.setMessage("Please Wait");
            this.progress.show();

        }

        @Override
        protected void onPostExecute(DashboardArticle[] articledesc) {
            super.onPostExecute(articledesc);

            horizontalAdapter2=new CustomAdapter2(dashactivity,articledesc);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
            horizontal_recycler_view4.setLayoutManager(horizontalLayoutManagaer);
            horizontal_recycler_view4.setAdapter(horizontalAdapter2);

            if (progress.isShowing()) {
                progress.dismiss();

            }


//
//
//
// articlelist4 = (ListView) findViewById(R.id.articlelist4);
//            Utility.setListViewHeightBasedOnChildren(articlelist1);
//            ArticleAdapter4 adapters = new ArticleAdapter4(dashactivity, articledesc);
//            articlelist4.setAdapter(adapters);
//

        }


        private class CustomResponseErrorHandler implements ResponseErrorHandler {
            @Override

            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        }
    }}