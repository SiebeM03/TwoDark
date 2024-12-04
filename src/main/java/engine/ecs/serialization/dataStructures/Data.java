package engine.ecs.serialization.dataStructures;


import java.util.ArrayList;
import java.util.List;

public record Data(List<ResourceData> resources) {
    public Data {
        resources = new ArrayList<>(resources);
    }
}