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


        sqLiteDatabase.execSQL("INSERT or replace INTO Category (Type, Color, TotalTime) VALUES('Sport', 'Dark blue', '30')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Category (Type, Color, TotalTime) VALUES('Work', 'Dark green', '24')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Category (Type, Color, TotalTime) VALUES('Housework', 'Dark yellow', '24')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Category (Type, Color, TotalTime) VALUES('Leisure', 'Dark red', '24')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Sport (Type, Color, TotalTime) VALUES('Running', 'Dark blue','12')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Sport (Type, Color, TotalTime) VALUES('Walking', 'Dark green','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Sport (Type, Color, TotalTime) VALUES('Swimming', 'Dark yellow','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Sport (Type, Color, TotalTime) VALUES('Gym', 'Dark red','6')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Work (Type, Color, TotalTime) VALUES('Studying', 'Dark blue','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Work (Type, Color, TotalTime) VALUES('Writing', 'Dark green','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Work (Type, Color, TotalTime) VALUES('Exercices', 'Dark yellow','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Work (Type, Color, TotalTime) VALUES('Lecture recap', 'Dark red','6')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Housework (Type, Color, TotalTime) VALUES('Cleaning', 'Dark blue','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Housework (Type, Color, TotalTime) VALUES('Cooking', 'Dark green','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Housework (Type, Color, TotalTime) VALUES('Laundry', 'Dark yellow','6')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Leisure (Type, Color, TotalTime) VALUES('TV', 'Dark blue','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Leisure (Type, Color, TotalTime) VALUES('Reading', 'Dark green','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Leisure (Type, Color, TotalTime) VALUES('Gaming', 'Dark yellow','6')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Leisure (Type, Color, TotalTime) VALUES('Sleeping', 'Dark red','6')");

        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Running', 'Dark blue', '10:49:45', '10:49:57', '12', '2018-04-18', 'Sport')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Walking', 'Dark green', '10:49:51', '10:49:57', '6', '2018-04-18', 'Sport')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Swimming', 'Dark yellow', '10:49:57', '10:50:03', '6', '2018-04-18', 'Sport')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Gym', 'Dark red', '10:50:03', '10:50:09', '6', '2018-04-18', 'Sport')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Studying', 'Dark blue', '10:50:09', '10:50:15', '6', '2018-04-18', 'Work')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Writing', 'Dark green', '10:50:15', '10:50:21', '6', '2018-04-18', 'Work')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Exercises', 'Dark yellow', '10:50:21', '10:50:27', '6', '2018-04-18', 'Work')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Lecture recap', 'Dark red', '10:50:27', '10:50:33', '6', '2018-04-18', 'Work')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Cleaning', 'Dark blue', '10:50:33', '10:50:39', '6', '2018-04-18', 'Housework')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Cooking', 'Dark green', '10:50:39', '10:50:45', '6', '2018-04-18', 'Housework')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Laundry', 'Dark yellow', '10:50:45', '10:50:51', '6', '2018-04-18', 'Housework')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('TV', 'Dark blue', '10:50:51', '10:50:57', '6', '2018-04-18', 'Leisure')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Reading', 'Dark green', '10:50:57', '10:51:03', '6', '2018-04-18', 'Leisure')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Gaming', 'Dark yellow', '10:51:03', '10:51:09', '6', '2018-04-18', 'Leisure')");
        sqLiteDatabase.execSQL("INSERT or replace INTO Activity (activityName, Color, startTime, endTime, totalTime, date, categoryName) " +
                "Values('Sleeping', 'Dark red', '10:51:09', '10:51:15', '6', '2018-05-02', 'Leisure')");

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
    public HashMap<String,String>getActivities(String categoryName){
        String category = categoryName.replace(" ","_");
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




    public Integer getCategoryTotalTime(String categoryName){
        String category = categoryName.replace(" ","_");
        SQLiteDatabase db = this.getWritableDatabase();
//        String[] columns = {"categoryName,totalTime"};
//        String selection = "categoryName = ?";
//        String[] selectionArgs = {category};
//        Cursor cursor = db.query(ACTIVITY_TABLE,columns,selection,selectionArgs,null,null,null);

        Cursor cursor = db.rawQuery("SELECT TotalTime FROM " + CATEGORY_TABLE + " where Type = ? ",new String[]{category});
        // totalCategoryTime equals 10 just for testing since no data time has been recorded
        Integer totalCategoryTime = 0;

        while(cursor.moveToNext()){
            String cTime = cursor.getString(0);
            if(cTime==null){
                cTime="0";
            }
            Integer cursorTime = Integer.parseInt(cTime);
            totalCategoryTime = totalCategoryTime + cursorTime;

        }

        return totalCategoryTime;
    }

    // needs to be changed to category table and activity name
    public int getActivityTotalTime(String categoryName, String activity){
        String category = categoryName.replace(" ","_");
        SQLiteDatabase db = this.getWritableDatabase();
//        String[] columns = {"categoryName,totalTime"};
//        String selection = "categoryName = ?";
//        String[] selectionArgs = {category};
//        Cursor cursor = db.query(ACTIVITY_TABLE,columns,selection,selectionArgs,null,null,null);

        Cursor cursor = db.rawQuery("SELECT TotalTime FROM " +category + " WHERE Type = ?",new String[]{activity});
        // totalCategoryTime equals 10 just for testing since no data time has been recorded
        int totalActivityTime = 0;

        while(cursor.moveToNext()){

            Integer cursorTime = Integer.parseInt(cursor.getString(0));
            totalActivityTime += cursorTime;

        }

        return totalActivityTime;
    }



   public String getCategoryColor(String categoryName){
       String category = categoryName.replace(" ","_");
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

    public void insertActivityData(String activityName, String totalTime, String startTime, String endTime, String date, String color, String categoryName){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Activity_COL2, activityName);
        contentValues.put(Activity_COL3, totalTime);
        contentValues.put(Activity_COL4, startTime);
        contentValues.put(Activity_COL5, endTime);
        contentValues.put(Activity_COL6, date);
        contentValues.put(Activity_COL7, color);
        contentValues.put(Activity_COL8,categoryName.replace(" ","_"));

        //insert returns -1 if it failed, so it is possible to check this way if it did work
        long result = sqLiteDatabase.insert(ACTIVITY_TABLE, null, contentValues);

        addToTotalTime(activityName, totalTime, categoryName.replace(" ","_"));



    }
    public void addToTotalTime(String activityName, String time, String categoryName) {
        String category = categoryName.replace(" ","_");
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




        ContentValues contentValues = new ContentValues();
        contentValues.put("TotalTime", updatedTotalTime.toString());
        sqLiteDatabase.update(category, contentValues, "Type = ?", new String[]{activityName});
        addTimeToCategoryTable(category, time);

    }

    public Integer addTimeToCategoryTable(String categoryName, String timeToAdd){
        String category = categoryName.replace(" ","_");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String totalTime = new String();
        Integer additionalTime;
        Integer updatedTotalTime;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + CATEGORY_TABLE + " where Type = ?", new String[]{category});

        additionalTime = Integer.parseInt(timeToAdd);
        while (cursor.moveToNext()){
            totalTime = cursor.getString(3);
        }
        if(totalTime==null){
            totalTime="0";
        }
        updatedTotalTime = additionalTime+ Integer.parseInt(totalTime);




        ContentValues contentValues = new ContentValues();
        contentValues.put("TotalTime", updatedTotalTime.toString());
        sqLiteDatabase.update(CATEGORY_TABLE, contentValues, "Type = ?", new String[]{category});
        return updatedTotalTime;
    }

    //Insert category specific types, this methods needs also be called when a new category is created
    public boolean insertCategoryTypes (String tableName, String typeName, String color){
        tableName = tableName.replace(" ","_");
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
        categoryName = categoryName.replace(" ","_");
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
        tableName = tableName.replace(" ","_");
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
        categoryName = categoryName.replace(" ","_");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("create table "+ categoryName + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Type TEXT, Color TEXT, TotalTime TEXT)");
        sqLiteDatabase.close();
        return true;
    }
    //function for inserting the category name and color in the category table
    public void insertCategorytoCategoryTable(String categoryName, String color){
        categoryName = categoryName.replace(" ","_");
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
            possibleActivityResultList.add(res.getString(1).replace("_"," "));
        }
        return possibleActivityResultList;
    }
    //Show possible activities or categories
   public ArrayList<String> showPossibleActivities (String tableName){
        tableName=tableName.replace(" ","_");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Get results from query and save them in a cursor
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name!='android_metadata' AND name!='sqlite_sequence'", null);

        //Transform Cursor into ArrayList with type String
        ArrayList<String> possibleActivityResultList = new ArrayList<String>();
        while (res.moveToNext()){
            //Cursor starts counting at 0, since the name of the activity_activity_type is saved at the
            // second position of the table it has to be 1
            possibleActivityResultList.add(res.getString(1).replace("_"," "));
        }
        return possibleActivityResultList;
    }

    //Update types for specific category
    public void editCategory (String oldName, String newName, String newColor){
        oldName = oldName.replace(" ","_");
        newName = newName.replace(" ","_");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String id = new String();
        //Get id from activity_activity_type type so it can be updated
        Cursor res = sqLiteDatabase.rawQuery("select ID from " + CATEGORY_TABLE + " where Type = ?", new String[]{oldName});
        while (res.moveToNext()){
            id = res.getString(0);
        }

        //Update name
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("Type", newName);
        contentValues.put("Color", newColor);
        sqLiteDatabase.update(CATEGORY_TABLE, contentValues, "ID = ?", new String[]{id});

        //Change name of responding table
        if(!oldName.equals(newName)) {
            sqLiteDatabase.execSQL("ALTER TABLE " + oldName + " RENAME TO " + newName);
        }
    }

    //Update types for specific category

    public void updateTypeData (String tableName, String oldName, String newName, String newColor, String newCategory, boolean flag){
        tableName = tableName.replace(" ","_");
        oldName = oldName.replace(" ","_");
        newName = newName.replace(" ","_");
        newCategory = newCategory.replace(" ","_");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String oldID = new String();
        String newID = new String();
        //Get id from activity_activity_type type so it can be updated
        Cursor res = sqLiteDatabase.rawQuery("select ID from " + tableName + " where Type = ?", new String[]{oldName});
        while (res.moveToNext()){
             oldID = res.getString(0);
        }
        newID = oldID+oldID;
        //Update name
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", newID);
        contentValues.put("Type", newName);
        contentValues.put ("Color", newColor);
        sqLiteDatabase.update(tableName, contentValues, "ID = ?", new String[]{oldID});

        if (flag) {
            sqLiteDatabase.execSQL("INSERT INTO " + newCategory + " select * from " + tableName + " where ID = ?", new String[]{newID});
            deleteData(tableName, newName);
        }

    }

    //Delete types of activities / Category
    public boolean deleteData (String tableName, String name){
        tableName = tableName.replace(" ","_");
        name = name.replace(" ","_");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(tableName, "type = ?", new String[] {name});

        return true;
    }

    //Delete category table
    public boolean deleteCategory(String tableName){
        tableName.replace(" ","_");
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
        for (res.moveToLast(); !res.isBeforeFirst(); res.moveToPrevious()){
            //Cursor starts counting at 0, since the name of the activity_activity_type is saved at the
            // second position of the table it has to be 1
            historyResultList.add(res.getString(1));
            buffer.append("Start time: "+res.getString(3)+" "+" ");
            buffer.append("End time: "+res.getString(4)+" "+" ");
            buffer.append("Date: "+res.getString(6)+" "+" ");
            buffer.append("Category: "+res.getString(7).replace("_"," ")+" "+" ");
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

    public  void updateHistory(String name, String oldStartTime, String changedStartTime, String changedEndTime, String storedElapsedTime){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String id = new String();
        String category = new String();
        String oldTime = new String();

        //Get results from query and save them in a cursor
        Cursor res = sqLiteDatabase.rawQuery("select  * from " + ACTIVITY_TABLE + " where activityName = ? AND startTime = ?", new String[]{name, oldStartTime});
        while (res.moveToNext()){
            id = res.getString(0);
            category = res.getString(7);
            oldTime = res.getString(5);
            }
      //  Cursor res2 = sqLiteDatabase.rawQuery("select " +  Activity_COL8+" from " + ACTIVITY_TABLE + " where activityName = ? AND startTime = ?", new String[]{name, oldStartTime});
       // Cursor res3 = sqLiteDatabase.rawQuery("select " + Activity_COL3 +" from " + ACTIVITY_TABLE + " where activityName = ? AND startTime = ?", new String[]{name, oldStartTime});
        //while (res.moveToNext()){

         //   category = res2.getString(0);
        //     }
       // while (res.moveToNext()){

         //   oldTime = res3.getString(0); }

        //Update time in activity table
        ContentValues contentValues = new ContentValues();
        contentValues.put("startTime", changedStartTime);
        contentValues.put("endTime", changedEndTime);
        contentValues.put("totalTime", storedElapsedTime);
        sqLiteDatabase.update(ACTIVITY_TABLE, contentValues, "ID = ?", new String[]{id});

        //Update time in general category table and type of activity table
       changeTotalTimeAfterHistoryUpdate(category, name, oldTime, storedElapsedTime);


    }


    public void changeTotalTimeAfterHistoryUpdate(String category, String activityName, String oldTime, String newTime){
        category = category.replace(" ","_");
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String currentActivityTotalTime = new String();
            String currentCategoryTotalTime = new String();
            String timeToBeSubstracted = new String();
            Integer additionalTime;
            Integer updatedActivityTotalTime;
            Integer updatedCategoryTotalTime;
            Cursor activityCursor = sqLiteDatabase.rawQuery("select * from " + category + " where Type = ?", new String[]{activityName});

            Cursor categoryCursor = sqLiteDatabase.rawQuery("select * from "+ CATEGORY_TABLE + " where Type = ?", new String[]{category});

            additionalTime = Integer.parseInt(newTime);
            timeToBeSubstracted = oldTime;
            //Update time for category table
            while (activityCursor.moveToNext()){
                currentActivityTotalTime = activityCursor.getString(3);
            }
            updatedActivityTotalTime = Integer.parseInt(currentActivityTotalTime) - Integer.parseInt(timeToBeSubstracted);
            updatedActivityTotalTime = updatedActivityTotalTime + additionalTime;

            ContentValues contentValues = new ContentValues();
            contentValues.put("TotalTime", updatedActivityTotalTime.toString());
            sqLiteDatabase.update(category, contentValues, "Type = ?", new String[]{activityName});

            //Update total category time
            while (categoryCursor.moveToNext()){
                currentCategoryTotalTime = categoryCursor.getString(3);
            }
            updatedCategoryTotalTime = Integer.parseInt(currentCategoryTotalTime) - Integer.parseInt(timeToBeSubstracted);
            updatedCategoryTotalTime = updatedCategoryTotalTime + additionalTime;

            ContentValues categoryContentValues = new ContentValues();
            categoryContentValues.put("TotalTime", updatedCategoryTotalTime.toString());
            sqLiteDatabase.update(CATEGORY_TABLE, categoryContentValues, "Type = ?", new String[]{category});


    }


}

