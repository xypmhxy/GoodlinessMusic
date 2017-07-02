package ren.test.goodlinessmusic.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ren.test.goodlinessmusic.beans.Music;

/**
 * Created by Administrator on 2017/06/27
 */

public class MusicUtils {
    private static List<Music> allMusics;

    public static List<Music> getAllMusics(Context context) {
        if (allMusics != null)
            return allMusics;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        allMusics = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
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
//            String initialLetter = Cn2Spell.getPinYinFirstLetter(music.getTittle());//调用工具类设置首字母
//            Log.d("rq", "parentPath " + parentPath);
            music.setInitialLetter("#");
            allMusics.add(music);
        }
        return allMusics;
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
}
