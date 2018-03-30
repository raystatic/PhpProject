package com.example.rahul.phpproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView profile, users, notifications;
    ViewPager viewPager;
    PagerViewAdapter pagerViewAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);

        if (!sharedPreferences.contains("email") || !sharedPreferences.contains("password"))
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        profile=findViewById(R.id.profile);
        users=findViewById(R.id.allUsers);
        notifications=findViewById(R.id.notifications);

        viewPager=findViewById(R.id.mainPager);

        pagerViewAdapter=new PagerViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerViewAdapter);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                changeTabs(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeTabs(int position) {
        if (position==0)
        {
            profile.setTextColor(getResources().getColor(R.color.textTabBright));
            profile.setTextSize(22);

            users.setTextColor(getResources().getColor(R.color.textTabLight));
            users.setTextSize(16);

            notifications.setTextColor(getResources().getColor(R.color.textTabLight));
            notifications.setTextSize(16);

        }
        if (position==1)
        {
            profile.setTextColor(getResources().getColor(R.color.textTabLight));
            profile.setTextSize(16);

            users.setTextColor(getResources().getColor(R.color.textTabBright));
            users.setTextSize(22);

            notifications.setTextColor(getResources().getColor(R.color.textTabLight));
            notifications.setTextSize(16);
        }
        if (position==2)
        {
            profile.setTextColor(getResources().getColor(R.color.textTabLight));
            profile.setTextSize(16);

            users.setTextColor(getResources().getColor(R.color.textTabLight));
            users.setTextSize(16);

            notifications.setTextColor(getResources().getColor(R.color.textTabBright));
            notifications.setTextSize(22);
        }
    }
}
