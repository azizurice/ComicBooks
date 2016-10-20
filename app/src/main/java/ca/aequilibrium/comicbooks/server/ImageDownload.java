package ca.aequilibrium.comicbooks.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Azizur on 20/10/2016.
 */

public class ImageDownload extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = ImageDownload.class.getSimpleName();
    private TaskComplete callback;

    public ImageDownload(TaskComplete callback) {
        this.callback = callback;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap image = null;

        try {
            InputStream in = new URL(params[0]).openStream();
            image = BitmapFactory.decodeStream(in);
        }
        catch (Exception e) {
            Log.d(TAG,e.getMessage());
        }

        return image;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {
            callback.onTaskComplete(result);

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}
