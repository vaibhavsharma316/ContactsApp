package mindbowser.assignment.assignment.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import mindbowser.assignment.assignment.helper.ContactsHandler;
import mindbowser.assignment.assignment.helper.DeletedContactsHandler;
import mindbowser.assignment.assignment.helper.FavoritesContactsHandler;

/**
 * Created by vaibhav on 3/29/2016.
 */
public class ContactsController{


        public void Contacts(Context context,ListView contact_list)
        {


            new ContactsHandler(context,contact_list).execute();




        }

    public void Deleted(Context context,ListView deleted_list)
    {


            new DeletedContactsHandler(context,deleted_list).execute();


    }


    public void Favorites(Context context,ListView favorite_list)
    {


            new FavoritesContactsHandler(context,favorite_list).execute();


    }






}
