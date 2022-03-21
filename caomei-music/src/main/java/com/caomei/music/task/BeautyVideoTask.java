package com.caomei.music.task;

import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;

public class BeautyVideoTask {

    public static void main(String[] args) throws Exception {
        sendPhoto("D:\\Backup\\Downloads\\video\\6835afc2388545e3b6e9807e49c854b6.mp4","-539999991");
    }

    public static void sendPhoto(String filePath, String chatId) throws Exception {
        BeautyBot beautyBot = new BeautyBot();
        SendVideo sendVideo = new SendVideo();
        InputFile inputFile = new InputFile(new File(filePath));
        sendVideo.setVideo(inputFile);
        sendVideo.setChatId(chatId);
        beautyBot.execute(sendVideo);
    }

}
