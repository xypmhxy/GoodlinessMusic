package ren.test.goodlinessmusic.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2017/07/05
 */

public class Album extends RealmObject implements Comparable<Album>{
    private String albumName;
    private String singer;
    private String headUrl;
    private String initialLetter;
    @PrimaryKey
    private long albumId;
    private int number;

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getInitialLetter() {
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(Album another) {
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
