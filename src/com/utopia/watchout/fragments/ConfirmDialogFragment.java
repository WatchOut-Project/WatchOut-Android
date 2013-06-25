
package com.utopia.watchout.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.utopia.watchout.R;

@SuppressLint("ValidFragment")
public class ConfirmDialogFragment extends DialogFragment {

    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_MESSAGE = "message";

    private OnConfirmListener mListener;
    // On devices prior to Honeycomb, the button order POSITIVE - NEUTRAL -
    // NEGATIVE
    // On newer devices using the Holo theme, the button order NEGATIVE -
    // NEUTRAL - POSITIVE
    // SO PLEASE USE HOLO THEME
    private boolean mIsConfirmRight;

    public ConfirmDialogFragment(OnConfirmListener listener, boolean isConfirmRight) {
        mListener = listener;
        mIsConfirmRight = isConfirmRight;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        String title = bundle.getString(EXTRA_TITLE);
        String msg = bundle.getString(EXTRA_MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(true);
        if (mIsConfirmRight) {
            builder.setNegativeButton(getString(R.string.skip), null);
            builder.setPositiveButton(getString(R.string.settings), mListener);
        } else {
            builder.setNegativeButton(getString(R.string.settings), mListener);
            builder.setPositiveButton(getString(R.string.skip), null);
        }
        return builder.create();
    }

    public void setTitle(String title) {
        Bundle arg = getArguments();
        if (arg == null)
            arg = new Bundle();

        arg.putString(EXTRA_TITLE, title);
        setArguments(arg);
    }

    ;

    public void setMessage(String message) {
        Bundle arg = getArguments();
        if (arg == null)
            arg = new Bundle();

        arg.putString(EXTRA_MESSAGE, message);
        setArguments(arg);
    }

    public interface OnConfirmListener extends OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction tr = manager.beginTransaction();
        tr.add(this, "Confirm Dialog");
        tr.commitAllowingStateLoss();
    }

}// end of class
