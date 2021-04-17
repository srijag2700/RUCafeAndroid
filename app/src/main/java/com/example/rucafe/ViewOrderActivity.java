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
import android.widget.TextView;
import android.widget.Toast;

import com.example.rucafe.projectfiles.Donut;
import com.example.rucafe.projectfiles.MenuItem;
import com.example.rucafe.projectfiles.Order;

import java.text.DecimalFormat;

public class ViewOrderActivity extends AppCompatActivity {
    private ListView currentOrderList;
    private TextView subtotal, tax, total;
    private Button submitOrderButton;
    private DecimalFormat df = new DecimalFormat("$#,###,###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_view_order);

        currentOrderList = findViewById(R.id.currentOrder);
        ArrayAdapter<MenuItem> currentOrderAdapter = new ArrayAdapter<MenuItem>(this, android.R.layout.simple_list_item_1, CafeVariables.currentOrder.getOrder());
        currentOrderList.setAdapter(currentOrderAdapter);

        subtotal = findViewById(R.id.subtotalText);
        subtotal.setText(df.format(CafeVariables.currentOrder.getSubtotal()));

        tax = findViewById(R.id.taxText);
        tax.setText(df.format(CafeVariables.currentOrder.getTax()));

        total = findViewById(R.id.totalText);
        total.setText(df.format(CafeVariables.currentOrder.getTotal()));

        currentOrderList.setOnItemClickListener((parent, view, position, id) -> {
            Context context = getApplicationContext();
            String toastText = "Tap and hold an item to remove it from your order.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();
        });

        currentOrderList.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) (parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("Would you like to remove this item from your order?").setTitle("Remove Item");
            alert.setPositiveButton("YES", (dialog, which) ->  {
                CafeVariables.currentOrder.remove((MenuItem) parent.getItemAtPosition(position));
                //currentOrderAdapter.remove((MenuItem) parent.getItemAtPosition(position));
                currentOrderAdapter.notifyDataSetChanged();
            });
            alert.setNegativeButton("NO", (dialog, which) -> {

            });
            AlertDialog dialog = alert.create();
            dialog.show();
            return true;
        });

        currentOrderAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                subtotal.setText(df.format(CafeVariables.currentOrder.getSubtotal()));
                tax.setText(df.format(CafeVariables.currentOrder.getTax()));
                total.setText(df.format(CafeVariables.currentOrder.getTotal()));
            }
        });

        submitOrderButton = findViewById(R.id.placeOrderButton);
        submitOrderButton.setOnClickListener(v -> {
            CafeVariables.allOrders.add(CafeVariables.currentOrder);
            CafeVariables.currentOrder = new Order();

            Context context = getApplicationContext();
            String toastText = "Order successfully placed!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();

            Intent intent1 = new Intent(ViewOrderActivity.this, MainActivity.class);
            startActivity(intent1);
        });

    }
}