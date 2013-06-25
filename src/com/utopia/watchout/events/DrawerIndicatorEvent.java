
package com.utopia.watchout.events;

public class DrawerIndicatorEvent {

    private boolean mIsEnable = false;

    public DrawerIndicatorEvent(boolean isEnable) {
        mIsEnable = isEnable;
    }

    public boolean getIsEnable() {
        return mIsEnable;
    }
}
