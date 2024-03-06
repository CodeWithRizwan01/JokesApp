package com.example.hindifunnyjokes.Fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hindifunnyjokes.AdapterClasses.RecyclerViewJokesAdapter;
import com.example.hindifunnyjokes.DbHelper.DbHelper;
import com.example.hindifunnyjokes.JokesActivity;
import com.example.hindifunnyjokes.ModelClasses.Jokes;
import com.example.hindifunnyjokes.ModelClasses.JokesModel;
import com.example.hindifunnyjokes.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavouriteFragment extends Fragment {
    RecyclerView favouriteRecycler;
    ArrayList<JokesModel> jokesList = new ArrayList<>();

    DbHelper dbHelper;

    private AdView mAdView;

    // For Interstitial Ad-App-Lovin
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private int retryAttempt;
    private int addCounter = 0;
    TextView tvFavouriteText;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        favouriteRecycler = view.findViewById(R.id.favouriteRecycler);
        tvFavouriteText = view.findViewById(R.id.tvFavouriteListText);
        dbHelper = new DbHelper(getContext());


        mAdView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext());
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // get data from data base
        List<Jokes> favouriteJokes = dbHelper.getAllFavouriteJokes();
        for (int i=0; i<favouriteJokes.size(); i++){
            jokesList.add(new JokesModel(R.drawable.black, R.drawable.bg1, R.drawable.bg2, favouriteJokes.get(i).getTitle(), favouriteJokes.get(i).getJoke()));
        }

        RecyclerViewJokesAdapter adapterJokes = new RecyclerViewJokesAdapter(getContext(), jokesList);
        favouriteRecycler.setAdapter(adapterJokes);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        favouriteRecycler.setLayoutManager(layoutManager);

        if (jokesList.isEmpty()) {
            tvFavouriteText.setVisibility(View.VISIBLE);
        } else {
            favouriteRecycler.setVisibility(View.VISIBLE);
        }

        adapterJokes.setOnFavouriteButtonClickListener(new RecyclerViewJokesAdapter.onFavouriteButtonClickListener() {
            @Override
            @SuppressLint("NotifyDataSetChanged")
            public void onFavouriteButtonClick(View view, int position) {
                // show interstitial ad-ap-lovin
                loadInterstitialAd();
                if (addCounter == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(getActivity());
                    }
                    addCounter = 0;
                } else {
                    addCounter++;
                }
                dbHelper.deleteFavouriteJoke(jokesList.get(position).getJokeTitle());
                jokesList.remove(position);
                adapterJokes.notifyItemRemoved(position);
                adapterJokes.notifyDataSetChanged();

                if (jokesList.isEmpty()) {
                    tvFavouriteText.setVisibility(View.VISIBLE);
                } else {
                    favouriteRecycler.setVisibility(View.VISIBLE);
                }
            }
        });

        adapterJokes.setOnCopyButtonClickListener(new RecyclerViewJokesAdapter.onCopyButtonClickListener() {
            @Override
            public void onCopyButtonClickListener(View view, int position) {
                // show interstitial ad-ap-lovin
                loadInterstitialAd();
                if (addCounter == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(getActivity());
                    }
                    addCounter = 0;
                } else {
                    addCounter++;
                }
                String joke = jokesList.get(position).getTextView();

                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Joke", joke);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getContext(), "Joke copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        adapterJokes.setOnShareButtonClickListener(new RecyclerViewJokesAdapter.onShareButtonClickListener() {
            @Override
            public void onShareButtonClickListener(View view, int position) {
                // show interstitial ad-ap-lovin
                loadInterstitialAd();
                if (addCounter == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(getActivity());
                    }
                    addCounter = 0;
                } else {
                    addCounter++;
                }
                String joke = jokesList.get(position).getTextView();
                String link = "https://play.google.com/store/apps/details?id=" + getContext().getPackageName();

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sub = "Check this awesome Joke: ";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
                myIntent.putExtra(Intent.EXTRA_TEXT, joke + "\n\n" + link);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });

        adapterJokes.setOnSaveButtonClickListener(new RecyclerViewJokesAdapter.onSaveButtonClickListener() {
            @Override
            public void onSaveButtonClick(View view, int position) {
                // show interstitial ad-ap-lovin
                loadInterstitialAd();
                if (addCounter == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(getActivity());
                    }
                    addCounter = 0;
                } else {
                    addCounter++;
                }
                saveViewAsImage(view);
            }
        });

        return view;
    }

    private void saveViewAsImage(View view) {
        if (checkPermission()) {
            Bitmap bitmap = getBitmapFromView(view);
            saveBitmapToGallery(bitmap);
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, JokesActivity.REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    private Bitmap getBitmapFromView(View view) {
        // Create a Bitmap with the same size as the view
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a Canvas to draw the view onto the Bitmap
        Canvas canvas = new Canvas(bitmap);

        // Draw the view onto the Canvas
        view.draw(canvas);

        return bitmap;
    }

    private void saveBitmapToGallery(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveBitmapToGalleryApi29AndAbove(bitmap);
        } else {
            saveBitmapToGalleryApi28AndBelow(bitmap);
        }
    }

    private void saveBitmapToGalleryApi29AndAbove(Bitmap bitmap) {
        ContentResolver resolver = getContext().getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "joke_image");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try {
            OutputStream fos = resolver.openOutputStream(imageUri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Objects.requireNonNull(fos).close();
            Toast.makeText(getContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveBitmapToGalleryApi28AndBelow(Bitmap bitmap) {
        String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File image = new File(imagesDir, "joke_image.png");

        try {
            FileOutputStream fos = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            // Notify gallery about the new image
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(image);
            mediaScanIntent.setData(contentUri);
            getContext().sendBroadcast(mediaScanIntent);

            Toast.makeText(getContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }
    void loadInterstitialAd(){
        InterstitialAd.load(getContext(),"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
    }
}