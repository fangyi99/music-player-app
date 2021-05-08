package com.example.product1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.product1.SongCollection.prepareSongs;

public class TracksFragment extends Fragment{
    /**
     * Big list with all the Songs found.
     */
    public ArrayList<Song> songList = prepareSongs();

    private RecyclerView mRecyclerView;
    public static MusicAdaptor mAdaptor;

    /**
     * Returns a new list with all songs.
     *
     * @note This is different than accessing `songs` directly
     *       because it duplicates it - you can then mess with
     *       it without worrying about changing the original.
     */

    public TracksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdaptor = new MusicAdaptor(getActivity(), songList);
        mRecyclerView.setAdapter(mAdaptor);
    }

}