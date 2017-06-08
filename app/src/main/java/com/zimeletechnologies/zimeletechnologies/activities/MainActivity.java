package com.zimeletechnologies.zimeletechnologies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zimeletechnologies.zimeletechnologies.R;

public class MainActivity extends AppCompatActivity {

    Button btnRegister;
    Button btnInfor;
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = (Button) findViewById(R.id.button3);
        btnInfor = (Button) findViewById(R.id.button2);
        btnDownload = (Button) findViewById(R.id.button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });


        btnInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, FlightInformationActivity.class));

            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, DownloadActivity.class));

            }
        });
    }


}
