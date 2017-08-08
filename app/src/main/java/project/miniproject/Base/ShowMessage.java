package project.miniproject.Base;

import android.content.Context;
import android.widget.Toast;
import project.miniproject.R;

/**
 * Created by gabri on 05/08/2017.
 */

public class ShowMessage {
    public static void message(Context context){
        Toast.makeText(context, R.string.messege , Toast.LENGTH_LONG).show();
    }
}
