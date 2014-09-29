package test.ids.sgv.ids_testtask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by sgv on 29.09.2014.
 */
public class Utils {

    /**
     * @param url http://upload.wikimedia.org/wikipedia/en/4/43/Beirut-the-rip-tide.jpg;
     * @return Beirut-the-rip-tide.jpg
     */
    public static String getNameFromUrl(String url) {
        int start = url.lastIndexOf("/") + 1;
        String name = url.substring(start, url.length());
        return name;
    }

    /**
     * @param url http://upload.wikimedia.org/wikipedia/en/4/43/Beirut-the-rip-tide.jpg;
     * @return Beirut-the-rip-tide
     */
    public static String getFileName(String url) {
        return removeJPG(getNameFromUrl(url));
    }

    public static String removeJPG(String name) {
        int start = name.lastIndexOf(".");
        String name1 = name.substring(0, start);
        return name1;
    }

    public static Bitmap getBitMapByURL(String url) {
        File imgFile = new File(url);
        Bitmap myBitmap = null;
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }


}
