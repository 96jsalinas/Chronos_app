package com.example.a96jsa.chronos_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.support.v4.app.NotificationCompat;
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
    private int selectedActivityTotalTime;
    private long selectedActivityElapsedTime;
    long timeStart;
    long timeStop;
    long timeElapsed;
    long savedTime;
    String recordingActivity;
    String  selectedActivity;
    boolean isRecording;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;
    int notificationID = 1;
    TextView selectedAc;
    Intent notificationIntent;
    PendingIntent pendingIntent;
     ImageButton pauseButton;
    ImageButton playButton;
     Chronometer simpleChronometer;
    SharedPreferences sharedPreferences;


    public static final String SHAREDPREFERENCES = "SharePreferences" ;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        mDrawerLayout = findViewById(R.id.drawer_layout);

        listView = findViewById(R.id.listView);
        simpleChronometer = findViewById(R.id.simpleChronometer);
        pauseButton = findViewById(R.id.pauseBut);
        playButton = findViewById(R.id.playBut);

        sharedPreferences = this.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            timeStart = sharedPreferences.getLong("timeStart",0);
            savedTime = sharedPreferences.getLong("savedTime",0);
            isRecording = sharedPreferences.getBoolean("isRecording",false);
        }

        //notification intent and pendingIntent created
        notificationIntent = new Intent(getApplicationContext(), MainScreen.class);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,notificationIntent,0);





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

        //default selected Activity
       selectedActivity = (String) listView.getItemAtPosition(0);
        selectedAc = findViewById(R.id.selectedAct);
        String activityName = listView.getItemAtPosition(0).toString();
        selectedActivityTotalTime = databaseHelper.getActivityTotalTimeFromActivityTable(activityName);
        if(selectedActivityTotalTime > 0){
            simpleChronometer.setBase(SystemClock.elapsedRealtime() - selectedActivityTotalTime * 1000);
        }else {
            simpleChronometer.setBase(0);
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedActivity    = (String) listView.getItemAtPosition(position);

                selectedAc.setText(selectedActivity);
                selectedActivityTotalTime = databaseHelper.getActivityTotalTimeFromActivityTable(selectedActivity);

                if(selectedActivityTotalTime > 0){
                    simpleChronometer.setBase(SystemClock.elapsedRealtime() - selectedActivityTotalTime * 1000);
                }else {
                    simpleChronometer.setBase(0);
                }

                playButton.setClickable(true);


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


        if(savedInstanceState != null){
            simpleChronometer.setBase(savedInstanceState.getLong("elapsedTime"));
        }
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                simpleChronometer.setBase(SystemClock.elapsedRealtime() - savedTime);
//                simpleChronometer.setBase(SystemClock.elapsedRealtime() - selectedActivityTotalTime);

//                timeStart = databaseHelper.getActivityStartTime(selectedActivity);
//                if(timeStart > 0){
//                    databaseHelper.setActivityStartTime(selectedActivity,Long.toString(timeStart));
//                }else{
//                    timeStart = System.currentTimeMillis();
//                }
                timeStart = System.currentTimeMillis();
                databaseHelper.setActivityStartTime(selectedActivity,Long.toString(timeStart));

                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                simpleChronometer.start();
                isRecording = true;
                recordingActivity = selectedActivity;

//



                playButton.setClickable(false);
                pauseButton.setClickable(true);
                Calendar rightNow = Calendar.getInstance();
                int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                int minute = rightNow.get(Calendar.MINUTE);
                int second = rightNow.get(Calendar.SECOND);
                String cTime = Integer.toString(hour)+":"+Integer.toString(minute)+":"+Integer.toString(second);
                startTime = cTime;



                mBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle(selectedActivity)
                        .setContentText("we are tracking time")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_add);

                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(notificationID,mBuilder.build());

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStart = databaseHelper.getActivityStartTime(selectedActivity);
                timeStop = System.currentTimeMillis();
                timeElapsed = timeStop - timeStart;
                selectedActivityElapsedTime = timeElapsed;

//                if(savedTime > 0) {
//                    savedTime = timeElapsed + savedTime;
//                }else {
//                    savedTime = timeElapsed;
//                }
                simpleChronometer.setBase(SystemClock.elapsedRealtime() - timeElapsed);
                simpleChronometer.stop();
                isRecording = false;

                mBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("tracking")
                        .setContentText("we stop tracking time")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_add);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(notificationID,mBuilder.build());

                //long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                //Stuff to enter into activity table, will be extracted into separate java class soon
              
//                String totalTime = Long.toString(savedTime/1000);
                String totalTime = Long.toString(timeElapsed);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c);
//                playButton.setVisibility(View.VISIBLE);
//                pauseButton.setVisibility(View.INVISIBLE);
                Calendar rightNow = Calendar.getInstance();
                int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                int minute = rightNow.get(Calendar.MINUTE);
                int second = rightNow.get(Calendar.SECOND);
                String cTime = Integer.toString(hour)+":"+Integer.toString(minute)+":"+Integer.toString(second);
                //databaseHelper.insertActivityData(selectedAc.getText().toString(),totalTime,startTime,cTime,formattedDate,formattedDate,databaseHelper.getCategoryColor(selectedCategory),selectedCategory);
                databaseHelper.updateActivityData(selectedActivity,totalTime,Long.toString(timeStop),cTime,formattedDate,databaseHelper.getCategoryColor(selectedCategory),selectedCategory);
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
        outState.putLong("timeStart",timeStart);
        outState.putLong("savedTime",savedTime);
        Toast.makeText(getApplicationContext(),"session saved",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timeStart = savedInstanceState.getLong("timeStart");
        savedTime = savedInstanceState.getLong("savedTime");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Context context = getApplicationContext();
         sharedPreferences =  context.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedActivity",selectedActivity);
        editor.putLong("selectedActivityElapsedTime",selectedActivityElapsedTime);
        editor.putString("recordingActivity",recordingActivity);
        editor.putLong("timeStart",timeStart);
        editor.putLong("savedTime",savedTime);
        editor.putBoolean("isRecording", isRecording);
        editor.commit();


//        Toast.makeText(getApplicationContext(),"on paused called",Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStop() {
        super.onStop();

        Toast.makeText(getApplicationContext(),"on stop called",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
       Toast.makeText(getApplicationContext(),"on resume called",Toast.LENGTH_SHORT).show();
        sharedPreferences = this.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            selectedActivity = sharedPreferences.getString("selectedActivity",selectedActivity);
            if(selectedActivity != null){
                selectedAc.setText(selectedActivity);
            }
            timeStart = sharedPreferences.getLong("timeStart",0);
            savedTime = sharedPreferences.getLong("savedTime",0);
            isRecording = sharedPreferences.getBoolean("isRecording",false);
        }
//        if(selectedActivityElapsedTime > 0){
//      simpleChronometer.setBase(SystemClock.elapsedRealtime() - savedTime);
//            simpleChronometer.setBase(SystemClock.elapsedRealtime() - selectedActivityElapsedTime);
//
//        }
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        if(isRecording){
            simpleChronometer.start();
        }else {
            simpleChronometer.stop();
        }




    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(),"on restart called",Toast.LENGTH_SHORT).show();
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
