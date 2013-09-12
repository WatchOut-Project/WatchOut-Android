package com.utopia.watchout.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.utopia.watchout.R;
import com.utopia.watchout.activities.MainActivity;

/**
 * Created by Doheum on 13. 8. 29.
 */
public class SignUpFragment extends SherlockFragment {

    public interface OnVingleSignUpListener {

        void onSignUpStart();

        void onSignUpFinish(String user_id, String username);

        void onSignUpError(String title, String message);

    }// end of interface

    private OnVingleSignUpListener mListener = null;
    private EditText mUsername = null;
    private EditText mEmail = null;
    private EditText mPassword = null;
    private EditText mPasswordHint = null;
    private View mUsernameDiv = null;
    private View mEmailDiv = null;
    private View mPasswordDiv = null;

    private int mUsernameCount = 0;
    private int mEmailCount = 0;
    private int mPasswordCount = 0;

    private Button mSignUp = null;

    private TextView mTermOfUse = null;

    private String mUsernameString = null;
    private String mEmailString = null;
    private String mPasswordString = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, null);
        mUsername = (EditText) view.findViewById(R.id.username);
        mEmail = (EditText) view.findViewById(R.id.email);
        mPassword = (EditText) view.findViewById(R.id.password);
        mPasswordHint = (EditText) view.findViewById(R.id.password_hint);
        mUsernameDiv = view.findViewById(R.id.username_divider);
        mEmailDiv = view.findViewById(R.id.email_divider);
        mPasswordDiv = view.findViewById(R.id.password_divider);

        mUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                isEnableSignUp();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEmailCount = mEmail.getText().toString().length();
                isEnableSignUp();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                isEnableSignUp();
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

        mSignUp = (Button) view.findViewById(R.id.signup);
        mSignUp.setEnabled(false);
        mSignUp.setTextColor(getResources().getColor(R.color.grey_hex_eb));
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                CharSequence text = "Sign-up!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                trySignUp();
            }
        });

        String string_format = getString(R.string.by_creating_an_account_you_agree_to_our_s);
        String term_and_condition = getString(R.string.terms_and_conditions);
        String term_and_condition_html = "<a href=\"http://m.vingle.net/about/terms\">"
                + term_and_condition + "</a>";
        String html_string = String.format(string_format, term_and_condition_html);

        mTermOfUse = (TextView) view.findViewById(R.id.term_of_use);
        mTermOfUse.setText(Html.fromHtml(html_string));
        mTermOfUse.setMovementMethod(LinkMovementMethod.getInstance());

        ColorStateList cl = null;
//        try {
//            XmlResourceParser xpp = getResources().getXml(R.color.url_text);
//            cl = ColorStateList.createFromXml(getResources(), xpp);
//            mTermOfUse.setLinkTextColor(cl);
//        } catch (Exception e) {
//        }

        return view;
    }

    public void isEnableSignUp() {
        if (mUsernameCount > 0 && mEmailCount > 0 && mPasswordCount > 5) {
            mSignUp.setEnabled(true);
            mSignUp.setTextColor(getResources().getColor(R.color.wo_orange_color));
        } else {
            mSignUp.setEnabled(false);
            mSignUp.setTextColor(getResources().getColor(R.color.grey_hex_eb));

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //GoogleAnalyticsHelper.sendViewSignUp(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity act = getActivity();
        if (act instanceof OnVingleSignUpListener)
            mListener = (OnVingleSignUpListener) act;

        Bundle bundle = getArguments();
        if (bundle != null) {
            //mUsername.setText(bundle.getString(BundleKey.EXTRA_USERNAME));
            //mEmail.setText(bundle.getString(BundleKey.EXTRA_EMAIL));
        }

        if (mUsernameString != null)
            mUsername.setText(mUsernameString);
        if (mEmailString != null)
            mEmail.setText(mEmailString);
        if (mPasswordString != null)
            mPassword.setText(mPasswordString);

        mUsernameDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_cc));
        mEmailDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_cc));
        mPasswordDiv.setBackgroundColor(getResources().getColor(R.color.grey_hex_cc));

        showSoftKeyboard(mUsername);
    }

    @Override
    public void onPause() {
        super.onPause();
        mListener = null;

        mUsernameString = mUsername.getText().toString();
        mEmailString = mEmail.getText().toString();
        mPasswordString = mPassword.getText().toString();
        hideSoftKeyboard();
    }

    private void trySignUp() {
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        // temporary intent to change sign activity to main
        Intent i = new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(i);

//        try {
//            SignHelper.checkValidation(getActivity(), username, email, password);
//            getVingleService().signUp(username, email, password, mSignUpResponseHandler);
//            if(mListener != null)
//                mListener.onSignUpStart();
//        } catch (SignException e) {
//            notifyError(e.getErrorTitle(), e.getErrorMessage());
//        }
    }

    private void notifyError(String title, String message) {
        if (mListener != null)
            mListener.onSignUpError(title, message);
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

//    private final APIResponseHandler<AuthJson> mSignUpResponseHandler = new APIResponseHandler<AuthJson>() {
//
//        @Override
//        public void onResponse(AuthJson auth) {
//
//            GoogleAnalyticsHelper.sendEventSignUpEmail(getActivity());
//
//            // email sign up is finished.
//
//            if (auth != null) {
//                String username = mUsername.getText().toString();
//                VinglePreference.setUserName(getActivity(), username);
//
//                if (mListener != null)
//                    mListener.onSignUpFinish("", username);
//            }
//        }
//
//        @Override
//        public void onErrorResponse(VolleyError error) {
//
//            if (error == null) {
//                notifyError("Error", "Unknown Error");
//                return;
//            }
//
//            int resp = error.networkResponse.statusCode;
//            if (resp == 503) {
//                notifyError(getStringWithoutException(R.string.system_maintenance_title),
//                        getStringWithoutException(R.string.system_maintenance_message));
//            } else {
//                SignException signException = SignHelper.parseAuthError(getActivity(), error.networkResponse);
//
//                if (signException != null) {
//                    notifyError(signException.getErrorTitle(), signException.getErrorMessage());
//                } else {
//                    notifyError(getStringWithoutException(R.string.login_server_connection_failed),
//                            getStringWithoutException(R.string.please_retry_connecting));
//                }
//            }
//        }
//    };

}// end of class
