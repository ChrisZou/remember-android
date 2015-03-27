package com.chriszou.remember.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Chris on 3/27/15.
 */

public class ItemTypeAdapterFactory implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        return new TypeAdapter<T>() {
            public void write(JsonWriter out, T value) throws IOException { delegate.write(out, value);}

            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = gson.getAdapter(JsonElement.class).read(in);
                if (jsonElement.isJsonObject()) {
                    if (jsonElement.getAsJsonObject().has("data")) jsonElement = jsonElement.getAsJsonObject().get("data");
                }
                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }
}
