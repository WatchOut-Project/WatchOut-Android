
package com.utopia.watchout.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.Session;
import com.utopia.watchout.R;
import com.utopia.watchout.custom.view.WOToast;
import com.utopia.watchout.fragments.LoadingFragment;
import com.utopia.watchout.helpers.BitmapHelper;
import com.utopia.watchout.helpers.ExiInterfaceHelper;
import com.utopia.watchout.helpers.FBHelper;
import com.utopia.watchout.helpers.FBHelper.OnRequestAPIListener;
import com.utopia.watchout.helpers.GPSTracker;
import com.utopia.watchout.helpers.KeyboardHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends SherlockFragmentActivity {

    private boolean mIsLocated = false;
    MenuItem mPostItem;

    EditText mContentTv;
    TextView mLocationTv;
    ImageView mImageView;

    String mImagePath;
    String mPlaceId;

    GPSTracker mGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.post);

        mContentTv = (EditText) findViewById(R.id.post_content);
        mLocationTv = (TextView) findViewById(R.id.post_location);
        mImageView = (ImageView) findViewById(R.id.post_image);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGPS = new GPSTracker(this);
        KeyboardHelper.show(this, mContentTv);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGPS.stopUsingGPS();
    }

    /**
     * Camera Handling
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.add_image, menu);
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {

        switch (item.getItemId()) {
            case R.id.take_a_photo:
                startCamera();
                return true;
            case R.id.choose_photo_from_gallery:
                startGallery();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private Uri mFileUri;

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mFileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        startActivityForResult(intent, IMAGE_CAPTURE);
    }

    private void startGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_photo)),
                IMAGE_PICK);
    }

    @SuppressLint("SimpleDateFormat")
    private Uri getOutputMediaFileUri() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getString(R.string.app_name));

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        return Uri.fromFile(mediaFile);
    }

    private static final int IMAGE_CAPTURE = 1;
    private static final int IMAGE_PICK = 2;
    private static final int PLACE_ACTIVITY = 3;

    private final static String EXTRA_FILE_URI = "file_uri";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_FILE_URI, mFileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            mFileUri = savedInstanceState.getParcelable(EXTRA_FILE_URI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PLACE_ACTIVITY:
                displaySelectedPlace(resultCode, data);
                break;
            default:
                Session session = Session.getActiveSession();
                if (session != null)
                    session.onActivityResult(this, requestCode, resultCode, data);
                break;
        }

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_CAPTURE:
                    updateImageView(mFileUri);
                    break;
                case IMAGE_PICK:
                    updateImageView(data.getData());
                    break;
                default:
                    break;
            }
        }
    }

    private Bitmap mSavedImageBitmap = null;

    private void updateImageView(Uri uri) {
        if (uri == null)
            return;
        mImagePath = getFilePath(uri);

        int size = getResources().getDimensionPixelSize(R.dimen.post_picutre_width);

        try {
            Bitmap bitmap = BitmapHelper.getThumbnail(this, uri, size);
            bitmap = BitmapHelper.centerCrop(bitmap);
            int rotation = ExiInterfaceHelper.getCameraPhotoOrientation(this, uri, mImagePath);
            bitmap = BitmapHelper.rotate(bitmap, rotation);
            if (mImageView != null)
                mImageView.setImageBitmap(bitmap);
            else
                mSavedImageBitmap = bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSavedImageBitmap != null)
            mImageView.setImageBitmap(mSavedImageBitmap);
    }

    private final static String SCHEME_CONTENT = "content";
    private final static String SCHEME_FILE = "file";

    public String getFilePath(Uri uri) {
        String path = null;
        String scheme = uri.getScheme();
        if (SCHEME_CONTENT.equals(scheme)) {
            String[] filePathColumn = {
                    MediaStore.Images.Media.DATA
            };

            Cursor cursor = getContentResolver().query(uri, filePathColumn,
                    null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            path = cursor.getString(columnIndex);
            cursor.close();
        }

        if (SCHEME_FILE.equals(scheme)) {
            MediaScannerConnection.scanFile(this,
                    new String[] {
                        uri.getPath()
                    },
                    new String[] {
                        "image/jpeg"
                    }, null);

            path = uri.getPath();
        }

        return path;
    }

    /**
     * ActionBar Custom
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.post, menu);
        mPostItem = menu.findItem(R.id.menu_post);
        mPostItem.setEnabled(mIsLocated);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.abs__home:
                // fall through
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_post:
//                FBHelper.publishStatus(this, mReqListener,
//                        mContentTv.getText().toString(), mImagePath, mPlaceId);
                WOToast.show(getApplicationContext(), "Currently posting is not supported.");
                return true;
            case R.id.menu_pick_place:
                startPickPlaceActivity();
                return true;
            case R.id.menu_pick_picture:
                registerForContextMenu(mImageView);
                openContextMenu(mImageView);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startPickPlaceActivity() {

        if (!GPSTracker.isGpsEnable(this))
            GPSTracker.showConfirmDialog(this);

        if (!mGPS.canGetLocation())
            return;

        Intent intent = new Intent(this, FBPickPlaceActivity.class);
        Location lastLocation = mGPS.getLocation();
        FBPickPlaceActivity.populateParameters(intent, lastLocation, null);
        startActivityForResult(intent, PLACE_ACTIVITY);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
    }

    public final static String EXTRA_LOCATION_ID = "location_id";
    public final static String EXTRA_LOCATION_NAME = "location_name";

    private void displaySelectedPlace(int resultCode, Intent data) {
        if (data == null)
            return;
        mPlaceId = data.getStringExtra(EXTRA_LOCATION_ID);
        String location = data.getStringExtra(EXTRA_LOCATION_NAME);
        mLocationTv.setText(location);
        mIsLocated = true;
        mPostItem.setEnabled(mIsLocated);
    }

    /**
     * Location Post
     */

    LoadingFragment mLoading = new LoadingFragment();

    OnRequestAPIListener mReqListener = new OnRequestAPIListener() {

        @Override
        public void onRequestAPIStart() {
            mLoading.show(getSupportFragmentManager(), LoadingFragment.class.getName());
        }

        @Override
        public void onRequestAPIProcess() {
        }

        @Override
        public void onRequestAPIFinish(Object object) {
            WOToast.show(PostActivity.this, getString(R.string.post_done_message));
            mLoading.dismiss();
            finish();
        }

        @Override
        public void onRequestAPIError(String title, String message) {
            WOToast.show(PostActivity.this, title);
            mLoading.dismiss();
        }
    };

}
