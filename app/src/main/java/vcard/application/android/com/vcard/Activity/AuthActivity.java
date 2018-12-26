package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vcard.application.android.com.vcard.R;

public class AuthActivity extends AppCompatActivity {

    EditText email,password;
    ProgressBar progressBar;
    Button sendButton;
    TextView verificationText;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        email = findViewById(R.id.auth_email_tv);
        password = findViewById(R.id.auth_password_et);
        sendButton = findViewById(R.id.auth_btn_send);
        progressBar = findViewById(R.id.auth_progressBar);
        verificationText = findViewById(R.id.auth_verification_code_tv);
        mAuth = FirebaseAuth.getInstance();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn(){
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        sendButton.setVisibility(View.INVISIBLE);
        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(AuthActivity.this,UserActivity.class));
                    finish();
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    sendButton.setVisibility(View.VISIBLE);
                    Toast.makeText(AuthActivity.this,"Error in login",Toast.LENGTH_SHORT).show();
                    //Snackbar.make(new View(getParent()),"Error in login",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
