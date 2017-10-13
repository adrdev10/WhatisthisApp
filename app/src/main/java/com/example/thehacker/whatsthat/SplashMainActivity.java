package com.example.thehacker.whatsthat;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import jp.wasabeef.blurry.Blurry;

public class SplashMainActivity extends AppCompatActivity {

    private ImageView image;
    private ImageView logoImage;
    private ImageButton button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);

        image = (ImageView)findViewById(R.id.BackgroundView);
        button = (ImageButton)findViewById(R.id.LogoImageButton);


        image.post(new Runnable() {
            @Override
            public void run() {
                setImageBlur();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
                Intent intent = new Intent(SplashMainActivity.this, NavigationActivity.class);
                startActivity(intent);

            }
        });

    }

    private void startAnimation() {


        button.setScaleX(0);
        button.setScaleY(0);
        button.animate().scaleX(1).scaleY(1);



    }

    private void setImageBlur() {
        Blurry.with(SplashMainActivity.this)
                .async()
                .sampling(1)
                .color(Color.argb(60, 0, 255, 255))
                .capture(findViewById(R.id.BackgroundView))
                .into((ImageView)findViewById(R.id.BackgroundView));

    }


}
