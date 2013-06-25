
package com.utopia.watchout.custom.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.watchout.R;

public class WOToast {
    public static void show(Context context, String message) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast, null);
        TextView messageTv = (TextView) view.findViewById(R.id.toast_message);
        messageTv.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 80);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
