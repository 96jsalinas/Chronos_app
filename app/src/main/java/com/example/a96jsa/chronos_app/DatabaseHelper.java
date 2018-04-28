package com.example.a96jsa.chronos_app;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseHelper extends SQLiteOpenHelper {

    /*


    * The methods will return the Boolean value true in order to make it possible to make
    * Toast notifications to indicate for the user if the requested task was successful


     */

    private final static String DATABASE_NAME = "Chronos.db";

   //ActivityTypeActivity table
    private final static String ACTIVITY_TABLE = "Activity";
    private final static String Acitivity_COL1 = "ID";
    private final static String Activity_COL2 = "activityName";
    private final static String Activity_COL3 = "totalTime";
    private final static String Activity_COL4 = "startTime";
    private final static String Activity_COL5 = "endTime";
    private final static String Activity_COL6 = "date";
    private final static String Activity_COL7 = "color";

    private final static String Activity_COL8 ="categoryName";


    //Category table
    private final static String CATEGORY_TABLE = "Category";

    //Sport table
    private final static String SPORT_TABLE = "Sport";

    //Work table
    private final static String WORK_TABLE = "Work";

    //Housework table
    private final static String HOUSEWORK_TABLE = "Housework";

    //Leisure table
    private final static String LEISURE_TABLE = "Leisure";



    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+ ACTIVITY_TABLE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, activityName TEXT, Color TEXT, " +
                " startTime TEXT, endTime TEXT, totalTime TEXT, date TEXT, categoryName TEXT)");

        sqLiteDatabase.execSQL("create table "+ CATEGORY_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Type TEXT, Color TEXT, TotalTime TEXT)");

        sqLiteDatabase.execSQL("create table "+ SPORT_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Type TEXT, Color TEXT, TotalTime TEXT)");

        sqLiteDatabase.execSQL("create table "+ WORK_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Type TEXT, Color TEXT, TotalTime TEXT)");

        sqLiteDatabase.execSQL("create table "+ HOUSEWORK_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Type TEXT, Color TEXT, TotalTime TEXT)");

        sqLiteDatabase.execSQL("create table "+ LEISURE_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Type TEXT, Color TEXT, TotalTime TEXT)");

        sqLiteDatabase.execSQL("INSERT or replace INTO Category (Type, Color, TotlaTime) VALUES('Sport', 'RED', '0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Category (Type, Color, TotlaTime) VALUES('Work', 'BLUE', '0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Category (Type, Color, TotlaTime) VALUES('Housework', 'BLACK', '0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Category (Type, Color, TotlaTime) VALUES('Leisure', 'YELLOW', '0')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Sport (Type, Color, TotalTime) VALUES('Running', 'RED','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Sport (Type, Color, TotalTime) VALUES('Walking', 'BLUE','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Sport (Type, Color, TotalTime) VALUES('Swimming', 'BACK','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Sport (Type, Color, TotalTime) VALUES('Gym', 'YELLOW','0')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Work (Type, Color, TotalTime) VALUES('Studying', 'RED','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Work (Type, Color, TotalTime) VALUES('Writing', 'BLUE','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Work (Type, Color, TotalTime) VALUES('Exercices', 'BLACK','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Work (Type, Color, TotalTime) VALUES('Lecture recap', 'YELLOW','0')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Housework (Type, Color, TotalTime) VALUES('Cleaning', 'RED','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Housework (Type, Color, TotalTime) VALUES('Cooking', 'BLUE','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Housework (Type, Color, TotalTime) VALUES('Laundry', 'BLACK','0')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Leisure (Type, Color, TotalTime) VALUES('TV', 'RED','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Leisure (Type, Color, TotalTime) VALUES('Reading', 'BLUE','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Leisure (Type, Color, TotalTime) VALUES('Gaming', 'BLACK','0')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Leisure (Type, Color, TotalTime) VALUES('Sleeping', 'YELLOW','0')");

        insertActivityData(sqLiteDatabase,"Running","5571","10:49:45","10:49:51","18-Apr-2018","RED","Sport");
        insertActivityData(sqLiteDatabase,"Walking","5571","10:49:51","10:49:57","18-Apr-2018","BLUE","Sport");
        insertActivityData(sqLiteDatabase,"Swimming","5571","10:49:57","10:50:03","18-Apr-2018","BLACK","Sport");
        insertActivityData(sqLiteDatabase,"Gym","5571","10:50:03","10:50:09","18-Apr-2018","YELLOW","Sport");
        insertActivityData(sqLiteDatabase,"Studying","5571","10:50:09","10:50:15","18-Apr-2018","RED","Work");
        insertActivityData(sqLiteDatabase,"Writing","5571","10:50:15","10:50:21","18-Apr-2018","BLUE","Leisure");
        insertActivityData(sqLiteDatabase,"Exercises","5571","10:50:21","10:50:27","18-Apr-2018","BLACK","Sport");
        insertActivityData(sqLiteDatabase,"Lecture recap","5571","10:50:27","10:50:33","18-Apr-2018","YELLOW","Leisure");
        insertActivityData(sqLiteDatabase,"Cleaning","5571","10:50:33","10:50:39","18-Apr-2018","RED","Housework");
        insertActivityData(sqLiteDatabase,"Cooking","5571","10:50:39","10:50:45","18-Apr-2018","BLUE","Leisure");
        insertActivityData(sqLiteDatabase,"Laundry","5571","10:50:45","10:50:51","18-Apr-2018","BLACK","Housework");
        insertActivityData(sqLiteDatabase,"TV","5571","10:50:51","10:50:57","18-Apr-2018","RED","Leisure");
        insertActivityData(sqLiteDatabase,"Reading","5571","10:50:57","10:51:03","18-Apr-2018","BLUE","Leisure");
        insertActivityData(sqLiteDatabase,"Gaming","5571","10:51:03","10:51:09","18-Apr-2018","BLACK","Leisure");
        insertActivityData(sqLiteDatabase,"Sleeping","5571","10:51:09","10:51:15","18-Apr-2018","YELLOW","Leisure");

    }




    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACTIVITY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SPORT_TABLE);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + WORK_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HOUSEWORK_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LEISURE_TABLE);
        onCreate(sqLiteDatabase);
    }

//    public ArrayList<String> getActivities(String category){
//        return this.showPossibleActivities(category);
//    }
    public HashMap<String,String>getActivities(String category){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name!='android_metadata' AND name!='sqlite_sequence' AND name!='Category'AND name!='Activity' ", null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Type,TotalTime FROM " +category,null);
        HashMap<String,String> activityList = new HashMap<>();
        while (cursor.moveToNext()){
            //Cursor starts counting at 0, since the name of the activity_activity_type is saved at the
            // second position of the table it has to be 1
            activityList.put(cursor.getString(0),cursor.getString(1));
        }


        return activityList;
    }




    public int getCategoryTotalTime(String category){
        SQLiteDatabase db = this.getWritableDatabase();
//        String[] columns = {"categoryName,totalTime"};
//        String selection = "categoryName = ?";
//        String[] selectionArgs = {category};
//        Cursor cursor = db.query(ACTIVITY_TABLE,columns,selection,selectionArgs,null,null,null);

        Cursor cursor = db.rawQuery("SELECT Type,TotalTime FROM " +category,null);
        // totalCategoryTime equals 10 just for testing since no data time has been recorded
        int totalCategoryTime = 10;

        while(cursor.moveToNext()){

            int cursorTime = Integer.parseInt(cursor.getString(1));
            totalCategoryTime += cursorTime;

        }

        return totalCategoryTime;
    }
   public String getCategoryColor(String category){
       SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String [] projection = {"Type", "Color"};
        String selection = "Type = ?";
        String[] selectionArgs = {category};

       Cursor cursor = sqLiteDatabase.query(CATEGORY_TABLE,projection,selection,selectionArgs,null,null,null);
        String color = "";
        while(cursor.moveToNext()){
            color = cursor.getString(1);
        }
        return color;
   }


    //Insert activity_activity_type values

    public void insertActivityData(SQLiteDatabase sqLiteDatabase, String activityName, String totalTime, String startTime, String endTime, String date, String color, String categoryName){

       // SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Activity_COL2, activityName);
        contentValues.put(Activity_COL3, totalTime);
        contentValues.put(Activity_COL4, startTime);
        contentValues.put(Activity_COL5, endTime);
        contentValues.put(Activity_COL6, date);
        contentValues.put(Activity_COL7, color);
        contentValues.put(Activity_COL8,categoryName);

        //insert returns -1 if it failed, so it is possible to check this way if it did work
        long result = sqLiteDatabase.insert(ACTIVITY_TABLE, null, contentValues);
        addToTotalTime(activityName, totalTime, categoryName);



    }
    public void addToTotalTime(String activityName, String time, String category) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String currentTotalTime = new String();
        Integer additionalTime;
        Integer updatedTotalTime;
        Cursor cursor1 = sqLiteDatabase.rawQuery("select * from " + category + " where Type = ?", new String[]{activityName});

        additionalTime = Integer.parseInt(time);
        while (cursor1.moveToNext()){
            currentTotalTime = cursor1.getString(3);
        }
        updatedTotalTime = additionalTime+ Integer.parseInt(currentTotalTime);



        String storedTotalTime = updatedTotalTime.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TotalTime", updatedTotalTime.toString());
        sqLiteDatabase.update(category, contentValues, "Type = ?", new String[]{activityName});
        addTimeToActivityTable(category, time);

    }

    public Integer addTimeToActivityTable (String category, String timeToAdd){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String totalTime = new String();
        Integer additionalTime;
        Integer updatedTotalTime;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + CATEGORY_TABLE + " where Type = ?", new String[]{category});

        additionalTime = Integer.parseInt(timeToAdd);
        while (cursor.moveToNext()){
            totalTime = cursor.getString(3);
        }
        updatedTotalTime = additionalTime+ Integer.parseInt(totalTime);



        String storedTotalTime = updatedTotalTime.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TotalTime", updatedTotalTime.toString());
        sqLiteDatabase.update(CATEGORY_TABLE, contentValues, "Type = ?", new String[]{category});
        return updatedTotalTime;
    }




    //Insert category specific types, this methods needs also be called when a new category is created
    public boolean insertCategoryTypes (String tableName, String typeName, String color){
        SQLiteDatabase sqLiteOpenHelper = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Type", typeName);
        contentValues.put("Color", color);
        contentValues.put("TotalTime","0");
        long result = sqLiteOpenHelper.insert(tableName, null, contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    //Check if category exists already exists
    public boolean checkCategory (String categoryName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM sqlite_master", null);
        ArrayList<String> categories = new ArrayList<String>();
        boolean isCategoryPresent = false;
        while (res.moveToNext()){
            categories.add(res.getString(1));
        }
        if (categories.contains(categoryName)){
            isCategoryPresent = true;

        }
        else {
//            createCategoryTable(categoryName);
//            insertCategoryTypes(CATEGORY_TABLE, categoryName);
            isCategoryPresent = false;
        }
        return isCategoryPresent;

    }
    //Check if activity_activity_type for the specific category exists
    public boolean checkActivity (String tableName, String activityName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + tableName, null);
        ArrayList<String> activities = new ArrayList<String>();
        while (res.moveToNext()){
            activities.add(res.getString(1));
        }

        if (activities.contains(activityName)){
            return true;
           }
          else {
            return false;
        }

    }

    //Generate table for new category
    public boolean createCategoryTable(String categoryName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("create table "+ categoryName + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Type TEXT, Color TEXT, TotalTime TEXT)");
        sqLiteDatabase.close();
        return true;
    }
    //function for inserting the category name and color in the category table
    public void insertCategorytoCategoryTable(String categoryName, String color){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("Type",categoryName);
        value.put("Color",color);
        sqLiteDatabase.insert(CATEGORY_TABLE,null,value);
        sqLiteDatabase.close();
    }
    public void insertActivityToActivityTable(String activityName, String color){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("activityName",activityName);
        value.put("Color",color);
        sqLiteDatabase.insert(ACTIVITY_TABLE,null,value);
        sqLiteDatabase.close();
    }

//    public ArrayList<String> getCategories(){
//        return this.showPossibleActivities(CATEGORY_TABLE);
//    }

    public ArrayList<String> getCategories(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Get results from query and save them in a cursor
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+CATEGORY_TABLE, null);

        //Transform Cursor into ArrayList with type String
        ArrayList<String> possibleActivityResultList = new ArrayList<String>();
        while (res.moveToNext()){
            //Cursor starts counting at 0, since the name of the activity_activity_type is saved at the
            // second position of the table it has to be 1
            possibleActivityResultList.add(res.getString(1));
        }
        return possibleActivityResultList;
    }
    //Show possible activities or categories
   public ArrayList<String> showPossibleActivities (String tableName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Get results from query and save them in a cursor
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name!='android_metadata' AND name!='sqlite_sequence'", null);

        //Transform Cursor into ArrayList with type String
        ArrayList<String> possibleActivityResultList = new ArrayList<String>();
        while (res.moveToNext()){
            //Cursor starts counting at 0, since the name of the activity_activity_type is saved at the
            // second position of the table it has to be 1
            possibleActivityResultList.add(res.getString(1));
        }
        return possibleActivityResultList;
    }



    //Update types for specific category
    public boolean updateTypeData (String tableName, String oldName, String newName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String id = new String();
        //Get id from activity_activity_type type so it can be updated
        Cursor res = sqLiteDatabase.rawQuery("select ID from " + tableName + " where Type = ?", new String[]{oldName});
        while (res.moveToNext()){
             id = res.getString(0);
        }

        //Update name
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("Type", newName);
        sqLiteDatabase.update(tableName, contentValues, "ID = ?", new String[]{id});


        return true;
    }

    //Delete types of activities / Category
    public boolean deleteData (String tableName, String Name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(tableName, "type = ?", new String[] {Name});

        return true;
    }

    //Delete category table
    public boolean deleteCategory(String tableName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + tableName);
        sqLiteDatabase.delete(CATEGORY_TABLE, "Type = ?",new String[]{tableName});


        return true;
    }

    //Show history
    public List<Map<String, String>> showHistory (String range){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Get results from query and save them in a cursor
        Cursor res = sqLiteDatabase.rawQuery("select  * from " + ACTIVITY_TABLE + " where "+ Activity_COL6 +" >= ?", new String[]{range});

        //Transform Cursor into ArrayList with type String
        ArrayList<String> historyResultList = new ArrayList<String>();
        ArrayList<String> arrayList = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            //Cursor starts counting at 0, since the name of the activity_activity_type is saved at the
            // second position of the table it has to be 1
            historyResultList.add(res.getString(1));
            for (int i = 3; i<=6; i++){
                buffer.append(res.getString(i)+" ");
            }
            arrayList.add(String.valueOf(buffer));
            buffer.delete(0, buffer.length());
        }



        List<Map<String, String>> data = new ArrayList<>();

        for (int j =0; j< historyResultList.size(); j++){

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("name", historyResultList.get(j));
            resultMap.put("date", arrayList.get(j));
            data.add(resultMap);
        }


        return data;
    }

}

