package org.rockets.serializer;

public interface IDeserializer {
    // TODO: Add a deserialize checked exception
    <T> T deserialize(String str, Class<T> typeOfT);
}
