package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcard.application.android.com.vcard.Helper.BottomNavigationViewHelper;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.User;

public class UserActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView userName,userEmail,userNumber,userCompny,address;
    Button button;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        setContentView(R.layout.activity_user);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.user_bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        userName = findViewById(R.id.user_name_tv);
        userEmail = findViewById(R.id.user_email_tv);
        userNumber = findViewById(R.id.user_number_tv);
        userCompny = findViewById(R.id.user_company_tv);
        address = findViewById(R.id.user_address_tv);
        button = findViewById(R.id.user_logout);

        userEmail.setText(MainActivity.prefConfig.readEmail());
        userName.setText(MainActivity.prefConfig.readName());
        userNumber.setText(MainActivity.prefConfig.readNumber());
        userCompny.setText(MainActivity.prefConfig.readCompany());
        address.setText(MainActivity.prefConfig.readAddress());

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
                MainActivity.prefConfig.writeAddress("None");
                MainActivity.prefConfig.writeUserId(0);
                MainActivity.prefConfig.writeNumber("None");
                MainActivity.prefConfig.writeEmail("None");
                MainActivity.prefConfig.writeCompany("None");
                Intent i = new Intent(UserActivity.this,WelcomeScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_user_account,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_account ){
            int userID = MainActivity.prefConfig.readUserId();
            Call<User> call = MainActivity.apiInterface.deleteUserAccount(userID);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.body().getResponse().equals("deleted")){

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
            MainActivity.prefConfig.writeLoginStatus(false);
            MainActivity.prefConfig.writeName("User");
            MainActivity.prefConfig.writeAddress("None");
            MainActivity.prefConfig.writeUserId(0);
            MainActivity.prefConfig.writeNumber("None");
            MainActivity.prefConfig.writeEmail("None");
            MainActivity.prefConfig.writeCompany("None");
            Intent i = new Intent(UserActivity.this,WelcomeScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }
}
