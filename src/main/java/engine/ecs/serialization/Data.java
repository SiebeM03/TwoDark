package engine.ecs.serialization;

import game.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public record Data(List<Resource> resources) {
    public Data {
        resources = new ArrayList<>(resources);
    }
}
