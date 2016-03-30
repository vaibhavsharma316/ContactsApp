package mindbowser.assignment.assignment.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import mindbowser.assignment.assignment.R;
import mindbowser.assignment.assignment.helper.Database;
import mindbowser.assignment.assignment.model.LocalDatabaseHandler;
import mindbowser.assignment.assignment.prefs.Local_prefs;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    Button contacts,delete,favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getReference();
        setButtonEventHandler();

        if (new Local_prefs(HomeActivity.this).getCopyStatus()==false) {
            new LocalDatabaseHandler(HomeActivity.this).execute();
        }

    }




    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.contacts_btn)
        {

            startActivity(new Intent(HomeActivity.this,ContactsActivity.class));

        }
        else if(view.getId()==R.id.delete_btn)
        {

            startActivity(new Intent(HomeActivity.this,DeletedContactsActivity.class));

        }
        else if(view.getId()==R.id.favourites_btn)
        {
            startActivity(new Intent(HomeActivity.this,FavouriteActivity.class));

        }
         }


        private void getReference()
        {
            contacts=(Button)findViewById(R.id.contacts_btn);
            delete=(Button)findViewById(R.id.delete_btn);
            favorite=(Button)findViewById(R.id.favourites_btn);
            }

        private void setButtonEventHandler()
        {

            contacts.setOnClickListener(this);
            delete.setOnClickListener(this);
            favorite.setOnClickListener(this);
        }

}
