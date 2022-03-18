package com.caomei.music.task;

import com.caomei.music.util.HttpCilentUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class BeautyTask {

    private static String bashFile = "/data/Downloads/img/";

    private static String bashUrl = "https://mmzztt.com/photo/page/${1}/";
    private static int page = 4;

    public static void configureTasks(String chatId) {
        String url = bashUrl.replace("${1}", page + "");

        Document doc = Jsoup.parse(HttpCilentUtil.SendGet(url));

        Elements photoUrls = doc.select("div.uk-grid.uk-grid-small").select("ul.uk-grid").select("li");
        for (int i = 0; i < 24; i++) {
            try {
                String photoUrl = photoUrls.get(i).select("img").attr("data-srcset");
                String filePath = bashFile + photoUrl.split("/")[photoUrl.split("/").length - 2] + photoUrl.substring(photoUrl.lastIndexOf("."));
                if (savePhoto(photoUrl, filePath))
                    sendPhoto(filePath, chatId);
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        page++;
    }


    public static boolean savePhoto(String photoUrl, String filePath) throws Exception {

        URL url = new URL(photoUrl);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestProperty("User-Agent", "PostmanRuntime/7.29.0");
        conn.setRequestProperty("Host", "t.iimzt.com");

        File img = new File(filePath);
        if (img.exists()) {
            return false;
        }

        InputStream inputStream = conn.getInputStream();
        OutputStream out = new FileOutputStream(filePath);
        byte[] buff = new byte[1024];
        int i = -1;
        while ((i = inputStream.read(buff)) != -1) {
            out.write(buff, 0, i);
        }
        inputStream.close();
        out.close();
        return true;
    }

    public static void sendPhoto(String filePath, String chatId) throws Exception {
        BeautyBot beautyBot = new BeautyBot();
        SendPhoto sendPhoto = new SendPhoto();
        InputFile inputFile = new InputFile(new File(filePath));
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setChatId(chatId);
        beautyBot.execute(sendPhoto);
    }

}
