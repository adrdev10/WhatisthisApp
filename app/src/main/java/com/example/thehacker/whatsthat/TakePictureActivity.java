package com.example.thehacker.whatsthat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TakePictureActivity extends AppCompatActivity {

    private Button cameraButton;
    private Button resultsButton;
    private TextView placeholderText;
    private ImageView picturePreview;
    private int myRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        cameraButton = (Button)findViewById(R.id.takePictureButton);
        resultsButton = (Button)findViewById(R.id.resultsButton);
        placeholderText = (TextView)findViewById(R.id.placeholderText);
        picturePreview = (ImageView)findViewById(R.id.picturePreview);

        // Check if camera permission granted
        int permissionsCheck = ContextCompat.checkSelfPermission(TakePictureActivity.this,
                Manifest.permission.CAMERA);

        if (permissionsCheck == PackageManager.PERMISSION_GRANTED) {
            // Code for if permission is granted
        } else {
            ActivityCompat.requestPermissions(TakePictureActivity.this,
                    new String[] {Manifest.permission.CAMERA}, myRequestCode);
        }

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Create file to hold picture
                File photoFile = null;
                try {
                    photoFile = createFile();
                } catch (IOException ex) {
                    // Code for if an error with creating the file
                }
                if (photoFile != null) {
                    
                    startActivityForResult(intent, myRequestCode);
                }
            }
        });
    }

    private File createFile() throws IOException {
        String photoPath;
        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == myRequestCode && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            picturePreview.setImageBitmap(imageBitmap);
        }

        cameraButton.setText("Retake Picture");

    }
}
