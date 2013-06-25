
package com.utopia.watchout.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.utopia.watchout.R;
import com.utopia.watchout.helpers.LanguageHelper;

public class SplashAcitivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (LanguageHelper.CODE_KOREAN.equals(LanguageHelper.getDefaultCode(this)))
            findViewById(R.id.splash_image).setBackgroundResource(R.drawable.splash_kor);
        else
            findViewById(R.id.splash_image).setBackgroundResource(R.drawable.splash_eng);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashAcitivity.this,
                        MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.no_anim,
                        R.anim.splash_exit);
            }
        }, 700);
    }
}
