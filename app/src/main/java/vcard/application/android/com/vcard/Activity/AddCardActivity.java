package vcard.application.android.com.vcard.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.FileUtils;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcard.application.android.com.vcard.BackgroundAsync.BackgroundWorker;
import vcard.application.android.com.vcard.BuildConfig;
import vcard.application.android.com.vcard.Helper.ApiInterface;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class AddCardActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 100;
    StorageReference storageReference;
    DatabaseReference databaseCard;
    ImageView card;
    TextView extractText, imageUritv;
    TextRecognizer detector;
    //    String filePath
    Bitmap bitmap, bitmap2;
    //    Button addCard;
    private byte[] uploadBytes;
    EditText company, email, phone, personName, address, designation;
    private Uri imageUri, compressedImageUri;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";
    private static final int REQUEST_WRITE_PERMISSION = 20;
    File photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        databaseCard = FirebaseDatabase.getInstance().getReference("card");
        company = findViewById(R.id.add_card_name_textView);
        email = findViewById(R.id.add_card_email_editView);
        phone = findViewById(R.id.add_card_contact_editView);
        card = findViewById(R.id.add_card_imageView);
        personName = findViewById(R.id.add_card_person_editView);
        address = findViewById(R.id.add_card_address_editView);
        designation = findViewById(R.id.add_card_designation_editView);
//        addCard = findViewById(R.id.add_card_add_card_btn);
        imageUritv = findViewById(R.id.add_card_image_uri);


        storageReference = FirebaseStorage.getInstance().getReference();
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
                    Toast.makeText(AddCardActivity.this, "Permission Denied!", Toast.LENGTH_LONG).show();
                }
        }
    }

    private void takePicture() throws IOException {
        File dir = null;
        Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeSpan = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        dir = new File(Environment.getExternalStorageDirectory() + "/Vcard");
        if (!dir.exists()) {
            dir = new File(Environment.getExternalStorageDirectory().getPath() + "/VCard");
            dir.mkdir();
        }
        photo = new File(dir, "VCard_" + timeSpan + ".jpg");
        imageUri = FileProvider.getUriForFile(AddCardActivity.this, BuildConfig.APPLICATION_ID + ".provider", photo);
//        imageUritv.setText(String.valueOf(imageUri));
        picture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(picture, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            card.setImageURI(imageUri);
            launchMediaScanIntent();
            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                bitmap = decodeBitmapUri(this, imageUri);
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
                    }
                    if (textBlocks.size() == 0) {
                        extractText.setText("Scan Failed: Found nothing to scan");
                    } else {
                        extractName(blocks);
                        extractEmail(blocks);
                        extractPhone(blocks);
                    }
                } else {
                    extractText.setText("Could not set up the detector!");
                }
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                        .show();
                Log.e("AddCardActivity", e.toString());
            }
        } else {
            finish();
        }
    }

    public void extractName(String str) {
        final String NAME_REGX = "^([A-Z]([a-z]*|\\.) *){1,2}([A-Z][a-z]+-?)+$";
        Pattern p = Pattern.compile(NAME_REGX, Pattern.MULTILINE);
        Matcher m = p.matcher(str);
        if (m.find()) {
            System.out.println(m.group());
            company.setText(m.group());
        }
    }

    public void extractEmail(String str) {
        final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern p = Pattern.compile(EMAIL_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(str);   // get a matcher object
        if (m.find()) {
            System.out.println(m.group());
            email.setText(m.group());
        }
    }

    public void extractPhone(String str) {
        System.out.println("Getting Phone Number");
        final String PHONE_REGEX = "^((\\+)?(\\d{2}[-])?(\\d{10}){1})?(\\d{11}){0,1}?$";
        Pattern p = Pattern.compile(PHONE_REGEX, Pattern.MULTILINE);
        Matcher m = p.matcher(str);   // get a matcher object
        if (m.find()) {
            System.out.println(m.group());
            phone.setText(m.group());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
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

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

    public void AddCard(View view) {
//        String image = imageToString();
        int userId = MainActivity.prefConfig.readUserId();
        String company_Name = company.getText().toString();
        String contact_Email1 = email.getText().toString();
        String contact_Number1 = phone.getText().toString();
        String person_Name1 = personName.getText().toString();
        String company_Address = address.getText().toString();
        String designation_1 = designation.getText().toString();

//        RequestBody userId = RequestBody.create(MultipartBody.FORM, String.valueOf(MainActivity.prefConfig.readUserId()));
        RequestBody companyName = RequestBody.create(MediaType.parse("multipart/form-data"), company_Name);
        RequestBody companyAddress = RequestBody.create(MediaType.parse("multipart/form-data"), company_Address);
        RequestBody contactEmail1 = RequestBody.create(MediaType.parse("multipart/form-data"), contact_Email1);
        RequestBody contactNumber1 = RequestBody.create(MediaType.parse("multipart/form-data"), contact_Number1);
        RequestBody personName1 = RequestBody.create(MediaType.parse("multipart/form-data"), person_Name1);
        RequestBody designation1 = RequestBody.create(MediaType.parse("multipart/form-data"), designation_1);
        RequestBody imageFile = RequestBody.create(MediaType.parse("multipart/form-data"), photo);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", photo.getName(), imageFile);

        Call<CardItem> call = MainActivity.apiInterface.addCard(userId, companyName,companyAddress,personName1,contactNumber1,contactEmail1,designation1);
        call.enqueue(new Callback<CardItem>() {
            @Override
            public void onResponse(Call<CardItem> call, Response<CardItem> response) {
                MainActivity.prefConfig.displayToast("Server Response: " + response.body().getResponse());

            }

            @Override
            public void onFailure(Call<CardItem> call, Throwable t) {

            }
        });
    }
}