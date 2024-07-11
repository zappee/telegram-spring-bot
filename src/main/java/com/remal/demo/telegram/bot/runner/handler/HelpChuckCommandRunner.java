/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  July 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Telegram command executor.
 */
package com.remal.demo.telegram.bot.runner.handler;

import com.remal.demo.telegram.bot.CommandBot;
import com.remal.demo.telegram.bot.runner.TelegramBaseCommandRunner;
import com.remal.demo.telegram.bot.runner.TelegramCommandRunner;
import com.remal.demo.telegram.bot.command.BotCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class HelpChuckCommandRunner extends TelegramBaseCommandRunner implements TelegramCommandRunner {

    private final CommandBot bot;

    public HelpChuckCommandRunner(CommandBot bot) {
        this.bot = bot;
    }

    @Override
    public void run(BotCommand command, String incomingMessage, long userId) throws TelegramApiException {
        var pathToImage = "/media/chuck.jpg";
        bot.execute(buildPhotoMessage(userId, pathToImage, pathToImage));
        bot.execute(buildTextMessage(userId, "When Alexander Bell invented the telephone, he had 3 missed "
                + "calls from Chuck Norris."));
    }
}
