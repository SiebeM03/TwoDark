package engine.ecs.serialization;

import com.google.gson.*;
import game.GameManager;
import game.resources.ResourceManager;

import java.lang.reflect.Type;

public class GameManagerSerializer implements JsonSerializer<GameManager>, JsonDeserializer<GameManager> {
    @Override
    public GameManager deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = new JsonObject();
        return context.deserialize(jsonObject, GameManager.class);
    }

    @Override
    public JsonElement serialize(GameManager src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("resourceManager", context.serialize(src.getResourceManager()));
        return result;
    }
}
