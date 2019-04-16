package vcard.application.android.com.vcard.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

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
    ProgressDialog progressDialog;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.login_email_tv);
        password = findViewById(R.id.login_password_tv);
        login = findViewById(R.id.login_btn);
        scrollView = findViewById(R.id.login_scrollView);

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging you in");
        progressDialog.setMessage("hold a second...");
        progressDialog.show();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().getResponse().equals("ok")){
                    progressDialog.cancel();
                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeUserId(response.body().getId());
                    MainActivity.prefConfig.writeName(response.body().getFirstName()+" "+response.body().getLastName());
                    MainActivity.prefConfig.writeEmail(userEmail);
                    MainActivity.prefConfig.writeNumber(response.body().getNumber());
                    MainActivity.prefConfig.writeCompany(response.body().getCompanyName());
                    MainActivity.prefConfig.writeAddress(response.body().getAddress());
                    Intent i = new Intent(Login.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }else if(response.body().getResponse().equals("fail")){
                    progressDialog.cancel();
                    final Snackbar snackbar = Snackbar.make(scrollView,"User not exist",Snackbar.LENGTH_LONG);
                    snackbar.show();
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
