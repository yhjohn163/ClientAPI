package com.jadan.client.validation;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Messages {

    private static MessageSource messageSource;

    public Messages(MessageSource source) {
        messageSource = source;
    }

    public static String getMessage(String key, String[] args) {
        return messageSource != null
                ? messageSource.getMessage( key , args, new Locale("en"))
                : "";
    }
}
