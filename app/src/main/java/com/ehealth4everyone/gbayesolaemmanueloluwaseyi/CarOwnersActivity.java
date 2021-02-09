package com.ehealth4everyone.gbayesolaemmanueloluwaseyi;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.adapter.CarOwnersArrayAdapter;
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.util.CSVFile;
import com.ehealth4everyone.restapi.utils.UriUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarOwnersActivity extends Activity {
    public static final String INTENT_GENDER = "gender";
    public static final String INTENT_COUNTRIES = "countries";
    public static final String INTENT_COLORS = "colors";
    public static final String INTENT_START_YEAR = "start_year";
    public static final String INTENT_END_YEAR = "end_year";

    Uri documents;

    ImageView backArrow;


    final int SELECT_DOCUMENT = 50;
    final String TAG = "CarOwnersActivity";

    String gender, startYear, endYear;
    ArrayList countries, color;


    ListView listView;
    CarOwnersArrayAdapter carOwnersArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_owners_main);

        handlePermission();

        Intent intent = getIntent();

        gender = intent.getStringExtra(INTENT_GENDER);
        startYear = intent.getStringExtra(INTENT_START_YEAR);
        endYear = intent.getStringExtra(INTENT_END_YEAR);
        countries = intent.getStringArrayListExtra(INTENT_COUNTRIES);
        color = intent.getStringArrayListExtra(INTENT_COLORS);

        listView = findViewById(R.id.car_owners_list);
        backArrow = findViewById(R.id.back);

        backArrow.setOnClickListener(v -> onBackPressed());
        carOwnersArrayAdapter = new CarOwnersArrayAdapter(CarOwnersActivity.this, R.layout.car_owners_item);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(carOwnersArrayAdapter);
        listView.setNestedScrollingEnabled(true);
        listView.onRestoreInstanceState(state);


        filterCarOwners();


    }

    public void handlePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    SELECT_DOCUMENT);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SELECT_DOCUMENT) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == SELECT_DOCUMENT) {
            if (resultCode == RESULT_OK) {
                // Get the url from data
                Uri documentUri = data.getData();
                this.documents = documentUri;
                if (null != documentUri) {
                    // Get the path from the Uri
                    final String path = UriUtils.getPathFromUri(getApplicationContext(), documents);

                    Log.i(TAG, "Document Path : " + path);
                    //documents.add(documentUri);
                    Log.d(TAG, "Added a Document");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void filterCarOwners() {
        try {
            File csvfile = new File(Environment.getExternalStorageDirectory() + "/ehealth" + "/car_ownsers_data.csv");
            if (!csvfile.exists()) {
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.dialog_warning);
                dialog.setCancelable(false);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                dialog.findViewById(R.id.bt_close).setOnClickListener(v -> {
                    dialog.dismiss();
                    onBackPressed();
                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
            InputStream inputStream = getContentResolver().openInputStream(Uri.fromFile(csvfile));
            CSVFile csvFile = new CSVFile(inputStream);
            List<String[]> carOwnersList = csvFile.read();

            for (String[] carOwnersData : carOwnersList) {
                if (carOwnersData[8].equalsIgnoreCase(gender)) {
                    carOwnersArrayAdapter.add(carOwnersData);
                }

                if (carOwnersData[6].equalsIgnoreCase(startYear)) {
                    carOwnersArrayAdapter.add(carOwnersData);
                }

                if (carOwnersData[6].equalsIgnoreCase(endYear)) {
                    carOwnersArrayAdapter.add(carOwnersData);
                }

                for (int i = 0; i < countries.size(); i++) {
                    if (countries.get(i).toString().equalsIgnoreCase(carOwnersData[4])) {
                        carOwnersArrayAdapter.add(carOwnersData);
                    }
                }

                for (int i = 0; i < color.size(); i++) {
                    if (color.get(i).toString().equalsIgnoreCase(carOwnersData[7])) {
                        carOwnersArrayAdapter.add(carOwnersData);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
