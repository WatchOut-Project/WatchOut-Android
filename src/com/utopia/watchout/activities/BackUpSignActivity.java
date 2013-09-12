package com.utopia.watchout.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.utopia.watchout.R;
import com.utopia.watchout.WODebug;
import com.utopia.watchout.custom.view.WOToast;
import com.utopia.watchout.helpers.FBHelper;

import java.util.Arrays;


/**
 * Activity which is started at the first time app launched.
 *
 * @author huewu.yang & The Finest Artist
 */
public class BackUpSignActivity extends WOPopUpActivity {

    private boolean mFinishApplication = false;
    private static Handler mUIHandler = new Handler();;
    private FBHelper mFacebook = null;
    private boolean isFetching = false;
    private String email;

    private Button mSignIn;
    private Button mSignUp;
    private Button mSignInFb;
    private Button mSignInGp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.act_sign);

        Log.i("Watch Out","HELLO");

        mSignIn = (Button) findViewById(R.id.signin);
        mSignUp = (Button) findViewById(R.id.signup);
        mSignInFb = (Button) findViewById(R.id.signin_fb);
        mSignInGp = (Button) findViewById(R.id.signin_gp);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BackUpSignActivity.this,SignInUpActivity.class);
                i.putExtra("SIGN", "IN");
                startActivity(i);
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BackUpSignActivity.this, SignInUpActivity.class);
                i.putExtra("SIGN", "UP");
                startActivity(i);
            }
        });

        mSignInFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WODebug.LogInfo("FB clicked");
                // start Facebook Login
                //signUpFB();
            }
        });

        mSignInGp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get google plus session and email of a user


                Intent i = new Intent(BackUpSignActivity.this, SignInUpActivity.class);
                i.putExtra("SIGN", "UP_GOOGLE_PLUS");
                i.putExtra("email","ehgma0821@gmail.com");
                startActivity(i);
            }
        });
//        mSignInGp.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (VingleDebug.initMode(VingleSignActivity.this)) {
//                    if (mPlusClient != null && !mPlusClient.isConnected()) {
//                        if (mConnectionResult == null)
//                        {
//                            VingleEventBus.getInstance().post(
//                            new ShowLoadingEvent(getString(R.string.connecting)));
//                            mPlusClient.connect();
//                        }
//                        else {
//                            try {
//                                mConnectionResult.startResolutionForResult(VingleSignActivity.this,
//                                REQUEST_CODE_RESOLVE_ERR);
//                            } catch (SendIntentException e) {
//                                // Try connecting again.
//                                mConnectionResult = null;
//                                mPlusClient.connect();
//                            }
//                        }
//                    }
//                }
//            }
//        });

        // https://www.googleapis.com/auth/userinfo.email
        // mPlusClient = new PlusClient.Builder(this, this, this)
        // .setScopes(Scopes.PLUS_LOGIN,
        // "https://www.googleapis.com/auth/userinfo.email")
        // .setVisibleActivities("http://schemas.google.com/CommentActivity")
        // .build();

        /** Log In **/
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.vingle.ACTION_LOGIN");
//        intentFilter.addAction("com.vingle.ACTION_SIGNUPPROCESS");
//        registerReceiver(mReceiver, intentFilter);
//
//        if (VingleDebug.TEST)
//            initTestLayout();
//
//        VinglePreference.clearAuth(this);
    }

    protected void onStart() {
        super.onStart();
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    };

    @Override
    public void onBackPressed() {
        tryToFinish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Facebook
        if (Session.getActiveSession() != null)
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

        // Google Plus
        // if (requestCode == REQUEST_CODE_RESOLVE_ERR && resultCode ==
        // RESULT_OK) {
        // mConnectionResult = null;
        // mPlusClient.connect();
        // }
    }

    private void tryToFinish() {

        // TEST LANGUAGE
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else {
            if (mFinishApplication) {
                finish();
            } else {
                WOToast.show(this, getString(R.string.please_press_back_button_again_to_exit_));
                mFinishApplication = true;
                mUIHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFinishApplication = false;
                    }
                }, 3000);
            }
        }
    }

    private void signUpFB(){
        Session session=Session.getActiveSession();
        if (session != null && session.isOpened())
        {
            // user is already logged in, get email permission
            try
            {
                Session.OpenRequest request = new Session.OpenRequest(this);
                request.setPermissions(Arrays.asList("email"));
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback()
            {
                // callback after Graph API response with user object

                @Override
                public void onCompleted(GraphUser user, Response response)
                {
                    if (user != null)
                    {
                        try
                        {
                            email = user.asMap().get("email").toString();
                            WODebug.LogDebug(email);
                        }
                        catch (Exception e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            email="";
                            WODebug.LogDebug(email);
                        }
                    }
                }
            });

        }
        else
        {
            // user is not logged in
            //show login screen

            // start Facebook Login

            try
            {
                Session.OpenRequest request = new Session.OpenRequest(this);
                request.setPermissions(Arrays.asList("email"));
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Session.openActiveSession(this, true, new Session.StatusCallback()
            {

                // callback when session changes state
                @Override
                public void call(final Session session, SessionState state, Exception exception)
                {
                    //session.openForRead(new Session.OpenRequest(this).setPermissions(Arrays.asList("email")));

                    if (session.isOpened())
                    {
                        // make request to the /me API

                        Request.executeMeRequestAsync(session, new Request.GraphUserCallback()

                        {
                            // callback after Graph API response with user object
                            @Override
                            public void onCompleted(GraphUser user, Response response)
                            {
                                if (user != null)
                                {
                                    // publishFeedDialog(session);
                                    try
                                    {
                                        email = user.asMap().get("email").toString();
                                        WODebug.LogDebug(email);
                                    }
                                    catch (Exception e)
                                    {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                        email="";
                                        WODebug.LogDebug(email);
                                    }
                                }
                            }
                        });

                    }
                    else if(session.isClosed())
                    {
                        WODebug.LogDebug("Unable to connect facebook, please try later..");
                    }

                }
            });
        }

    }
}// end of class




