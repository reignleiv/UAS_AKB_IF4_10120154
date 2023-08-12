// NIM      : 10120049
// Nama     : Mochammad Gymnastiar
// Kelas    : IF-2

package com.example.uas_akb_if4_10120154;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AboutSliderAdapter extends RecyclerView.Adapter<AboutSliderAdapter.ViewHolder> {
    Context context;
    int header[] = {
            R.string.heading_one,
            R.string.heading_two,
            R.string.heading_three,
    };
    int desc[] = {
            R.string.desc_one,
            R.string.desc_two,
            R.string.desc_three,
    };
    // Constructor of our ViewPager2Adapter class
    AboutSliderAdapter(Context ctx) {
        this.context = ctx;
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_layout, parent, false);
        return new ViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        holder.cardHeader.setText(header[position]);
        holder.cardBody.setText(desc[position]);
    }

    @Override
    public int getItemCount() {
        return header.length;
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardHeader;
        TextView cardBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardHeader = itemView.findViewById(R.id.card_header);
            cardBody = itemView.findViewById(R.id.card_body);
        }
    }
}
