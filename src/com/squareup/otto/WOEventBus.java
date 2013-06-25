
package com.squareup.otto;

import android.os.Handler;

public class WOEventBus extends Bus {

    private static final Handler mHandler = new Handler();
    private static final WOEventBus mBus = new WOEventBus(ThreadEnforcer.ANY);

    public static final WOEventBus getInstance() {
        return mBus;
    }

    private WOEventBus(ThreadEnforcer enforcer) {
        super(enforcer);
    }

    @Override
    protected void dispatch(Object arg0, EventHandler arg1) {
        try {
            super.dispatch(arg0, arg1);
        } catch (RuntimeException e) {
            // send exception to ga and do't terminate an app.
        }
    }

    public void postAsync(final Object object) {
        // get main thread.
        // post it inside maing thread.
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                post(object);
            }
        });
    }

}// end of class
