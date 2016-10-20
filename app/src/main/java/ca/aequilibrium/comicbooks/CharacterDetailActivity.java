package ca.aequilibrium.comicbooks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Azizur on 20/10/2016.
 */

public class CharacterDetailActivity extends BaseActivity {

    public static final String EXTRA_CHARACTER_NO = "characterNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        //Enable the Up button
        getActionBar().setDisplayHomeAsUpEnabled(true);


        int characterNo = (Integer)getIntent().getExtras().get(EXTRA_CHARACTER_NO);
        String characterTitle= BaseActivity.comicCharacters.get(characterNo).getTitle();

        TextView textView = (TextView)findViewById(R.id.character_text);
        textView.setText(characterTitle);
        Bitmap characterImage =BaseActivity.comicCharacters.get(characterNo).getThumbnail();
        CardView cardView =(CardView) findViewById(R.id.card_view1);
        ImageView imageView = (ImageView)cardView.findViewById(R.id.character_image);
        imageView.setImageBitmap(characterImage);

        imageView.setContentDescription(characterTitle);

        TextView textDetail=(TextView)findViewById(R.id.character_detail);
        String characterDetail=BaseActivity.comicCharacters.get(characterNo).getDetail();
        textDetail.setText(characterDetail);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
