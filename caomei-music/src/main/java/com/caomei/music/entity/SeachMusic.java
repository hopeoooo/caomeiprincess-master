package com.caomei.music.entity;
import java.util.List;

public class SeachMusic {

    private String id;
    private String name;
    private List artist;
    private String album;//专辑
    private String pic_id;
    private String url_id;
    private String lyric_id;
    private String source;


    public SeachMusic(String id, String name, List artist, String album, String pic_id, String url_id, String lyric_id, String source) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.pic_id = pic_id;
        this.url_id = url_id;
        this.lyric_id = lyric_id;
        this.source = source;
    }

    public SeachMusic() {
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setArtist(List artist) {
        this.artist = artist;
    }
    public List getArtist() {
        return artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
    public String getAlbum() {
        return album;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }
    public String getPic_id() {
        return pic_id;
    }

    public void setUrl_id(String url_id) {
        this.url_id = url_id;
    }
    public String getUrl_id() {
        return url_id;
    }

    public void setLyric_id(String lyric_id) {
        this.lyric_id = lyric_id;
    }
    public String getLyric_id() {
        return lyric_id;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }

}