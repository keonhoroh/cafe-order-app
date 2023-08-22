package com.example.rucafe;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class manages all the code associated with the RUCafe activity.
 * @author Nathan Roh
 */
public class RUCafeController extends AppCompatActivity {

    private static final int STARTING_ORDER_NUM = 1;

    private ArrayList<Order> allOrders;

    private Order currentOrder;
    private ActivityResultLauncher<Intent> resultLauncher;


    /**
     * Executes the appropriate code for when the view is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}. Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rucafe);
        if(currentOrder == null) {
            currentOrder = new Order(STARTING_ORDER_NUM);
        }
        if (allOrders == null) {
            allOrders = new ArrayList<>();
        }
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            /**
             * Defines behavior for what child activities should return.
             * @param result the output of the child activity
             */
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    currentOrder = (Order) data.getSerializableExtra("order");
                    allOrders = (ArrayList<Order>) data.getSerializableExtra("allOrders");
                }
            }
        };
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);

    }

    /**
     * Launches the Coffee View.
     * @param view the current view
     */
    public void openCoffeeView(View view) {
        Intent intent = new Intent(this, CoffeeController.class);
        intent.putExtra("currentOrder", currentOrder);
        intent.putExtra("allOrders", allOrders);
        resultLauncher.launch(intent);
    }

    /**
     * Launches the Donut View.
     * @param view the current view
     */
    public void openDonutView(View view) {
        Intent intent = new Intent(this, DonutController.class);
        intent.putExtra("currentOrder", currentOrder);
        intent.putExtra("allOrders", allOrders);
        resultLauncher.launch(intent);
    }


    /**
     * Launches the OrderingBasket View.
     * @param view the current view
     */
    public void openOrderingBasketView(View view) {
        if (currentOrder.getItemList().isEmpty()) {
            if (currentOrder.getItemList().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Your shopping cart is empty", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Intent intent = new Intent(this, OrderingBasketController.class);
        intent.putExtra("currentOrder", currentOrder);
        intent.putExtra("allOrders", allOrders);
        resultLauncher.launch(intent);
    }

    /**
     * Launches the Store Orders View.
     * @param view the current view
     */
    public void openStoreOrdersView(View view) {
        if (allOrders.isEmpty()) {
            Toast.makeText(getApplicationContext(), "There are no orders currently placed", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, StoreOrdersController.class);
        intent.putExtra("currentOrder", currentOrder);
        intent.putExtra("allOrders", allOrders);
        resultLauncher.launch(intent);
    }

}