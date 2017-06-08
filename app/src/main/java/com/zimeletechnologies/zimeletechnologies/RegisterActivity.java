package com.zimeletechnologies.zimeletechnologies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnRegister;
    EditText name, address, age;
    DatePicker dob;
    String string_dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setToolbar();

        btnRegister = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        age = (EditText) findViewById(R.id.age);
        dob = (DatePicker) findViewById(R.id.dob);

        Calendar dateInstance = Calendar.getInstance();
        dob.updateDate(dateInstance.get(Calendar.YEAR), dateInstance.get(Calendar.MONTH), dateInstance.get(Calendar.DATE));
        dob.setMaxDate(dateInstance.getTimeInMillis());

        btnRegister.setOnClickListener(this);
    }

    private void setToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle("Register");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.register:

                if(TextUtils.isEmpty(name.getText().toString())){

                    name.setError(getString(R.string.name_error));
                    name.findFocus();

                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(name.getWindowToken(), 0);

                }else if(TextUtils.isEmpty(address.getText().toString())){

                    address.setError(getString(R.string.address_error));
                    address.findFocus();

                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(address.getWindowToken(), 0);

                }else if(TextUtils.isEmpty(age.getText().toString())){

                    age.setError(getString(R.string.age_error));
                    age.findFocus();

                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(age.getWindowToken(), 0);

                }else{

                    string_dob = dob.getYear() + "/" + (dob.getMonth() + 1) + "/" + dob.getDayOfMonth() ;

                    SimpleDateFormat format = new SimpleDateFormat("yyyy/M/dd");

                    try {

                        Date date1 = format.parse(string_dob);
                        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

                        Intent intent = new Intent(RegisterActivity.this, UserDetails.class);
                        intent.putExtra("name",name.getText().toString());
                        intent.putExtra("address", address.getText().toString());
                        intent.putExtra("age", age.getText().toString());
                        intent.putExtra("dob", dateFormat.format(date1));
                        startActivity(intent);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

}
