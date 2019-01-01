package vcard.application.android.com.vcard.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vcard.application.android.com.vcard.BuildConfig;
import vcard.application.android.com.vcard.R;

public class AddCardActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 100;
    StorageReference storageReference;
    ImageView card;
    TextView extractText;
    TextRecognizer detector;
    EditText company,address,email,phone,firstName,lastName,contact,personaEmail,designation;
    private Uri imageUri;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";
    private static final int REQUEST_WRITE_PERMISSION = 20;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        company = findViewById(R.id.add_card_name_textView);
//        address = findViewById(R.id.add_card_address_textView);
        email = findViewById(R.id.add_card_email_editView);
//        phone = findViewById(R.id.add_card_contact_textView);
//        firstName = findViewById(R.id.add_card_person1_fName_textView);
//        lastName = findViewById(R.id.add_card_person1_lName_textView);
//        contact = findViewById(R.id.add_card_person1_contact_textView);
//        personaEmail = findViewById(R.id.add_card_person1_email_textView);
//        designation = findViewById(R.id.add_card_person1_designation_textView);

        card = findViewById(R.id.add_card_imageView);
//        extractText = findViewById(R.id.add_card_textView);
        storageReference = FirebaseStorage.getInstance().getReference();
//        if (savedInstanceState != null) {
//            imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
//            extractText.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
//        }

        detector = new TextRecognizer.Builder(getApplicationContext()).build();

        ActivityCompat.requestPermissions(AddCardActivity.this, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        takePicture();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddCardActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void takePicture() throws IOException {
        File dir = null;
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
//        imageUri = FileProvider.getUriForFile(AddCardActivity.this,
//                BuildConfig.APPLICATION_ID + ".provider", photo);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, CAMERA_REQUEST);
        Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeSpan = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "VCard_"+timeSpan;
        dir = new File(Environment.getExternalStorageDirectory()+"/Vcard");
        if(!dir.exists()){
            dir = new File(Environment.getExternalStorageDirectory().getPath()+"/VCard");
            dir.mkdir();
        }
        File photo = new File(dir, "VCard_"+timeSpan+".jpg");
////      File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
////      File image = File.createTempFile(imageFileName,".jpg",storageDir);
//        imageUri = Uri.parse(image.getAbsolutePath());
        imageUri = FileProvider.getUriForFile(AddCardActivity.this,BuildConfig.APPLICATION_ID + ".provider", photo);
        picture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(picture,CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
                card.setImageURI(imageUri);
                launchMediaScanIntent();
                try {
                    Bitmap bitmap = decodeBitmapUri(this, imageUri);
                    if (detector.isOperational() && bitmap != null) {
                        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                        SparseArray<TextBlock> textBlocks = detector.detect(frame);
                        String blocks = "";
                        String lines = "";
                        String words = "";
                        for (int index = 0; index < textBlocks.size(); index++) {
                            //extract scanned text blocks here
                            TextBlock tBlock = textBlocks.valueAt(index);
                            blocks = blocks + tBlock.getValue() + "\n" + "\n";
//                            for (Text line : tBlock.getComponents()) {
//                                //extract scanned text lines here
//                                lines = lines + line.getValue() + "\n";
//                                for (Text element : line.getComponents()) {
//                                    //extract scanned text words here
//                                    words = words + element.getValue() + ", ";
//                                }
//                            }
                        }
                        if (textBlocks.size() == 0) {
                            extractText.setText("Scan Failed: Found nothing to scan");
                        } else {
                            extractName(blocks);
                            extractEmail(blocks);
//                            extractText.setText(extractText.getText() + "Blocks: " + "\n");
//                            extractText.setText(extractText.getText() + blocks + "\n");
//                            extractText.setText(extractText.getText() + "---------" + "\n");
//                            extractText.setText(extractText.getText() + "Lines: " + "\n");
//                            extractText.setText(extractText.getText() + lines + "\n");
//                            extractText.setText(extractText.getText() + "---------" + "\n");
//                            extractText.setText(extractText.getText() + "Words: " + "\n");
//                            extractText.setText(extractText.getText() + words + "\n");
//                            extractText.setText(extractText.getText() + "---------" + "\n");
                        }
                    } else {
                        extractText.setText("Could not set up the detector!");
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                            .show();
                    Log.e("AddCardActivity", e.toString());
                }
            }
            else{
                finish();
            }
        }


    public void extractName(String str){
        final String NAME_REGX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(NAME_REGX, Pattern.MULTILINE);
        Matcher m =  p.matcher(str);
        if(m.find()){
            System.out.println(m.group());
            company.setText(m.group());
        }
    }

    public void extractEmail(String str){
        final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern p = Pattern.compile(EMAIL_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(str);   // get a matcher object
        if(m.find()){
            System.out.println(m.group());
            email.setText(m.group());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
//            outState.putString(SAVED_INSTANCE_RESULT, extractText.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }
}