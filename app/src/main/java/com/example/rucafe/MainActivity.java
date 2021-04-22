package com.example.rucafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

/**
 * This class represents the activity for the Main Menu.
 * It controls the buttons to order donuts, order coffee, view the current order, and view all store orders.
 * @author Srija Gottiparthi, Catherine Nguyen
 */

public class MainActivity extends AppCompatActivity {
    private Button donutsButton, coffeeButton, viewButton, allButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // order donuts button
        donutsButton = findViewById(R.id.orderDonuts);
        donutsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DonutActivity.class);
            startActivity(intent);
        });

        // order coffee button
        coffeeButton = findViewById(R.id.orderCoffee);
        coffeeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CoffeeActivity.class);
            startActivity(intent);
        });

        // view order button
        viewButton = findViewById(R.id.viewOrder);
        viewButton.setOnClickListener(v -> {
            if(CafeVariables.currentOrder.getOrder().isEmpty()) {
                Context context = getApplicationContext();
                String toastText = "You have no items in your order!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();
            }

            else {
                Intent intent = new Intent(MainActivity.this, ViewOrderActivity.class);
                startActivity(intent);
            }
        });

        // all orders button
        allButton = findViewById(R.id.allOrders);
        allButton.setOnClickListener(v -> {
            if(CafeVariables.allOrders.getStore().isEmpty()) {
                Context context = getApplicationContext();
                String toastText = "You have no store orders!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();
            }

            else {
                Intent intent = new Intent(MainActivity.this, AllOrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}