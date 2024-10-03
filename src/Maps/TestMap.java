package Maps;

import Enemies.DemonEnemy;
import Enemies.GuardianEnemy;
import Enemies.PhantomEnemy;
import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import EnhancedMapTiles.PuzzlePlatform;
import EnhancedMapTiles.PuzzleTile;
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
        this.playerStartPosition = getMapTile(541, 10).getLocation();
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        BugEnemy bugEnemy = new BugEnemy(getMapTile(16, 10).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy);

        PhantomEnemy PhantomEnemy = new PhantomEnemy(getMapTile(278, 4).getLocation().addY(0), getMapTile(278, 4).getLocation().addY(0), Direction.LEFT);
        enemies.add(PhantomEnemy);

        PhantomEnemy PhantomEnemy2 = new PhantomEnemy(getMapTile(316, 5).getLocation().addY(2), getMapTile(320, 5).getLocation().addY(2), Direction.LEFT);
        enemies.add(PhantomEnemy2);

        BugEnemy bugEnemy1 = new BugEnemy(getMapTile(40, 12).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy1);

        BugEnemy bugEnemy2 = new BugEnemy(getMapTile(56, 8).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy2);

        DemonEnemy DemonEnemy = new DemonEnemy(getMapTile(260, 6).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy);

        DemonEnemy DemonEnemy1 = new DemonEnemy(getMapTile(267, 8).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy1);

        DinosaurEnemy dinosaurEnemy = new DinosaurEnemy(getMapTile(19, 1).getLocation().addY(2), getMapTile(22, 1).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy);

        DinosaurEnemy dinosaurEnemy2 = new DinosaurEnemy(getMapTile(63, 10).getLocation().addY(2), getMapTile(65, 10).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy2);

        DinosaurEnemy dinosaurEnemy3 = new DinosaurEnemy(getMapTile(81, 6).getLocation().addY(2), getMapTile(82, 6).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy3);

        GuardianEnemy GuardianEnemy = new GuardianEnemy(getMapTile(471, 6).getLocation().addY(0), getMapTile(473, 6).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy);

        GuardianEnemy GuardianEnemy2 = new GuardianEnemy(getMapTile(528, 10).getLocation().addY(0), getMapTile(530, 10).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy2);

        GuardianEnemy GuardianEnemy3 = new GuardianEnemy(getMapTile(528, 6).getLocation().addY(6), getMapTile(526, 2).getLocation().addY(2), Direction.LEFT);
        enemies.add(GuardianEnemy3);

       



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


        // First set of PuzzlePlatform and PuzzleTile
PuzzlePlatform pp = new PuzzlePlatform(
    ImageLoader.load("PuzzlePlatform.png"),
    getMapTile(553, 9).getLocation(),
    getMapTile(547, 9).getLocation(),
    TileType.JUMP_THROUGH_PLATFORM,
    3,
    new Rectangle(0, 6, 16, 4),
    Direction.RIGHT
);
enhancedMapTiles.add(pp);

PuzzleTile pt = new PuzzleTile(getMapTile(544, 7).getLocation());
pt.setPuzzlePlatform(pp); // Link the puzzle platform to the tile
enhancedMapTiles.add(pt);

// Second set of PuzzlePlatform and PuzzleTile
PuzzlePlatform pp1 = new PuzzlePlatform(
    ImageLoader.load("PuzzlePlatform.png"),
    getMapTile(555, 9).getLocation(),
    getMapTile(550, 9).getLocation(),
    TileType.JUMP_THROUGH_PLATFORM,
    3,
    new Rectangle(0, 6, 16, 4),
    Direction.RIGHT
);
enhancedMapTiles.add(pp1);

PuzzleTile pt1 = new PuzzleTile(getMapTile(546, 2).getLocation());
pt1.setPuzzlePlatform(pp1); // Link the puzzle platform to the tile
enhancedMapTiles.add(pt1);

       

//end level
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
