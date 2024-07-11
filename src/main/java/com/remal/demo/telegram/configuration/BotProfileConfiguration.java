/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  February 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Telegram Bot configuration, used by the Spring IoC container when initialize
 *     the CommandBot.
 */
package com.remal.demo.telegram.configuration;

import com.remal.demo.telegram.bot.model.BotProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotProfileConfiguration {

    @Value("${telegram.botToken}")
    private String botToken;

    @Value("${telegram.botName}")
    private String botName;

    @Bean
    public BotProfile botProfile () {
        return BotProfile.builder().botToken(botToken).botName(botName).build();
    }
}
