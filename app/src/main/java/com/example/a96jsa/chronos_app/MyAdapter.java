package com.example.a96jsa.chronos_app;

import java.util.ArrayList;

import com.example.a96jsa.chronos_app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends ArrayAdapter<Model> {

    private final Context context;
    private final ArrayList<Model> modelsArrayList;
    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    String category;
    String categoryColor;



    public MyAdapter(Context context, ArrayList<Model> modelsArrayList) {

        super(context, R.layout.target_item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
        databaseHelper = new DatabaseHelper(context);


    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Intent intent = new Intent();
        intent.setClass(parent.getContext(),ManageCategories.class);



        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater

        View rowView = null;
        if(!modelsArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.target_item, parent, false);
           // rowView.setBackgroundColor(Color.RED);

            // 3. Get title
            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
            // 4. Set the text for textView
            titleView.setText(modelsArrayList.get(position).getTitle());
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Toast.makeText(parent.getContext(), "Should show activities of this category", Toast.LENGTH_SHORT).show();
                }
            });
          category = modelsArrayList.get(position).getTitle();
          categoryColor = databaseHelper.getCategoryColor(category);
//          if(categoryColor.contains("BLUE")){
//              rowView.setBackgroundColor(Color.BLUE);
//          }else{
//              rowView.setBackgroundColor(Color.RED);
//          }
            switch (categoryColor){
                case "BLUE":
                    rowView.setBackgroundColor(Color.BLUE);
                    break;
                case "BLACK":
                    rowView.setBackgroundColor(Color.BLACK);
                    break;
                case "YELLOW":
                    rowView.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    rowView.setBackgroundColor(Color.RED);
                    break;
            }

            ImageButton editButton = rowView.findViewById(R.id.edit_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(),Customize.class);
                    String category = modelsArrayList.get(position).getTitle();
                    Model model = modelsArrayList.get(position);
                    intent.putExtra("categoryName",category);
                    intent.putExtra("preexisting","true");
                    parent.getContext().startActivity(intent);
                }
            });

            ImageButton deleteButton = rowView.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(parent.getContext(), "Should delete category", Toast.LENGTH_SHORT).show();
                    String category = modelsArrayList.get(position).getTitle();
                    Model model = modelsArrayList.get(position);
                    databaseHelper.deleteCategory(category);
                    remove(model);
                    notifyDataSetChanged();
                    Toast.makeText(parent.getContext(), category + " has been removed", Toast.LENGTH_SHORT).show();



                }
            });

            ImageButton statsButton = rowView.findViewById(R.id.stats_button);
            statsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(),IndividualStats.class);
                    String category = modelsArrayList.get(position).getTitle();
                    Model model = modelsArrayList.get(position);
                    intent.putExtra("categoryName",category);
                    parent.getContext().startActivity(intent);
                }
            });
        }
        else{
            rowView = inflater.inflate(R.layout.group_header_item, parent, false);
            TextView titleView = (TextView) rowView.findViewById(R.id.header);
            titleView.setText(modelsArrayList.get(position).getTitle());

        }

        // 5. retrn rowView
        return rowView;
    }

}
