package com.example.a96jsa.chronos_app;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class MainStats extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stats);

        mDrawerLayout = findViewById(R.id.drawer_layout);



        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<String> categories = new ArrayList<>();
        categories = db.getCategories();


        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart_main);
        ArrayList<Integer> arrayListColors = new ArrayList<>();
        List<PieEntry> entries = new ArrayList<>();


        for(String category : categories){

            Float totalTime = (float) db.getCategoryTotalTime(category);
            String categoryColor = db.getCategoryColor(category);
            arrayListColors.add(CustomColors.getColor(categoryColor));
            entries.add(new PieEntry(totalTime,category));
        }


        Legend legend = pieChart.getLegend();
        legend.setTextSize(23);
        legend.setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        PieDataSet set = new PieDataSet(entries,"");
        set.setValueTextSize(13);
        set.setValueTextColor(Color.parseColor("WHITE"));
        set.setColors(arrayListColors);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setHoleColor(CustomColors.getColor("Background color"));
        pieChart.setEntryLabelTextSize(18);
        pieChart.setHoleRadius(50);


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Intent intent = new Intent(getBaseContext(), ManageCategories.class);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        int id  = menuItem.getItemId();
                        switch (id){
                            case R.id.nav_main_screen:
                                Intent mainIntent = new Intent(getBaseContext(),MainScreen.class);
                                startActivity(mainIntent);
                                break;
                            case R.id.nav_manage_categories:
                                Intent manCatIntent = new Intent(getBaseContext(),ManageCategories.class);
                                startActivity(manCatIntent);
                                break;
                            case R.id.nav_history:
                                Intent historyIntent = new Intent(getBaseContext(),History.class);
                                startActivity(historyIntent);
                                break;
                            case R.id.nav_main_stats:
                                Intent statsIntent = new Intent(getBaseContext(),MainStats.class);
                                startActivity(statsIntent);
                                break;

                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
