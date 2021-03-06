package ca.aequilibrium.comicbooks;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.aequilibrium.comicbooks.fragments.GridFragment;
import ca.aequilibrium.comicbooks.fragments.LinearFragment;
import ca.aequilibrium.comicbooks.model.ComicCharacter;
import ca.aequilibrium.comicbooks.server.RestClient;
import ca.aequilibrium.comicbooks.server.ImageDownload;
import ca.aequilibrium.comicbooks.server.TaskComplete;

/**
 * Created by Azizur on 19/10/2016.
 */
public class MainActivity extends BaseActivity implements TaskComplete {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] titles;
    private int currentPosition = 0;
    static  int index=0;
    static int numberOfInvokingThisMethod=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    if(BaseActivity.comicCharacters==null) {
        BaseActivity.comicCharacters = new ArrayList<ComicCharacter>();

        new RestClient(this).execute(BaseActivity.MARVEL_URL,"GET");
        showProgressDialog();
    }

        titles = getResources().getStringArray(R.array.titles);
        mDrawerList = (ListView) findViewById(R.id.navigation_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        //Display the correct fragment.
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }


        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getFragmentManager().addOnBackStackChangedListener(new ComicFragmentManager());
    }


    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }

    public void selectItem(int position) {
        currentPosition = position;
        Fragment fragment;
        if(position == 1) {
            fragment = new LinearFragment();
        } else {
            fragment = new GridFragment();
        }

         FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "visible_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        setActionBarTitle(position);
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private class ComicFragmentManager implements FragmentManager.OnBackStackChangedListener {
        @Override
        public void onBackStackChanged() {
            FragmentManager fragMan = getFragmentManager();
            Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
            if (fragment instanceof GridFragment) {
                currentPosition = 0;
            }
            if (fragment instanceof LinearFragment) {
                currentPosition = 1;
            }
            setActionBarTitle(currentPosition);
            mDrawerList.setItemChecked(currentPosition, true);
        }

    }

    @Override
    public synchronized void onTaskComplete(Object data) {
        if (data instanceof String){
            Log.d(TAG,data.toString());
            index=0;
            numberOfInvokingThisMethod=0;
        }

        if (index<BaseActivity.comicCharacters.size()-1){
            if (numberOfInvokingThisMethod>0 && data instanceof Bitmap) {
                BaseActivity.comicCharacters.get(index).setThumbnail((Bitmap) data);
                index++;
            }

            numberOfInvokingThisMethod++;
            ComicCharacter comicCharacter=BaseActivity.comicCharacters.get(index);
            ImageDownload download = new ImageDownload(this);
            download.execute(comicCharacter.getCharacterURL().toString());
        }


        if (index==BaseActivity.comicCharacters.size()/2){
            hideProgressDialog();
        }


    }

}
