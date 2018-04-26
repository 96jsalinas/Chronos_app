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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Customize extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String selectedCategory;
    String selectedColor = "RED";
    EditText editText;
    Button submitButton;
    Boolean categoryChecked = true;
    Boolean activityChecked = false;
    private DrawerLayout mDrawerLayout;
    DatabaseHelper databaseHelper;

    ArrayAdapter<String> categorySpinnerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        submitButton = (Button) findViewById(R.id.submitButton);
        editText = (EditText)findViewById(R.id.editText);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton categoryButton = findViewById(R.id.catRadioButton);
        RadioButton activityButton = findViewById(R.id.catRadioButton);
        radioGroup.check(categoryButton.getId());

        databaseHelper = new DatabaseHelper(getApplicationContext());

        ArrayList<String> categoryList = databaseHelper.getCategories();

        final Spinner categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
        categorySpinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categoryList);
        categorySpinner.setAdapter(categorySpinnerArrayAdapter);
        categorySpinner.setOnItemSelectedListener(this);
        categorySpinner.setVisibility(View.INVISIBLE);

        //spinner

        ArrayList<String> colorListString = new ArrayList<>();
        colorListString.add("RED");
        colorListString.add("BLUE");
        colorListString.add("GREEN");
        colorListString.add("YELLOW");
        colorListString.add("MAGENTA");
        final Spinner colorSpinner = (Spinner)findViewById(R.id.colorSpinner);
        final ArrayAdapter<String> colorSpinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,colorListString);
        colorSpinner.setAdapter(colorSpinnerArrayAdapter);
        colorSpinner.setOnItemSelectedListener(this);
        colorSpinner.setVisibility(View.VISIBLE);



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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.catRadioButton:
                        categoryChecked = true;
                        activityChecked = false;
                        Toast.makeText(getApplicationContext(),"category checked",Toast.LENGTH_SHORT).show();
                        categorySpinner.setVisibility(View.INVISIBLE);
                        colorSpinner.setVisibility(View.VISIBLE);
                        break;
                    case R.id.actRadioButton:
                        activityChecked = true;
                        categoryChecked = false;
                        Toast.makeText(getApplicationContext(),"activity checked",Toast.LENGTH_SHORT).show();
                        categorySpinner.setVisibility(View.VISIBLE);
                        colorSpinner.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ediTextValue = editText.getText().toString();
                Toast.makeText(getApplicationContext(),"data inserted",Toast.LENGTH_SHORT).show();
                if(categoryChecked){
                    databaseHelper.insertCategorytoCategoryTable(ediTextValue,selectedColor);
                    databaseHelper.createCategoryTable(ediTextValue);
                    categorySpinnerArrayAdapter.add(ediTextValue);
                    categorySpinnerArrayAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"spinner updated",Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseHelper.insertCategoryTypes(selectedCategory,ediTextValue,selectedColor);
                    databaseHelper.insertActivityToActivityTable(ediTextValue,selectedColor);

                }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewId = parent.getId();

        switch (viewId){
            case R.id.categorySpinner:
                selectedCategory = parent.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),selectedCategory,Toast.LENGTH_SHORT).show();

                break;
            case R.id.colorSpinner:
                selectedColor = parent.getSelectedItem().toString();;
                Toast.makeText(getApplicationContext(),selectedColor,Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}
