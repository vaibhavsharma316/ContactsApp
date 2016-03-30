package mindbowser.assignment.assignment.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * Created by vaibhav on 3/29/2016.
 */
public class ContactsHandler extends AsyncTask<Void,Void,Void> {

        private Context context;
        private ProgressDialog progressDialog;
        private List<Model> contactModels=new ArrayList<Model>();
        private ListView listview;
        private boolean contacts_available=false;
        private AlertDialog alertDialog;
       public ContactsHandler(Context context, ListView listView)
        {
                this.context=context;
                this.listview=listView;
        }




    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {

            contactModels=new Database(context).getAllContacts();

            if (contactModels.size()==0)
            {
                contacts_available=false;
            }
        else
            {
                contacts_available=true;

            }
        } catch (Exception ex)
        {

            ex.printStackTrace();

        }



        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {

        progressDialog.dismiss();

        if (contacts_available)
        {


            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



                    setUpDialog(contactModels.get(position).getNumber());

                }
            });

             listview.setAdapter(new ContactsAdapter());
        }
        else
        {
            Toast.makeText(context,"You have no contacts in your phone",Toast.LENGTH_SHORT).show();

        }



        super.onPostExecute(voids);
    }

    private void setUpDialog(final String number)
    {


        final AlertDialog.Builder builder =new AlertDialog.Builder(context);
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_dialog_row,null);
        TextView call=(TextView)view.findViewById(R.id.call_user_choice_option);
        TextView msg=(TextView)view.findViewById(R.id.msg_user_choice_option);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
                Intent place_call=new Intent(Intent.ACTION_CALL);
                   place_call.setData(Uri.parse("tel:"+ number));
               context.startActivity(place_call);

            }
        });


        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION_CODES.LOLLIPOP>=19) {

                    Intent send_msg = new Intent(Intent.ACTION_SENDTO);
                    send_msg.setData(Uri.parse("smsto:" + Uri.encode(number)));
                    context.startActivity(send_msg);

                }
                else
                {
                    Intent send_msg = new Intent(Intent.ACTION_VIEW);
                    send_msg.putExtra("address", number);
                    send_msg.setType("vnd.android-dir/mms-sms");
                    context.startActivity(send_msg);
                }


            }
        });



        builder.setView(view);
       alertDialog= builder.create();
        alertDialog.show();







    }



class ContactsAdapter extends BaseAdapter
{

    private ImageView contact_image;
    private TextView contact_name;
    private TextView contact_number;
    private TextView contact_first_letter;
    private Button contact_delete;
    private Button contact_favorite;
    private AlertDialog dialog=null;
    @Override
    public int getCount() {
        return contactModels.size();
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
        contact_delete=(Button)layout.findViewById(R.id.contacts_delete_list_btn);
        contact_favorite=(Button)layout.findViewById(R.id.contacts_favourite_list_btn);
        final Model model_get_records=contactModels.get(position);

        contact_delete.setVisibility(View.VISIBLE);
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


        if (model_get_records.getDelete_status().equalsIgnoreCase("n"))
        {
            contact_favorite.setVisibility(View.VISIBLE);
        }
        else
        {
            contact_favorite.setVisibility(View.GONE);
        }

        if (model_get_records.getFavorite_status().equalsIgnoreCase("y"))
        {

            contact_favorite.setVisibility(View.GONE);


        }
        else if (model_get_records.getFavorite_status().equalsIgnoreCase("n"))
        {
            contact_favorite.setVisibility(View.VISIBLE);
        }


        contact_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this contact ?");
                builder.setTitle("Confirmation");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    dialog.dismiss();


                        int count  = new Database(context).deleteContact(contactModels.get(position).getId());

                        if (count>0)
                        {
                            new ContactsHandler(context,listview).execute();
                            Toast.makeText(context,"Contact removed successfully",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(context,"Contact could not be removed successfully",Toast.LENGTH_SHORT).show();
                        }




                    }
                });


                                 builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                                      dialog.dismiss();


                    }
                });


              dialog= builder.create();
                dialog.show();
            }
        });


        contact_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



              int count = new Database(context).addToFavorite(contactModels.get(position).getId());
               if (count>0) {
                  new ContactsHandler(context,listview).execute();
                   Toast.makeText(context, "Contact added to favorites", Toast.LENGTH_SHORT).show();

               }




            }
        });



        return layout;
    }



}











}
