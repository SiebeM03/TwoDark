package TDA.main.controls;

public class GameControls {
    public WindowControls windowControls;
    public DeveloperControls developerControls;
    public PlayerControls playerControls;

    public GameControls() {
        this.windowControls = new WindowControls();
        this.developerControls = new DeveloperControls();
        this.playerControls = new PlayerControls();
    }
}
