package TDA.ui;

import woareXengine.ui.constraints.ConstraintUtils;
import woareXengine.ui.main.Ui;
import woareXengine.ui.text.basics.Font;
import woareXengine.util.Assets;

public class TDAUi {
    private static TDAUi instance;

    public GameUi gameUi;

    private TDAUi() {
        this.gameUi = new GameUi();
        Ui.getContainer().add(gameUi, ConstraintUtils.fill());
    }


    public static TDAUi get() {
        if (instance == null) {
            instance = new TDAUi();
        }
        return instance;
    }
}
