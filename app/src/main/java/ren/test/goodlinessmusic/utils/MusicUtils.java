package ren.test.goodlinessmusic.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import ren.test.goodlinessmusic.beans.Album;
import ren.test.goodlinessmusic.beans.Artist;
import ren.test.goodlinessmusic.beans.Music;

/**
 * Created by Administrator on 2017/06/27
 */

public class MusicUtils {
    private static List<Music> allMusics;
    private static List<Artist> artists;
    private static List<Album> albumList;

    public static Music getRecentMusic() {
        Realm realm = RealmUtils.getDefaultRealm();
        RealmQuery<Music> query = realm.where(Music.class);
        return query.equalTo("isrecentPlay", true).or().equalTo("isrecentPlay", true).findAllSorted("playTime", Sort.DESCENDING).first(null);
    }

    public static List<Music> queryMusics(Cursor cursor) {
        if (cursor == null)
            return null;
        List<Music> musics = new ArrayList<>();
        while (cursor.moveToNext()) {
            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长

            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐

            if (isMusic == 0 || duration <= 150 * 1000)
                continue;

            long id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));//音乐id

            String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题

            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家

            long artistId = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));//艺术家ID

            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小

            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径

            String parentPath = url.substring(0, url.lastIndexOf(File.separator));//文件父目录

            String album = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片图片

            long album_id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID
            Music music = new Music(id, title, artist, artistId, duration, size, url, album, null, null, album_id, isMusic, null, parentPath, null, false, false);
            setContent(music);
            String initialLetter = Cn2Spell.getPinYinFirstLetter(music.getTittle());//调用工具类设置首字母
//            Log.d("rq", "parentPath " + parentPath);
            music.setInitialLetter(initialLetter);
            musics.add(music);
            music.setBigPic(getArtworkFromFile(music.getId()) + "");
        }
        cursor.close();
        Collections.sort(musics);//根据首字母排序
        return musics;

    }

    /**
     * 判断显示的歌曲名和歌手
     */
    private static void setContent(Music music) {
        String tittle = music.getTittle();
        String artist = music.getArtist();
        if (tittle.contains("-")) {
            artist = tittle.split("-")[0].trim();
            tittle = tittle.split("-")[1].trim();
        }
        music.setTittle(tittle);
        music.setArtist(artist);
    }

    private static Uri getArtworkFromFile(long songid) {
        Bitmap bm = null;
        if (songid < 0) {
            return null;
        }
        try {
            Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
            return uri;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void loadAllMusic(Context context) {
        queryFromSql(); //从本地数据库查询
        if (!allMusics.isEmpty())
            return;
        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor = contentResolver.query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, "1=1 )"+" group by "+"( "+"artist", null,null);
        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        allMusics = queryMusics(cursor);
        insertToSql();
    }

    /**
     * 从本地数据库查询歌曲
     */
    private static void queryFromSql() {
        Realm realm = RealmUtils.getDefaultRealm();
        RealmQuery<Music> query = realm.where(Music.class);
        allMusics = realm.copyFromRealm(query.findAll());
        realm.close();
    }

    /**
     * 将查询的结果添加到本地数据库
     */
    private static void insertToSql() {
        Realm realm = RealmUtils.getDefaultRealm();
        realm.beginTransaction();
        realm.copyToRealm(allMusics);
        for (Music music : allMusics) {
            saveArtistOrAlbum(music, realm);
        }
        realm.commitTransaction();
        realm.close();
    }

    private static void saveArtistOrAlbum(Music music, Realm realm) {
        Artist artist = realm.where(Artist.class).equalTo("artist", music.getArtist()).or().equalTo("artist", music.getArtist()).findFirst();
        if (artist == null) {
            artist = new Artist();
            artist.setId(music.getArtistId());
        }
        artist.setArtist(music.getArtist());
        artist.setNumber(artist.getNumber() + 1);
        String initialLetter = Cn2Spell.getPinYinFirstLetter(artist.getArtist());//调用工具类设置首字母
        artist.setInitialLetter(initialLetter);
        artist.setHeadUrl("null");
        realm.insertOrUpdate(artist);

        Album album = realm.where(Album.class).equalTo("singer", music.getArtist()).or().equalTo("singer", music.getArtist()).findFirst();
        if (album == null) {
            album = new Album();
            album.setAlbumId(music.getAlbumId());
        }
        album.setSinger(music.getArtist());
        album.setNumber(album.getNumber() + 1);
        album.setAlbumName(music.getAlbumImage());
        String initialLetter_ = Cn2Spell.getPinYinFirstLetter(album.getAlbumName());//调用工具类设置首字母
        album.setInitialLetter(initialLetter_);
        String albumImg = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), album.getAlbumId()) + "";
        album.setHeadUrl(albumImg);
        realm.insertOrUpdate(album);
    }

    public static List<Music> getAllMusics() {
        return allMusics;
    }

    public static void loadMusicsByArtist() {
        Realm realm = RealmUtils.getDefaultRealm();
        RealmQuery<Artist> query = realm.where(Artist.class);
        artists = realm.copyFromRealm(query.findAll());
        Collections.sort(artists);
        realm.close();
    }

    public static List<Artist> getArtistMusics() {
        return artists;
    }

    public static void loadAlbum() {
        Realm realm = RealmUtils.getDefaultRealm();
        RealmQuery<Album> query = realm.where(Album.class);
        albumList = realm.copyFromRealm(query.findAll());
        Collections.sort(albumList);
        realm.close();
    }

    public static List<Album> getAlbumList() {
        return albumList;
    }
}
