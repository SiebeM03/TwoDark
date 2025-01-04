package old.testGame.resources.types;

import old.testGame.resources.Resource;

public class Metal extends Resource {
    public Metal() {
        setName("Metal");
        setAmount(0);
        this.texturePath = "src/assets/images/seperateImages/metal2.png";
        this.clickDelay = 2.0f;
    }
}
