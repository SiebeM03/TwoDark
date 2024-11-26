package engine.ecs.serialization;

import com.google.gson.*;
import game.resources.Resource;

import java.lang.reflect.Type;

public class ResourceSerializer implements JsonSerializer<Resource>, JsonDeserializer<Resource> {

    @Override
    public Resource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        try {
            return context.deserialize(json, Class.forName(type));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonElement serialize(Resource src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = context.serialize(src, src.getClass()).getAsJsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
        return result;
    }
}
