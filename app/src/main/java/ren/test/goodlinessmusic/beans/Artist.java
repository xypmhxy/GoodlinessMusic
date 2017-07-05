package ren.test.goodlinessmusic.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2017/07/04
 */

public class Artist extends RealmObject implements Comparable<Artist>{
    private String artist;
    private String headUrl;
    private int  number;
    @PrimaryKey
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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public int compareTo(Artist another) {
        if (another == null || initialLetter == null || another.getInitialLetter() == null)
            return 0;
        if (initialLetter.equals("#") && !another.getInitialLetter().equals("#")) {
            return 1;
        } else if (!initialLetter.equals("#") && another.getInitialLetter().equals("#")) {
            return -1;
        } else {
            return initialLetter.compareToIgnoreCase(another.getInitialLetter());
        }
    }
}
