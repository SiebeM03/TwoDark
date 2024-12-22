package testGame.resources.types;

import engine.ecs.components.SpriteRenderer;
import testGame.resources.Resource;

public class Stone extends Resource {
    public Stone() {
        setName("Stone");
        setAmount(0);
        this.texturePath = "src/assets/images/seperateImages/stone2.png";
        this.clickDelay = 2.0f;
    }
}
