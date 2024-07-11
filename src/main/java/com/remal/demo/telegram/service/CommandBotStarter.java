/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  July 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Telegram Bot starter.
 */
package com.remal.demo.telegram.service;

import com.remal.demo.telegram.bot.CommandBot;
import com.remal.demo.telegram.bot.model.BotProfile;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class CommandBotStarter {

    private final ApplicationContext applicationContext;
    private final BotProfile profile;

    public CommandBotStarter(ApplicationContext applicationContext, BotProfile profile) {
        this.applicationContext = applicationContext;
        this.profile = profile;
    }

    @PostConstruct
    public void init() throws TelegramApiException {
        log.info("starting the telegram bot: {token: \"{}\", name: \"{}\"}...", profile.getBotToken(), profile.getBotName());
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        CommandBot bot = new CommandBot(applicationContext, profile);
        botsApi.registerBot(bot);
    }
}
