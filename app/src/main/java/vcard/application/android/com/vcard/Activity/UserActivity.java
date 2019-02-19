package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vcard.application.android.com.vcard.Helper.BottomNavigationViewHelper;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.User;

public class UserActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView userName,userEmail,userNumber,userCompny;
    Button button;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.user_bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        userName = findViewById(R.id.user_name_tv);
        userEmail = findViewById(R.id.user_email_tv);
        userNumber = findViewById(R.id.user_number_tv);
        userCompny = findViewById(R.id.user_company_tv);
        button = findViewById(R.id.user_logout);

        userEmail.setText(MainActivity.prefConfig.readEmail());
        userName.setText(MainActivity.prefConfig.readName());
        userNumber.setText(MainActivity.prefConfig.readNumber());
        userCompny.setText(MainActivity.prefConfig.readCompany());

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_search:
                        startActivity(new Intent(UserActivity.this, SearchActivity.class));
                        break;
                    case R.id.ic_add:
                        startActivity(new Intent(UserActivity.this, AddCardActivity.class));
                        break;
                    case R.id.ic_favorite:
                        startActivity(new Intent(UserActivity.this, FavoriteActivity.class));
                        break;
                    case R.id.ic_home:
                        startActivity(new Intent(UserActivity.this, MainActivity.class));
                        break;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.prefConfig.writeLoginStatus(false);
                MainActivity.prefConfig.writeName("User");
                startActivity(new Intent(UserActivity.this,WelcomeScreen.class));
            }
        });
    }
}
