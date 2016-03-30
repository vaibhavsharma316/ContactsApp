package mindbowser.assignment.assignment.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import mindbowser.assignment.assignment.R;
import mindbowser.assignment.assignment.controller.ContactsController;
import mindbowser.assignment.assignment.helper.ContactsHandler;


public class ContactsActivity extends AppCompatActivity{

    ListView contacts_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getReference();

       new ContactsController().Contacts(ContactsActivity.this,contacts_list);
      }




        private void getReference()
        {

            contacts_list=(ListView)findViewById(R.id.contacts_list);
         }









}
