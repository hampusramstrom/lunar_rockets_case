package org.rockets.serializer;

import java.time.OffsetDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer implements ISerializer {

    @Override
    public String serialize(Object src) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdapter());
        Gson gson = gsonBuilder.create();
        return gson.toJson(src);
    }
}
