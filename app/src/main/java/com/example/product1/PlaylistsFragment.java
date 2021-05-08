package com.example.product1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistsFragment extends Fragment {

    public PlaylistsFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        ImageView favourites = view.findViewById(R.id.imgFavourites);
        favourites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToFavouriteActivity(v);
            }
        });
        return view;
    }

    public void goToFavouriteActivity(View view){
        System.out.println("favourite list :" + PlaySongActivity.favList);
        Intent intent = new Intent(getContext(), FavouriteActivity.class);
        PlaylistsFragment.this.startActivity(intent);
    }

}