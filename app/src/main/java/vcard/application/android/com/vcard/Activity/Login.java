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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.login_email_tv);
        password = findViewById(R.id.login_password_tv);
        login = findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
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
                    MainActivity.prefConfig.writeName(response.body().getName());
                    MainActivity.prefConfig.writeEmail(userEmail);
                    MainActivity.prefConfig.writeNumber(response.body().getNumber());
                    MainActivity.prefConfig.writeCompany(response.body().getCompanyName());
                    MainActivity.prefConfig.displayToast("Login");
                    startActivity(new Intent(Login.this,MainActivity.class));
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
