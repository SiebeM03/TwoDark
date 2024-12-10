package testGame.tools.types;

import testGame.resources.types.Stone;
import testGame.resources.types.Wood;
import testGame.tools.Tool;

public class Axe extends Tool {

    public Axe() {
        setName("Axe");
        addResourceIncrease(Stone.class, 3 * level());
        addResourceIncrease(Wood.class, 4 * level());

    }
}
