package com.example.rucafe;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rucafe.projectfiles.Donut;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DonutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner donutFlavorSpinner, donutQuantitySpinner;
    private Button addToOrderButton;
    private ListView donutList;
    private TextView donutSubtotal;

    private DecimalFormat df = new DecimalFormat("$#,###,###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);

        donutFlavorSpinner = findViewById(R.id.donutFlavors);
        donutFlavorSpinner.setOnItemSelectedListener(this);

        donutQuantitySpinner = findViewById(R.id.donutQuantity);
        donutQuantitySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> donutFlavors = ArrayAdapter.createFromResource(this, R.array.donut_flavors_array, android.R.layout.simple_spinner_dropdown_item);
        donutFlavorSpinner.setAdapter(donutFlavors);

        Integer[] quantities = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        ArrayAdapter<Integer> donutQuantities = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, quantities);
        donutQuantitySpinner.setAdapter(donutQuantities);

        donutList = findViewById(R.id.donutList);
        ArrayList<Donut> selectedDonuts = new ArrayList<Donut>();
        ArrayAdapter<Donut> selectedDonutsAdapter = new ArrayAdapter<Donut>(this, android.R.layout.simple_list_item_1, selectedDonuts);
        donutList.setAdapter(selectedDonutsAdapter);

        donutSubtotal = findViewById(R.id.donutSubtotalAmountLabel);

        addToOrderButton = findViewById(R.id.addDonut);
        addToOrderButton.setOnClickListener(v -> {
            String selectedDonutFlavor = (String) donutFlavorSpinner.getSelectedItem();
            int selectedDonutQuantity = (int) donutQuantitySpinner.getSelectedItem();
            Donut newDonut = new Donut(selectedDonutFlavor, selectedDonutQuantity);
            selectedDonuts.add(newDonut);
            selectedDonutsAdapter.notifyDataSetChanged();
        });

        selectedDonutsAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                double donutSum = 0;
                super.onChanged();
                for (Donut d : selectedDonuts) {
                    d.itemPrice();
                    donutSum += d.getPrice();
                }
                String donutSumString = df.format(donutSum);
                donutSubtotal.setText(donutSumString);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}