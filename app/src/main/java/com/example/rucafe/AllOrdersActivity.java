package com.example.rucafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rucafe.projectfiles.MenuItem;
import com.example.rucafe.projectfiles.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class represents the activity for the All Orders menu.
 * It controls the functions of picking an order number and cancelling an entire order.
 * @author Srija Gottiparthi, Catherine Nguyen
 */

public class AllOrdersActivity extends AppCompatActivity {
    private ListView currentOrderList;
    private Spinner orderNumbers;
    private TextView currentOrderTotal;
    private Button cancelButton;

    private DecimalFormat df = new DecimalFormat("$#,###,###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        orderNumbers = findViewById(R.id.orderNumberSpinner);

        ArrayList<Integer> allStoreOrderNumbers = new ArrayList<Integer>();
        for (Order o : CafeVariables.allOrders.getStore()) {
            allStoreOrderNumbers.add(o.getOrderNumber());
        }
        ArrayAdapter<Integer> allStoreOrderNumbersAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, allStoreOrderNumbers);
        orderNumbers.setAdapter(allStoreOrderNumbersAdapter);
        orderNumbers.setSelection(0);

        currentOrderList = findViewById(R.id.allOrdersList);
        currentOrderTotal = findViewById(R.id.allTotalText);

        orderNumbers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Order currentOrder = CafeVariables.allOrders.getOrderByOrderNumber((int) orderNumbers.getSelectedItem());
                ArrayList<MenuItem> selectedOrder = currentOrder.getOrder();
                ArrayAdapter<MenuItem> selectedOrderAdapter = new ArrayAdapter<MenuItem>(view.getContext(), android.R.layout.simple_list_item_1, selectedOrder);
                currentOrderList.setAdapter(selectedOrderAdapter);
                currentOrderTotal.setText(df.format(currentOrder.getTotal()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancelButton = findViewById(R.id.cancelOrderButton);
        cancelButton.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Would you like to remove this order? This action is not reversible!").setTitle("Cancel Order");
            alert.setPositiveButton("YES", (dialog, which) ->  {
                //Order currentOrder = CafeVariables.allOrders.getOrderByOrderNumber((int) orderNumbers.getSelectedItem());
                CafeVariables.allOrders.remove(CafeVariables.allOrders.getOrderByOrderNumber((int) orderNumbers.getSelectedItem()));
                //allStoreOrderNumbers.remove(CafeVariables.allOrders.getOrderByOrderNumber((int) orderNumbers.getSelectedItem()).getOrderNumber());
                // it's ok code, you can start working when you feel like it :)

                if (CafeVariables.allOrders.getStore().isEmpty()) {
                    Context context = getApplicationContext();
                    String toastText = "All orders have been removed!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();

                    Intent intent = new Intent(AllOrdersActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                else {
                    ArrayList<Integer> newStoreOrderNumbers = new ArrayList<Integer>();
                    for (Order o : CafeVariables.allOrders.getStore()) {
                        newStoreOrderNumbers.add(o.getOrderNumber());
                    }
                    ArrayAdapter<Integer> newStoreOrderNumbersAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, newStoreOrderNumbers);
                    orderNumbers.setAdapter(newStoreOrderNumbersAdapter);
                    orderNumbers.setSelection(0);

                    allStoreOrderNumbersAdapter.notifyDataSetChanged();
                    orderNumbers.setSelection(0);
                }

            });
            alert.setNegativeButton("NO", (dialog, which) -> {

            });
            AlertDialog dialog = alert.create();
            dialog.show();
        });

    }
}