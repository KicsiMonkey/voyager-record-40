package hu.bme.yjzygk.voyagerrecord40.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LayoutAsImageSharer {
    private static Bitmap getBitmap(ViewGroup layout) {
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(layout.getDrawingCache());
        layout.setDrawingCacheEnabled(false);

        return bmp;
    }

    private static String[] saveBitmap(Bitmap getbitmap, float width, float height) {

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Golden Record");
        boolean wasFolderMade = false;
        if (!folder.exists())
            wasFolderMade = folder.mkdir();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSS", Locale.getDefault()).format(new Date());

        File file = new File(folder.getPath(),"Record_" + timeStamp + ".png");
        boolean wasFileCreated = false;
        if (!file.exists()) {
            try {
                wasFileCreated = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream ostream = null;
        try {
            ostream = new FileOutputStream(file);

            System.out.println(ostream);

            Bitmap save = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Canvas now = new Canvas(save);
            now.drawRect(new Rect(0,0,(int) width, (int) height), paint);
            now.drawBitmap(
                    getbitmap
                    , new Rect(0,0, getbitmap.getWidth(), getbitmap.getHeight())
                    , new Rect(0,0,(int) width, (int) height), null);

            save.compress(Bitmap.CompressFormat.PNG, 100, ostream);

        } catch (NullPointerException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Toast.makeText(getApplicationContext(), "File error", Toast.LENGTH_SHORT).show();
        }

        String type = "image/png";
        String mediaPath = file.getPath();
        return new String[]{type, mediaPath};
    }

    private static void createInstagramIntent(Activity a, String type, String mediaPath) {

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        a.startActivity(Intent.createChooser(share, "Share to"));
    }

    public static void shareLayout(Activity a, ViewGroup layout) {
        Toast.makeText(a, "Saving and sharing image...", Toast.LENGTH_SHORT).show();
        Bitmap bmp = getBitmap(layout);
        String[] shareParams = saveBitmap(bmp, layout.getMeasuredWidth(), layout.getMeasuredHeight());
        createInstagramIntent(a, shareParams[0], shareParams[1]);
    }

    public static void saveLayout(Activity a, ViewGroup layout) {
        Bitmap bmp = getBitmap(layout);
        saveBitmap(bmp, layout.getMeasuredWidth(), layout.getMeasuredHeight());
        Toast.makeText(a, "Image saved.", Toast.LENGTH_SHORT).show();
    }
}
