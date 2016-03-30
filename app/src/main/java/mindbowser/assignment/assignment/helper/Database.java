package mindbowser.assignment.assignment.helper;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mindbowser.assignment.assignment.model.Model;
import mindbowser.assignment.assignment.prefs.Local_prefs;

/**
 * Created by vaibhav on 3/29/2016.
 */
public class Database extends SQLiteOpenHelper {


    public final static String DB_NAME = "info";
    public final static String TABLE_NAME = "contacts";
    public final static String ID = "id";
    public final static String CONTACT_ID = "contact_id";
    public final static String CONTACT_NAME = "contact_name";
    public final static String CONTACT_IMAGE = "contact_image";
    public final static String CONTACT_NUMBER = "contact_number";
    public final static String DELETE_CONTACT_STATUS = "delete_status";
    public final static String FAVORITE_CONTACT_STATUS = "favorite_status";

    public Database(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACT_ID

                + " TEXT ," + CONTACT_NAME + " TEXT, " + CONTACT_NUMBER + " TEXT, " + CONTACT_IMAGE + " TEXT, " + DELETE_CONTACT_STATUS + " TEXT, " + FAVORITE_CONTACT_STATUS + " TEXT " + " ) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void copyToLocal(List<Model> contactModels) {

        SQLiteDatabase db = this.getWritableDatabase();


        Log.v("Database contactModels", "" + contactModels.size());

        for (int i = 0; i < contactModels.size(); i++) {
            ContentValues contentValues = new ContentValues();

            if (contactModels.get(i).getName().isEmpty() == false) {
                contentValues.put(Database.CONTACT_NAME, contactModels.get(i).getName());
            } else {
                contentValues.put(Database.CONTACT_NAME, "");

            }
            if (contactModels.get(i).getId().isEmpty() == false) {
                contentValues.put(Database.CONTACT_ID, contactModels.get(i).getId());
            } else {
                contentValues.put(Database.CONTACT_ID, "");
            }

            if (contactModels.get(i).getNumber().isEmpty() == false) {
                contentValues.put(Database.CONTACT_NUMBER, contactModels.get(i).getNumber());
            } else {
                contentValues.put(Database.CONTACT_NUMBER, "");
            }

            if (contactModels.get(i).getUri().isEmpty() == false) {
                contentValues.put(Database.CONTACT_IMAGE, contactModels.get(i).getUri());
            } else {
                contentValues.put(Database.CONTACT_IMAGE, "");
            }

            if (contactModels.get(i).getDelete_status().isEmpty() == false) {
                contentValues.put(Database.DELETE_CONTACT_STATUS, contactModels.get(i).getDelete_status());
            } else {
                contentValues.put(Database.DELETE_CONTACT_STATUS, "");
            }

            if (contactModels.get(i).getFavorite_status().isEmpty() == false) {
                contentValues.put(Database.FAVORITE_CONTACT_STATUS, contactModels.get(i).getFavorite_status());
            } else {
                contentValues.put(Database.FAVORITE_CONTACT_STATUS, "");
            }

            db.insert(TABLE_NAME, null, contentValues);

        }
        db.close();

    }








    public List<Model> getAllContacts() {
        List<Model> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE "+DELETE_CONTACT_STATUS+" = ?" + "ORDER BY contact_name ASC", new String[]{"n"});



        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Model model = new Model();
                model.setId(cursor.getString(cursor.getColumnIndex(CONTACT_ID)));
                model.setUri(cursor.getString(cursor.getColumnIndex(CONTACT_IMAGE)));

                model.setName(cursor.getString(cursor.getColumnIndex(CONTACT_NAME)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(CONTACT_NUMBER)));
                model.setDelete_status(cursor.getString(cursor.getColumnIndex(DELETE_CONTACT_STATUS)));
                model.setFavorite_status(cursor.getString(cursor.getColumnIndex(FAVORITE_CONTACT_STATUS)));
                list.add(model);

            }

        }

        db.close();

        return list;


    }






    public int addToFavorite (String ID)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(FAVORITE_CONTACT_STATUS,"y");

        int rows=db.update(TABLE_NAME,contentValues,CONTACT_ID+" =? ",new String[]{ID});




        db.close();


        return rows;


    }


    public int removeFromFavorite (String ID)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(FAVORITE_CONTACT_STATUS,"n");
     int rows=db.update(TABLE_NAME,contentValues,CONTACT_ID+" =? ",new String[]{ID});


        db.close();

        return rows;
    }



    public int deleteContact(String ID)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DELETE_CONTACT_STATUS,"y");

        int rows=db.update(TABLE_NAME,contentValues,CONTACT_ID+" =? ",new String[]{ID});
        db.close();
        return rows;


    }




    public List<Model> getDeletedContacts()
    {

        SQLiteDatabase db=this.getReadableDatabase();
        List<Model> list = new ArrayList<>();


       Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE "+DELETE_CONTACT_STATUS+" =? " +" ORDER BY contact_name ASC", new String[]{"y"});


        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Model model = new Model();
                model.setId(cursor.getString(cursor.getColumnIndex(CONTACT_ID)));
                model.setUri(cursor.getString(cursor.getColumnIndex(CONTACT_IMAGE)));
                model.setName(cursor.getString(cursor.getColumnIndex(CONTACT_NAME)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(CONTACT_NUMBER)));
                model.setDelete_status(cursor.getString(cursor.getColumnIndex(DELETE_CONTACT_STATUS)));
                model.setFavorite_status(cursor.getString(cursor.getColumnIndex(FAVORITE_CONTACT_STATUS)));
                list.add(model);

            }

        }
        db.close();
        return list;





    }



    public  List<Model> getFavorites()
    {

        SQLiteDatabase db=this.getReadableDatabase();
        List<Model> list = new ArrayList<>();
        Log.v("getFavorites","method called");

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE "+FAVORITE_CONTACT_STATUS+" = ? " +" ORDER BY contact_name ASC", new String[]{"y"});
        Log.v("getFavorites count", "" + cursor.getCount());

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Model model = new Model();
                model.setId(cursor.getString(cursor.getColumnIndex(CONTACT_ID)));
                model.setUri(cursor.getString(cursor.getColumnIndex(CONTACT_IMAGE)));
                model.setName(cursor.getString(cursor.getColumnIndex(CONTACT_NAME)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(CONTACT_NUMBER)));
                model.setDelete_status(cursor.getString(cursor.getColumnIndex(DELETE_CONTACT_STATUS)));
                model.setFavorite_status(cursor.getString(cursor.getColumnIndex(FAVORITE_CONTACT_STATUS)));
                list.add(model);

            }

        }

        return list;



    }


    public int restoreContact(String ID)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DELETE_CONTACT_STATUS,"n");

        int rows=db.update(TABLE_NAME,contentValues,CONTACT_ID+" =? ",new String[]{ID});

        db.close();


        Log.v("Contact","Restored ");

        return rows;

    }


}
