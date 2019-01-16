package vcard.application.android.com.vcard.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class ShowCard extends AppCompatActivity {

    TextView number, email,address,companyEmail,name;
    ImageButton message;
    CardItem cardItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        cardItem = new CardItem();
        name = findViewById(R.id.show_card_company_name);
        number = findViewById(R.id.show_card_number);
        email = findViewById(R.id.show_card_email);
        message = findViewById(R.id.show_card_message);
        address = findViewById(R.id.show_card_company_address);
        companyEmail = findViewById(R.id.show_card_company_email);


        name.setText(cardItem.getName());

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

    /*@SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.show_card_number:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number.getText()));
                startActivity(intent);
                break;
            case R.id.show_card_company_address:
                String url = "http://maps.google.com/maps?daddr="+address.getText();
                Intent addressIntent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                startActivity(addressIntent);
                break;
            case R.id.show_card_message:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms", String.valueOf(number.getText()),null)));
                break;
            case R.id.show_card_email:
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setType("text/plain");
                mailIntent.putExtra(Intent.EXTRA_EMAIL, email.getText());
                startActivity(Intent.createChooser(mailIntent, "Send Email"));
                break;
            case R.id.show_card_company_email:
                Intent companyMailIntent = new Intent(Intent.ACTION_SENDTO);
                companyMailIntent.setType("text/plain");
                companyMailIntent.putExtra(Intent.EXTRA_EMAIL, companyEmail.getText());
                startActivity(Intent.createChooser(companyMailIntent, "Send Email"));
                break;
        }
    }*/
}
