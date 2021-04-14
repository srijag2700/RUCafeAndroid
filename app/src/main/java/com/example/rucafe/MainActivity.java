package com.example.rucafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button donutsButton = findViewById(R.id.orderDonuts);
        donutsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DonutActivity.class);
            startActivity(intent);
        });

        Button coffeeButton = findViewById(R.id.orderCoffee);
        coffeeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CoffeeActivity.class);
            startActivity(intent);
        });

        Button viewButton = findViewById(R.id.viewOrder);
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewOrderActivity.class);
            startActivity(intent);
        });

        Button allButton = findViewById(R.id.allOrders);
        allButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllOrdersActivity.class);
            startActivity(intent);
        });
    }
}