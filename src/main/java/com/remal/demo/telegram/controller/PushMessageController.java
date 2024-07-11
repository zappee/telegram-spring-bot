/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  February 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Telegram message pusher rest service.
 */
package com.remal.demo.telegram.controller;

import com.remal.demo.telegram.bot.CommandBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/api")
public class PushMessageController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
    private final CommandBot telegramBot;

    @Value("${telegram.myUserId}")
    private Long userId;

    public PushMessageController(CommandBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @GetMapping("push")
    public String push() throws TelegramApiException {
        var now = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        var message = String.format("[%s] Hello world.", now);
        log.debug("pushing a text message to telegram user: {userId: {}, message: \"{}\"}", userId, message);
        telegramBot.sendMessage(userId, message);
        telegramBot.sendPhoto(userId, "/media/hello-2.png");
        return message;
    }
}
