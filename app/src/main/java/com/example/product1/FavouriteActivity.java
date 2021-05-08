package com.example.product1;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteActivity extends AppCompatActivity{

    RecyclerView mRecyclerView;
    MusicAdaptor mAdaptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FavouriteActivity.this));

        mAdaptor = new MusicAdaptor(FavouriteActivity.this, PlaySongActivity.favList);
        mRecyclerView.setAdapter(mAdaptor);


    }
}
