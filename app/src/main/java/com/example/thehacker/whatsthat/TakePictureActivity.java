package com.example.thehacker.whatsthat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;


public class TakePictureActivity extends AppCompatActivity {


    private ImageButton cameraButton;
    private ImageButton resultsButton;

    private Button cameraButton;
    private Button resultsButton;

    private ImageView picturePreview;
    private int myRequestCode = 1;
    private String API_KEY = ""; // ADD API KEY!!!
    private static File photoURI;
    private TextView testText;
    private static String fileName;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        cameraButton = (ImageButton)findViewById(R.id.takePictureButton);
        resultsButton = (ImageButton)findViewById(R.id.resultsButton);

        picturePreview = (ImageView)findViewById(R.id.picturePreview);
        testText = (TextView)findViewById(R.id.testText);

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
                // Was file Type for external
                String photoFile = null;
                try {
                    photoFile = createFile();
                } catch (IOException ex) {
                    // Code for if an error with creating the file
                }
                if (photoFile != null) {
                    photoURI = new File(getApplicationContext().getFilesDir(), photoFile);
                     /*photoURI = FileProvider.getUriForFile(TakePictureActivity.this,
                            "com.example.thehacker.whatsthat",
                            photoFile);*/
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, myRequestCode);
                }
            }
        });

        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                
  
                testText.setText("Sending to Clarifai");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitArray = bos.toByteArray();

                ClarifaiClient blah = new ClarifaiBuilder(API_KEY).buildSync();

                blah.getDefaultModels().foodModel().predict()
                        .withInputs(
                                ClarifaiInput.forImage(bitArray)//new File(fileName))
                        )
                        .executeAsync(new ClarifaiRequest.Callback<List<ClarifaiOutput<Concept>>>() {
                            @Override
                            public void onClarifaiResponseSuccess(List<ClarifaiOutput<Concept>> clarifaiOutputs) {
                                String testJSON = new Gson().toJson(clarifaiOutputs);
                                try {
                                    JSONArray test = new JSONArray(testJSON);
                                    JSONObject test2 = test.getJSONObject(0);
                                    JSONArray test3 = test2.getJSONArray("data");

                                    JSONObject test4 = test3.getJSONObject(0);
                                    String test5 = test4.getString("name");
                                    Double test6 = test4.getDouble("value");
                                    testText.setText(test5 + " " + test6.toString());
                                    Log.v("TakePic", test5 + " " + test6.toString());

                                } catch (JSONException e) {
                                    Log.e("TakePic", e.toString());
                                }
                            }

                            @Override
                            public void onClarifaiResponseUnsuccessful(int errorCode) {

                            }

                            @Override
                            public void onClarifaiResponseNetworkError(IOException e) {

                            }
                        });

            }
        });
    }

    // Returned File for external access
    private String createFile() throws IOException {
        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "whatsthat_" + timeStamp + "_";
        File storageDir = getFilesDir();
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir);

        fileName = image.getAbsolutePath();
        return image.getName();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == myRequestCode && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap)extras.get("data");
            picturePreview.setImageBitmap(imageBitmap);
        }



    }
}
