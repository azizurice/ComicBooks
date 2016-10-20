package ca.aequilibrium.comicbooks;

import android.app.Activity;
import android.app.ProgressDialog;

import java.util.ArrayList;

import ca.aequilibrium.comicbooks.model.ComicCharacter;

/**
 * Created by Azizur on 19/10/2016.
 */

public class BaseActivity extends Activity {
    private ProgressDialog mProgressDialog;
    public static String MARVEL_URL ="http://gateway.marvel.com/v1/public/comics?ts=17&apikey=c30573ac063f8a33b1de98dffc550caf&hash=7f2feb587bc9e1e5353669b50619baaf&limit=100";
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
