package com.ehealth4everyone.gbayesolaemmanueloluwaseyi.adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class CarOwnersArrayAdapter extends ArrayAdapter<String[]> {
    private final List<String[]> carOwnersList = new ArrayList<>();


    static class ItemViewHolder {
        TextView fullName, email;
        ImageView view;
    }

    public CarOwnersArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(String[] object) {
        carOwnersList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.carOwnersList.size();
    }

    @Override
    public String[] getItem(int index) {
        return this.carOwnersList.get(index);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.car_owners_item, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.fullName = (TextView) row.findViewById(R.id.fullName);
            viewHolder.email = (TextView) row.findViewById(R.id.email);
            viewHolder.view = (ImageView) row.findViewById(R.id.view);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) row.getTag();
        }
        String[] stat = getItem(position);
        viewHolder.fullName.setText(stat[1] + " " + stat[2]);
        viewHolder.email.setText(stat[3]);


        viewHolder.view.setOnClickListener(v -> {
            View modelBottom = LayoutInflater.from(this.getContext()).inflate(R.layout.car_owners_bottomsheet, null);
            DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();

            int height = displayMetrics.heightPixels;
            int maxHeight = (int) (height * 0.30);
            modelBottom.setMinimumHeight(maxHeight);

            TextView fullName = modelBottom.findViewById(R.id.FullName);
            TextView country = modelBottom.findViewById(R.id.country);
            TextView carDetails = modelBottom.findViewById(R.id.carDetails);
            TextView jobTitle = modelBottom.findViewById(R.id.job_title);
            TextView bio = modelBottom.findViewById(R.id.bio);
            TextView gender = modelBottom.findViewById(R.id.Gender);
            CardView email = modelBottom.findViewById(R.id.email);

            fullName.setText(stat[1] + " " + stat[2]);
            country.setText(stat[4]);
            jobTitle.setText(stat[9]);
            bio.setText(stat[10]);
            gender.setText(stat[8]);
            carDetails.setText(stat[5] + ", " + stat[7] + ", " + stat[6]);

            email.setOnClickListener(v1 -> {
                String mailto = "mailto:" + "" + stat[3] +
                        "?cc=" +
                        "&subject=" + Uri.encode("") +
                        "&body=" + Uri.encode("");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    getContext().startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "Error to open email app", Toast.LENGTH_SHORT).show();
                }
            });


            BottomSheetDialog dialog = new BottomSheetDialog(this.getContext());
            dialog.setContentView(modelBottom);

            dialog.show();
        });

        return row;
    }
}
