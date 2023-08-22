package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * This class manages all the code associated with the Coffee activity.
 * @author Nathan Roh
 */
public class CoffeeController extends AppCompatActivity {

    private static final String [] amountArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
        "19", "20", "21", "22", "23", "24", "25"};
    private static final String [] sizeArray = {"Short", "Tall", "Grande", "Venti"};

    private Order order;
    private ArrayList<Order> orderList;
    Intent data;

    Spinner sizeSelector;
    Spinner amountSelector;

    CheckBox sweetCream;
    CheckBox frenchVanilla;
    CheckBox irishCream;
    CheckBox caramel;
    CheckBox mocha;

    TextView coffeeSubTotal;


    /**
     * Sets up the Coffee activity whenever a coffee activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        order = (Order) getIntent().getSerializableExtra("currentOrder");
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("allOrders");

        data = new Intent();
        data.putExtra("order", order);
        data.putExtra("allOrders", orderList);
        setResult(RESULT_OK, data);

        initializeSpinners();
        setActivityValues();
        updatePrice();
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

    /**
     * Initializes the values for both spinners in the Coffee view.
     */
    private void initializeSpinners() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, sizeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
        sizeSelector = findViewById(R.id.sizeSpinner);
        sizeSelector.setAdapter(adapter);
        sizeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                updatePrice();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, amountArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
        amountSelector = findViewById(R.id.amountSpinner);
        amountSelector.setAdapter(adapter2);
        amountSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
     * Sets the instance variables that represent an item in the activity.
     */
    private void setActivityValues() {
        sweetCream = findViewById(R.id.sweetCream);
        frenchVanilla = findViewById(R.id.frenchVanilla);
        irishCream = findViewById(R.id.irishCream);
        caramel = findViewById(R.id.caramel);
        mocha = findViewById(R.id.mocha);
        coffeeSubTotal = findViewById(R.id.coffeeSubTotal);

    }

    /**
     * Updates the prices displayed when the target is clicked.
     * @param view the current view
     */
    public void updatePriceFromClick (View view) {
        updatePrice();
    }

    /**
     * Adds the coffee order to the current Ordering Basket when the "Add to Order" button is pressed.
     * @param view the current view
     */
    public void addToOrder(View view) {
        Coffee coffee = createCoffee();
        order.addItem(coffee);
        Toast.makeText(getApplicationContext(), "Coffee Added to Order", Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates the price of the coffee order whenever an add-in is added/removed, the amount is changed, or the size is changed.
     */
    void updatePrice() {
        Coffee coffee = createCoffee();
        coffeeSubTotal.setText(String.format("$%.2f", coffee.itemPrice()* coffee.getAmount()));
    }


    /**
     * Creates an instance of Coffee using the attributes selected on the Coffee View
     * @return an instance of Coffee
     */
    private Coffee createCoffee() {
        String sizeString = sizeSelector.getSelectedItem().toString();
        String amountString = amountSelector.getSelectedItem().toString();
        ArrayList<String> stringArr = createAddIns();
        int amount = Integer.parseInt(amountString);
        return new Coffee(sizeString, stringArr, amount, order.getItemNum());
    }

    /**
     * Creates the list of add-ins based on the add-ins selected on the Coffee View
     * @return an ArrayList of add-ins
     */
    private ArrayList<String> createAddIns() {
        ArrayList<String> stringList = new ArrayList<>();
        if (sweetCream.isChecked()) {
            stringList.add("Sweet Cream");
        }
        if (frenchVanilla.isChecked()) {
            stringList.add("French Vanilla");
        }
        if (irishCream.isChecked()) {
            stringList.add("Irish Cream");
        }
        if (caramel.isChecked()) {
            stringList.add("Caramel");
        }
        if (mocha.isChecked()) {
            stringList.add("Mocha");
        }
            return stringList;
    }

}