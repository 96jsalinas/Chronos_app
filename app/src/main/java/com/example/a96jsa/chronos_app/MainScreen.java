package com.example.a96jsa.chronos_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
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

import java.text.ParseException;
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
    private String selectedCategory=null;
    private boolean isRecording = false;
    private String theSelectedAc;
    private String theSelectedCat;
    String timeStop;
    long timeElapsed;
    long savedTime;
    Chronometer simpleChronometer;
    SharedPreferences sharedPreferences;
    public static final String SHAREDPREFERENCES = "SharePreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        mDrawerLayout = findViewById(R.id.drawer_layout);

        listView = findViewById(R.id.listView);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this);

        sharedPreferences = this.getSharedPreferences(SHAREDPREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences!= null){
            isRecording = sharedPreferences.getBoolean("isRecording",false);
            startTime = sharedPreferences.getString("timeStart",null);
            savedTime = sharedPreferences.getLong("savedTime",0);
            theSelectedAc = sharedPreferences.getString("selectedActivity",null);
            theSelectedCat = sharedPreferences.getString("selectedCategory",null);
        }

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
                selectedAc.setTextColor(CustomColors.getColor(databaseHelper.getCategoryColor(selectedCategory)));
                theSelectedAc = selectedActivity;
                theSelectedCat = selectedCategory;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedActivity",theSelectedAc);
                editor.putString("selectedCategory",theSelectedCat);
                editor.apply();
          }
        });


        final TextView selectedAc = findViewById(R.id.selectedAct);
        if(!theActivityList.isEmpty()) {
            selectedAc.setText(theActivityList.get(0));
            for (int i = 0; i < categoryList.size(); ++i) {
                final HashMap<String, String> activityList = databaseHelper.getActivities(categoryList.get(i));
                Set set = activityList.entrySet();
                Iterator iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry) iterator.next();
                    String ac = mentry.getKey().toString();
                    if (ac.equals(selectedAc.getText())) {
                        selectedCategory = categoryList.get(i);
                    }
                }
            }
            selectedAc.setTextColor(CustomColors.getColor(databaseHelper.getCategoryColor(selectedCategory)));
        }
        if(theSelectedAc!=null&&theSelectedCat!=null){
            selectedAc.setText(theSelectedAc);
            selectedAc.setTextColor(CustomColors.getColor(databaseHelper.getCategoryColor(theSelectedCat)));
        }
        simpleChronometer = findViewById(R.id.simpleChronometer);
        final ImageButton pauseButton = findViewById(R.id.pauseBut);
        final ImageButton playButton = findViewById(R.id.playBut);
        if(isRecording){
            playButton.setVisibility(View.INVISIBLE);
        }else {
            pauseButton.setVisibility(View.INVISIBLE);
        }
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedCategory!=null) {
                    simpleChronometer.setBase(SystemClock.elapsedRealtime()-savedTime);
                    simpleChronometer.start();
                    isRecording = true;
                    playButton.setVisibility(View.INVISIBLE);
                    pauseButton.setVisibility(View.VISIBLE);
                    Calendar rightNow = Calendar.getInstance();
                    int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                    int minute = rightNow.get(Calendar.MINUTE);
                    int second = rightNow.get(Calendar.SECOND);
                    String cTime = Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second);
                    startTime = cTime;
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rightNow = Calendar.getInstance();
                int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                int minute = rightNow.get(Calendar.MINUTE);
                int second = rightNow.get(Calendar.SECOND);
                String cTime = Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second);
                timeStop = cTime;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date sDate = format.parse(startTime);
                    Date eDate = format.parse(timeStop);
                    timeElapsed = (eDate.getTime() - sDate.getTime()) / 1000;
                }catch (ParseException e){
                    e.printStackTrace();
                }
                if(savedTime > 0){
                    savedTime = timeElapsed + savedTime;
                }else{
                    savedTime = timeElapsed;
                }
                simpleChronometer.setBase(SystemClock.elapsedRealtime() - timeElapsed*1000);
                simpleChronometer.stop();
                isRecording = false;

                String totalTime = Long.toString(timeElapsed);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c);
                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.INVISIBLE);
                rightNow = Calendar.getInstance();
                hour = rightNow.get(Calendar.HOUR_OF_DAY);
                minute = rightNow.get(Calendar.MINUTE);
                second = rightNow.get(Calendar.SECOND);
                cTime = Integer.toString(hour)+":"+Integer.toString(minute)+":"+Integer.toString(second);
                databaseHelper.insertActivityData(selectedAc.getText().toString(),totalTime,startTime,cTime,formattedDate,formattedDate,databaseHelper.getCategoryColor(theSelectedCat),theSelectedCat);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("timeStart",startTime);
        outState.putLong("savedTime",savedTime);
        outState.putString("selectedCategory",theSelectedCat);
        outState.putString("selectedActivity",theSelectedAc);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startTime = savedInstanceState.getString("timeStart");
        savedTime = savedInstanceState.getLong("savedTime");
        theSelectedCat = savedInstanceState.getString("selectedCategory");
        theSelectedAc = savedInstanceState.getString("selectedActivity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Context context = getApplicationContext();
        sharedPreferences =  context.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("timeStart",startTime);
        editor.putLong("savedTime",savedTime);
        editor.putBoolean("isRecording", isRecording);
        editor.putString("selectedActivity",theSelectedAc);
        editor.putString("selectedCategory",theSelectedCat);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = this.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            startTime = sharedPreferences.getString("timeStart",null);
            savedTime = sharedPreferences.getLong("savedTime",0);
            isRecording = sharedPreferences.getBoolean("isRecording",false);
            theSelectedAc = sharedPreferences.getString("selectedActivity",null);
            theSelectedCat = sharedPreferences.getString("selectedCategory",null);
        }
        if(savedTime > 0){
            simpleChronometer.setBase(SystemClock.elapsedRealtime() - savedTime);

        }
        if(isRecording){
            simpleChronometer.start();
        }else {
            simpleChronometer.stop();
        }

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
