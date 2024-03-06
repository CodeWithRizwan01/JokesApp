package com.example.hindifunnyjokes.AdapterClasses;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hindifunnyjokes.ModelClasses.HomeModel;
import com.example.hindifunnyjokes.R;

import java.util.ArrayList;

public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.ViewHolder> {
    ArrayList<HomeModel> arrayList;
    private ArrayList<HomeModel> originalList;
    Context context;
    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyclerViewHomeAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerViewHomeAdapter(ArrayList<HomeModel> arrayList) {
        this.arrayList = arrayList;
        this.originalList = new ArrayList<>(arrayList);

    }

    @NonNull
    @Override
    public RecyclerViewHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home, parent, false);
        return new ViewHolder(rootView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHomeAdapter.ViewHolder holder, int position) {

        HomeModel homeModel = arrayList.get(position);

        holder.text.setText(homeModel.getTextView());
        holder.image.setImageResource(homeModel.getImageView());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.tv_text);
            image = itemView.findViewById(R.id.iv_image);
            cardView = itemView.findViewById(R.id.listItemCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public void filterList(String query) {
        arrayList.clear();

        if (query.isEmpty()) {
            arrayList.addAll(originalList); // If the query is empty, show the original list
        } else {
            query = query.toLowerCase();
            for (HomeModel model : originalList) {
                if (model.getTextView().toLowerCase().contains(query)) {
                    arrayList.add(model);
                }
            }
        }

        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
}
