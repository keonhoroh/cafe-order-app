package com.example.rucafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 * @author Nathan Roh
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private  String[] flavors;
    private int[] imageIds;
    private DonutController donuts;


    /**
     * A constructor that includes the necessary information for the RecyclerView.
     * @param donuts The class that is calling this method
     * @param flavors The array of available flavors
     * @param imageIds The array of corresponding image IDs
     */
    public RecyclerViewAdapter(DonutController donuts, String[] flavors, int[] imageIds) {
        super();
        this.donuts = donuts;
        this.flavors = flavors;
        this.imageIds = imageIds;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent the parent of the view
     * @param viewType the type of view
     * @return
     */
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_layout, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        int pos = position;
        holder.imageView.setImageResource(imageIds[position]);
        holder.textView.setText(flavors[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donuts.launchDonutPurchase(flavors[pos], imageIds[pos]);
            }
        });
    }

    /**
     * Get the number of items in the array.
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return flavors.length;
    }
}