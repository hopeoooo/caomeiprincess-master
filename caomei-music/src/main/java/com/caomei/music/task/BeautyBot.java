package com.caomei.music.task;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hope
 * @since 2022/3/18
 */
@Component
public class BeautyBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "beauty_img_bot";
    }

    @Override
    public String getBotToken() {
        return "5174325769:AAFUioqzSMlZV5sh_zV4bbNKCPLRd6DngMw";
    }

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            final Long chatId = message.getChatId();
            String content = message.getText();
            if ("/ghs".equals(content)) {
                cachedThreadPool.execute(new Runnable() {
                    public void run() {
                        BeautyTask.configureTasks(String.valueOf(chatId));
                    }
                });
            }
        }
    }
}
