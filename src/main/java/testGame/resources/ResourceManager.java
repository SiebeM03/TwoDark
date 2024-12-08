package testGame.resources;

import java.util.ArrayList;
import java.util.List;

public class ResourceManager {
    private static List<Resource> resources = new ArrayList<>();

    public static void addResource(Resource resource) {
        resources.add(resource);
        System.out.println(resource.getClass().getSimpleName() + " has been added to the resource manager");
    }

    public static List<Resource> getResources() {
        return resources;
    }

    public static Resource getResource(Class<? extends Resource> resource) {
        for (Resource r : resources) {
            if (r.getClass().equals(resource)) {
                return r;
            }
        }
        return null;
    }
}
