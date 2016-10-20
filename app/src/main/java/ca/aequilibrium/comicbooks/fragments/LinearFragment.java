package ca.aequilibrium.comicbooks.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.aequilibrium.comicbooks.BaseActivity;
import ca.aequilibrium.comicbooks.R;
import ca.aequilibrium.comicbooks.adapter.ImageCaptionAdapter;

/**
 * Created by Azizur on 19/10/2016.
 */

public class LinearFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView characterRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_linear_recycler,
                container, false);
        ImageCaptionAdapter adapter = new ImageCaptionAdapter(BaseActivity.comicCharacters);
        characterRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        characterRecycler.setLayoutManager(layoutManager);
        adapter.setListener(new ImageCaptionAdapter.Listener() {
            public void onClick(int position) {
                // Call detail activity here
            }
        });
        return characterRecycler;
    }

}
