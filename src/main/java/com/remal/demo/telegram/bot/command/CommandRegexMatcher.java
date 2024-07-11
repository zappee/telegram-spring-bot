/*
 *  Copyright (c) 2020-2024 Remal Software and Arnold Somogyi All rights reserved
 *
 *  Since:  July 2024
 *  Author: Arnold Somogyi <arnold.somogyi@gmail.com>
 *
 *  Description:
 *     Command matcher that checks whether the incoming Telegram message is a
 *     valid command or not.
 */
package com.remal.demo.telegram.bot.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandRegexMatcher {

    public static boolean isMatch(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}
