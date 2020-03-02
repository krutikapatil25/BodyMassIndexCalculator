package com.future.bodymassindexproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CalculateActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks
{

    TextView tvCity, tvWeather;
    GoogleApiClient gac;
    Location loc;
    TextView tvCalWelcome, tvHeight, tvFeet, tvInches;
    EditText etWeight;
    Button btnCalculate, btnView;
    Spinner spFeet, spInches;
    SharedPreferences sp;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        tvCalWelcome = (TextView) findViewById(R.id.tvcalWelcome);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvFeet = (TextView) findViewById(R.id.tvFeet);
        tvInches = (TextView) findViewById(R.id.tvInches);
        etWeight = (EditText) findViewById(R.id.etWeight);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnView = (Button) findViewById(R.id.btnView);
        spFeet = (Spinner) findViewById(R.id.spFeet);
        spInches = (Spinner) findViewById(R.id.spInches);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvWeather = (TextView) findViewById(R.id.tvWeather);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        gac = builder.build();




        sp = getSharedPreferences("p1", MODE_PRIVATE);


        ArrayList<String> feet = new ArrayList<>();
        feet.add("1");
        feet.add("2");
        feet.add("3");
        feet.add("4");
        feet.add("5");
        feet.add("6");
        feet.add("7");
        feet.add("8");

        ArrayAdapter<String> feetAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feet);

        spFeet.setAdapter(feetAdapter);

        ArrayList<String> inches = new ArrayList<>();
        inches.add("1");
        inches.add("2");
        inches.add("3");
        inches.add("4");
        inches.add("5");
        inches.add("6");
        inches.add("7");
        inches.add("8");
        inches.add("9");
        inches.add("10");
        inches.add("11");
        inches.add("12");

        ArrayAdapter<String> inchesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inches);

        spInches.setAdapter(inchesAdapter);


        Intent i = getIntent();
        String name = i.getStringExtra("name");
        tvCalWelcome.setTextColor(Color.BLUE);
        tvCalWelcome.setText("Welcome " + name);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String f = spFeet.getSelectedItem().toString();
                float fe = Float.parseFloat(f);
                String i = spInches.getSelectedItem().toString();
                float in = Float.parseFloat(i);
                float meters = (float) ((fe * 0.3048) + (in * 0.0254));

                String weight = etWeight.getText().toString();
                if (weight.length() == 0) {
                    etWeight.setError("Enter your weight");
                    etWeight.requestFocus();
                    return;
                }

                float wt = Float.parseFloat(weight);

                float bmi = wt / (meters * meters);
                //  Toast.makeText(CalculateActivity.this, "" + bmi, Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("weight", weight);
                editor.putString("bmi", String.valueOf(bmi));
                editor.commit();

                Intent j = new Intent(CalculateActivity.this, ResultActivity.class);
                j.putExtra("bmi", bmi);
                startActivity(j);


            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String weight = etWeight.getText().toString();
                Intent i = new Intent(CalculateActivity.this, ViewActivity.class);

                i.putExtra("weight", weight);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {

        if (item.getItemId() == R.id.AboutUs) {
            Toast.makeText(this, "Developed By : Krutika Patil", Toast.LENGTH_LONG).show();


        }

        if (item.getItemId() == R.id.Website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.weightreporter.com"));
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gac != null)
            gac.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gac != null)
            gac.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if ( loc != null)
        {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            Geocoder g = new Geocoder(this, Locale.ENGLISH);
            try
            {
                List<Address> la =g.getFromLocation(lat,lon,1);
                Address add = la.get(0);
                String msg =  add.getLocality() ;

                tvCity.setText(msg);
                String c = tvCity.getText().toString();
                String url = "http://api.openweathermap.org/data/2.5/weather?units=metric";
                String api = "6595d0e02beaf006ec86562677754c42";
                MyTask mt = new MyTask();
                mt.execute(url + "&q=" + c + "&appid=" + api);


            }
            catch (Exception e)
            {
                Toast.makeText(this, "Location Issue", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection suspended", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();

    }

    class MyTask extends AsyncTask<String, Void, Double>
    {
        double temp;

        @Override
        protected Double doInBackground(String... strings) {

            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.connect();

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String json = "", line = "";

                while ((line = br.readLine()) != null)
                {
                    json = json + line + "\n";
                }

                JSONObject j = new JSONObject(json);
                JSONObject p = j.getJSONObject("main");
                temp = p.getDouble("temp");

            }
            catch (Exception e) {
                Toast.makeText(CalculateActivity.this, "Issue:" + e, Toast.LENGTH_SHORT).show();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvWeather.setText("Temp: " + aDouble);
        }
    }




}
