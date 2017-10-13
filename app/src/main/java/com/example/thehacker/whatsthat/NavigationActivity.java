package com.example.thehacker.whatsthat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavigationActivity extends AppCompatActivity {
    //button fields
    private Button takeAPicture;
    private  Button gallery;
    private Button history;

    public void takeAPicture(){
        //set up button
        takeAPicture =(Button)findViewById(R.id.takeAPicture);
        //prepare onclick listener
        takeAPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create Intent and pass intent to takeAPicture
                Intent intent = new Intent(NavigationActivity.this, TakePictureActivity.class);

            }
        });

    }
    public void gallery(){
        //set up Button
        gallery =(Button)findViewById(R.id.Gallery);
        //onclick listener
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create Intent and pass Intent to Gallery
                Intent intent = new Intent(NavigationActivity.this, gallery.class);
            }
        });

    }

    public void history(){
        //set up Button
        history = (Button)findViewById(R.id.History);
        //onclick listener
        history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //create Intent and pass Intent to Gallery
                Intent intent = new Intent(NavigationActivity.this, history.class);
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }
}
