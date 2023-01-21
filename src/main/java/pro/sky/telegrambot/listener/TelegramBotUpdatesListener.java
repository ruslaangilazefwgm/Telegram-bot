package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final NotificationService notificationService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
        this.telegramBot.setUpdatesListener(this);
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream()
                .filter(update -> update.message() != null)
                .filter(update -> update.message().text() != null)
                .forEach(this::processsUpdate);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processsUpdate(Update update) {
        String userMessage = update.message().text();
        Long chatId = update.message().chat().id();
        if (userMessage.equals("/start")) {
            this.telegramBot.execute(new SendMessage(chatId, "Привет! Я могу напомнить тебе о каком-либо событии"));
        } else {
            if (this.notificationService.processNotification(chatId, userMessage)) {
                this.telegramBot.execute(new SendMessage(chatId, "Напоминалка создана!"));
            } else {
                this.telegramBot.execute(new SendMessage(chatId, "Я принимаю сообщения в формате '01.01.2022 20:00 Сделать домашнюю работу'"));
            }
        }
    }
}
