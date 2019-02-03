//package vcard.application.android.com.vcard.Fragment;
//
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Environment;
//import android.os.StrictMode;
//import android.provider.MediaStore;
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import vcard.application.android.com.vcard.R;
//
//import static android.app.Activity.RESULT_OK;
//import static android.content.Context.CAMERA_SERVICE;
//
//public class CameraFragment extends Fragment {
//    Button camera,save;
//    private int REQUEST_CAMERA=1;
//    ImageView ivCard;
//    StorageReference storageReference;
//    ProgressDialog dialog;
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_camera,container,false);
//        camera = (Button)view.findViewById(R.id.fragment_camera_btn);
//        ivCard = (ImageView)view.findViewById(R.id.fragment_camera_iv);
//        save = (Button)view.findViewById(R.id.fragment_camera_save_btn);
//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage();
//            }
//        });
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //saveImage();
//            }
//        });
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        storageReference = FirebaseStorage.getInstance().getReference();
//        dialog = new ProgressDialog(getContext());
//        return view;
//    }
//
//    public void selectImage(){
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        startActivityForResult(intent,REQUEST_CAMERA);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
//            dialog.show();
//            Uri uri = data.getData();
//            StorageReference filepath = storageReference.child("VCard").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    dialog.dismiss();
//                    Uri downloadUri = taskSnapshot.getUploadSessionUri();
//                    Picasso.get().load(downloadUri).centerCrop().into(ivCard);
//                }
//            });
//        }
//    }
//}