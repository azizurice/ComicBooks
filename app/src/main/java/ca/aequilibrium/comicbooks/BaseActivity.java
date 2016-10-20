package ca.aequilibrium.comicbooks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import ca.aequilibrium.comicbooks.model.ComicCharacter;

/**
 * Created by Azizur on 19/10/2016.
 */

public class BaseActivity extends Activity {
    private ProgressDialog mProgressDialog;
    public static String MARVEL_URL = "http://gateway.marvel.com/v1/public/comics?ts=17&apikey=3b4c677c969802964ca49b1283749e53&hash=e25ef0935e9c7a8b6915a7d920001e69&limit=50";
    public static ArrayList<ComicCharacter> comicCharacters;
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
