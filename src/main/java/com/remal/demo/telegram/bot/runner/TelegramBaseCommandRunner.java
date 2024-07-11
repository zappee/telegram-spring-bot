/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  July 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Telegram command executor base functions.
 */
package com.remal.demo.telegram.bot.runner;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.InputStream;

@Slf4j
public abstract class TelegramBaseCommandRunner implements TelegramCommandRunner {

    protected SendMessage buildTextMessage(Long userId, String message) {
        return SendMessage.builder()
                    .chatId(userId)
                    .text(message)
                    .build();
    }

    protected SendPhoto buildPhotoMessage(Long userId, String mediaName, String pathToImage) {
        InputStream imgIStream = this.getClass().getResourceAsStream(pathToImage);
        return SendPhoto.builder()
                .chatId(userId)
                .photo(new InputFile(imgIStream, mediaName))
                .parseMode(ParseMode.HTML)
                .build();
    }
}
