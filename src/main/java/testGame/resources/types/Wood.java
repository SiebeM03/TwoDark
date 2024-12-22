package testGame.resources.types;

import engine.ecs.components.SpriteRenderer;
import testGame.resources.Resource;

public class Wood extends Resource {
    public Wood() {
        setName("Wood");
        setAmount(0);
        this.texturePath = "src/assets/images/seperateImages/tree2.png";
        this.clickDelay = 1.0f;
    }
}
