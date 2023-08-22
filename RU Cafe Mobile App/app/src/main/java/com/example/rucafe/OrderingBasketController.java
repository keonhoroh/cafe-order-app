package com.example.rucafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * This class manages all the code associated with the Ordering Basket activity.
 * @author Nathan Roh
 */
public class OrderingBasketController extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Order order;
    private ArrayList<Order> orderList;
    private Intent data;
    private ArrayAdapter<String> adapter;

    ListView listview;

    TextView subTotal;
    TextView taxAmount;
    TextView totalPrice;

    /**
     * Sets up the OrderingBasket activity whenever a OrderingBasket activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_basket);

        order = (Order) getIntent().getSerializableExtra("currentOrder");
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("allOrders");

        data = new Intent();
        data.putExtra("order", order);
        data.putExtra("allOrders", orderList);
        setResult(RESULT_OK, data);

        subTotal = findViewById(R.id.subTotal);
        taxAmount = findViewById(R.id.taxAmount);
        totalPrice = findViewById(R.id.totalPrice);
        listview = findViewById(R.id.listview);
        setListView();
        updatePrices();


    }

    /**
     * Establishes the program's behavior when an item in the ListView is selected.
     * @param adapterView The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this
     *            will be a view provided by the adapter)
     * @param i The position of the view in the adapter.
     * @param l The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Item");
        alert.setMessage("Would you like to delete this item?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            /**
             * The behavior when "yes" is clicked in the dialog box.
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             */
            public void onClick(DialogInterface dialog, int which) {
                order.getItemList().remove(i);
                setListView();
                updatePrices();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {

            /**
             * The behavior when "no" is clicked in the dialog box.
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             */
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = alert.create();
        dialog.show();
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
     * Places an order by adding it to the list of orders.
     * @param view the current view
     */
    public void placeOrder(View view) {
        if (order.getItemList().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Order Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        orderList.add(order);
        order = new Order(order.getOrderNum() + 1);
        data.putExtra("order", order);
        setListView();
        updatePrices();
        Toast.makeText(getApplicationContext(), "Order Successfully Added", Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets the values in the ListView.
     */
    private void setListView() {
        String[] list = new String[order.getItemList().size()];
        for(int i = 0; i < order.getItemList().size(); i++) {
            list[i] = order.getItemList().get(i).toString();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setOnItemClickListener(this);
        listview.setAdapter(adapter);
    }

    /**
     * Updates the prices displayed in the TextViews.
     */
    private void updatePrices() {
        subTotal.setText("$" + String.format("%.2f", order.getSubTotal()));
        taxAmount.setText("$" + String.format("%.2f", order.getTaxAmount()));
        totalPrice.setText("$" + String.format("%.2f", order.getFinalBill()));

    }

}