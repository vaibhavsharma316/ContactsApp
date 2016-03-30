package mindbowser.assignment.assignment.model;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mindbowser.assignment.assignment.helper.Database;
import mindbowser.assignment.assignment.prefs.Local_prefs;

/**
 * Created by vaibhav on 3/29/2016.
 */
public class LocalDatabaseHandler extends AsyncTask<Void,Void,Void>{

    Context context;
    private List<Model> contactModels=new ArrayList<Model>();
    private  ProgressDialog progressDialog;


   public LocalDatabaseHandler(Context context)
    {
        this.context=context;

    }

    @Override
    protected void onPreExecute() {

        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Initializing..");
        progressDialog.show();
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... voids) {


        try {

            ContentResolver contentResolver=context.getContentResolver();
            Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

            if (cursor.getCount()>0)
            {
            cursor.moveToFirst();

                while (cursor.moveToNext())
                {






                    if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0)
                    {
                        Cursor phoneQuery=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))},null);

        while (phoneQuery.moveToNext())
        {


            if (phoneQuery.getString(phoneQuery.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).isEmpty()==false && cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)).isEmpty()==false

                  &&  cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).isEmpty()==false
                    &&phoneQuery.getString(phoneQuery.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).isEmpty()==false)
            {
                Model model_instance=new Model();


                model_instance.setId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));


                model_instance.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));



                model_instance.setNumber(phoneQuery.getString(phoneQuery.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));


                model_instance.setDelete_status("n");


                model_instance.setFavorite_status("n");




                if (phoneQuery.getString(phoneQuery.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))!=null)
                {
                    model_instance.setUri(phoneQuery.getString(phoneQuery.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));

                }
                else
                {
                    model_instance.setUri("");

                }




                contactModels.add(model_instance);
            }



        }
                        phoneQuery.close();
                    }





                }

                cursor.close();


               Database db=new Database(context);

               db.copyToLocal(contactModels);
               new Local_prefs(context).setCopyStatus(true);

            }





        } catch (Exception ex)
        {

            ex.printStackTrace();

        }







        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        progressDialog.dismiss();
        super.onPostExecute(aVoid);
    }
}
