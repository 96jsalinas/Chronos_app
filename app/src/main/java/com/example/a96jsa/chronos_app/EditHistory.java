package com.example.a96jsa.chronos_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EditHistory extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Button button;
    private EditText editTextStart;
    private EditText editTextEnd;
    private EditText editDateStart;
    private EditText editDateEnd;
    private DatabaseHelper databaseHelper;
    private Spinner editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_history);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        TextView textView = findViewById(R.id.textActivity);
        button = findViewById(R.id.submitChange);
        editTextStart = findViewById(R.id.inputText);
        editTextEnd = findViewById(R.id.editText3);
        editDateStart = findViewById(R.id.editDateStart);
        editDateEnd = findViewById(R.id.editDateEnd);
        editTextName = findViewById(R.id.activitySpinner);
        databaseHelper = new DatabaseHelper(this);

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
        final ArrayAdapter<String> activitySpinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,theActivityList);
        editTextName.setAdapter(activitySpinnerAdapter);

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

        Intent intent = getIntent();

        HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("Values");
        //Contains activity name
        final String extra = hashMap.get("name");
        //Contains activity data
        String extra2 = hashMap.get("date");
        final String startTimeForQuerying = extra2.substring(12, 20);
        final String[] startTimeForStorage = {extra2.substring(12, 20)};
        final String[] endTimeForStorage = {extra2.substring(32, 40)};
        final String[] endTimeForComparison = {extra2.substring(32, 40)};
        final String[] nameForStorage = {extra};
        final String[] startDate = {extra2.substring(52, 60)};
        final String[] endDate = {extra2.substring(72, 80)};


        textView.setText(extra + " " + " " + extra2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String changedName = editTextName.getSelectedItem().toString();
                String changedStartTime = editTextStart.getText().toString();
                String changedEndTime = editTextEnd.getText().toString();
                String changedStartDate = editDateStart.getText().toString();
                String changedEndDate = editDateEnd.getText().toString();

                if (changedName != null && !changedName.isEmpty()){
                    nameForStorage[0] = changedName;
                }

                if (changedStartTime != null && !changedStartTime.isEmpty()){
                    startTimeForStorage[0] = changedStartTime;
                }

                if (changedEndTime != null && !changedEndTime.isEmpty()){
                    endTimeForStorage[0] = changedEndTime;
                }

                if (changedStartDate != null && !changedStartDate.isEmpty()){
                    startDate[0] = changedStartDate;
                }

                if (changedEndDate != null && !changedEndDate.isEmpty()){
                    endDate[0] = changedEndDate;
                }

                calculateNewElapsedTime(extra, startTimeForQuerying, startTimeForStorage[0], endTimeForStorage[0], nameForStorage[0], endTimeForComparison[0], startDate[0], endDate[0]);
                Intent intent = new Intent(getBaseContext(), History.class);
                startActivity(intent);
            }
        });
    }

    private void calculateNewElapsedTime(String extra, String substring, String changedStartTime, String changedEndTime, String changedName, String endTimeForComparison, String startDate, String endDate) {
        String dtStart = changedStartTime;
        String dtEnd =   changedEndTime;
        String dtName = changedName;
        String midnight1 = "24:00:00";
        String midnight2 = "00:00:00";
        String storedStartTime = new String();
        String storedEndTime = new String();
        String storedElapsedTime = new String();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Long seconds;

        try {
            Date midnightT = format.parse(midnight1);
            Date midnightT2 = format.parse(midnight2);

            Date dateStart = format.parse(dtStart);
            Date dateEnd = format.parse(dtEnd);

            if (!startDate.equals(endDate)){
                seconds = (midnightT.getTime() - dateStart.getTime() + (dateEnd.getTime()-midnightT2.getTime())) / 1000;
            } else {

                seconds = (dateEnd.getTime() - dateStart.getTime()) / 1000;
            }
            storedElapsedTime = seconds.toString();
            storedStartTime = format.format(dateStart);
            storedEndTime = format.format(dateEnd);
            databaseHelper.updateHistory(extra, substring, storedStartTime, storedEndTime, storedElapsedTime, dtName, endTimeForComparison);


        } catch (ParseException e) {
            e.printStackTrace();
        }


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