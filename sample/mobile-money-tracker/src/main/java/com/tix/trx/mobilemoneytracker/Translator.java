package com.tix.trx.mobilemoneytracker;

import com.google.gson.Gson;
import com.tix.trx.mobilemoneytracker.domain.Request;

public class Translator {

    private static final Gson GSON = new Gson();

    public Request apply(String msg) {
        return GSON.fromJson(msg, Request.class);
    }
}
