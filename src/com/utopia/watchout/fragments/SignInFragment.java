package com.utopia.watchout.fragments;

/**
 * Created by Doheum on 13. 9. 3.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.content.res.Resources;

import com.utopia.watchout.R;
import com.utopia.watchout.WOConstant;
import com.utopia.watchout.activities.MainActivity;
import com.utopia.watchout.activities.PostActivity;
import com.utopia.watchout.activities.SignInUpActivity;

public class SignInFragment extends SignFragment {

    public interface OnVingleSignInListener {

        void onSignInStart();

        void onSignInFinish(String username);

        void onSignInError(String title, String message);

        void onForgetPassword();

    }// end of interface

    private OnVingleSignInListener mListener = null;

    private EditText mUsername = null;
    private EditText mPassword = null;
    private EditText mPasswordHint = null;
    private View mUsernameDiv = null;
    private View mPasswordDiv = null;
    private Button mSignIn = null;
    private Button mForgetPwd = null;

    private int mUsernameCount = 0;
    private int mPasswordCount = 0;
    private boolean mRestoreUserInfo = true;

    private String mUsernameString = null;
    private String mPasswordString = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, null);
        mUsername = (EditText) view.findViewById(R.id.username);
        mPassword = (EditText) view.findViewById(R.id.password);
        mPasswordHint = (EditText) view.findViewById(R.id.password_hint);
        mUsernameDiv = view.findViewById(R.id.username_divider);
        mPasswordDiv = view.findViewById(R.id.password_divider);

        mUsername.setOnFocusChangeListener(new OnFocusChangeListener() {
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

        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUsernameCount = mUsername.getText().toString().length();
                isEnableSignIn();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
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

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPasswordCount = mPassword.getText().toString().length();
                isEnableSignIn();
                if (mPasswordCount == 0) {
                    mPasswordHint.setVisibility(View.VISIBLE);
                } else {
                    mPasswordHint.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSignIn = (Button) view.findViewById(R.id.signin);
        mSignIn.setEnabled(false);
        mSignIn.setTextColor(getResources().getColor(R.color.grey_hex_eb));
        mSignIn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                trySignIn(true);
            }
        });

        mForgetPwd = (Button) view.findViewById(R.id.forget_pwd);
        SpannableString text = new SpannableString(getStringWithoutException(R.string.forgot_your_password));
        text.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, text.length(), 0);
        text.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        mForgetPwd.setText(text);
        mForgetPwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onForgetPassword();
            }
        });
        return view;
    }

    public void isEnableSignIn() {
        FragmentActivity act = getActivity();
        if (act == null)
            return;

        Resources resource = act.getApplicationContext().getResources();

        if (mUsernameCount > 0 && mPasswordCount > 5) {
            mSignIn.setEnabled(true);
            mSignIn.setTextColor(resource.getColor(R.color.grey_hex_ff));
        } else {
            mSignIn.setEnabled(false);
            mSignIn.setTextColor(resource.getColor(R.color.grey_hex_eb));

        }
    }

    protected boolean trySignIn(boolean showError) {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String uid = getArguments().getString(WOConstant.BundleKey.EXTRA_UID);

        // temporary intent to change sign activity to main
        Intent i = new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(i);

        return true;
//        try {
//            SignHelper.checkSignInValidation(getActivity(), username, password);
//
//            if (uid == null)
//                getVingleService().signIn(username, password, mSignInResponseHandler);
//            if (mListener != null)
//                mListener.onSignInStart();
//            else {
//                VingleDebug.LogError(username);
//                getVingleService().signInWithFacebookConnect(username, password,
//                        mSignInResponseHandler);
//                if (mListener != null)
//                    mListener.onSignInStart();
//            }
//
//            return true;
//
//        } catch (SignException e) {
//            if (showError)
//                notifyError(e.getErrorTitle(), e.getErrorMessage());
//            return false;
//        }
    }

    private void notifyError(String title, String message) {
        if (mListener != null)
            mListener.onSignInError(title, message);
    }

    @Override
    public void onStart() {
        super.onStart();
        //GoogleAnalyticsHelper.sendViewSignIn(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity act = getActivity();
        if (act instanceof OnVingleSignInListener)
            mListener = (OnVingleSignInListener) act;

        if (mRestoreUserInfo)
            restoreStoredInfo();

        if (mUsernameString != null)
            mUsername.setText(mUsernameString);
        if (mPasswordString != null)
            mPassword.setText(mPasswordString);

        mUsernameDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_cc));
        mPasswordDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_cc));

        showSoftKeyboard(mUsername);
    }

    private void restoreStoredInfo() {
        String username = null;

        // first check argument.
        if (getArguments() != null)
            username = getArguments().getString(WOConstant.BundleKey.EXTRA_USERNAME);

        // then try preference
//        if (username == null)
//            username = VinglePreference.getUserName(getActivity());

        if (username != null && username.length() > 0
                && getArguments().getString(WOConstant.BundleKey.EXTRA_UID) == null)
            mUsername.setText(username);

        mRestoreUserInfo = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mListener = null;

        mUsernameString = mUsername.getText().toString();
        mPasswordString = mPassword.getText().toString();

        hideSoftKeyboard();
    }

    private void showSoftKeyboard(View view) {

        // hide keypad.
        InputMethodManager im = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // im.showSoftInput(getView(), InputMethodManager.SHOW_FORCED);
        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void hideSoftKeyboard() {
        // hide keypad.
        InputMethodManager im = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        im.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

}// end of class
