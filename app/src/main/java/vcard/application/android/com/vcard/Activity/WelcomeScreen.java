package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import vcard.application.android.com.vcard.Adapter.SliderAdapter;
import vcard.application.android.com.vcard.R;

public class WelcomeScreen extends AppCompatActivity {

    TextView login;
    ViewPager mViewPager;
    LinearLayout mDotLayout;
    TextView[] mDots;
    SliderAdapter sliderAdapter;
    int currentPage = 0;
    int NUM_PAGES = 4;
    final long DELAY_MS = 800;
    final long PERIOD_MS = 3000;
    Timer timer;
    Button create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        mViewPager = findViewById(R.id.welcome_screen_viewPager);
        mDotLayout = findViewById(R.id.welcome_screen_dots);
        create_account = findViewById(R.id.welcome_screen_create_account);
        login = findViewById(R.id.welcome_screen_login_tv);

        sliderAdapter = new SliderAdapter(this);
        mViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mViewPager.addOnPageChangeListener(viewListener);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES-1) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this,CreateAccount.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this,Login.class));
            }
        });
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for (int i=0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(50);
            mDots[i].setTextColor(getResources().getColor(R.color.primaryWhiteTransparent));

            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
