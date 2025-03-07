package TDA.scene.prefabs.battleScene;

import TDA.entities.dinos.Dino;
import TDA.rendering.TDARenderEngine.renderSystem.TDARenderSystem;
import TDA.scene.Scene;
import TDA.scene.systems.battle.BattleContext;
import woareXengine.util.Logger;

public class BattleScene extends Scene {
    private final BattleContext context;

    public BattleScene(BattleContext context) {
        super(new TDARenderSystem(), new BattleCamera());
        this.context = context;

        Logger.success("Battle Scene initialized");
    }

    @Override
    protected void createEntities() {
        Logger.info("Creating entities");
        for (Dino dino : context.orderedDinosList()) {
            addEntity(dino);
            System.out.print("\t");
            System.out.println("Added dino at " + dino.transform.getX() + ", " + dino.transform.getY());
        }
    }

    @Override
    protected void addSystems() {
        addSystem(context);
    }
}
