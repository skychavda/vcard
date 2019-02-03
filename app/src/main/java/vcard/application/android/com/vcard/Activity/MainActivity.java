package vcard.application.android.com.vcard.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import java.util.List;

import vcard.application.android.com.vcard.Adapter.SectionPagerAdapter;
import vcard.application.android.com.vcard.Fragment.HomeFragment;
import vcard.application.android.com.vcard.Helper.BottomNavigationViewHelper;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;


public class MainActivity extends AppCompatActivity {

    private SectionPagerAdapter sectionPagerAdapter;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    private static final int CAMERA_PERMISSION = 100;

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.main_viewPager);
        setUpViewPager(viewPager);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION);
        }
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
        }
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},101);
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_search:
                        startActivity(new Intent(MainActivity.this,SearchActivity.class));
                        break;
                    case R.id.ic_add:
                        startActivity(new Intent(MainActivity.this,AddCardActivity.class));
                        break;
                    case R.id.ic_favorite:
                        startActivity(new Intent(MainActivity.this,FavoriteActivity.class));
                        break;
                    case R.id.ic_profile:
                        startActivity(new Intent(MainActivity.this,UserActivity.class));
                        break;
                }
                return false;
            }
        });

        TabLayout tabLayout = (TabLayout)findViewById(R.id.main_tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(0).setText("VCard");

        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        viewPager.setCurrentItem(1);

    }

    public void setUpViewPager(ViewPager viewPager){
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        //sectionPagerAdapter.addFragment(new CameraFragment());
        sectionPagerAdapter.addFragment(new HomeFragment());
        viewPager.setAdapter(sectionPagerAdapter);
    }
}
