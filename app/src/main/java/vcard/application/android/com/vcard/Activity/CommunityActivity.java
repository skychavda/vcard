package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import vcard.application.android.com.vcard.Helper.BottomNavigationViewHelper;
import vcard.application.android.com.vcard.R;

public class CommunityActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.community_bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_search:
                        startActivity(new Intent(CommunityActivity.this,SearchActivity.class));
                        break;
                    case R.id.ic_home:
                        startActivity(new Intent(CommunityActivity.this,MainActivity.class));
                        break;
                    case R.id.ic_favorite:
                        startActivity(new Intent(CommunityActivity.this,FavoriteActivity.class));
                        break;
                    case R.id.ic_profile:
                        startActivity(new Intent(CommunityActivity.this,UserActivity.class));
                        break;
                }
                return false;
            }
        });
    }
}
