
package com.utopia.watchout.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utopia.watchout.R;

@SuppressLint("ValidFragment")
public class LoadingFragment extends DialogFragment {

    public final static String EXTRA_MESSAGE = "message";
    private String mMessage = null;

    public LoadingFragment() {
    }

    public LoadingFragment(String message) {
        mMessage = message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Dialog);

        if (savedInstanceState != null)
            mMessage = savedInstanceState.getString(EXTRA_MESSAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(EXTRA_MESSAGE, mMessage);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        if (mMessage != null)
            dialog.setMessage(mMessage);
        else
            dialog.setMessage(getString(R.string.loading_));
        return dialog;
    };

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction tr = manager.beginTransaction();
        tr.add(this, this.getClass().getName());
        tr.commitAllowingStateLoss();
    }

}// end of class
