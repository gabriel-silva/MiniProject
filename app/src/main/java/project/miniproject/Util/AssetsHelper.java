package project.miniproject.Util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gabri on 05/08/2017.
 */

public class AssetsHelper {
    public static String fileJson(Context context) throws IOException {
        InputStream file = context.getAssets().open("pontosref.json");
        int size = file.available();
        byte[] buffer = new byte[size];
        file.read(buffer);
        file.close();
        String json = new String(buffer, "UTF-8");
        return json;
    }
}
