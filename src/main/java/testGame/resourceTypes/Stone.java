package testGame.resourceTypes;

import testGame.Resource;

public class Stone extends Resource {
    public Stone() {
        setClickDelay(4.0f);
        setName("Stone");
        setAmount(0);
    }
}
