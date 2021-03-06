package vcard.application.android.com.vcard.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import vcard.application.android.com.vcard.Adapter.HomeFragmentRecyclerAdapter;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class ShowCard extends AppCompatActivity {

    TextView number, email, address, companyEmail, name, shareCard;
    ImageButton message;
    CardItem cardItem;
    ImageView imageView;
    String cardId;
    RequestOptions options;
    File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        cardItem = new CardItem();
        imageView = findViewById(R.id.show_card_imageView);
        name = findViewById(R.id.show_card_company_name);
        number = findViewById(R.id.show_card_number);
//        email = findViewById(R.id.show_card_email);
        message = findViewById(R.id.show_card_message);
        address = findViewById(R.id.show_card_company_address);
        companyEmail = findViewById(R.id.show_card_company_email);
        options = new RequestOptions().autoClone();
        shareCard = findViewById(R.id.show_card_create_vcf);
        mFile = this.getExternalFilesDir(null);
        cardId = getIntent().getStringExtra("cardId");

//        imageView.setImage;
        name.setText(getIntent().getStringExtra("name"));
        number.setText(getIntent().getStringExtra("number"));
        companyEmail.setText(getIntent().getStringExtra("email"));
        address.setText(getIntent().getStringExtra("address"));
        Glide.with(this).load(getIntent().getStringExtra("image")).apply(options).into(imageView);


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
                String url = "http://maps.google.com/maps?daddr=" + address.getText();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", String.valueOf(number.getText()), null)));
            }
        });
//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
//                mailIntent.setData(Uri.parse("mailto:"+email.getText()));
//                startActivity(mailIntent);
//            }
//        });
        companyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + companyEmail.getText()));
                startActivity(mailIntent);
            }
        });

        shareCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File vcfFile = new File(mFile, name.getText().toString() + ".vcf");
                FileWriter fw = null;
                try {
                    fw = new FileWriter(vcfFile);
                    fw.write("BEGIN:VCARD\r\n");
                    fw.write("VERSION:3.0\r\n");
                    fw.write("N:" + name.getText().toString() + "\r\n");
                    fw.write("FN:" + name.getText().toString() + "\r\n");
//                    fw.write("ORG:" + p.getCompanyName() + "\r\n");
                    fw.write("TITLE:" + name.getText().toString() + "\r\n");
//                    fw.write("TEL;TYPE=WORK,VOICE:" + number + "\r\n");
                    fw.write("TEL;TYPE=HOME,VOICE:" + number.getText().toString() + "\r\n");
//                    fw.write("ADR;TYPE=WORK:;;" + p.getStreet() + ";" + p.getCity() + ";" + p.getState() + ";" + p.getPostcode() + ";" + p.getCountry() + "\r\n");
                    fw.write("EMAIL;TYPE=PREF,INTERNET:" + companyEmail.getText().toString() + "\r\n");
                    fw.write("END:VCARD\r\n");
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(vcfFile);
                Intent intent = new Intent();
                File file = new File(vcfFile.getAbsolutePath());
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share VCard"));
            }
        });
    }
}
