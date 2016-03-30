package mindbowser.assignment.assignment.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
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
public class DeletedContactsHandler extends AsyncTask<Void,Void,Void> {


    private Context context;
    private ProgressDialog progressDialog;
    private List<Model> DeletedContactsModels=new ArrayList<>();
    private ListView listview;


    public DeletedContactsHandler(Context context, ListView listView)
    {
        this.context=context;
        this.listview=listView;
    }


    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            DeletedContactsModels=  new Database(context).getDeletedContacts();

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

        if (DeletedContactsModels.size()>0)
        {
            listview.setAdapter(new DeletedContactsAdapter());
        }
       else
        {
            Toast.makeText(context,"No deleted contacts found",Toast.LENGTH_SHORT).show();

        }

        super.onPostExecute(aVoid);
    }


    class DeletedContactsAdapter extends BaseAdapter
    {
        private ImageView contact_image;
        private TextView contact_name;
        private TextView contact_number;
        private TextView contact_first_letter;
        private Button restore_contact;

        @Override
        public int getCount() {
            return DeletedContactsModels.size();
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

            restore_contact=(Button)layout.findViewById(R.id.contacts_restore_list_btn);
            final Model model_get_records=DeletedContactsModels.get(position);


            contact_name.setText(model_get_records.getName());


            if (model_get_records.getDelete_status().equalsIgnoreCase("y"))
            {
                restore_contact.setVisibility(View.VISIBLE);
            }

           restore_contact.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

               int count  = new Database(context).restoreContact(DeletedContactsModels.get(position).getId());

                   if (count>0)
                   {
                       Toast.makeText(context,"Contact restored successfully",Toast.LENGTH_SHORT).show();
                       new DeletedContactsHandler(context,listview).execute();
                   }
               }
           });



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
