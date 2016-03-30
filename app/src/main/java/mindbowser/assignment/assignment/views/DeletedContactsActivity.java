package mindbowser.assignment.assignment.views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import mindbowser.assignment.assignment.R;
import mindbowser.assignment.assignment.controller.ContactsController;


public class DeletedContactsActivity extends AppCompatActivity {

    ListView deleted_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_contacts);
        getReference();
        new ContactsController().Deleted(DeletedContactsActivity.this,deleted_list);


    }

    private void getReference()
    {

        deleted_list=(ListView)findViewById(R.id.deleted_list);

    }


    }
