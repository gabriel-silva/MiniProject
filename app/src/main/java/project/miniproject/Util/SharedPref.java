package project.miniproject.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.GridLayout;
import project.miniproject.R;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gabri on 05/08/2017.
 */

public class SharedPref {

    private GridLayout gridLayout;

    public SharedPref(Activity activity) {
        gridLayout = activity.findViewById(R.id.grid_layout);
    }

    public int[] getChecked(Context context) {

        int[] checked = new int[gridLayout.getChildCount() - 1];
        for (int i = 0; i < checked.length; i++) {
            checked[i] = SharedPref.getPref(context).getInt("category" + (i + 1), 0);
        }
        return checked;
    }

    public void checkedPoint(Context context) {

        SharedPreferences.Editor checked = SharedPref.getPref(context).edit();
        for (int i = 0; i < gridLayout.getChildCount() - 1; i++) {
            checked.putInt("category" + (i + 1), (i + 1));
        }
        checked.commit();

    }

    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(Constants.STATE, MODE_PRIVATE);
    }

    public static void state(Context context) {
        SharedPreferences.Editor state = getPref(context).edit();
        state.putString("state", "checked");
        state.commit();
    }

    public static boolean isEmptyState(Context context) {
        String state = getPref(context).getString("state", "");
        return state.equals("checked");
    }

    public static void stateVisibleFrameLayout(Context context) {
        SharedPreferences.Editor stateFrameLoyout = SharedPref.getPref(context).edit();
        stateFrameLoyout.putString("frameVisible", "visible");
        stateFrameLoyout.commit();
    }


    public static void stateInvisibleFrameLayout(Context context) {
        SharedPreferences.Editor stateFrameLoyout = SharedPref.getPref(context).edit();
        stateFrameLoyout.remove("frameVisible");
        stateFrameLoyout.commit();
    }

    public static boolean isVisible(Context context) {
        String state = getPref(context).getString("frameVisible", "");
        return state.equals("visible");
    }

}
