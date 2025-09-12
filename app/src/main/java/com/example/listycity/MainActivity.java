package com.example.listycity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    Button btnAddCity, btnDeleteCity;

    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    int selectedIndex = -1; // -1 means nothing selected yet

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        btnAddCity = findViewById(R.id.btn_add_city);
        btnDeleteCity = findViewById(R.id.btn_delete_city);

        // starter data
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin",
                "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        // use a built-in layout that supports "activated" state highlighting
        cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1, dataList);
        cityList.setAdapter(cityAdapter);

        // select on tap
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityList.setItemChecked(position, true);
            btnDeleteCity.setEnabled(true);
        });

        // add flow: press ADD CITY -> type -> CONFIRM
        btnAddCity.setOnClickListener(v -> showAddCityDialog());

        // delete flow: must have a selection
        btnDeleteCity.setOnClickListener(v -> {
            if (selectedIndex < 0 || selectedIndex >= dataList.size()) {
                Toast.makeText(this, "Pick a city first", Toast.LENGTH_SHORT).show();
                return;
            }
            String removed = dataList.remove(selectedIndex);
            cityAdapter.notifyDataSetChanged();

            // clear selection
            cityList.clearChoices();
            cityList.requestLayout();
            selectedIndex = -1;
            btnDeleteCity.setEnabled(false);

            Toast.makeText(this, "Deleted " + removed, Toast.LENGTH_SHORT).show();
        });

        // start with delete disabled until a city is selected
        btnDeleteCity.setEnabled(false);
    }

    private void showAddCityDialog() {
        final EditText input = new EditText(this);
        input.setHint("City name");
        input.setSingleLine(true);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});

        FrameLayout container = new FrameLayout(this);
        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        container.setPadding(pad, pad, pad, pad);
        container.addView(input, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        new AlertDialog.Builder(this)
                .setTitle("Add a city")
                .setView(container)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(this, "Please type a city name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (dataList.contains(name)) {
                        Toast.makeText(this, "That city is already in the list", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dataList.add(name);
                    cityAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Added " + name, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
