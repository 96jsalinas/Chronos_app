package com.example.a96jsa.chronos_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private String startTime;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        mDrawerLayout = findViewById(R.id.drawer_layout);

        listView = findViewById(R.id.listView);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this);

        final ArrayList<String> categoryList = databaseHelper.getCategories();
        ArrayList<String> theActivityList = new ArrayList<>();
        for(int i=0;i<categoryList.size();++i){
            final HashMap<String, String> activityList = databaseHelper.getActivities(categoryList.get(i));
            Set set = activityList.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()){
                Map.Entry mentry = (Map.Entry)iterator.next();
                theActivityList.add(mentry.getKey().toString());
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, theActivityList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String  selectedActivity    = (String) listView.getItemAtPosition(position);
                TextView selectedAc = findViewById(R.id.selectedAct);
                selectedAc.setText(selectedActivity);

                final ArrayList<String> categoryList = databaseHelper.getCategories();
                for(int i=0;i<categoryList.size();++i){
                    final HashMap<String, String> activityList = databaseHelper.getActivities(categoryList.get(i));
                    Set set = activityList.entrySet();
                    Iterator iterator = set.iterator();
                    while (iterator.hasNext()){
                        Map.Entry mentry = (Map.Entry)iterator.next();
                        String ac = mentry.getKey().toString();
                        if(ac.equals(selectedActivity)){
                            selectedCategory = categoryList.get(i);
                        }
                    }
                }

          }
        });

        final TextView selectedAc = findViewById(R.id.selectedAct);
        selectedAc.setText(theActivityList.get(0));
        for(int i=0;i<categoryList.size();++i){
            final HashMap<String, String> activityList = databaseHelper.getActivities(categoryList.get(i));
            Set set = activityList.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()){
                Map.Entry mentry = (Map.Entry)iterator.next();
                String ac = mentry.getKey().toString();
                if(ac.equals(selectedAc.getText())){
                    selectedCategory = categoryList.get(i);
                }
            }
        }
        final Chronometer simpleChronometer = findViewById(R.id.simpleChronometer);
        final ImageButton pauseButton = findViewById(R.id.pauseBut);
        pauseButton.setClickable(false);
        final ImageButton playButton = findViewById(R.id.playBut);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                simpleChronometer.start();
                playButton.setClickable(false);
                pauseButton.setClickable(true);
                Calendar rightNow = Calendar.getInstance();
                int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                int minute = rightNow.get(Calendar.MINUTE);
                int second = rightNow.get(Calendar.SECOND);
                String cTime = Integer.toString(hour)+":"+Integer.toString(minute)+":"+Integer.toString(second);
                startTime = cTime;
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleChronometer.stop();
                long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                //Stuff to enter into activity table, will be extracted into separate java class soon
                String totalTime = Long.toString(elapsedMillis/1000);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                playButton.setClickable(true);
                pauseButton.setClickable(false);
                Calendar rightNow = Calendar.getInstance();
                int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                int minute = rightNow.get(Calendar.MINUTE);
                int second = rightNow.get(Calendar.SECOND);
                String cTime = Integer.toString(hour)+":"+Integer.toString(minute)+":"+Integer.toString(second);
                databaseHelper.insertActivityData(selectedAc.getText().toString(),totalTime,startTime,cTime,formattedDate,databaseHelper.getCategoryColor(selectedCategory),selectedCategory);
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

        FloatingActionButton launchCustomize = (FloatingActionButton) findViewById(R.id.fab);
        launchCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dynIntent = new Intent(getBaseContext(),Customize.class);
                startActivity(dynIntent);
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

    public void changeSelectedActivity(String activity){
        TextView selectedActivity = (TextView) findViewById(R.id.selectedAct);
        selectedActivity.setText(activity);
    }
}
