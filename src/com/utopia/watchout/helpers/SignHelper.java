package com.utopia.watchout.helpers;

/**
 * Created by Doheum on 13. 9. 3.
 */

import android.content.Context;
import android.util.Patterns;

import com.utopia.watchout.R;

public class SignHelper {

    // Username error
    // - If user input username less than 4 characters
    // Title : Unavailable Username
    // Message : Please use at least 4 characters!
    // OK button
    // - If user input username more than 15 characters
    //
    // Title : Unavailable Username
    // Message : Username is too long! (maximum is 15 characters)
    // OK button
    // - If user input username that has already been taken
    //
    // Title : Unavailable Username
    // Message : Username has already been taken!
    // OK button
    // Email address error
    // - If user input unavailable email address
    // Title : Unavailable Email
    // Message : Email is invalid
    // OK button
    // - If user input email that has already been taken
    //
    // Title : Unavailable Email
    // Message : Email has already been taken!
    // OK button
    // Password error
    // - If user input password less than 6 characters
    // Title : Unavailable Password
    // Message : Please use at least 6 characters!
    // OK button

    public static class SignException extends Exception {

        private static final long serialVersionUID = 1L;
        private String mTtitle = "";
        private String mMessage = "";

        public SignException(String title, String message) {
            mTtitle = title;
            mMessage = message;
        }

        public String getErrorTitle() {
            return mTtitle;
        }

        public String getErrorMessage() {
            return mMessage;
        }
    }

//    public static SignException parseAuthError(Context context, NetworkResponse response) {
//        try {
//            Gson gson = new Gson();
//            String json = new String(
//                    response.data, HttpHeaderParser.parseCharset(response.headers));
//            AuthJson auth = gson.fromJson(json, AuthJson.class);
//
//            if(auth != null && auth.error != null){
//                StringBuffer errStr = new StringBuffer();
//                for (String str : auth.error)
//                    errStr.append(str).append("\n");
//                return new SignException(context.getString(R.string.login_server_connection_failed),
//                        errStr.toString().trim());
//            }
//
//        } catch (Exception e) {
//            //silently handle exception..
//        }
//        return new SignException(context.getString(R.string.login_server_connection_failed), "Fail to connect.");
//    }

    public static boolean checkSignInValidation(Context context, String usernameOrEmail,
                                                String password)
            throws SignException {

        if (usernameOrEmail == null) {
            throw new SignException(context.getString(R.string.unavailable_username_or_email),
                    context.getString(R.string.email_is_invalid));
        } else if ((Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches() == false)) {
            if (usernameOrEmail.length() < 4) {
                throw new SignException(context.getString(R.string.unavailable_username),
                        context.getString(R.string.please_use_at_least_4_characters));
            }

            if (usernameOrEmail.length() > 15) {
                throw new SignException(context.getString(R.string.unavailable_username),
                        context.getString(R.string.username_is_too_long_maximum_is_15_characters));
            }
        }

        return true;
    }

    public static boolean checkValidation(Context context, String username, String password)
            throws SignException {

        if (username == null || username.length() < 4) {
            throw new SignException(context.getString(R.string.unavailable_username),
                    context.getString(R.string.please_use_at_least_4_characters));
        }

        if (username == null || username.length() > 15) {
            throw new SignException(context.getString(R.string.unavailable_username),
                    context.getString(R.string.username_is_too_long_maximum_is_15_characters));
        }

        if (password == null || password.length() < 6) {
            throw new SignException(context.getString(R.string.unavailable_password),
                    context.getString(R.string.please_use_at_least_6_characters));
        }

        return true;
    }

    public static boolean checkValidation(Context context, String username, String email,
                                          String password) throws SignException {

        if (username == null || username.length() < 4) {
            throw new SignException(context.getString(R.string.unavailable_username),
                    context.getString(R.string.please_use_at_least_4_characters));
        }

        if (username == null || username.length() > 15) {
            throw new SignException(context.getString(R.string.unavailable_username),
                    context.getString(R.string.username_is_too_long_maximum_is_15_characters));
        }

        if (email == null
                || Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
            throw new SignException(context.getString(R.string.unavailable_email),
                    context.getString(R.string.email_is_invalid));
        }

        if (password == null || password.length() < 6) {
            throw new SignException(context.getString(R.string.unavailable_password),
                    context.getString(R.string.please_use_at_least_6_characters));
        }

        return true;
    }

    private SignHelper() {
    }

}// end of class

