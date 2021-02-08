package com.ehealth4everyone.gbayesolaemmanueloluwaseyi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ListView;

import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.adapter.CarOwnersArrayAdapter;
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.util.CSVFile;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class CarOwnersActivity extends Activity {
    public static final String INTENT_GENDER = "gender";
    public static final String INTENT_COUNTRIES = "countries";
    public static final String INTENT_COLORS = "colors";

    String gender;
    String[] countries, colors;

    ListView listView;
    CarOwnersArrayAdapter carOwnersArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_owners_main);

        Intent intent = getIntent();

        gender = intent.getStringExtra(INTENT_GENDER);
        countries = intent.getStringArrayExtra(INTENT_COUNTRIES);
        colors = intent.getStringArrayExtra(INTENT_COLORS);
        Log.d("TAG", "onCreate: " + countries.length);


        listView = (ListView) findViewById(R.id.car_owners_list);
        carOwnersArrayAdapter = new CarOwnersArrayAdapter(CarOwnersActivity.this, R.layout.car_owners_item);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(carOwnersArrayAdapter);
        listView.setNestedScrollingEnabled(true);
        listView.onRestoreInstanceState(state);

        InputStream inputStream = getResources().openRawResource(R.raw.car_ownsers_data);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> carOwnersList = csvFile.read();

        for (String[] carOwnersData : carOwnersList) {
            if(carOwnersData[8].equalsIgnoreCase(gender)){
                carOwnersArrayAdapter.add(carOwnersData);
            }

            for(String country: countries){
                if(carOwnersData[4].equalsIgnoreCase(country)){
                    carOwnersArrayAdapter.add(carOwnersData);
                }
            }

            for(String color: colors){
                if(carOwnersData[7].equalsIgnoreCase(color)){
                    carOwnersArrayAdapter.add(carOwnersData);
                }
            }

        }
    }
}
