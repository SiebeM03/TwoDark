package old.testGame.tools.types;

import old.testGame.resources.types.Metal;
import old.testGame.resources.types.Stone;
import old.testGame.tools.Tool;

public class Pickaxe extends Tool {
    public Pickaxe() {
        setName("Pickaxe");
        addResourceIncrease(Stone.class, 2 * level());
        addResourceIncrease(Metal.class, 5 * level());
    }
}
