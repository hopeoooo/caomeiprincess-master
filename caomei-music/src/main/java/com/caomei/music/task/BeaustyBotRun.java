package com.caomei.music.task;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author hope
 * @since 2022/3/18
 */
@Component
public class BeaustyBotRun implements ApplicationRunner {


    @Autowired
    private BeautyBot beautyBot;

    @Override
    @SneakyThrows
    public void run(ApplicationArguments args) throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(beautyBot);
    }

}
