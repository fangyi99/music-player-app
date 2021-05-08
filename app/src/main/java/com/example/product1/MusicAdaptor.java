package com.example.product1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MusicAdaptor extends RecyclerView.Adapter<MusicAdaptor.ViewHolder> implements Filterable{

    private Context mContext;
    private ArrayList<Song> songList;
    private ArrayList<Song> filterListFull;

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView imgCoverArt;
        TextView txtTitle, txtArtiste;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtArtiste = itemView.findViewById(R.id.txtArtiste);
            imgCoverArt = itemView.findViewById(R.id.imgCoverArt);
        }
    }

    public MusicAdaptor(Context mContext, ArrayList<Song> songList) {
        this.mContext = mContext;
        this.songList = songList;
        this.filterListFull = new ArrayList<>(songList); //copy without affecting the original list after filter
    }

    @Override
    public int getItemCount() {
        return filterListFull.size();
    }

    @Override
    public MusicAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Song currentSong = filterListFull.get(position);
        if (currentSong != null) {
            holder.txtTitle.setText(currentSong.getTitle());
            holder.txtArtiste.setText(currentSong.getArtiste());
            Picasso.with(mContext).load(currentSong.getCoverArt()).into(holder.imgCoverArt);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sendDataToActivity(currentSong);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) { //constraint = user input
            ArrayList<Song> filteredList = new ArrayList<>();
            //if no user input -> show all songs from copy list
            if(constraint == null || constraint.length()==0){
                filterListFull = songList;
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Song item : filterListFull){
                    if(item.getTitle().toLowerCase().contains(filterPattern)){ //compare user input with song title
                        filteredList.add(item);
                    }
                }
                filterListFull = filteredList;
            }
            FilterResults results = new FilterResults();
            results.values = filterListFull;
            return results;
        }

        @Override
        //update search results
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterListFull = (ArrayList<Song>) results.values;
            notifyDataSetChanged();
        }
    };

    public void sendDataToActivity(Song song){
        Intent intent = new Intent(mContext, PlaySongActivity.class);
        intent.putExtra("title", song.getTitle());
        intent.putExtra("artiste", song.getArtiste());
        intent.putExtra("songLength", song.getSongLength());
        intent.putExtra("fileLink", song.getFileLink());
        intent.putExtra("coverArt", song.getCoverArt());
        mContext.startActivity(intent);
    }



}
