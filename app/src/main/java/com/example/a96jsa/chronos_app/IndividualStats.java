package com.example.a96jsa.chronos_app;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndividualStats extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    TextView categoryName_tv;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_stats);
        categoryName = getIntent().getStringExtra("categoryName");
        categoryName_tv = (TextView)findViewById(R.id.categoryName);
        categoryName_tv.setText(categoryName);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        HashMap<String,String> activityList = databaseHelper.getActivities(categoryName);

        PieChart pieChart = (PieChart)findViewById(R.id.pie_chart_indiv);
        List<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(20,"Mex"));
//        entries.add(new PieEntry(10,"Finland"));
//        entries.add(new PieEntry(5,"Canada"));
//        entries.add(new PieEntry(21,"Russia"));
        if(activityList.size() > 0){
            Toast.makeText(getApplicationContext(),"Not empty category", Toast.LENGTH_SHORT).show();
            Set set = activityList.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()){
                Map.Entry mentry = (Map.Entry)iterator.next();
               // entries.add(new PieEntry(Integer.parseInt(mentry.getValue().toString()),mentry.getKey().toString()));
                entries.add(new PieEntry(databaseHelper.getActivityTotalTime(mentry.getKey().toString()),mentry.getKey().toString()));
            }

        }else {
            Toast.makeText(getApplicationContext(),"Empty category", Toast.LENGTH_SHORT).show();
        }

        Legend legend = pieChart.getLegend();
        legend.setTextSize(23);
        legend.setEnabled(false);
        pieChart.getDescription().setEnabled(false);


        PieDataSet set = new PieDataSet(entries,"");
        set.setColors(new int[]{CustomColors.getColor("Dark blue"),
                CustomColors.getColor("Dark red"),
                CustomColors.getColor("Dark green"),
                CustomColors.getColor("Dark orange"),
                CustomColors.getColor("Dark yellow"),
                CustomColors.getColor("Light yellow"),
                CustomColors.getColor("Light green"),
                CustomColors.getColor("Light orange"),
                CustomColors.getColor("Light blue"),
                CustomColors.getColor("Light red")});
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setHoleColor(CustomColors.getColor("Background color"));


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
