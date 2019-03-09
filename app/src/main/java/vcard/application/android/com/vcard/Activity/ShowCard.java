package vcard.application.android.com.vcard.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import vcard.application.android.com.vcard.Adapter.HomeFragmentRecyclerAdapter;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class ShowCard extends AppCompatActivity {

    TextView number, email,address,companyEmail,name;
    ImageButton message;
    CardItem cardItem;
    ImageView imageView;
    String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        cardItem = new CardItem();
        imageView = findViewById(R.id.show_card_imageView);
        name = findViewById(R.id.show_card_company_name);
        number = findViewById(R.id.show_card_number);
        email = findViewById(R.id.show_card_email);
        message = findViewById(R.id.show_card_message);
        address = findViewById(R.id.show_card_company_address);
        companyEmail = findViewById(R.id.show_card_company_email);

        cardId = getIntent().getStringExtra("cardId");

//        imageView.setImage;
        name.setText(getIntent().getStringExtra("name"));
        number.setText(getIntent().getStringExtra("number"));
        companyEmail.setText(getIntent().getStringExtra("email"));


        number.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number.getText()));
                startActivity(intent);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://maps.google.com/maps?daddr="+address.getText();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms", String.valueOf(number.getText()),null)));
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:"+email.getText()));
                startActivity(mailIntent);
            }
        });
        companyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:"+companyEmail.getText()));
                startActivity(mailIntent);
            }
        });
    }
}
