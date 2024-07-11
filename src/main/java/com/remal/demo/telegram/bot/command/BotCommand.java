/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  July 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Commands and its handlers that telegram bot can accept.
 */
package com.remal.demo.telegram.bot.command;

import com.remal.demo.telegram.bot.runner.handler.HelpChuckCommandRunner;
import com.remal.demo.telegram.bot.runner.handler.StartCommandRunner;
import com.remal.demo.telegram.bot.runner.handler.HelpCommandRunner;
import lombok.Getter;

@Getter
public enum BotCommand {
    WELCOME("/start", StartCommandRunner.class.getSimpleName(), "user first time visit"),
    HELP("^\\s*help$\\s*", HelpCommandRunner.class.getSimpleName(), "hello"),
    HELP_PROFILE("^\\s*help\\s*chuck$\\s*", HelpChuckCommandRunner.class.getSimpleName(), "hello");

    private final String regex;
    private final String runnerClass;
    private final String description;

    BotCommand(String regex, String runnerClass, String description) {
        this.regex = regex;
        this.runnerClass = runnerClass;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name() + ": "
                + "{"
                + "regex: \"" + regex + "\""
                + "}";
    }
}
