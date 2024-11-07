package Maps;

import java.util.ArrayList;

import Enemies.BugEnemy;
import Enemies.Judy;
import Enemies.MiniBoss;
import Enemies.DemonEnemy;
import Enemies.DinosaurEnemy;
import Enemies.FishEnemy;
import Enemies.GuardianEnemy;
import Enemies.PhantomEnemy;
import Enemies.Rose;
import Enemies.Spirit;
import Enemies.VolcanoEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.Collectible;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HealingCollectible;
import EnhancedMapTiles.HorizontalMovingPlatform;
import EnhancedMapTiles.PortalTile;
import EnhancedMapTiles.PuzzlePlatform;
import EnhancedMapTiles.PuzzleTile;
import EnhancedMapTiles.VerticalMovingPlatform;
import EnhancedMapTiles.TrailCollectible;
import GameObject.Rectangle;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import Level.TileType;
import NPCs.Fproof;
import NPCs.Grave;
import NPCs.Sword;
import NPCs.FireballMaster;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;



// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(0, 6).getLocation();
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        // Enemy spawns
        Rose roseEnemy = new Rose(getMapTile(1187, 11).getLocation().addY(2)); 
        enemies.add(roseEnemy);

        VolcanoEnemy volcanoEnemy = new VolcanoEnemy(getMapTile(727, 8).getLocation().addY(2)); 
        enemies.add(volcanoEnemy);

        VolcanoEnemy volcanoEnemy1 = new VolcanoEnemy(getMapTile(732, 8).getLocation().addY(2)); 
        enemies.add(volcanoEnemy1);

        Spirit spirit = new Spirit(getMapTile(737, 8).getLocation().addY(2)); 
        enemies.add(spirit);

        FishEnemy fishEnemy = new FishEnemy(getMapTile(1152, 12).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(fishEnemy);

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

        Judy judy = new Judy(getMapTile(1130, 6).getLocation().addY(-2), getMapTile(1127, 6).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(judy);

        MiniBoss mb1 = new MiniBoss(getMapTile(393, 8).getLocation().addY(-2), getMapTile(396, 8).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(mb1);



        DinosaurEnemy dinosaurEnemy2 = new DinosaurEnemy(getMapTile(63, 10).getLocation().addY(2), getMapTile(65, 10).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy2);

        DinosaurEnemy dinosaurEnemy3 = new DinosaurEnemy(getMapTile(81, 6).getLocation().addY(2), getMapTile(82, 6).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy3);

        GuardianEnemy GuardianEnemy = new GuardianEnemy(getMapTile(471, 6).getLocation().addY(0), getMapTile(473, 6).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy);

        GuardianEnemy GuardianEnemy2 = new GuardianEnemy(getMapTile(528, 10).getLocation().addY(0), getMapTile(530, 10).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy2);

        GuardianEnemy GuardianEnemy3 = new GuardianEnemy(getMapTile(526, 6).getLocation().addY(0), getMapTile(531, 6).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy3);


        GuardianEnemy GuardianEnemy4 = new GuardianEnemy(getMapTile(568, 9).getLocation().addY(0), getMapTile(571, 9).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy4);

        GuardianEnemy GuardianEnemy5 = new GuardianEnemy(getMapTile(587, 10).getLocation().addY(0), getMapTile(590, 10).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy5);

        GuardianEnemy GuardianEnemy6 = new GuardianEnemy(getMapTile(603, 11).getLocation().addY(0), getMapTile(610, 11).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy6);

        GuardianEnemy GuardianEnemy7 = new GuardianEnemy(getMapTile(646, 5).getLocation().addY(0), getMapTile(648, 5).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy7);

        
        

        

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

    // Vertical platform
    VerticalMovingPlatform vmp = new VerticalMovingPlatform(
    ImageLoader.load("GreenPlatform.png"),
    getMapTile(10, 5).getLocation(),  // Start location
    getMapTile(10, 10).getLocation(), // End location
    TileType.JUMP_THROUGH_PLATFORM,
    3,
    new Rectangle(0, 6, 16, 5),
    Direction.UP  // Starting direction
);
enhancedMapTiles.add(vmp);

 // Add a collectible at a specific location (e.g., tile (10, 6)).
        Collectible collectible = new Collectible(getMapTile(3, 6).getLocation());
        enhancedMapTiles.add(collectible);

        TrailCollectible speedCollectible = new TrailCollectible(getMapTile(6, 6).getLocation());
        enhancedMapTiles.add(speedCollectible);

        HealingCollectible HealthCollectible = new HealingCollectible(getMapTile(10, 6).getLocation());
        enhancedMapTiles.add(HealthCollectible);

  
        // Portal
    Point portalLocation = getMapTile(171, 12).getLocation(); // Location for the portal tile
    PortalTile portalTile = new PortalTile(portalLocation);

    Point teleportDestination = getMapTile(693, 12).getLocation(); // Destination for the teleport
    portalTile.setTeleportDestination(teleportDestination);

    enhancedMapTiles.add(portalTile);

// Portal 2
    Point portalLocation1 = getMapTile(9, 9).getLocation(); // Location for the portal tile
    PortalTile portalTile1 = new PortalTile(portalLocation1);

    Point teleportDestination1 = getMapTile(939, 9).getLocation(); // Destination for the teleport
    portalTile1.setTeleportDestination(teleportDestination1);

    enhancedMapTiles.add(portalTile1);

    //Portal 3
    Point portalLocation2 = getMapTile(100, 2).getLocation(); // Location for the portal tile
    PortalTile portalTile2 = new PortalTile(portalLocation2);

    Point teleportDestination2 = getMapTile(1060, 6).getLocation(); // Destination for the teleport
    portalTile2.setTeleportDestination(teleportDestination2);

    enhancedMapTiles.add(portalTile2);

    Point portalLocation3 = getMapTile(1245, 8).getLocation(); // Location for the portal tile
    PortalTile portalTile3 = new PortalTile(portalLocation3);

    Point teleportDestination3 = getMapTile(99, 8).getLocation(); // Destination for the teleport
    portalTile3.setTeleportDestination(teleportDestination3);

    enhancedMapTiles.add(portalTile3);


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

PuzzlePlatform pp2 = new PuzzlePlatform(
    ImageLoader.load("PuzzlePlatform.png"),
    getMapTile(661, 6).getLocation(),
    getMapTile(658, 6).getLocation(),
    TileType.JUMP_THROUGH_PLATFORM,
    3,
    new Rectangle(0, 6, 16, 4),
    Direction.LEFT
);
enhancedMapTiles.add(pp2);

PuzzleTile pt2 = new PuzzleTile(getMapTile(648, 1).getLocation());
pt2.setPuzzlePlatform(pp2); // Link the puzzle platform to the tile
enhancedMapTiles.add(pt2);

//end level
        EndLevelBox endLevelBox = new EndLevelBox(getMapTile(750, 10).getLocation());
        enhancedMapTiles.add(endLevelBox);

        return enhancedMapTiles;
    }


    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Walrus walrus = new Walrus(getMapTile(30, 10).getLocation().subtractY(13));
        npcs.add(walrus);

        Fproof fproof = new Fproof(getMapTile(103, 9).getLocation().subtractY(13));
        npcs.add(fproof);

        Grave grave = new Grave(getMapTile(46, 12).getLocation().subtractY(13));
        npcs.add(grave);

        Sword sword = new Sword(getMapTile(119, 9).getLocation().subtractY(13));
        npcs.add(sword);

        FireballMaster FBM = new FireballMaster(getMapTile(1120, 5).getLocation().subtractY(13));
        npcs.add(FBM);

        return npcs;
    }
}


// Add coords for platform puzzle, box 648,1 plat 661,6 to 658,6