package com.logischtech.smartnews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.logischtech.smartnews.ApiControllers.HttpSocailLogins;
import com.logischtech.smartnews.Helpers.InternalStorage;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Models.SocialLoginType;

public class Login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    icon_manager icon_manager;
    ImageView fblink;
    LoginButton fblogin;
    ImageView linkedin_login;
    TextView fb_status;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    Button linkedin_logout;
    TextView gname;
    Button btnsecret;
    TextView gemail;
    Button glogout;
    ImageView gsignin;
    ImageView gprofile;
    GoogleApiClient googleApiClient;
    private static final int REQ_Code=9001;
    TwitterLoginButton twitterLoginButton;
    ImageView login_linked;
    ImageView linkedin_profile;
    TextView linkedin_textdetails;
    private String TWITTER_KEY = "12AYe7MQrFeioLQw7FWFrxFZx";
    private String TWITTER_SECRET = "VrMrhOcIvcLU1QOA0X6eeZk5CnhVEWrFCCeZQ7oqvduofBMYlN";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        FacebookSdk.sdkInitialize(getApplicationContext());

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET))
                .debug(true)
                .build();
      Twitter.initialize(config);

        icon_manager =new icon_manager();
//        ((TextView)findViewById(R.id.tvfbicon)).setTypeface(icon_manager.get_icons("ionicons.ttf",this));
//        ((TextView)findViewById(R.id.tvtwittericon)).setTypeface(icon_manager.get_icons("ionicons.ttf",this));
        ((TextView)findViewById(R.id.tvgooglelink)).setTypeface(icon_manager.get_icons("ionicons.ttf",this));
        ((TextView)findViewById(R.id.tvlinkedlink)).setTypeface(icon_manager.get_icons("ionicons.ttf",this));
       // ((TextView)findViewById(R.id.linkicon1)).setTypeface(icon_manager.get_icons("ionicons.ttf",this));

//        profile_section =(RelativeLayout)findViewById(R.id.profile_section);
//        gemail = (TextView)findViewById(R.id.gmail);
//        gname =(TextView)findViewById(R.id.gname);
        computePakageHash();
        initializeControl();




       gsignin =(ImageView)findViewById(R.id.gmail_login_id);

        gsignin.setOnClickListener(this);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();
        fblogin = (LoginButton) findViewById(R.id.fb_login_id);
//        fb_status =(TextView)findViewById(R.id.fb_status);
        login_linked = (ImageView)findViewById(R.id.in_login);

        callbackManager= CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
            };
        profileTracker= new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        FacebookCallback<LoginResult> facebookCallback  = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                    Profile profile = Profile.getCurrentProfile();
                    nextActivity(profile);



               // Toast.makeText(getApplicationContext(),"Logged in ",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

                finish();

            }
        } ;
        fblogin.setReadPermissions("user_friends");
        fblogin.registerCallback(callbackManager,facebookCallback);

        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                login(session);
                // Do something with result, which provides a TwitterSession for making API calls
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
              //  Toast.makeText(getApplicationContext(),"You need to install an twitter app first  ",Toast.LENGTH_SHORT).show();

            }
        });





    }


    private void initializeControl(){
         linkedin_login = (ImageView)findViewById(R.id.in_login);
        linkedin_login.setOnClickListener(this);
 linkedin_logout = (Button)findViewById(R.id.linkedin_logout);
        linkedin_logout.setOnClickListener(this);
//        linkedin_profile =(ImageView)findViewById(R.id.img_profile_linkedin);
//       linkedin_textdetails =(TextView)findViewById(R.id.linkedin_text);

        linkedin_login.setVisibility(View.VISIBLE);
        linkedin_logout.setVisibility(View.GONE);
//        linkedin_profile.setVisibility(View.GONE);
//        linkedin_textdetails.setVisibility(View.GONE);

        linkedin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();

            }
        });
    }

    private void computePakageHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.logischtech.smartnews",PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures){
                MessageDigest md =MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash",Base64.encodeToString(md.digest(),Base64.DEFAULT));
             //   ((TextView)findViewById(R.id.hashcode)).setText(Base64.encodeToString(md.digest(),Base64.NO_WRAP));

            }


        }
        catch (Exception ex){

        }
    }

    private void login(TwitterSession session) {
        String name = session.getUserName();
        String PartnerId = String.valueOf(session.getId());


        String Email = "aaushi007@gmail.com";
        String PicUrl = "";
        String PhoneNumber= "";
        SocialLoginType socialLoginType = SocialLoginType.Twitter;

        int Id = 0;
        if(InternetStatus.getInstance(getApplicationContext()).isOnline()){
            new HttpSocailLogins(Login.this,getApplicationContext()).execute(Id,PartnerId,name,Email,socialLoginType,PicUrl ,PhoneNumber);

        }
        else {
            new AlertDialog.Builder(Login.this)

                    .setTitle("Error")
                    .setMessage("No internet connection available .  .")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }





    }


    @Override
    protected  void  onResume(){
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    public void onBackPressed() {

//        super.onBackPressed();
//
//
//        Intent i = new Intent(getApplicationContext(),Login.class);
//        startActivity(i);
//
//      //  finish();
//
//
//       // System.exit(0);
//        // Do as you please
//
//        return;

    }


    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected  void onStop(){
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (InternetStatus.getInstance(getApplicationContext()).isOnline()){

            LISessionManager.getInstance(getApplicationContext()).onActivityResult(this,requestCode, resultCode, data);

            callbackManager.onActivityResult(requestCode, resultCode, data);
            twitterLoginButton.onActivityResult(requestCode,resultCode,data);
//        final TwitterAuthClient twitterAuthClient = new TwitterAuthClient();
//        if(twitterAuthClient.getRequestCode()==requestCode) {
//            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
//        }
//        else{
//
//        }

            if (requestCode == REQ_Code) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleResult(result);
            }


        }
        else {
            new AlertDialog.Builder(Login.this)

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
    private void nextActivity(Profile profile) {
        if(profile!=null){

            String name = profile.getFirstName();
            String surname = profile.getLastName();
            String PicUrl = profile.getProfilePictureUri(200,200).toString();
            String PartnerId  = profile.getId();
            String Email = "aaushi007@gmail.com";
            String PhoneNumber= "";
            SocialLoginType socialLoginType = SocialLoginType.Facebook;

            int Id = 0;

            if(InternetStatus.getInstance(getApplicationContext()).isOnline()){
                new HttpSocailLogins(Login.this,getApplicationContext()).execute(Id,PartnerId,name,Email,socialLoginType,PicUrl ,PhoneNumber);

            }
            else{
                new AlertDialog.Builder(Login.this)

                        .setTitle("Error")
                        .setMessage("Please check your internet connection .")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                 Login.this.finish();

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }


//            Intent main = new Intent(Login.this,UserProfile.class);
//            main.putExtra("name",profile.getFirstName());
//            main.putExtra("id",profile.getId());
//            main.putExtra("surname",profile.getLastName());
//
//            main.putExtra("imageUrl",profile.getProfilePictureUri(200,200).toString());
//            startActivity(main);



    }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gmail_login_id:
                signin();
                break;

//           case  R.id.glogout:
//                break;
            case R.id.in_login:
                handleLogin();
                break;
           case  R.id.linkedin_logout:
                handleLogout();
                break;

        }

    }

    private void  handleLogout(){
        LISessionManager.getInstance(getApplicationContext()).clearSession();
        linkedin_login.setVisibility(View.VISIBLE);
        linkedin_logout.setVisibility(View.GONE);
//        linkedin_profile.setVisibility(View.GONE);
//        linkedin_textdetails.setVisibility(View.GONE);


    }
    private void handleLogin(){


        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.
                linkedin_login.setVisibility(View.VISIBLE);
               linkedin_logout.setVisibility(View.VISIBLE);
//                linkedin_profile.setVisibility(View.VISIBLE);
//                linkedin_textdetails.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                fetchPersonalNfo();

            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                Log.e("Error",error.toString());

            }
        }, true);





    }
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE,Scope.R_EMAILADDRESS);
    }

    private void fetchPersonalNfo(){
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,public-profile-url,picture-url,email-address,picture-urls::(original))";
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
               JSONObject jsonObject = apiResponse.getResponseDataAsJson();

                try {
                    String name = jsonObject.getString("firstName");
                    String PartnerId = jsonObject.getString("id");
                    String lastname = jsonObject.getString("lastName");
                    String PicUrl = jsonObject.getString("pictureUrl");
                    String Email = jsonObject.getString("emailAddress");
                  //  Picasso.with(getApplicationContext()).load(PicUrl).into(linkedin_profile);
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("First Name :" +firstname + "\n");
//                    sb.append("Last Name:" +lastname + "\n");
//                    sb.append("Id :" +id + "\n");
//
//                    sb.append("Email:" +emailAddress + "\n");
//
//                    linkedin_textdetails.setText(sb);
//
                    String PhoneNumber= "";
                    SocialLoginType socialLoginType = SocialLoginType.LinkedIn;

                    int Id = 0;



//
//            gname.setText(name + id);
//            gemail.setText(email);
                    //  Glide.with(this).load(img_url).into(gprofile);


                    if(InternetStatus.getInstance(getApplicationContext()).isOnline())
                    {
                        new HttpSocailLogins(Login.this,getApplicationContext()).execute(Id,PartnerId,name,Email,socialLoginType,PicUrl ,PhoneNumber);

                    }
                    else{
                        new AlertDialog.Builder(Login.this)

                                .setTitle("Error")
                                .setMessage("Please check your internet connection .")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();                        }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }



                // Success!
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making POST request!
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signin(){

        Intent gin = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(gin,REQ_Code);
       // glogout.setVisibility(View.VISIBLE);
      //  gsignin.setVisibility(View.GONE);

    }
//    private void signout(){
////        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
////            @Override
////            public void onResult(@NonNull Status status) {
////
////                updateUI(false);
////
////            }
////        });
//
//    }
    private void handleResult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String Email = account.getEmail();
            String PicUrl = "";
            String PartnerId= account.getId();
           // String PicUrl = account.getPhotoUrl().toString();
            String surname = getIntent().getStringExtra("surname");
            String PhoneNumber= "";
            SocialLoginType socialLoginType = SocialLoginType.GooglePlus;

            int Id = 0;



//
//            gname.setText(name + id);
//            gemail.setText(email);
          //  Glide.with(this).load(PicUrl).into(gprofile);


            if(InternetStatus.getInstance(getApplicationContext()).isOnline())
            {
                new HttpSocailLogins(Login.this,getApplicationContext()).execute(Id,PartnerId,name,Email,socialLoginType,PicUrl ,PhoneNumber);

            }
            else {
                new AlertDialog.Builder(Login.this)

                        .setTitle("Error")
                        .setMessage("Please check your internet connection .")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();                        }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

            updateUI(true);
        }
        else{
            updateUI(false);
        }

    }

    private void updateUI(boolean isLogin)
    {
        if(isLogin){
          //  profile_section.setVisibility(View.VISIBLE);
            glogout.setVisibility(View.VISIBLE);
            gsignin.setVisibility(View.GONE);


        }
        else {

          //  profile_section.setVisibility(View.GONE);
            glogout.setVisibility(View.GONE);
            gsignin.setVisibility(View.VISIBLE);


        }

    }








}