package old.engine.ecs.serialization;

import com.google.gson.*;
import old.engine.ecs.Component;
import old.engine.ecs.GameObject;
import old.engine.ecs.Transform;

import java.lang.reflect.Type;

public class GameObjectSerializer implements JsonDeserializer<GameObject> {
    @Override
    public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        JsonArray components = jsonObject.getAsJsonArray("components");
        Transform transform = context.deserialize(jsonObject.get("transform"), Transform.class);
        int zIndex = context.deserialize(jsonObject.get("zIndex"), int.class);

        GameObject go = new GameObject(name, transform, zIndex);
        for (JsonElement e : components) {
            Component c = context.deserialize(e, Component.class);
            go.addComponent(c);
        }

        // If the GameObject has a MouseEventConsumer, reset the SpriteRenderer's color. This prevents GameObjects from having the hover color right after deserialization
//        MouseEventConsumer mouseEventConsumer = go.getComponent(MouseEventConsumer.class);
//        SpriteRenderer spriteRenderer = go.getComponent(SpriteRenderer.class);
//        if (mouseEventConsumer != null && spriteRenderer != null) {
//            spriteRenderer.setColor(mouseEventConsumer.defaultColor);
//        }
        return go;
    }
}
