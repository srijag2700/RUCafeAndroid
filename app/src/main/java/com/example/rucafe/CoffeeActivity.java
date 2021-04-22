package com.example.rucafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rucafe.projectfiles.Coffee;
import com.example.rucafe.projectfiles.Donut;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class represents the activity for the Order Coffee menu.
 * It controls the functions of picking a coffee size, choosing a quantity, adding and removing coffee, and adding coffee to the current order.
 * @author Srija Gottiparthi, Catherine Nguyen
 */

public class CoffeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner coffeeQuantitySpinner;
    private Button addToSelectedButton, addCoffeeToOrderButton;
    private RadioButton choiceShort, choiceTall, choiceGrande, choiceVenti;
    private RadioGroup coffeeSizeGroup;
    private CheckBox cream, syrup, milk, caramel, whippedCream;
    private ListView coffeeList;
    private TextView coffeeSubtotal;

    private DecimalFormat df = new DecimalFormat("$#,###,###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        coffeeQuantitySpinner = findViewById(R.id.coffeeQuantity);
        coffeeQuantitySpinner.setOnItemSelectedListener(this);

        Integer[] quantities = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        ArrayAdapter<Integer> coffeeQuantities = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, quantities);
        coffeeQuantitySpinner.setAdapter(coffeeQuantities);

        coffeeSizeGroup = findViewById(R.id.pickSize);
        choiceShort = findViewById(R.id.choiceShort);
        choiceTall = findViewById(R.id.choiceTall);
        choiceGrande = findViewById(R.id.choiceGrande);
        choiceVenti = findViewById(R.id.choiceVenti);

        coffeeList = findViewById(R.id.coffeeList);
        ArrayList<Coffee> selectedCoffee = new ArrayList<Coffee>();
        ArrayAdapter<Coffee> selectedCoffeeAdapter = new ArrayAdapter<Coffee>(this, android.R.layout.simple_list_item_1, selectedCoffee);
        coffeeList.setAdapter(selectedCoffeeAdapter);

        coffeeSubtotal = findViewById(R.id.coffeeSubtotalAmount);

        addToSelectedButton = findViewById(R.id.addCoffee);
        addToSelectedButton.setOnClickListener(v -> {
            int selectedSizeID = coffeeSizeGroup.getCheckedRadioButtonId();
            RadioButton selectedSizeButton = coffeeSizeGroup.findViewById(selectedSizeID);
            try {
                String selectedSize = (String) selectedSizeButton.getText();

                ArrayList<String> selectedAddIns = new ArrayList<>();
                cream = findViewById(R.id.choiceCream);
                if (cream.isChecked()) {
                    selectedAddIns.add("Cream");
                }
                syrup = findViewById(R.id.choiceSyrup);
                if (syrup.isChecked()) {
                    selectedAddIns.add("Syrup");
                }
                milk = findViewById(R.id.choiceMilk);
                if (milk.isChecked()) {
                    selectedAddIns.add("Milk");
                }
                caramel = findViewById(R.id.choiceCaramel);
                if (caramel.isChecked()) {
                    selectedAddIns.add("Caramel");
                }
                whippedCream = findViewById(R.id.choiceWhippedCream);
                if (whippedCream.isChecked()) {
                    selectedAddIns.add("Whipped Cream");
                }

                int selectedCoffeeQuantity = (int) coffeeQuantitySpinner.getSelectedItem();
                Coffee newCoffee = new Coffee(selectedSize, selectedCoffeeQuantity, selectedAddIns);
                selectedCoffee.add(newCoffee);
                selectedCoffeeAdapter.notifyDataSetChanged();
            }
            catch (NullPointerException e) {
                Context context = getApplicationContext();
                String toastText = "Please select a coffee size!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();
            }
        });

        selectedCoffeeAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                double coffeeSum = 0;
                super.onChanged();
                for (Coffee c : selectedCoffee) {
                    c.itemPrice();
                    coffeeSum += c.getPrice();
                }
                String coffeeSumString = df.format(coffeeSum);
                coffeeSubtotal.setText(coffeeSumString);
            }
        });

        coffeeList.setOnItemClickListener((parent, view, position, id) -> {
            Context context = getApplicationContext();
            String toastText = "Tap and hold an item to remove it from your order.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();
        });

        coffeeList.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("Would you like to remove this item from your order?").setTitle("Remove Item");
            alert.setPositiveButton("YES", (dialog, which) ->  {
                selectedCoffeeAdapter.remove((Coffee) parent.getItemAtPosition(position));
                selectedCoffeeAdapter.notifyDataSetChanged();
            });
            alert.setNegativeButton("NO", (dialog, which) -> {

            });
            AlertDialog dialog = alert.create();
            dialog.show();
            return true;
        });

        addCoffeeToOrderButton = findViewById(R.id.placeCoffeeOrderButton);
        addCoffeeToOrderButton.setOnClickListener(v -> {
            if(selectedCoffee.isEmpty()) {
                Context context = getApplicationContext();
                String toastText = "You have no coffee selected!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();
            }

            else {
                for (Coffee c : selectedCoffee) {
                    CafeVariables.currentOrder.add(c);
                }
                selectedCoffee.clear();
                selectedCoffeeAdapter.notifyDataSetChanged();

                Context context = getApplicationContext();
                String toastText = "Coffee added to order.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();

                Intent intent = new Intent(CoffeeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Determines what to do when an item is selected
     * @param parent the parent
     * @param view the view
     * @param position the position of the selected item
     * @param id the id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    /**
     * Determines what to do when nothing is selected
     * @param parent the parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

