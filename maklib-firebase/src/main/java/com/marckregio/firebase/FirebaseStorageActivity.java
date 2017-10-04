package com.marckregio.firebase;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * Created by eCopy on 10/2/2017.
 */

public class FirebaseStorageActivity extends AppCompatActivity {

    private static String IMAGE_FOLDER = "imageuploads/";
    private static int IMAGE_REQUEST = 1002;
    private FirebaseStorage fbStorage;
    private StorageReference storageRef;
    private UploadTask uploadTask;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbStorage = FirebaseStorage.getInstance();
        storageRef = fbStorage.getReference();

        //filePicker(); //TEST Method, proper file picker is inside maklib-ui
    }

    public void filePicker(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Image"), IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadToFirebase(Uri uri){
        if (uri != null){
            Log.v("FIREBASE", "Uploading....");
            StorageReference reference = storageRef.child(IMAGE_FOLDER + System.currentTimeMillis() + "." + getFileExtension(uri));
            uploadTask = reference.putFile(uri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.v("FIREBASE", downloadUrl.getPath());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.v("FIREBASE", "Upload failed");
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.v("FIREBASE", taskSnapshot.getTotalByteCount() + "");
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Log.v("FIREBASE", "Upload complete");
                }
            });

            //uploadTask.pause();
            //uploadTask.resume();
            //uploadTask.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null){
            filePath = data.getData();
            Log.v("FIREBASE", filePath.getPath());
            //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            uploadToFirebase(filePath);
        }
    }
}