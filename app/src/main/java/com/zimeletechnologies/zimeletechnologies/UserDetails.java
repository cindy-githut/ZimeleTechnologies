package com.zimeletechnologies.zimeletechnologies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class UserDetails extends AppCompatActivity {

    String age, dob, address,name;
    Intent intent;
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        setToolbar();

        details = (TextView) findViewById(R.id.details);

        intent = getIntent();

        if(intent.hasExtra("name") && intent.getStringExtra("name") != null){
            name = intent.getStringExtra("name");
        }

        if(intent.hasExtra("age") && intent.getStringExtra("age") != null){
            age = intent.getStringExtra("age");
        }

        if(intent.hasExtra("address") && intent.getStringExtra("address") != null){
            address = intent.getStringExtra("address");
        }

        if(intent.hasExtra("dob") && intent.getStringExtra("dob") != null){
            dob = intent.getStringExtra("dob");
        }

        details.setText("Name: " + name + "\n"
                + "Age: " + age + "\n"
                + "DOB: " + dob + "\n"
                + "Address: " +address + "\n");

    }
    private void setToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle("Details");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}
