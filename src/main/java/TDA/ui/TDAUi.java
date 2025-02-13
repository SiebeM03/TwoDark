package TDA.ui;

import woareXengine.ui.main.Ui;
import woareXengine.ui.text.basics.Font;
import woareXengine.util.Assets;

public class TDAUi {
    private static TDAUi instance;

    public GameUi gameUi;
    public Font font = Assets.getFont("src/assets/fonts/test.fnt");

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
