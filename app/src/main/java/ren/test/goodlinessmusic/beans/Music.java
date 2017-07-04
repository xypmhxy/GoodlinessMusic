package ren.test.goodlinessmusic.beans;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2017/4/1
 * 本地音乐实体类
 */
public class Music extends RealmObject implements Comparable<Music>, Serializable {
    private static final long serialVersionUID = 1L;
    @PrimaryKey
    private long id;
    private String tittle;
    private String artist;
    private long duration;
    private long size;
    private String url;
    private String albumImage;
    private String smallPic;
    private String bigPic;
    private long albumId;
    private int isMusci;
    private String initialLetter;
    private String parentPath;
    private String playTime;
    private boolean isrecentPlay;
    private boolean isFavorite;

    public Music(long id, String tittle, String artist, long duration, long size, String url,
                 String albumImage, String smallPic, String bigPic, long albumId, int isMusci,
                 String initialLetter, String parentPath, String playTime, boolean isrecentPlay,
                 boolean isFavorite) {
        this.id = id;
        this.tittle = tittle;
        this.artist = artist;
        this.duration = duration;
        this.size = size;
        this.url = url;
        this.albumImage = albumImage;
        this.smallPic = smallPic;
        this.bigPic = bigPic;
        this.albumId = albumId;
        this.isMusci = isMusci;
        this.initialLetter = initialLetter;
        this.parentPath = parentPath;
        this.playTime = playTime;
        this.isrecentPlay = isrecentPlay;
        this.isFavorite = isFavorite;
    }

    public Music() {
    }

    @Override
    public int compareTo(Music another) {
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

    public String getInitialLetter() {
        return this.initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }

    public int getIsMusci() {
        return this.isMusci;
    }

    public void setIsMusci(int isMusci) {
        this.isMusci = isMusci;
    }

    public long getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumImage() {
        return this.albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTittle() {
        return this.tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParentPath() {
        return this.parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getPlayTime() {
        return this.playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public boolean getIsrecentPlay() {
        return this.isrecentPlay;
    }

    public void setIsrecentPlay(boolean isrecentPlay) {
        this.isrecentPlay = isrecentPlay;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getSmallPic() {
        return this.smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getBigPic() {
        return this.bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }
}
