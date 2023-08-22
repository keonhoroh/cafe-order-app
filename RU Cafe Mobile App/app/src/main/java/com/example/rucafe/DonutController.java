package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This class manages all the code associated with the Donut activity.
 * @author Nathan Roh
 */
public class DonutController extends AppCompatActivity {

    private static final String[] flavors = {"Strawberry Frosted", "Chocolate Frosted", "Jelly", "Glazed", "Lemon Filled",
            "Sugar", "Old Fashioned", "Blueberry", "Cinnamon Sugar", "Powdered", "Pumpkin Filled", "Butternut"};
    private static int[] imageIds = {R.drawable.strawberryfrosted, R.drawable.chocolatefrosted, R.drawable.jelly, R.drawable.glazed,
            R.drawable.lemonfilled, R.drawable.sugar, R.drawable.oldfashioned, R.drawable.blueberry, R.drawable.cinnamonsugar,
            R.drawable.powdered, R.drawable.pumpkinfilled, R.drawable.butternut};

    private Order order;
    private ArrayList<Order> orderList;
    private Intent data;
    private ActivityResultLauncher<Intent> resultLauncher;

    private RecyclerView recycler;

    /**
     * Sets up the Donut activity whenever a donut activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);

        order = (Order) getIntent().getSerializableExtra("currentOrder");
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("allOrders");

        data = new Intent();
        data.putExtra("order", order);
        data.putExtra("allOrders", orderList);
        setResult(RESULT_OK, data);

        recycler = findViewById(R.id.recycler);
        setRecycler();

        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            /**
             * Defines behavior for what child activities should return.
             * @param result the output of the child activity
             */
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent newData = result.getData();
                    order = (Order) newData.getSerializableExtra("order");
                    orderList = (ArrayList<Order>) newData.getSerializableExtra("allOrders");
                    setResult(RESULT_OK, newData);
                }
            }
        };
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
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
     * Sets the RecyclerView for the Donut View.
     */
    private void setRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(new RecyclerViewAdapter(this, flavors, imageIds));
    }

    /**
     * Launches the DonutPurchase view.
     * @param flavor the flavor selected from the RecyclerView
     * @param imageId the image associated with the above flavor.
     */
    public void launchDonutPurchase(String flavor, int imageId) {
        Intent intent = new Intent(this, DonutPurchaseController.class);
        intent.putExtra("currentOrder", order);
        intent.putExtra("allOrders", orderList);
        intent.putExtra("flavor", flavor);
        intent.putExtra("image", imageId);

        resultLauncher.launch(intent);
    }

}