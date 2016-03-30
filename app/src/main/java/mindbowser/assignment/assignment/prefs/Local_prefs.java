package mindbowser.assignment.assignment.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vaibhav on 3/29/2016.
 */
public class Local_prefs {

   private SharedPreferences sharedPreferences;
   private SharedPreferences.Editor editor;
Context context;
    public Local_prefs(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);


    }


    public void setCopyStatus(boolean status)
    {

        editor=sharedPreferences.edit();
        editor.putBoolean("status",status);
        editor.commit();
    }

        public boolean getCopyStatus()
        {
            return sharedPreferences.getBoolean("status",false);
        }


}
