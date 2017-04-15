package com.example.dejan.hackaday.Model;

public enum Genres {
    DRAMA(18),
    ACTION(28),
    CRYME(80),
    ADVENTURE(12),
    SCIFI(878),
    FAMILY(10751),
    ANIMATION(16),
    FANTASY(14),
    ROMANSE(10749),
    MYSTERY(9648),
    COMEDY(35),
    MUSIC(10420),
    TRILER(53);

    public final int genresId;

    Genres(int genre){
        this.genresId = genre;
    }

}
