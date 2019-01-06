package com.baturtulek.movie;

public class Movie {

    private int id;
    private String name;
    private int voteCount;
    private double voteAvg;
    private String popularity;
    private String original_language;
    private boolean isAdult;
    private String plot;
    private String imgPath;

    public Movie(int id, String name, int voteCount, double voteAvg, String popularity, String original_language, boolean isAdult, String plot, String imgP) {
        this.id = id;
        this.name = name;
        this.voteCount = voteCount;
        this.voteAvg = voteAvg;
        this.popularity = popularity;
        this.original_language = original_language;
        this.isAdult = isAdult;
        this.plot = plot;
        this.imgPath = imgP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    public double getVoteAvg() {
        return voteAvg;
    }
    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", voteCount=" + voteCount +
                ", voteAvg=" + voteAvg +
                ", popularity='" + popularity + '\'' +
                ", original_language='" + original_language + '\'' +
                ", isAdult=" + isAdult +
                ", plot='" + plot + '\'' +
                '}';
    }
}
