package mindbowser.assignment.assignment.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mindbowser.assignment.assignment.R;
import mindbowser.assignment.assignment.model.Model;

/**
 * Created by vaibhav on 3/30/2016.
 */
public class FavoritesContactsHandler extends AsyncTask<Void,Void,Void> {


    Context context;
    private  FavoriteContactsAdapter favoriteContactsAdapter;
    private ListView favorite_list;
    private ProgressDialog progressDialog;
    private List<Model> favorite_model=new ArrayList<>();
    public FavoritesContactsHandler(Context context, ListView favorite_list)
    {
        this.context=context;
        this.favorite_list=favorite_list;

    }

    @Override
    protected void onPreExecute() {

       progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Deleting contact..");
        progressDialog.show();

        super.onPreExecute();

    }


    @Override
    protected Void doInBackground(Void... voids) {

            try {

            favorite_model=  new Database(context).getFavorites();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();

        if (favorite_model.size()>0)
        {
            favoriteContactsAdapter=new FavoriteContactsAdapter();
            favoriteContactsAdapter.notifyDataSetChanged();
            favorite_list.setAdapter(favoriteContactsAdapter);
        }
        else
        {
          Toast.makeText(context,"You have no contacts in favorites",Toast.LENGTH_SHORT).show();

        }

        super.onPostExecute(aVoid);
    }



    class FavoriteContactsAdapter extends BaseAdapter
    {

        private ImageView contact_image;
        private TextView contact_name;
        private TextView contact_number;
        private TextView contact_first_letter;
        private Button contact_favorite;
        @Override
        public int getCount() {
            return favorite_model.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout=inflater.inflate(R.layout.contacts_list_rows,null);

            contact_name=(TextView)layout.findViewById(R.id.contact_name);
            contact_number=(TextView)layout.findViewById(R.id.contact_number);
            contact_image=(ImageView)layout.findViewById(R.id.contact_image);
            contact_first_letter=(TextView)layout.findViewById(R.id.contact_name_letter);

            contact_favorite=(Button)layout.findViewById(R.id.contacts_favourite_list_btn);
            final Model model_get_records=favorite_model.get(position);

            contact_favorite.setVisibility(View.VISIBLE);
            contact_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                int count=new Database(context).removeFromFavorite(model_get_records.getId());

                    if (count>0)
                    {
                        favorite_model.remove(position);
                        favorite_list.setAdapter(favoriteContactsAdapter);


                        Toast.makeText(context,"Contact removed from favorites",Toast.LENGTH_SHORT).show();
                    }


                }
            });
            contact_name.setText(model_get_records.getName());



            if (model_get_records.getNumber()!=null) {
                contact_number.setText(model_get_records.getNumber());
            }
            else
            {
                contact_number.setText("");

            }

            if (model_get_records.getUri().isEmpty()==false)
            {

                contact_image.setVisibility(View.VISIBLE);
                contact_first_letter.setVisibility(View.GONE);
                contact_image.setImageURI(Uri.parse(model_get_records.getUri()));


            }
            else
            {

                contact_first_letter.setVisibility(View.VISIBLE);
                contact_image.setVisibility(View.GONE);
                contact_first_letter.setText(model_get_records.getName().substring(0,1).toUpperCase());

            }






            return layout;
        }
    }







}


