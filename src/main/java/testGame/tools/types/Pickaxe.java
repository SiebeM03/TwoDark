package testGame.tools.types;

import testGame.resources.types.Metal;
import testGame.resources.types.Stone;
import testGame.tools.Tool;

public class Pickaxe extends Tool {
    public Pickaxe() {
        setName("Pickaxe");
        addResourceIncrease(Stone.class, 2 * level());
        addResourceIncrease(Metal.class, 5 * level());
    }

    @Override
    public void onHover() {

    }
}
