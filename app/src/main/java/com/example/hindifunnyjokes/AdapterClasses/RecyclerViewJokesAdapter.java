package com.example.hindifunnyjokes.AdapterClasses;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hindifunnyjokes.DbHelper.DbHelper;
import com.example.hindifunnyjokes.ModelClasses.HomeModel;
import com.example.hindifunnyjokes.ModelClasses.JokesModel;
import com.example.hindifunnyjokes.R;

import java.util.ArrayList;

public class RecyclerViewJokesAdapter extends RecyclerView.Adapter<RecyclerViewJokesAdapter.ViewHolder> {

    DbHelper dbHelper;

    Context context;
    ArrayList<JokesModel> arrayList;

    public RecyclerViewJokesAdapter(Context context, ArrayList<JokesModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        dbHelper = new DbHelper(context);
    }

    onItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyclerViewJokesAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    onFavouriteButtonClickListener onFavouriteButtonClickListener;

    public void setOnFavouriteButtonClickListener(RecyclerViewJokesAdapter.onFavouriteButtonClickListener onFavouriteButtonClickListener) {
        this.onFavouriteButtonClickListener = onFavouriteButtonClickListener;
    }

    onCopyButtonClickListener onCopyButtonClickListener;

    public void setOnCopyButtonClickListener(RecyclerViewJokesAdapter.onCopyButtonClickListener onCopyButtonClickListener) {
        this.onCopyButtonClickListener = onCopyButtonClickListener;
    }

    onShareButtonClickListener onShareButtonClickListener;

    public void setOnShareButtonClickListener(RecyclerViewJokesAdapter.onShareButtonClickListener onShareButtonClickListener) {
        this.onShareButtonClickListener = onShareButtonClickListener;
    }

    onSaveButtonClickListener onSaveButtonClickListener;

    public void setOnSaveButtonClickListener(RecyclerViewJokesAdapter.onSaveButtonClickListener onSaveButtonClickListener) {
        this.onSaveButtonClickListener = onSaveButtonClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewJokesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_joke, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewJokesAdapter.ViewHolder holder, int position) {
        JokesModel jokesModel = arrayList.get(position);
        holder.tVText.setText(jokesModel.getTextView());
        holder.IvImage.setImageResource(jokesModel.getImageView());

        Drawable favFill = ContextCompat.getDrawable(context, R.drawable.baseline_favorite_fill_24);
        Drawable favBorder = ContextCompat.getDrawable(context, R.drawable.baseline_favorite_border_24);

        boolean isFavourite = dbHelper.isJokeExist(arrayList.get(position).getJokeTitle());
        if (isFavourite) {
            holder.btnFavourite.setCompoundDrawablesWithIntrinsicBounds(favFill, null, null, null);
        } else {
            holder.btnFavourite.setCompoundDrawablesWithIntrinsicBounds(favBorder, null, null, null);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView IvImage;
        TextView tVText;
        AppCompatButton btnFavourite;
        AppCompatButton btnCopyJoke;
        AppCompatButton btnShareJoke;
        AppCompatButton btnSaveJoke;
        RelativeLayout viewImageText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tVText = itemView.findViewById(R.id.listItemText);
            IvImage = itemView.findViewById(R.id.listItemImage);
            btnFavourite = itemView.findViewById(R.id.btnFavourite);
            btnCopyJoke = itemView.findViewById(R.id.btnCopyJoke);
            btnShareJoke = itemView.findViewById(R.id.btnShareJoke);
            btnSaveJoke = itemView.findViewById(R.id.btnSaveJoke);
            viewImageText = itemView.findViewById(R.id.viewImageText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        JokesModel clickedItem = arrayList.get(position);
                        clickedItem.toggleImage();
                        notifyItemChanged(position);
                    }
                }
            });

            btnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFavouriteButtonClickListener != null) {
                        onFavouriteButtonClickListener.onFavouriteButtonClick(btnFavourite, getAdapterPosition());
                    }
                }
            });

            btnCopyJoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCopyButtonClickListener != null) {
                        onCopyButtonClickListener.onCopyButtonClickListener(btnCopyJoke, getAdapterPosition());
                    }
                }
            });

            btnShareJoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onShareButtonClickListener != null) {
                        onShareButtonClickListener.onShareButtonClickListener(btnShareJoke, getAdapterPosition());
                    }
                }
            });

            btnSaveJoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSaveButtonClickListener != null) {
                        onSaveButtonClickListener.onSaveButtonClick(viewImageText, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface onFavouriteButtonClickListener {
        void onFavouriteButtonClick(View view, int position);
    }

    public interface onCopyButtonClickListener {
        void onCopyButtonClickListener(View view, int position);
    }

    public interface onShareButtonClickListener {
        void onShareButtonClickListener(View view, int position);
    }

    public interface onSaveButtonClickListener {
        void onSaveButtonClick(View view, int position);
    }

}
