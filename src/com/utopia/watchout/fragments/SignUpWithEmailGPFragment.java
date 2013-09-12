package com.utopia.watchout.fragments;

/**
 * Created by Doheum on 13. 9. 3.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.utopia.watchout.R;
import com.utopia.watchout.WOConstant;
import com.utopia.watchout.activities.MainActivity;
import com.utopia.watchout.helpers.KeyboardHelper;

public class SignUpWithEmailGPFragment extends SignFragment {

    public interface OnFacebookSignUpListener {

        void onFacebookSignUpStart();

        void onFacebookSignUpFinish(String user_id, String username);

        void onFacebookSignUpError(String title, String message);

    }// end of interface

    private OnFacebookSignUpListener mListener = null;

    private EditText mUsernameEdit = null;
    private EditText mPasswordEdit = null;
    private EditText mPasswordHintEdit = null;
    private EditText mEmailEdit = null;
    private View mUsernameDiv = null;
    private View mEmailDiv = null;
    private View mPasswordDiv = null;
    private Button mSignUp = null;
    private CheckBox mPostFb = null;

    private String mEmail = null;
    private String mUserName = "";

    private int mUsernameCount = 0;
    private int mEmailCount = 0;
    private int mPasswordCount = 0;

    private String mUsernameString = null;
    private String mPasswordString = null;

    // R.string.hey_s_change_your_username_if_you_want_but_please_make_sure_to_create_a_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arg = getArguments();
        mEmail = arg.getString(WOConstant.BundleKey.EXTRA_EMAIL);
        mUserName = arg.getString(WOConstant.BundleKey.EXTRA_USERNAME);

        if (mEmail == null)
            mEmail = "";

        if (mUserName == null)
            mUserName = "";

        mUsernameCount = mUserName.length();
        mEmailCount = mEmail.length();

        View view = inflater.inflate(R.layout.gp_signup, null);

        mUsernameEdit = (EditText) view.findViewById(R.id.username);
        mUsernameEdit.setText(mUserName);
        mPasswordEdit = (EditText) view.findViewById(R.id.password);
        mPasswordHintEdit = (EditText) view.findViewById(R.id.password_hint);
        mEmailEdit = (EditText) view.findViewById(R.id.email);
        mEmailEdit.setText(mEmail);

        mUsernameDiv = view.findViewById(R.id.username_divider);
        mEmailDiv = view.findViewById(R.id.email_divider);
        mPasswordDiv = view.findViewById(R.id.password_divider);

        mUsernameEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mUsernameDiv.setBackgroundColor(getResources().getColor(
                            R.color.grey_hex_66));
                } else {
                    mUsernameDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_cc));
                }
            }
        });

        mUsernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUsernameCount = mUsernameEdit.getText().toString().length();
                isEnableSignUp();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mEmailEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEmailDiv
                            .setBackgroundColor(getResources().getColor(R.color.grey_hex_66));
                } else {
                    mEmailDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_cc));
                }
            }
        });

        mEmailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEmailCount = mEmailEdit.getText().toString().length();
                isEnableSignUp();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPasswordEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mPasswordDiv.setBackgroundColor(getResources().getColor(
                            R.color.grey_hex_66));
                } else {
                    mPasswordDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_cc));
                }
            }
        });

        mPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPasswordCount = mPasswordEdit.getText().toString().length();
                isEnableSignUp();
                if (mPasswordCount == 0) {
                    mPasswordHintEdit.setVisibility(View.VISIBLE);
                } else {
                    mPasswordHintEdit.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPasswordEdit.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                trySignUp();
                return false;
            }
        });

        mPostFb = (CheckBox) view.findViewById(R.id.facebook_post_checkbox);
//        if (FBHelper.hasPublishPermission(getActivity())) {
//            mPostFb.setChecked(true);
//        }

        mPostFb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    if (!FBHelper.hasPublishPermission(getActivity())) {
//                        FBHelper.reqPermission(getActivity(), new OnRequestAPIListener() {
//                            @Override
//                            public void onRequestAPIStart() {
//                            }
//
//                            @Override
//                            public void onRequestAPIProcess() {
//                            }
//
//                            @Override
//                            public void onRequestAPIError(String title, String message) {
//                                buttonView.setChecked(false);
//                            }
//
//                            @Override
//                            public void onRequestAPIFinish(Object resultGraph) {
//                                GoogleAnalyticsHelper
//                                        .sendEventAcceptFbPublishPermission(getActivity());
//                            }
//                        }, PermissionType.PUBLISH);
//                    }
                }
            }
        });

        mSignUp = (Button) view.findViewById(R.id.fb_signup);
        mSignUp.setEnabled(false);
        mSignUp.setTextColor(getResources().getColor(R.color.grey_hex_eb));
        mSignUp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                trySignUp();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity act = getActivity();
        if (act instanceof OnFacebookSignUpListener)
            mListener = (OnFacebookSignUpListener) act;

        if (mUsernameString != null)
            mUsernameEdit.setText(mUsernameString);
        if (mPasswordString != null)
            mPasswordEdit.setText(mPasswordString);

        mUsernameDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_66));
        mEmailDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_66));
        mPasswordDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_66));

        KeyboardHelper.show(getActivity(), mPasswordEdit);
        if (mUsernameEdit.getText() == null || mUsernameEdit.getText().length() == 0)
            KeyboardHelper.show(getActivity(), mUsernameEdit);
    }

    @Override
    public void onPause() {
        super.onPause();
        mListener = null;

        mUsernameString = mUsernameEdit.getText().toString();
        mPasswordString = mPasswordEdit.getText().toString();
    }

    public void isEnableSignUp() {
        if (mUsernameCount > 0 && mEmailCount > 0 && mPasswordCount > 5) {
            mSignUp.setEnabled(true);
            mSignUp.setTextColor(getResources().getColor(R.color.grey_hex_ff));
        } else {
            mSignUp.setEnabled(false);
            mSignUp.setTextColor(getResources().getColor(R.color.grey_hex_eb));
        }
    }

    private void trySignUp() {
        String username = mUsernameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        String email = mEmailEdit.getText().toString();

        // temporary intent to change sign activity to main
        Intent i = new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(i);

//        VinglePreference.addToServerGpUserName(getActivity(), email, username);
//        VinglePreference.addToServerGpPassword(getActivity(), email, password);
//        VinglePreference.setIsGpConnected(getActivity(), true);

//        try {
//            SignHelper.checkValidation(getActivity(), username, email, password);
//            getVingleService()
//                    .signUpWithFacebook(username, email, password, mSignUpResponseHandler);
//            if(mListener != null)
//                mListener.onFacebookSignUpStart();
//        } catch (SignException e) {
//            notifyError(e.getErrorTitle(), e.getErrorMessage());
//        }
    }

    private void notifyError(String title, String message) {
        if (mListener != null)
            mListener.onFacebookSignUpError(title, message);
    }

}// end of class

