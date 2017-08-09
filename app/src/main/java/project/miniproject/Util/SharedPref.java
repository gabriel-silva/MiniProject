package project.miniproject.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gabri on 05/08/2017.
 */

public class SharedPref {


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

    public static void setStateCheckBox(Context context, ArrayList<CheckBox> checkBox){

        SharedPreferences.Editor checked = SharedPref.getPref(context).edit();
        for (int i = 0; i < checkBox.size(); i++) {
            if (!checkBox.get(i).isChecked()) {
                checked.putInt("category" + (i + 1), 0);
            } else {
                checked.putInt("category" + (i + 1), (i + 1));
            }

        }
        checked.commit();
    }


    public static int[] getChecked(Context context) {

        int[] checked = new int[getCountCheckBox(context)];
        for (int i = 0; i < checked.length; i++) {
            checked[i] = SharedPref.getPref(context).getInt("category" + (i + 1), 0);
        }
        return checked;
    }

    public static void checkedPoint(Context context) {

        SharedPreferences.Editor checked = SharedPref.getPref(context).edit();
        for (int i = 0; i < getCountCheckBox(context); i++) {
            checked.putInt("category" + (i + 1), (i + 1));
        }
        checked.commit();

    }

    public static int getCountCheckBox(Context context){
        int checked = SharedPref.getPref(context).getInt("countCheckBox", 0);
        return checked;
    }


}
