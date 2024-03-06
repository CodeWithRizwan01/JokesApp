package com.example.hindifunnyjokes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class StartActivity extends AppCompatActivity {
//    private ViewPager viewPager;
    private LinearLayout indicatorLayout;
//    TextView tvSkip;
    AppCompatButton btnStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

//        viewPager = findViewById(R.id.viewPager);
//        indicatorLayout = findViewById(R.id.indicatorLayout);
        btnStarted = findViewById(R.id.btn_started);
//        tvSkip = findViewById(R.id.tv_skip);
        btnStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // Increment the current page index to move to the next page
//                int nextPage = viewPager.getCurrentItem() + 1;
//
//                // Check if it's within the valid range of pages
//                if (nextPage < viewPager.getAdapter().getCount()) {
//                    viewPager.setCurrentItem(nextPage);
//                } else {
                    // If it's the last page, start the MainActivity
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);

            }
        });
//        tvSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StartActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });


//        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        addIndicatorDots(0);

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                addIndicatorDots(position);
//                // Check if it's the third page, and update the button text accordingly
//                if (position == 2) {
//                    btnStarted.setText("Get Started");
//                } else {
//                    btnStarted.setText("Next");
//                }
//            }


//    private void addIndicatorDots(int currentPage) {
//        ImageView[] dots = new ImageView[3]; // Change this to the number of pages
//
//        indicatorLayout.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(getResources().getDrawable(
//                    (i == currentPage) ? R.drawable.dot : R.drawable.inactive_dot));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//            params.setMargins(8, 0, 8, 0);
//            indicatorLayout.addView(dots[i], params);
//        }
    }
//    private class MyPagerAdapter extends FragmentPagerAdapter {
//        MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//
//                return new FirstFragment();
//        }
//
//        @Override
//        public int getCount() {
//            return 0; // Change this to the number of pages
//        }
//    }
}