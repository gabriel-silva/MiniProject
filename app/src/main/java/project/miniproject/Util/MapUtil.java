package project.miniproject.Util;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by gabri on 06/08/2017.
 */

public class MapUtil {

    private static GoogleMap map;

    public static GoogleMap getMap() {
        return map;
    }

    public static void setMap(GoogleMap map) {
        MapUtil.map = map;
    }

}
