package com.example.product1;

public class Song {
    private String title;
    private String artiste;
    private String fileLink;
    private Double songLength;
    private String coverArt;

    public Song(String _title, String _artiste, String _fileLink, Double _songLength, String _coverArt){
        this.title = _title;
        this.artiste = _artiste;
        this.fileLink = _fileLink;
        this.songLength = _songLength;
        this.coverArt = _coverArt;
    }

    public String getTitle(){
        return title;
    }

    public String getArtiste(){
        return artiste;
    }

    public String getFileLink(){
        return fileLink;
    }

    public Double getSongLength(){
        return songLength;
    }

    public String getCoverArt(){
        return coverArt;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setArtiste(String artiste){
        this.artiste = artiste;
    }

    public void setFileLink(String fileLink){
        this.fileLink = fileLink;
    }

    public void setSongLength(Double songLength){
        this.songLength = songLength;
    }

    public void setCoverArt(String coverArt){
        this.coverArt = coverArt;
    }


}
