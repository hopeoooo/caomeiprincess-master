package com.caomei.music.enums;


public enum TypesEnum {

    查询("search"),//搜索结果
    歌曲详情("url"),//完善获取音乐信息
    播放列表("playlist"),
    用户歌单("userlist"),
    歌词("lyric"),
    音乐封面("pic");

    private String type;

    TypesEnum(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }



}
