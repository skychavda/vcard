package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.User;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login;
    public int errorFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.login_email_tv);
        password = findViewById(R.id.login_password_tv);
        login = findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().length() == 0){
                    email.setError("Enter email");
                    errorFlag = 1;
                }
                if(password.getText().toString().length() == 0){
                    password.setError("Enter password");
                    errorFlag = 1;
                }
                if(errorFlag == 0) {
                    userLogin();
                }
            }
        });
    }

    public void userLogin(){
        final String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        Call<User> call = MainActivity.apiInterface.performUserLogin(userEmail,userPassword);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().getResponse().equals("ok")){
                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeUserId(response.body().getId());
                    MainActivity.prefConfig.writeName(response.body().getFirstName()+" "+response.body().getLastName());
                    MainActivity.prefConfig.writeEmail(userEmail);
                    MainActivity.prefConfig.writeNumber(response.body().getNumber());
                    MainActivity.prefConfig.writeCompany(response.body().getCompanyName());
                    MainActivity.prefConfig.writeAddress(response.body().getAddress());
                    MainActivity.prefConfig.displayToast("Login");
                    Intent i = new Intent(Login.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }else if(response.body().getResponse().equals("fail")){
                    MainActivity.prefConfig.displayToast("Failed");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        email.setText("");
        password.setText("");
    }
}
