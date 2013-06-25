
package com.utopia.watchout.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.FacebookException;
import com.facebook.model.GraphPlace;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PlacePickerFragment;
import com.utopia.watchout.R;
import com.utopia.watchout.helpers.ScreenHelper;

public class FBPickPlaceActivity extends SherlockFragmentActivity {

    PlacePickerFragment placePickerFragment;

    public static void populateParameters(Intent intent, Location location, String searchText) {
        intent.putExtra(PlacePickerFragment.LOCATION_BUNDLE_KEY, location);
        intent.putExtra(PlacePickerFragment.SEARCH_TEXT_BUNDLE_KEY, searchText);
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature((long) Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_fb_pick_place);

        // Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.select_place);

        // PickPlace TopBar Padding
        int topbarHeight = getResources().getDimensionPixelSize(R.dimen.pick_place_topbar_height);
        int paddingTop = ScreenHelper.getABHeight(this) - topbarHeight;
        findViewById(R.id.content).setPadding(0, paddingTop, 0, 0);

        FragmentManager fm = getSupportFragmentManager();
        placePickerFragment = (PlacePickerFragment) fm.findFragmentById(R.id.place_picker_fragment);
        if (savedInstanceState == null) {
            placePickerFragment.setSettingsFromBundle(getIntent().getExtras());
        }

        placePickerFragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
            @Override
            public void onError(PickerFragment<?> fragment, FacebookException error) {
                FBPickPlaceActivity.this.onError(error);
            }
        });

        placePickerFragment
                .setOnSelectionChangedListener(new PickerFragment.OnSelectionChangedListener() {
                    @Override
                    public void onSelectionChanged(PickerFragment<?> fragment) {
                        if (placePickerFragment.getSelection() != null) {
                            finishActivity();
                        }
                    }
                });
        placePickerFragment
                .setOnDoneButtonClickedListener(new PickerFragment.OnDoneButtonClickedListener() {
                    @Override
                    public void onDoneButtonClicked(PickerFragment<?> fragment) {
                        finishActivity();
                    }
                });
    }

    private void finishActivity() {
        Intent data = new Intent();
        GraphPlace place = placePickerFragment.getSelection();
        String id = place.getId();
        String location = place.getName();
        data.putExtra(PostActivity.EXTRA_LOCATION_ID, id);
        data.putExtra(PostActivity.EXTRA_LOCATION_NAME, location);
        setResult(RESULT_OK, data);
        finish();
    }

    private void onError(Exception error) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_dialog_title)
                .setMessage(error.getMessage())
                .setPositiveButton(R.string.ok_button, null)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            placePickerFragment.loadData(false);
        } catch (Exception ex) {
            onError(ex);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.abs__home:
                // fall through
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
