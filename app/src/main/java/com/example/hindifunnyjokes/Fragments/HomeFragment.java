package com.example.hindifunnyjokes.Fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hindifunnyjokes.AdapterClasses.RecyclerViewHomeAdapter;
import com.example.hindifunnyjokes.JokesActivity;
import com.example.hindifunnyjokes.ModelClasses.HomeModel;
import com.example.hindifunnyjokes.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<HomeModel> jokeList = new ArrayList<>();
    EditText searchEditText;
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private AdView mAdView;
    private int retryAttempt;
    private int addCounter = 0;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchEditText = view.findViewById(R.id.searchEditText);

        mAdView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext());
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        jokeList.add(new HomeModel(R.drawable.baby,"Funny jokes"));
        jokeList.add(new HomeModel(R.drawable.couple,"Husband Wife"));
        jokeList.add(new HomeModel(R.drawable.love,"GF-BF"));
        jokeList.add(new HomeModel(R.drawable.teacher,"Teacher Student"));
        jokeList.add(new HomeModel(R.drawable.kids,"Friendship"));
        jokeList.add(new HomeModel(R.drawable.celebrity,"Celebrity Jokes"));
        jokeList.add(new HomeModel(R.drawable.kidding,"Desi Jokes"));
        jokeList.add(new HomeModel(R.drawable.naughty,"Naughty Jokes"));
        jokeList.add(new HomeModel(R.drawable.lawyer,"Lawyer Jokes"));
        jokeList.add(new HomeModel(R.drawable.doctor,"Doctor-patient"));
        jokeList.add(new HomeModel(R.drawable.drunk,"Sharabi Jokes"));
        jokeList.add(new HomeModel(R.drawable.homeless,"Beggar Jokes"));
        jokeList.add(new HomeModel(R.drawable.etiquette,"Political Jokes"));
        jokeList.add(new HomeModel(R.drawable.drunk,"Pathan Jokes"));
        jokeList.add(new HomeModel(R.drawable.cop,"Police Jokes"));
        jokeList.add(new HomeModel(R.drawable.kids,"Family Jokes"));

        RecyclerViewHomeAdapter  adapter = new RecyclerViewHomeAdapter(jokeList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewHomeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
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
                Intent intent = new Intent(getActivity(), JokesActivity.class);
                intent.putExtra("title", jokeList.get(position).getTextView());
                startActivity(intent);
            }
        });

        GridLayoutManager layoutManager =new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);


        // Set up a TextWatcher for the search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
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