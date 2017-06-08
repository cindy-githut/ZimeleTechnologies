package com.zimeletechnologies.zimeletechnologies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zimeletechnologies.zimeletechnologies.R;
import com.zimeletechnologies.zimeletechnologies.adapters.PlainAdapter;
import com.zimeletechnologies.zimeletechnologies.models.PlainItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FlightInformationActivity extends AppCompatActivity {

    OkHttpClient client;
    String todaysDate;
    PlainItem plainItem;
    ArrayList<PlainItem> flightsList;
    PlainAdapter flightsAdapter;
    ListView listview;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_information);

        client = new OkHttpClient();
        flightsList = new ArrayList<>();
        listview = (ListView) findViewById(R.id.listview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        setToolbar();

        //get todays date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        todaysDate = df.format(cal.getTime());

        String url = "https://www.flysaa.com/za/en/mandiGetJsonWebService.json?serviceName=flightSchedules&username=test&password=1234&depAirport=JNB&destAirport=CPT&date="+ todaysDate
                + "&product=flt&language=EN&sessionId=D9FDF251C4ADED6830EC38F3F31F1690";

        Log.d("URL", url);
        populateListWithData(url);
    }

    private void setToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle("Flight Information");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
    private void populateListWithData(String url) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                try{
                    FlightInformationActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(FlightInformationActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch(Exception exc){
                    Log.d("Error:", exc.getLocalizedMessage());
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String responseString = response.body().string();

                try{

                    FlightInformationActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()) {

                                try {

                                    JSONObject flights = new JSONObject(responseString);
                                    handleServerResponse(flights);

                                } catch (JSONException | ParseException e) {
                                    e.printStackTrace();
                                }

                            }else{

                                Toast.makeText(FlightInformationActivity.this, "Something went wrong please, try again later.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }catch(Exception exc){
                    Log.d("Error:", exc.getLocalizedMessage());
                }

            }
        });
    }
    private void handleServerResponse(JSONObject jsonObject) throws JSONException, ParseException {

        JSONObject flightSchedules = jsonObject.getJSONObject("flightSchedules");
        JSONArray flights = flightSchedules.getJSONArray("flight");
        String summary;

        for (int i = 0; i < flights.length(); i++){

            JSONObject flight = flights.getJSONObject(i);

            plainItem = new PlainItem();

            if(flight.has("summary") && flight.getString("summary") != null){

                summary = flight.getString("summary");
                //split by whitespace
                String[] splited = summary.split("\\s+");

                plainItem.setFlightNo(splited[5]);
                plainItem.setDeparture(splited[0]);
                plainItem.setArrival(splited[2]);

            }else{

                plainItem.setFlightNo("");
                plainItem.setDeparture("");
                plainItem.setArrival("");

            }

            if(flight.has("arrivalAirport") && flight.getString("arrivalAirport") != null){
                plainItem.setDestination(flight.getString("arrivalAirport"));
            }else{
                plainItem.setDestination("");
            }

            if(flight.has("departureAirport") && flight.getString("departureAirport") != null){
                plainItem.setOrigin(flight.getString("departureAirport"));
            }else{
                plainItem.setOrigin("");
            }

            if(flight.has("date") && flight.getString("date") != null){
                plainItem.setDate( getDayOfTheWeek (flight.getString("date")));
            }else{
                plainItem.setDate("");
            }

            flightsList.add(plainItem);
        }

        try{

            flightsAdapter = new PlainAdapter(flightsList, FlightInformationActivity.this);
            listview.setAdapter(flightsAdapter);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    private String getDayOfTheWeek(String date) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1=format.parse(date);
        DateFormat dayFormat = new SimpleDateFormat("EEEE - d. MMM yyyy");

        return dayFormat.format(date1);
    }
}
