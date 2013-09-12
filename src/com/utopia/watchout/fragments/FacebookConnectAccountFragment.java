package com.utopia.watchout.fragments;

/**
 * Created by Doheum on 13. 9. 3.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.utopia.watchout.R;
import com.utopia.watchout.WOConstant;

public class FacebookConnectAccountFragment extends SignFragment {

    public interface OnConnectFacebookListener {

        void onRequestConnectWithFacebook(String uid);

        void onRequestSignUpWithFacebook(String username, String email);

    }

    private OnConnectFacebookListener mListener = null;

    private TextView mUsername = null;
    private Button mHaveAccount = null;
    private Button mDontHaveAccount = null;

    private String mUserName;
    private String mEmail;
    private String mUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arg = getArguments();
        mEmail = arg.getString(WOConstant.BundleKey.EXTRA_EMAIL);
        mUserName = arg.getString(WOConstant.BundleKey.EXTRA_USERNAME);
        mUid = arg.getString(WOConstant.BundleKey.EXTRA_UID);

        View v = inflater.inflate(R.layout.fb_connect_account, null);

        mUsername = (TextView) v.findViewById(R.id.hey_username);
        mUsername.setText(String.format(getString(R.string.hey_comma_s), mUserName));
        mHaveAccount = (Button) v.findViewById(R.id.already_have_account);
        mHaveAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRequestConnectWithFacebook(mUid);
                }
            }
        });

        mDontHaveAccount = (Button) v.findViewById(R.id.dont_have_account);
        mDontHaveAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRequestSignUpWithFacebook(mUserName, mEmail);
                }
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity act = getActivity();
        if (act instanceof OnConnectFacebookListener)
            mListener = (OnConnectFacebookListener) act;

    }

    @Override
    public void onPause() {
        super.onPause();
        mListener = null;
    }

}// end of class

