package com.utopia.watchout.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.view.MenuItem;
import com.utopia.watchout.R;
import com.utopia.watchout.fragments.FacebookConnectAccountFragment;
import com.utopia.watchout.fragments.SignInFragment;
import com.utopia.watchout.fragments.SignUpFragment;
import com.utopia.watchout.fragments.SignUpWithEmailFBFragment;
import com.utopia.watchout.fragments.SignUpWithEmailGPFragment;

/**
 * Activity which is started at the first time app launched.
 *
 * @author huewu.yang & The Finest Artist
 */
// extends PopUpActivity implements
//OnVingleSignUpListener, OnVingleSignInListener,
//        OnFacebookSignUpListener, OnConnectFacebookListener
public class SignInUpActivity extends WOPopUpActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sign_progress);

        // Action Bar
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.actionbar_sign_up_custom);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        /** Log In **/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.watchout.ACTION_LOGIN");
        intentFilter.addAction("com.watchout.ACTION_SIGNUPPROCESS");
        registerReceiver(mReceiver, intentFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    protected void onResume() {
        super.onResume();
        Bundle b = getIntent().getExtras();
        String sign = b.getString("SIGN");
        if (sign.equals("IN")) {
            showSignIn(null);
        } else if (sign.equals("UP")) {
            showSignUp(null, null);
        } else if (sign.equals("CONNECT_ACCOUNT")) {
            String username = b.getString("username");
            String email = b.getString("email");
            String uid = b.getString("uid");
            showConnectAccount(username, email, uid);
        } else if (sign.equals("UP_FACEBOOK")) {
            String username = b.getString("username");
            String email = b.getString("email");
            showSignUpFacebook(username, email);
        } else if (sign.equals("UP_GOOGLE_PLUS")) {
            String username = b.getString("username");
            String email = b.getString("email");
            showSignUpGP(username, email);
        } else {
            // Error....
        }
        //com.facebook.Settings.publishInstallAsync(this, VingleDebug.getFacebookAppID(this));
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        //GAServiceManager.getInstance().dispatch();
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    };

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SignInUpActivity.this, SignActivity.class);
        startActivity(i);
    }


    // //////////////////////////////////////////////////////////////
    // Fragment Helper
    // //////////////////////////////////////////////////////////////

    protected void showSignIn(String username) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("signin") == null) {
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = new SignInFragment();
            f.setArguments(new Bundle());
            f.getArguments().putString("username", username);
            ft.replace(R.id.content, f, "signin");
            ft.commitAllowingStateLoss();
        }
        setTitle(getString(R.string.sign_in));
    }

    protected void showSignUp(String username, String email) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("signup") == null) {
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = new SignUpFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("email", email);
            f.setArguments(bundle);
            ft.replace(R.id.content, f, "signup");
            ft.commitAllowingStateLoss();
        }

        setTitle(getString(R.string.sign_up));
    }

    private void showSignUpFacebook(String username, String email) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("signupfb") == null) {
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = new SignUpWithEmailFBFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("email", email);
            f.setArguments(bundle);
            ft.replace(R.id.content, f, "signupfb");
            ft.commitAllowingStateLoss();
        }

        setTitle(getString(R.string.sign_up));
    }

    private void showSignUpGP(String username, String email) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("signupgp") == null) {
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = new SignUpWithEmailGPFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("email", email);
            f.setArguments(bundle);
            ft.replace(R.id.content, f, "signupgp");
            ft.commitAllowingStateLoss();
        }

        setTitle(getString(R.string.sign_up));
    }

    protected void showConnectAccount(String username, String email, String uid) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("connectaccount") == null) {
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = new FacebookConnectAccountFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("email", email);
            bundle.putString("uid", uid);
            f.setArguments(bundle);
            ft.replace(R.id.content, f, "connectaccount");
            ft.commitAllowingStateLoss();
        }

        setTitle(getString(R.string.sign_up));
    }

    private void showConnectWithFacebook(String uid) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("connectwithfacebook") == null) {
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = new SignInFragment();
            Bundle bundle = new Bundle();
            bundle.putString("uid", uid);
            f.setArguments(bundle);
            ft.setCustomAnimations(R.anim.appear_from_left, R.anim.disappear_to_left,
                    R.anim.appear_from_left,
                    R.anim.disappear_to_right);
            ft.replace(R.id.content, f, "connectwithfacebook");
            ft.addToBackStack("connectwithfacebook");
            ft.commitAllowingStateLoss();
        }

        setTitle(getString(R.string.connect_account));
    }

    // //////////////////////////////////////////////////////////////
    // Private Helper
    // //////////////////////////////////////////////////////////////

//    protected void showDashboard(String username) {
//
//        VinglePreference.setIsSignUpProcessing(this, false);
//        VinglePreference.setSignUpProcessing(this, IVingleService.SIGN_UP_STEP_DONE);
//
//        Intent i = new Intent(IntentKey.ACTION_SHOW_HOME);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        i.putExtra("username", username);
//        startActivity(i);
//
//        /** on your login method: **/
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("com.vingle.ACTION_LOGIN");
//        sendBroadcast(broadcastIntent);
//
//        finish();
//    }

    protected void showSignUpProcess(String username, boolean facebook) {
//        Intent i = new Intent(SignInUpActivity.this, SignUpProcessActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        i.putExtra("username", username);
//        i.putExtra("facebook", facebook);
//        startActivity(i);
//        finish();
    }

    protected void showForgetPassword() {
        // launch our web.
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://www.watchout.org/users/password/new"));
        startActivity(i);
    }

    protected void showError(final String title, final String message) {
//        ErrorDialogFragment errorDialog = new ErrorDialogFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("title", title);
//        bundle.putString("message", message);
//        errorDialog.setArguments(bundle);
//        errorDialog.show(getSupportFragmentManager(), "Error Dialog");
    }

}// end of class
