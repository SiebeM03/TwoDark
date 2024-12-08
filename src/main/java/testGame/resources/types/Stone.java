package testGame.resources.types;

import testGame.resources.Resource;

public class Stone extends Resource {
    public Stone() {
        setClickDelay(2.0f);
        setName("Stone");
        setAmount(0);
    }
}
