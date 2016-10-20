package ca.aequilibrium.comicbooks.server;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ca.aequilibrium.comicbooks.BaseActivity;
import ca.aequilibrium.comicbooks.model.ComicCharacter;

/**
 * Created by Azizur on 20/10/2016.
 */

public class RestClient extends AsyncTask<String, Void, String> {
    private static final String TAG = RestClient.class.getSimpleName();

    private HttpURLConnection conn;
    private TaskComplete callback;
    public RestClient(TaskComplete callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String result ="Failed" ;
        try {
            openConnection(params[0], params[1]);
            Log.i(TAG, params[0]+" param1 : "+params[1]);
            //Read response body
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                String response = streamToString(conn.getInputStream());
                parseResult(response);
                result="Success";
                Log.d(TAG,result.toString());
            } else {
                String response = streamToString(conn.getErrorStream());
                result="Failed";
                Log.d(TAG,response.toString());
            }
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
           if (result.equalsIgnoreCase("Success")) {
            callback.onTaskComplete(result);
        }
    }

    private void openConnection(String uri, String httpMethod) throws Exception {
        if (conn != null) {
            closeConnection();
        }

        URL url = new URL(uri);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod(httpMethod);
        conn.setDoInput(true);
        conn.setDoOutput(httpMethod != "GET");
        conn.addRequestProperty("Content-Type", "application/json");
        conn.connect();

        Log.d(TAG,"[" + httpMethod + "] " + url);
    }


    private void closeConnection() {
        if (conn != null) {
            conn.disconnect();
        }
        conn = null;
    }



   private String streamToString(InputStream input) throws IOException {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int read;
        while ((read = input.read(bytes)) > 0) {
            baos.write(bytes, 0, read);
        }
        return new String(baos.toByteArray());
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONObject jasonData=response.getJSONObject("data");
            JSONArray resultsArray=jasonData.getJSONArray("results");
            for(int i=0; i<resultsArray.length(); i++){
                JSONObject comicBook =resultsArray.optJSONObject(i);
                String title =comicBook.getString("title");
                String description=comicBook.getString("description");
                JSONObject jsonThumbnail=comicBook.getJSONObject("thumbnail");
                String thumbnailPath=jsonThumbnail.getString("path");
                String thumbnailExtension=jsonThumbnail.getString("extension");
                String characterURL=thumbnailPath+"/portrait_large."+thumbnailExtension;
                BaseActivity.comicCharacters.add(new ComicCharacter(title,description,characterURL));
                System.out.println("Title: "+title+ "  Detail :"+description+ " Character URL: "+characterURL);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}