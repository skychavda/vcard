package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.User;

public class CreateAccount extends AppCompatActivity {

    public EditText name, number, email, password;
    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        name = findViewById(R.id.create_account_first_name_tv);
        number = findViewById(R.id.create_account_number_tv);
        email = findViewById(R.id.create_account_email_tv);
        password = findViewById(R.id.create_account_password_tv);
        createAccount = findViewById(R.id.create_account_create_account_btn);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });
    }

    public void performRegistration() {
        String userName = name.getText().toString();
        String userNumber = number.getText().toString();
        final String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        Call<User> call = MainActivity.apiInterface.performRegistration(userEmail, userPassword, userNumber, userName);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("ok")) {
                    Log.d("CreatAccount","Response");
                    MainActivity.prefConfig.displayToast("Registration Success");
                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeName(userEmail);
                    startActivity(new Intent(CreateAccount.this, MainActivity.class));
                } else if (response.body().getResponse().equals("exist")) {
                    MainActivity.prefConfig.displayToast("User already exist");
                } else if (response.body().getResponse().equals("error")) {
                    MainActivity.prefConfig.displayToast("Something wrong");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        name.setText("");
        number.setText("");
        email.setText("");
        password.setText("");
    }
}
