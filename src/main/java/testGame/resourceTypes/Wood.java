package testGame.resourceTypes;

import testGame.Resource;

public class Wood extends Resource {
    public Wood() {
        setClickDelay(1.0f);
        setName("Wood");
        setAmount(0);
    }
}
