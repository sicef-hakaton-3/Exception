package com.example.dejan.hackaday;

import com.example.dejan.hackaday.Model.Genres;

import java.util.List;

/**
 * Created by dejan on 11/05/2016.
 */
public enum Moods {

    SAD("Sad"),
    BORED("Bored"),
    ANGRY("Angry"),
    LOVE("Love"),
    HAPPY("Happy");

    public final String mood;

    Moods(String m) {
        this.mood = m;
    }

    @Override
    public String toString() {
        return mood;
    }

    public static Moods getMood(String mode) {
        switch (mode.toUpperCase()) {
            case "SAD":
                return Moods.SAD;
            case "BORED":
                return Moods.BORED;
            case "ANGRY":
                return Moods.ANGRY;
            case "LOVE":
                return Moods.LOVE;
            case "HAPPY":
                return Moods.HAPPY;
        }
        return Moods.HAPPY;
    }


}
