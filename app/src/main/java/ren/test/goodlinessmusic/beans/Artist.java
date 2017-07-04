package ren.test.goodlinessmusic.beans;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/07/04
 */

public class Artist extends RealmObject{
    private String artist;
    private int  number;
    private long id;
    private String initialLetter;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInitialLetter() {
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }
}
