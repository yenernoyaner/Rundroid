package com.syyazilim.runout.activity;

import com.syyazilim.runout.R;
import com.syyazilim.runout.R.id;
import com.syyazilim.runout.R.layout;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient;

public class AnimationActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation);
        (new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linear_layout_sub);

                ObjectAnimator colorFade = ObjectAnimator.ofObject(linearLayout, "backgroundColor", new ArgbEvaluator(), Color.argb(255, 201, 255, 254), 0xFFFFFF);
                colorFade.setDuration(3000);
                colorFade.start();
                //overridePendingTransition(R.anim.fade_out, R.anim.deneme_anim);

            }
        }).start();

    }


}

