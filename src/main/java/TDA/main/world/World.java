package TDA.main.world;


import TDA.main.world.tiles.Tile;
import TDA.main.world.tiles.types.Grass;
import TDA.main.world.tiles.types.Path;
import TDA.main.world.tiles.types.Water;

import static TDA.main.world.WorldConfigs.MAP_HEIGHT;
import static TDA.main.world.WorldConfigs.MAP_WIDTH;

public class World {
    private WorldGenerator generator;
    public Tile[][] tiles;

    public World() {
        generator = new WorldGenerator();
        generator.generatePixelBuffer();

        tiles = new Tile[MAP_HEIGHT][MAP_WIDTH];
    }

    public void init() {
        generateTiles();

        giveWaterTilesCollision();
    }

    private void generateTiles() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                int pixel = generator.readPixel(x, y);

                switch (pixel) {
                    case 0 -> tiles[y][x] = new Water(x, y);
                    case 1 -> tiles[y][x] = new Grass(x, y);
                    case 2 -> tiles[y][x] = new Path(x, y);
                }
            }
        }
    }

    private void giveWaterTilesCollision() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                Tile tile = tiles[y][x];
                assert tile != null;
                if (tile.getClass().isAssignableFrom(Water.class)) {
                    Water water = (Water) tile;
                    water.createCollisionBox();
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        if (!isInBounds(x, y)) return null;
        return tiles[y][x];
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < MAP_WIDTH && y >= 0 && y < MAP_HEIGHT;
    }
}
