
package com.utopia.watchout.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.squareup.otto.WOEventBus;
import com.utopia.watchout.R;
import com.utopia.watchout.events.HelpCancelEvent;
import com.utopia.watchout.events.HelpConfirmEvent;

public class HelpNotiFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getString(R.string.dialog_title);
        String message = getString(R.string.dialog_message);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.icon)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                WOEventBus.getInstance().post(new HelpConfirmEvent());
                            }
                        }
                )
                .setNegativeButton(R.string.dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                WOEventBus.getInstance().post(new HelpCancelEvent());
                            }
                        }
                )
                .create();
        
        return dialog;
    }
}
