package TDA.main.controls;

public class GameControls {
    public WindowControls windowControls;
    public DeveloperControls developerControls;
    public PlayerControls playerControls;
    public InventoryControls inventoryControls;

    public GameControls() {
        this.windowControls = new WindowControls();
        this.developerControls = new DeveloperControls();
        this.playerControls = new PlayerControls();
        this.inventoryControls = new InventoryControls();
    }
}
