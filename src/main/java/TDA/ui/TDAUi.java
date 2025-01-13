package TDA.ui;

import woareXengine.ui.main.Ui;

public class TDAUi {
    private static TDAUi instance;

    public GameUi gameUi;

    private TDAUi() {
        this.gameUi = new GameUi();
        Ui.getContainer().add(gameUi);
    }


    public static TDAUi get() {
        if (instance == null) {
            instance = new TDAUi();
        }
        return instance;
    }
}
