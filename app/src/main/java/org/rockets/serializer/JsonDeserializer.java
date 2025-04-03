package org.rockets.serializer;

import java.time.OffsetDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonDeserializer implements IDeserializer {


    @Override
    public <T> T deserialize(String str, Class<T> typeOfT) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(str, typeOfT);
    }
}
