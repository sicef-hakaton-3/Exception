package com.example.dejan.hackaday.Model;

/**
 * Created by dejan on 11/05/2016.
 */
public class Movie {
    private String PosterPath;
    private String Title;
    private String Genres;
    private Double Vote;

    public void setPosterPath(String posterPath){
        this.PosterPath = posterPath;
    }
    public String getPosterPath(){
        return this.PosterPath;
    }

    public void setTitle(String title){
        this.Title = title;
    }
    public String getTitle(){
        return this.Title;
    }

    public void setGenres(String genres){
        this.Genres = genres;
    }
    public String getGenres(){
        return this.Genres;
    }

    public void setVote(Double vote){
        this.Vote = vote;
    }
    public Double getVote(){
        return this.Vote;
    }
}
