package com.example.rucafe;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * This class is used to get the views from the row layout file.
 * @author Nathan Roh
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;

    /**
     * Gets the views from the row layout file.
     * @param itemView
     */
    public ViewHolder(@NotNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.item_image_view);
        textView = itemView.findViewById(R.id.item_text_view);
    }
}