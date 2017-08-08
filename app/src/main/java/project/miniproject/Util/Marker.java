package project.miniproject.Util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import project.miniproject.R;

public class Marker extends SharedPref{

    public Marker(Activity activity) {
        super(activity);
    }

    private void addMarker(JSONArray jsonArray, int count) throws JSONException {

        LatLng location = new LatLng(jsonArray.getJSONObject(count).getDouble(Constants.LATITUDE),
                jsonArray.getJSONObject(count).getDouble(Constants.LONGITUTE));

        MapUtil.getMap().addMarker(new MarkerOptions()
                .position(location)
                .title(jsonArray.getJSONObject(count)
                        .getString(Constants.NAME)));

    }

    public void logicMarker(Context context) {

        try {

            int[] checked = getChecked(context);
            JSONArray jsonArray = new JSONArray(AssetsHelper.fileJson(context));

            for (int i = 0; i < jsonArray.length(); i++) {
                for (int j = 0; j < checked.length; j++) {
                    if (checked[j] == jsonArray.getJSONObject(i).getInt(Constants.CATEGORY)) {
                        addMarker(jsonArray, i);
                    }
                }
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }


}