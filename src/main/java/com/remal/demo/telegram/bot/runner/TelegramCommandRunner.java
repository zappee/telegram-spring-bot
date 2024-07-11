/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  July 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Telegram command executor interface.
 */
package com.remal.demo.telegram.bot.runner;

import com.remal.demo.telegram.bot.command.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramCommandRunner {

    void run(BotCommand command, String incomingMessage, long userId) throws TelegramApiException;
}
