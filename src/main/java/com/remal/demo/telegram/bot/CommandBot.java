/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  July 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Telegram Bot.
 */
package com.remal.demo.telegram.bot;

import com.remal.demo.telegram.bot.model.BotProfile;
import com.remal.demo.telegram.bot.command.BotCommand;
import com.remal.demo.telegram.bot.command.CommandRegexMatcher;
import com.remal.demo.telegram.bot.runner.TelegramCommandRunner;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Setter
@Component
public class CommandBot extends TelegramLongPollingBot {

    private final String botName;
    private final ApplicationContext applicationContext;

    public CommandBot(ApplicationContext applicationContext, BotProfile profile) {
        super(profile.getBotToken());
        this.botName = profile.getBotName();
        this.applicationContext = applicationContext;
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var incomingMessage = Objects.isNull(message.getText()) ? "" : message.getText().toLowerCase();
        var user = message.getFrom();
        var userId = user.getId();
        log.debug("new incoming telegram message: {userId: {}, incomingMessage: \"{}\"}", userId, incomingMessage);

        Optional<BotCommand> command = Arrays.stream(BotCommand.values())
                .filter(x -> CommandRegexMatcher.isMatch(x.getRegex(), incomingMessage))
                .findFirst();

        if (command.isPresent()) {
            getAndRunCommandHandler(command.get(), incomingMessage, userId);
        } else {
            log.debug(String.format(
                    "an unknown command has been received: {userId: %o, incomingMessage: \"%s\"}",
                    userId,
                    incomingMessage));
        }
    }

    public void sendMessage(long userId, String message) throws TelegramApiException {
        execute(SendMessage.builder()
                .chatId(userId)
                .text(message)
                .build());
    }

    public void sendPhoto(long userId, String pathToImage) throws TelegramApiException {
        InputStream img = this.getClass().getResourceAsStream(pathToImage);
        execute(SendPhoto.builder()
                .chatId(userId)
                .photo(new InputFile(img, pathToImage))
                .build());
    }

    private void getAndRunCommandHandler(BotCommand command, String incomingMessage, long userId) {
        try {
            // converts the class name to Spring bean name, e.g.: StartCommandRunner -> startCommandRunner
            char[] chars = command.getRunnerClass().toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            String beanName = new String(chars);

            log.debug(String.format("getting a bean: {bean: \"%s\", userId: %o, message: \"%s\"}", beanName, userId, incomingMessage));
            TelegramCommandRunner runner = applicationContext.getBean(beanName, TelegramCommandRunner.class);

            log.debug(String.format("pushing response to user: {bean: \"%s\", userId: %o, message: \"%s\"}", beanName, userId, incomingMessage));
            runner.run(command, incomingMessage, userId);
        } catch (TelegramApiException e) {
            log.error("Error occurred while trying to push messages to user.", e);
        }
    }
}
