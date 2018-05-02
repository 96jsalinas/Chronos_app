package com.example.a96jsa.chronos_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
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
    long timeStart;
    long timeStop;
    long timeElapsed;
    long savedTime;
    boolean isRecording;

     Chronometer simpleChronometer;
    SharedPreferences sharedPreferences;

    TimerService timerService;
    public static final String SHAREDPREFERENCES = "SharePreferences" ;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        mDrawerLayout = findViewById(R.id.drawer_layout);

        listView = findViewById(R.id.listView);
//        if(savedInstanceState != null){
//            timerService = savedInstanceState.getParcelable("timeService");
//        }else {
//            timerService = new TimerService();
//        }
        //timerService = new TimerService("timeService");

//        if(savedInstanceState != null){
//            timeStart = savedInstanceState.getLong("startTime");
//            savedTime = savedInstanceState.getLong("savedTime");
//
//        }
        sharedPreferences = this.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            timeStart = sharedPreferences.getLong("timeStart",0);
            savedTime = sharedPreferences.getLong("savedTime",0);
            isRecording = sharedPreferences.getBoolean("isRecording",false);
        }





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
        simpleChronometer = findViewById(R.id.simpleChronometer);
        final ImageButton pauseButton = findViewById(R.id.pauseBut);
        pauseButton.setClickable(false);
        final ImageButton playButton = findViewById(R.id.playBut);
        if(savedInstanceState != null){
            simpleChronometer.setBase(savedInstanceState.getLong("elapsedTime"));
        }
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //simpleChronometer.setBase(SystemClock.elapsedRealtime());
//                if(timeStart == 0){
//                    timeStart = (int)System.currentTimeMillis();
//                }else{
//
//                }
                timeStart = System.currentTimeMillis();
                //check if chronometer has recorded time
//                if(timerService.isCounting()){
//                    simpleChronometer.setBase(SystemClock.elapsedRealtime() - timerService.getElapsedTimeSeconds() * 1000);
//                    simpleChronometer.start();
//                    timerService.resumeService();
//
//                    Toast.makeText(getApplicationContext(),"chronometer resumed",Toast.LENGTH_SHORT).show();
//                }else {
//                    simpleChronometer.setBase(SystemClock.elapsedRealtime());
//                    simpleChronometer.start();
//                    timerService.onCreate();
//                }
//                if(savedTime > 0) {
//
//                    simpleChronometer.setBase(SystemClock.elapsedRealtime() - savedTime);
//                }else {
//                    simpleChronometer.setBase(SystemClock.elapsedRealtime());
//                }
                //simpleChronometer.setBase(SystemClock.elapsedRealtime());
                simpleChronometer.setBase(SystemClock.elapsedRealtime() - savedTime);
                simpleChronometer.start();
                isRecording = true;


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
//                timerService.stopService();
//                simpleChronometer.stop();
//                //check if chronometer has been initialized
//                if(timerService != null){
//                    //simpleChronometer.setBase( timerService.getElapsedTimeSeconds());
//                    simpleChronometer.setBase(SystemClock.elapsedRealtime() - timerService.getElapsedTimeSeconds() * 1000);
//                    chronometer_tv.setText(Integer.toString(timerService.getElapsedTimeSeconds()));
//                }
                timeStop = System.currentTimeMillis();
                timeElapsed = timeStop - timeStart;

                if(savedTime > 0) {
                    savedTime = timeElapsed + savedTime;
                }else {
                    savedTime = timeElapsed;
                }
                simpleChronometer.setBase(SystemClock.elapsedRealtime() - savedTime);
                simpleChronometer.stop();
                isRecording = false;

                long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                //Stuff to enter into activity table, will be extracted into separate java class soon


                String totalTime = Long.toString(elapsedMillis);
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
                //databaseHelper.insertActivityData(selectedAc.getText().toString(),totalTime,startTime,cTime,formattedDate,databaseHelper.getCategoryColor(selectedCategory),selectedCategory);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("timeService",timerService);
        outState.putLong("timeStart",timeStart);
        outState.putLong("savedTime",savedTime);
        Toast.makeText(getApplicationContext(),"session saved",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        timerService = savedInstanceState.getParcelable("timeService");
//        simpleChronometer.setBase(SystemClock.elapsedRealtime() - timerService.getElapsedTimeSeconds() * 1000);
//        if(timerService.isPlaying()){
//            simpleChronometer.start();
//        }
        timeStart = savedInstanceState.getLong("timeStart");
        savedTime = savedInstanceState.getLong("savedTime");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Context context = getApplicationContext();
         sharedPreferences =  context.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("timeStart",timeStart);
        editor.putLong("savedTime",savedTime);
        editor.putBoolean("isRecording", isRecording);
        editor.commit();


        Toast.makeText(getApplicationContext(),"on paused called",Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStop() {
        super.onStop();

//        Toast.makeText(getApplicationContext(),"on stop called",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        simpleChronometer.setBase(213);
       Toast.makeText(getApplicationContext(),"on resume called",Toast.LENGTH_SHORT).show();
        sharedPreferences = this.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            timeStart = sharedPreferences.getLong("timeStart",0);
            savedTime = sharedPreferences.getLong("savedTime",0);
            isRecording = sharedPreferences.getBoolean("isRecording",false);
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
    protected void onRestart() {
        super.onRestart();
//        Toast.makeText(getApplicationContext(),"on restart called",Toast.LENGTH_SHORT).show();
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
