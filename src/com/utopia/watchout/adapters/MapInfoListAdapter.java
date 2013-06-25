
package com.utopia.watchout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utopia.watchout.R;
import com.utopia.watchout.helpers.DateHelper;
import com.utopia.watchout.helpers.ScreenHelper;
import com.utopia.watchout.helpers.objects.FBImage;
import com.utopia.watchout.helpers.objects.FBStatus;
import com.utopia.watchout.utils.Utils;

import java.util.Locale;

public class MapInfoListAdapter extends ArrayAdapter<FBStatus> {

    public MapInfoListAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getMarkerView(position, convertView, parent);
    }

    private View getMarkerView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.marker_item, null);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(params);
        }

        final TextView timeView = (TextView) Utils.findViewByIdInTag(convertView,
                R.id.item_time);
        final TextView placeView = (TextView) Utils.findViewByIdInTag(convertView,
                R.id.item_place);
        final TextView contentView = (TextView) Utils.findViewByIdInTag(convertView,
                R.id.item_content);
        final WebView webView = (WebView) Utils.findViewByIdInTag(convertView,
                R.id.item_image);
        final RelativeLayout webViewLayout = (RelativeLayout) Utils.findViewByIdInTag(convertView,
                R.id.item_image_layout);
        final ProgressBar loading = (ProgressBar) Utils.findViewByIdInTag(convertView,
                R.id.item_loading);

        FBStatus status = getItem(position);

        if (status.getCreatedTimeMillis() != 0) {
            timeView.setVisibility(View.VISIBLE);
            timeView.setText(DateHelper.getDate(status.getCreatedTimeMillis(),
                    DateHelper.KOR_FORMAT,
                    Locale.KOREA));
        } else
            timeView.setVisibility(View.GONE);
        placeView.setText(status.getPlaceName());
        contentView.setText(status.getNameAndMessage());

        FBImage[] images = status.getImages();
        if (images != null) {
            webViewLayout.setVisibility(View.VISIBLE);
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 20);
            webView.getSettings().setAppCachePath("");
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

            webView.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            WebViewClient client = new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    view.setBackgroundColor(0xffffff);
                    final Animation fade = new AlphaAnimation(0.0f, 1.0f);
                    fade.setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            loading.setVisibility(View.GONE);
                        }
                    });
                    fade.setDuration(200);
                    view.startAnimation(fade);
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode,
                        String description,
                        String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            };
            webView.setWebViewClient(client);

            int width = ScreenHelper.getWidth(getContext())
                    - getContext().getResources().getDimensionPixelSize(R.dimen.wo_margin_huge) * 2
                    - getContext().getResources().getDimensionPixelSize(R.dimen.wo_margin_normal)
                    * 2;
            int height = width * images[0].getHeight() / images[0].getWidth();

            webView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            webViewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));

            String HTML_FORMAT = "<html><head>"
                    + "<meta name=\"viewport\" "
                    + "content=\"width=device-width, target-densitydpi=device-dpi, user-scalable=no\" />"
                    + "</head><body style=\"margin:0; padding:0\" onLoad=\"javascript:preloader()\" ><center>"
                    + "<img src=\"%s\" style=\"width: %dpx; height: %dpx\" /></center></body></html>";
            String html = String.format(HTML_FORMAT, images[0].getUrl(), width, height);
            String mime = "text/html";
            String encoding = "utf-8";

            webView.loadDataWithBaseURL(null, html, mime, encoding, null);
        } else {
            webViewLayout.setVisibility(View.GONE);
        }

        return convertView;
    }

}// end of class
