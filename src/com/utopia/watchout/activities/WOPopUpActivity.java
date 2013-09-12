package com.utopia.watchout.activities;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.utopia.watchout.R;

/**
 * Created by Doheum on 13. 8. 29.
 */
public abstract class WOPopUpActivity extends SherlockFragmentActivity {

    private String TAG = "";
   // private DefaultEventDispatcher mEventDispatcher = null;
    public boolean mDonepressed = false;

    @Override
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        TAG = this.getComponentName().getShortClassName();
        //VingleService.startVingleService(this, mVingleConn);

        //mEventDispatcher = new DefaultEventDispatcher(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //VingleEventBus.getInstance().register(mEventDispatcher);
        //com.facebook.Settings.publishInstallAsync(this, WODebug.getFacebookAppID(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //VingleEventBus.getInstance().unregister(mEventDispatcher);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Bug on API Level > 11.
        // Don't call super()
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if (mVingleConn != null && mVingle != null)
        //    unbindService(mVingleConn);
    }

    // /////////////////////////////////////////////////////////
    // abstract methods.
    // /////////////////////////////////////////////////////////
//    abstract void onVingleServiceConnected();
//
//    abstract void onVingleServiceDisconnected();

    // /////////////////////////////////////////////////////////
    // IVingleActivity implementations.
    // /////////////////////////////////////////////////////////

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        View custom = getSupportActionBar().getCustomView();

        if (custom == null) {
            getSupportActionBar().setTitle(title);
        } else {
            ((TextView) custom.findViewById(R.id.title)).setText(title);
        }
    }

    public void setTitle(String title) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        View custom = getSupportActionBar().getCustomView();

        if (custom == null) {
            getSupportActionBar().setTitle(title);
        } else {
            ((TextView) custom.findViewById(R.id.title)).setText(title);
        }
    }

    // /////////////////////////////////////////////////////////
    // Vingle Service Connection..
    // /////////////////////////////////////////////////////////
//    private IVingleService mVingle = null;
//
//    public IVingleService getVingleService() {
//        return mVingle;
//    }
//
//    protected ServiceConnection mVingleConn = new ServiceConnection() {
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            VingleDebug.LogDebug(TAG, "Service Disconnected");
//            mVingle = null;
//            onVingleServiceDisconnected();
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            VingleDebug.LogDebug(TAG, "Service Connected");
//            mVingle = ((VingleService.LocalBinder) service).getService();
//            onVingleServiceConnected();
//        }
//    };


}// end of class