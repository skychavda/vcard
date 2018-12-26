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

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.search_bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        startActivity(new Intent(SearchActivity.this,MainActivity.class));
                        break;
                    case R.id.ic_add:
                        startActivity(new Intent(SearchActivity.this,AddCardActivity.class));
                        break;
                    case R.id.ic_favorite:
                        startActivity(new Intent(SearchActivity.this,FavoriteActivity.class));
                        break;
                    case R.id.ic_profile:
                        startActivity(new Intent(SearchActivity.this,UserActivity.class));
                        break;
                }
                return false;
            }
        });
    }
}
