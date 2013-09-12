package com.utopia.watchout.fragments;

/**
 * Created by Doheum on 13. 9. 3.
 */

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class SignFragment extends WOFragment {

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
    };
}

