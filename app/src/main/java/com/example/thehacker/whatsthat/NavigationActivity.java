
package com.example.thehacker.whatsthat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class NavigationActivity extends AppCompatActivity {

    private ImageButton navPictureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navPictureButton = (ImageButton) findViewById(R.id.navPictureButton);

        navPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationActivity.this, TakePictureActivity.class);
                startActivity(intent);
            }
        });
    }
}

