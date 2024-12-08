package engine.ecs.serialization.dataStructures;


import java.util.ArrayList;
import java.util.List;

public record Data(List<ResourceData> resources, List<ToolData> tools) {
    public Data {
        resources = new ArrayList<>(resources);
        tools = new ArrayList<>(tools);
    }
}