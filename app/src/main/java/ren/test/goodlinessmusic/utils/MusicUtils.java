package ren.test.goodlinessmusic.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
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
import ren.test.goodlinessmusic.beans.Music;

/**
 * Created by Administrator on 2017/06/27
 */

public class MusicUtils {
    private static List<Music> allMusics;
    private static List<List<Music>> artistMusics;
//    private static List<Music> allMusics;
    private static Map<String, String> singerMap;

    public static List<Music> queryMusics(Cursor cursor){
        if (cursor==null)
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

            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小

            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径

            String parentPath = url.substring(0, url.lastIndexOf(File.separator));//文件父目录

            String album = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片图片

            long album_id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID
            Music music = new Music(id, title, artist, duration, size, url, album, null, null, album_id, isMusic, null, parentPath, null, false, false);
            setContent(music);
            String initialLetter = Cn2Spell.getPinYinFirstLetter(music.getTittle());//调用工具类设置首字母
//            Log.d("rq", "parentPath " + parentPath);
            music.setInitialLetter(initialLetter);
            musics.add(music);
        }
        cursor.close();
        Collections.sort(musics);//根据首字母排序
//        insertToSql();
        return musics;

    }

    public static void loadAllMusic(Context context) {
        queryFromSql(); //从本地数据库查询
        if (!allMusics.isEmpty())
            return;
//        queryFromSqlBySinger();//根据歌手进行查询分类
        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor = contentResolver.query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, "1=1 )"+" group by "+"( "+"artist", null,null);
        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
        allMusics=queryMusics(cursor);
        insertToSql();
//        allMusics = new ArrayList<>();
//        while (cursor != null && cursor.moveToNext()) {
//            long duration = cursor.getLong(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
//
//            int isMusic = cursor.getInt(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐
//
//            if (isMusic == 0 || duration <= 150 * 1000)
//                continue;
//
//            long id = cursor.getLong(cursor
//                    .getColumnIndex(MediaStore.Audio.Media._ID));//音乐id
//
//            String title = cursor.getString((cursor
//                    .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题
//
//            String artist = cursor.getString(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
//
////            singerMap.put(artist, artist);//将歌手添加到map方便查询
//
//            long size = cursor.getLong(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小
//
//            String url = cursor.getString(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径
//
//            String parentPath = url.substring(0, url.lastIndexOf(File.separator));//文件父目录
//
//            String album = cursor.getString(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片图片
//
//            long album_id = cursor.getLong(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID
//            Music music = new Music(id, title, artist, duration, size, url, album, null, null, album_id, isMusic, null, parentPath, null, false, false);
//            setContent(music);
//            String initialLetter = Cn2Spell.getPinYinFirstLetter(music.getTittle());//调用工具类设置首字母
////            Log.d("rq", "parentPath " + parentPath);
//            music.setInitialLetter(initialLetter);
//            allMusics.add(music);
//        }
//        if (cursor != null)
//            cursor.close();
//        Collections.sort(allMusics);//根据首字母排序
    }


    public static List<Music> getAllMusics(){
        return allMusics;
    }

    public static void loadMusicsByArtist(Context context){
        Realm realm = RealmUtils.getDefaultRealm();
        RealmQuery<Music> query = realm.where(Music.class);
        RealmResults<Music>result=query.distinct("artist").sort("artist");
        List<Music>list=realm.copyFromRealm(result);
        realm.close();
        realm= RealmUtils.getDefaultRealm();
        query = realm.where(Music.class);
        artistMusics=new ArrayList<>();
        for (Music music : list) {
            String artist=music.getArtist();
            RealmResults<Music>musics=query.equalTo("artist"  ,"").or().equalTo("artist"  ,artist).findAll();
            List<Music>mmm=realm.copyFromRealm(musics);
            artistMusics.add(mmm);
        }
//        list.clear();
//        list=null;
        realm.close();
    }

    public static List<List<Music>> getArtistMusics(){
        return artistMusics;
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

    /**
     * 将查询的结果添加到本地数据库
     */
    private static void insertToSql() {
        Realm realm = RealmUtils.getDefaultRealm();
        realm.beginTransaction();
        realm.copyToRealm(allMusics);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 从本地数据库查询歌曲
     */
    private static void queryFromSql() {
        Realm realm = RealmUtils.getDefaultRealm();
        RealmQuery<Music> query = realm.where(Music.class);
        allMusics = realm.copyFromRealm(query.findAll());
//        singerMap = new HashMap<>();
//        for (Music allMusic : allMusics) {
//            singerMap.put(allMusic.getArtist(), allMusic.getArtist());
//        }
        realm.close();
    }

    /**
     * 根据歌手进行分类查询
     */
    public static List<List<Music>> queryFromSqlBySinger() {
        if (singerMap == null || singerMap.isEmpty())
            return null;
        Iterator<Map.Entry<String, String>> it = singerMap.entrySet().iterator();
        List<List<Music>> singerMusics = new ArrayList<>();
        while (it.hasNext()) {
            String artist = it.next().getValue();
            singerMusics.add(RealmUtils.query("artist", artist, "artist"));
        }
        return singerMusics;
    }
}
