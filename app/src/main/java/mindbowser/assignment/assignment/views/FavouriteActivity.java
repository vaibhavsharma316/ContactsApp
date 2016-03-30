package mindbowser.assignment.assignment.views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import mindbowser.assignment.assignment.R;
import mindbowser.assignment.assignment.controller.ContactsController;


public class FavouriteActivity extends AppCompatActivity {

    ListView favourite_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        getReference();

        new ContactsController().Favorites(FavouriteActivity.this,favourite_list);



    }


    private void getReference()
    {

        favourite_list=(ListView)findViewById(R.id.favourite_list);

    }



}
