package ca.aequilibrium.comicbooks.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ca.aequilibrium.comicbooks.BaseActivity;
import ca.aequilibrium.comicbooks.R;
import ca.aequilibrium.comicbooks.adapter.ImageCaptionAdapter;

/**
 * Created by Azizur on 19/10/2016.
 */

public class GridFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_grid_recycler, container, false);
        RecyclerView characterRecycler = (RecyclerView)layout.findViewById(R.id.character_recycler);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        characterRecycler.setLayoutManager(layoutManager);
        ImageCaptionAdapter adapter = new ImageCaptionAdapter(BaseActivity.comicCharacters);

        characterRecycler.setAdapter(adapter);
        adapter.setListener(new ImageCaptionAdapter.Listener() {
            public void onClick(int position) {
                // call here detail activity
            }
        });
        return layout;
    }
}
