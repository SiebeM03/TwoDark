package engine.ecs.serialization;

import com.google.gson.*;
import engine.ecs.Component;
import testGame.resources.Resource;
import testGame.tools.Tool;

import java.lang.reflect.Type;

public class ComponentSerializer implements JsonSerializer<Component>, JsonDeserializer<Component> {
    @Override
    public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return context.deserialize(element, Class.forName(type));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + type, e);
        }
    }

    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));

        // Only serialize uid of Resource objects, the actual data will be stored in data.txt
        if (Resource.class.isAssignableFrom(src.getClass())) {
            JsonObject properties = new JsonObject();
            properties.add("uid", context.serialize(((Resource) src).getUid())); // Serialize only the UID
            result.add("properties", properties);
        } else if (Tool.class.isAssignableFrom(src.getClass())) {
            JsonObject properties = new JsonObject();
            properties.add("uid", context.serialize(((Tool) src).getUid())); // Serialize only the UID
            result.add("properties", properties);
        } else {
            result.add("properties", context.serialize(src, src.getClass()));
        }

        return result;
    }
}
