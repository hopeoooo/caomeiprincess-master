package com.caomei.music.controller;

import com.caomei.music.enums.TypesEnum;
import com.caomei.music.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MusicController {
    @RequestMapping(value = {"/music/{musicId}"})
    public String music(Model model, @PathVariable("musicId") String musicId) {
        model.addAttribute("musicId",musicId);
        return "music";
    }

    @RequestMapping(value = {"/musicList/{musicId}"})
    public String musicList(Model model,@PathVariable("musicId") String musicId) {
        model.addAttribute("musicId",musicId);
        return "musicList";
    }

    @Autowired
    SeachUtil seachUtil;
    @Autowired
    UrlUtil urlUtil;
    @Autowired
    LyricUtil lyricUtil;
    @Autowired
    PicUtil picUtil;
    @Autowired
    UserListUtil userListUtil;
    @Autowired
    PlayListUtil playListUtil;
    /**
     * 调用接口 返回数据
     */
    @RequestMapping(value = {"/musicData"},method = RequestMethod.POST)
    @ResponseBody
    public String musicData(String callback,String types,String count,String source,String pages,String name,String id,String uid) {
        if(TypesEnum.查询.getType().equals(types)){

            String json = seachUtil.seachMusics(source,count,pages,name);
            return callback+"("+json+")";

        }else if (TypesEnum.歌曲详情.getType().equals(types)){

            String json = urlUtil.getMusic(source,id);
            return callback+"("+json+")";

        }else if (TypesEnum.歌词.getType().equals(types)){

            String json = lyricUtil.getLyric(source,id);
            return callback+"("+json+")";

        }else if (TypesEnum.音乐封面.getType().equals(types)){

            String json = picUtil.getPic(source,id);
            return callback+"("+json+")";

        }else if (TypesEnum.用户歌单.getType().equals(types)){

            String json = userListUtil.getUserList(uid);
            return callback+"("+json+")";

        }else if (TypesEnum.播放列表.getType().equals(types)){

            String json = playListUtil.getPlayList(id);
            return callback+"("+json+")";

        }

        return null;
    }

}
