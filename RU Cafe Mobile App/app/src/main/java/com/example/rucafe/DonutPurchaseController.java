package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * This class manages all the code associated with the Donut Purchase activity.
 * @author Nathan Roh
 */
public class DonutPurchaseController extends AppCompatActivity {

    private static final String [] amountArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25"};

    private Order order;
    private ArrayList<Order> orderList;
    private Intent data;
    private String type;

    ImageView donutDisplay;
    TextView donutName;
    TextView donutSubTotal;
    Spinner donutAmountSpinner;

    /**
     * Sets up the DonutPurchase activity whenever a DonutPurchase activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut_purchase);

        order = (Order) getIntent().getSerializableExtra("currentOrder");
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("allOrders");

        donutDisplay = findViewById(R.id.donutDisplay);
        donutName = findViewById(R.id.donutName);
        donutAmountSpinner = findViewById(R.id.donutAmountSpinner);
        donutSubTotal = findViewById(R.id.donutSubTotal);
        donutDisplay.setImageResource(getIntent().getIntExtra("image", R.drawable.cakedonuts));
        donutName.setText(getIntent().getStringExtra("flavor"));

        data = new Intent();
        data.putExtra("order", order);
        data.putExtra("allOrders", orderList);
        setResult(RESULT_OK, data);

        establishType();
        initializeSpinner();
        updatePrice();
    }

    /**
     * Assigns a type of donut based on the flavor selected.
     */
    private void establishType() {
        String flavor = getIntent().getStringExtra("flavor");

        if (flavor.equals("Old Fashioned") || flavor.equals("Blueberry") || flavor.equals("Cinnamon Sugar")) {
            type = "Cake Donuts";
        } else if (flavor.equals("Jelly") || flavor.equals("Pumpkin Filled") || flavor.equals("Lemon Filled")) {
            type = "Donut Holes";
        }
        else {
            type = "Yeast Donuts";
        }
    }

    /**
     * Initializes the spinner in the DonutPurchase activity.
     */
    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, amountArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
        donutAmountSpinner = findViewById(R.id.donutAmountSpinner);
        donutAmountSpinner.setAdapter(adapter);
        donutAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                updatePrice();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });
    }

    /**
     * Updates the price displayed.
     */
    private void updatePrice() {
        Donut donut = createDonut();
        donutSubTotal.setText(String.format("$%.2f", donut.itemPrice()* donut.getAmount()));
        donutName.setText(donut.toStringDonutView());
    }

    /**
     * Creates an instance of Donut.
     * @return the Donut that was created.
     */
    private Donut createDonut() {
        String amountString = donutAmountSpinner.getSelectedItem().toString();
        int amount = Integer.parseInt(amountString);
        return new Donut(type, amount, getIntent().getStringExtra("flavor"), order.getItemNum());
    }

    /**
     * Adds a donut to the order.
     * @param view the current view.
     */
    public void addToOrder(View view) {
        Donut donut = createDonut();
        order.addItem(donut);
        data.putExtra("order", order);
        Toast.makeText(getApplicationContext(), "Donut Added to Order", Toast.LENGTH_SHORT).show();
    }

    /**
     * Overrides the function of the back button within the layout.
     * @param item The menu item that was selected.
     * @return true if the item selected is the back button, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}