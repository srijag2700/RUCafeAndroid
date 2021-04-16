package com.example.rucafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rucafe.projectfiles.Donut;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DonutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner donutFlavorSpinner, donutQuantitySpinner;
    private Button addToSelectedButton, addDonutsToOrderButton;
    private ListView donutList;
    private TextView donutSubtotal;

    private DecimalFormat df = new DecimalFormat("$#,###,###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
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

        addToSelectedButton = findViewById(R.id.addDonut);
        addToSelectedButton.setOnClickListener(v -> {
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

        donutList.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("Would you like to remove this donut?").setTitle("Remove Donut");
            alert.setPositiveButton("YES", (dialog, which) ->  {
                selectedDonutsAdapter.remove((Donut) parent.getItemAtPosition(position));
                selectedDonutsAdapter.notifyDataSetChanged();
            });
            alert.setNegativeButton("NO", (dialog, which) -> {

            });
            AlertDialog dialog = alert.create();
            dialog.show();
        });

        addDonutsToOrderButton = findViewById(R.id.placeDonutOrderButton);
        addDonutsToOrderButton.setOnClickListener(v -> {
            for (Donut d : selectedDonuts) {
                CafeVariables.currentOrder.add(d);
            }
            selectedDonuts.clear();
            selectedDonutsAdapter.notifyDataSetChanged();

            Context context = getApplicationContext();
            String toastText = "Donuts added to order.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();

            Intent intent1 = new Intent(DonutActivity.this, MainActivity.class);
            startActivity(intent1);
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