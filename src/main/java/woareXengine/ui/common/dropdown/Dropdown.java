package woareXengine.ui.common.dropdown;

import woareXengine.io.userInputs.MouseButton;
import woareXengine.ui.common.Button;
import woareXengine.ui.components.ClickableUi;
import woareXengine.ui.components.EventData;
import woareXengine.ui.components.UiBlock;
import woareXengine.util.Assets;
import woareXengine.util.Color;


public class Dropdown extends Button {
    private Option selected;
    private Option[] options;
    private UiBlock optionsBlock;

    public Dropdown(Option... options) {
        super(200, 40, Color.WHITE, new Color(0.8f, 0.8f, 0.8f), Assets.getFont("src/assets/fonts/rounded.fnt").createText(options[0].text, 0.8f));
        this.options = options;
        this.selected = options[0];
    }

    @Override
    protected void init() {
        optionsBlock = new UiBlock();
        add(optionsBlock);
        optionsBlock.setTransform(0);
        optionsBlock.transform.setHeight(options.length * 40);
        optionsBlock.transform.setY(-optionsBlock.transform.getHeight());

        for (int i = 0; i < options.length; i++) {
            Button button = new Button(160, 40, Color.WHITE, new Color(0.8f, 0.8f, 0.8f), Assets.getFont("src/assets/fonts/rounded.fnt").createText(options[i].text, 0.8f));
            optionsBlock.add(button);
            button.setTransform(0);
            button.transform.setWidth(160);
            button.transform.setHeight(40);
            button.transform.setX(0);
            button.transform.setY(optionsBlock.transform.getHeight() - 40 * (i + 1));
            int finalI = i;
            button.addMouseListener(data -> {
                if (data.isClick(MouseButton.LEFT)) {
                    setSelected(finalI);
                    optionsBlock.show(false);
                }
            });
            button.addMouseListener(options[i].listener);
        }

        optionsBlock.show(false);

        addMouseListener(data -> {
            if (data.isClick(MouseButton.LEFT)) {
                optionsBlock.show(true);
            }
        });
    }

    public void setSelected(int index) {
        selected = options[index];
    }

    @Override
    public void updateSelf() {
        super.updateSelf();
        this.text.textString = selected.text;
    }
}
