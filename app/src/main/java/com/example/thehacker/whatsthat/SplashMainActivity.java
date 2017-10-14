
package com.example.thehacker.whatsthat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashMainActivity extends AppCompatActivity {

    private Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);

        mainButton = (Button)findViewById(R.id.mainButton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashMainActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });
    }
}

