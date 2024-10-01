package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(2, 11).getLocation();
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        BugEnemy bugEnemy = new BugEnemy(getMapTile(16, 10).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy);

        BugEnemy bugEnemy1 = new BugEnemy(getMapTile(40, 12).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy1);

        BugEnemy bugEnemy2 = new BugEnemy(getMapTile(56, 8).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy2);

        BugEnemy bugEnemy3 = new BugEnemy(getMapTile(261, 11).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy3);

        BugEnemy bugEnemy4 = new BugEnemy(getMapTile(267, 8).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy4);

        DinosaurEnemy dinosaurEnemy = new DinosaurEnemy(getMapTile(19, 1).getLocation().addY(2), getMapTile(22, 1).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy);

        DinosaurEnemy dinosaurEnemy2 = new DinosaurEnemy(getMapTile(63, 10).getLocation().addY(2), getMapTile(65, 10).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy2);

        DinosaurEnemy dinosaurEnemy3 = new DinosaurEnemy(getMapTile(81, 6).getLocation().addY(2), getMapTile(82, 6).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy3);

        DinosaurEnemy dinosaurEnemy4 = new DinosaurEnemy(getMapTile(277, 7).getLocation().addY(2), getMapTile(279, 7).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy4);

        DinosaurEnemy dinosaurEnemy5 = new DinosaurEnemy(getMapTile(141, 11).getLocation().addY(2), getMapTile(138, 7).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy5);


        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        HorizontalMovingPlatform hmp = new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getMapTile(24, 6).getLocation(),
                getMapTile(27, 6).getLocation(),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        );
        enhancedMapTiles.add(hmp);

        HorizontalMovingPlatform hmp1 = new HorizontalMovingPlatform(
            ImageLoader.load("GreenPlatform.png"),
            getMapTile(154, 4).getLocation(),
            getMapTile(158, 4).getLocation(),
            TileType.JUMP_THROUGH_PLATFORM,
            3,
            new Rectangle(0, 6,16,4),
            Direction.RIGHT
    );
    enhancedMapTiles.add(hmp1);

    HorizontalMovingPlatform hmp2 = new HorizontalMovingPlatform(
        ImageLoader.load("GreenPlatform.png"),
        getMapTile(160, 4).getLocation(),
        getMapTile(166, 4).getLocation(),
        TileType.JUMP_THROUGH_PLATFORM,
        3,
        new Rectangle(0, 6,16,4),
        Direction.RIGHT
);
enhancedMapTiles.add(hmp2);


        EndLevelBox endLevelBox = new EndLevelBox(getMapTile(32, 7).getLocation());
        enhancedMapTiles.add(endLevelBox);

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Walrus walrus = new Walrus(getMapTile(30, 10).getLocation().subtractY(13));
        npcs.add(walrus);

        return npcs;
    }
}
