package com.example.a96jsa.chronos_app;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends ArrayAdapter<Model> {

    private final Context context;
    private final ArrayList<Model> modelsArrayList;
    private boolean isCategoryList = true;
    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    String category;
    String categoryColor;
    String parentCategory;



    public MyAdapter(Context context, ArrayList<Model> modelsArrayList,boolean isCategoryList, String theparentCategory) {

        super(context, R.layout.target_item, modelsArrayList);

        this.isCategoryList = isCategoryList;
        this.context = context;
        this.modelsArrayList = modelsArrayList;
        databaseHelper = new DatabaseHelper(context);
        parentCategory = theparentCategory;

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

            // 3. Get title
            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
            // 4. Set the text for textView
            titleView.setText(modelsArrayList.get(position).getTitle());
          category = modelsArrayList.get(position).getTitle();
          if(isCategoryList) {
              categoryColor = databaseHelper.getCategoryColor(category);
          }else {
              category=parentCategory;
              categoryColor = "BLUE";
              if(parentCategory!=null){
                  categoryColor=databaseHelper.getCategoryColor(parentCategory);
              }
          }

            rowView.setBackgroundColor(CustomColors.getColor(categoryColor));

            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isCategoryList) {
                        String category = modelsArrayList.get(position).getTitle();
                        Intent intent = new Intent(parent.getContext(), ManageActivities.class);
                        intent.putExtra("categoryName", category);
                        parent.getContext().startActivity(intent);
                    }
                }
            });


            ImageButton editButton = rowView.findViewById(R.id.edit_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isCategoryList) {
                        Intent intent = new Intent(parent.getContext(), Customize.class);
                        String category = modelsArrayList.get(position).getTitle();
                        Model model = modelsArrayList.get(position);
                        intent.putExtra("categoryName", category);
                        intent.putExtra("preexisting", "true");
                        parent.getContext().startActivity(intent);
                    }else{
                        Intent intent = new Intent(parent.getContext(), Customize.class);
                        String activity = modelsArrayList.get(position).getTitle();
                        Model model = modelsArrayList.get(position);
                        intent.putExtra("categoryName", category);
                        intent.putExtra("preexisting", "true");
                        intent.putExtra("isActivity","true");
                        intent.putExtra("activityName",activity);
                        parent.getContext().startActivity(intent);
                    }
                }
            });

            ImageButton deleteButton = rowView.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCategoryList) {
                        String category = modelsArrayList.get(position).getTitle();
                        Model model = modelsArrayList.get(position);
                        databaseHelper.deleteCategory(category);
                        remove(model);
                        notifyDataSetChanged();
                        Toast.makeText(parent.getContext(), category + " has been removed", Toast.LENGTH_SHORT).show();
                    }else{
                        String activity = modelsArrayList.get(position).getTitle();
                        Model model = modelsArrayList.get(position);
                        databaseHelper.deleteData(category,activity);
                        remove(model);
                        notifyDataSetChanged();
                        Toast.makeText(parent.getContext(), activity + " has been removed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ImageButton statsButton = rowView.findViewById(R.id.stats_button);
            statsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isCategoryList) {
                        Intent intent = new Intent(parent.getContext(), IndividualStats.class);
                        String category = modelsArrayList.get(position).getTitle();
                        Model model = modelsArrayList.get(position);
                        intent.putExtra("categoryName", category);
                        parent.getContext().startActivity(intent);
                    }else {
                        Intent intent = new Intent(parent.getContext(),ActivityHistory.class);
                        String activity = modelsArrayList.get(position).getTitle();
                        intent.putExtra("activityName",activity);
                        parent.getContext().startActivity(intent);
                    }
                }
            });
        }
        else{
            rowView = inflater.inflate(R.layout.group_header_item, parent, false);
            TextView titleView = (TextView) rowView.findViewById(R.id.header);
            titleView.setText(modelsArrayList.get(position).getTitle());

        }

        // 5. return rowView
        return rowView;
    }

}
