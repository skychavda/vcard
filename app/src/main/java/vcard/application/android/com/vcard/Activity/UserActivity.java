package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vcard.application.android.com.vcard.Helper.BottomNavigationViewHelper;
import vcard.application.android.com.vcard.R;

public class UserActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        auth = FirebaseAuth.getInstance();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.user_bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        userName = findViewById(R.id.user_name_tv);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_search:
                        startActivity(new Intent(UserActivity.this,SearchActivity.class));
                        break;
                    case R.id.ic_add:
                        startActivity(new Intent(UserActivity.this,AddCardActivity.class));
                        break;
                    case R.id.ic_favorite:
                        startActivity(new Intent(UserActivity.this,FavoriteActivity.class));
                        break;
                    case R.id.ic_home:
                        startActivity(new Intent(UserActivity.this,MainActivity.class));
                        break;
                }
                return false;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(UserActivity.this,AuthActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = auth.getCurrentUser();
        userName.setText(user.getEmail());
    }
}
