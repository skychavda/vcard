package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import vcard.application.android.com.vcard.R;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.splash_image);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        logo.startAnimation(animation);

        final Intent i = new Intent(this,MainActivity.class);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        thread.start();
    }
}
