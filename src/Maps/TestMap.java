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
import EnhancedMapTiles.LavaPerk;
import EnhancedMapTiles.Door;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HealingCollectible;
import EnhancedMapTiles.HorizontalMovingPlatform;
import EnhancedMapTiles.KeyCollectible;
import EnhancedMapTiles.PortalTile;
import EnhancedMapTiles.PuzzlePlatform;
import EnhancedMapTiles.PuzzleTile;
import EnhancedMapTiles.VerticalMovingPlatform;
import EnhancedMapTiles.TrailCollectible;
import EnhancedMapTiles.KeyCollectible;
import EnhancedMapTiles.Door;
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
import NPCs.Shotgun;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;


// FOR MAP; All Y values must be moved +11 tiles!!! Example, 6 went to 17. 
// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(0, 17).getLocation();
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        // Enemy spawns
        Rose roseEnemy = new Rose(getMapTile(1187, 22).getLocation().addY(2)); 
        enemies.add(roseEnemy);

        VolcanoEnemy volcanoEnemy = new VolcanoEnemy(getMapTile(727, 19).getLocation().addY(2)); 
        enemies.add(volcanoEnemy);

        VolcanoEnemy volcanoEnemy1 = new VolcanoEnemy(getMapTile(732, 19).getLocation().addY(2)); 
        enemies.add(volcanoEnemy1);

        Spirit spirit = new Spirit(getMapTile(737, 19).getLocation().addY(2)); 
        enemies.add(spirit);

        FishEnemy fishEnemy = new FishEnemy(getMapTile(1152, 23).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(fishEnemy);

        BugEnemy bugEnemy = new BugEnemy(getMapTile(16, 21).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy);


        PhantomEnemy PhantomEnemy = new PhantomEnemy(getMapTile(278, 15).getLocation().addY(0), getMapTile(278, 15).getLocation().addY(0), Direction.LEFT);
        enemies.add(PhantomEnemy);

        PhantomEnemy PhantomEnemy2 = new PhantomEnemy(getMapTile(316, 16).getLocation().addY(2), getMapTile(320, 16).getLocation().addY(2), Direction.LEFT);
        enemies.add(PhantomEnemy2);

        BugEnemy bugEnemy1 = new BugEnemy(getMapTile(40, 23).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy1);

        BugEnemy bugEnemy2 = new BugEnemy(getMapTile(56, 19).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy2);

        DemonEnemy DemonEnemy = new DemonEnemy(getMapTile(260, 17).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy);

        DemonEnemy DemonEnemy1 = new DemonEnemy(getMapTile(267, 19).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy1);

        DinosaurEnemy dinosaurEnemy = new DinosaurEnemy(getMapTile(19, 12).getLocation().addY(2), getMapTile(22, 12).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy);

        Judy judy = new Judy(getMapTile(1130, 17).getLocation().addY(-2), getMapTile(1127, 17).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(judy);

        MiniBoss mb1 = new MiniBoss(getMapTile(393, 19).getLocation().addY(-2), getMapTile(396, 19).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(mb1);



        DinosaurEnemy dinosaurEnemy2 = new DinosaurEnemy(getMapTile(63, 21).getLocation().addY(2), getMapTile(65, 21).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy2);

        DinosaurEnemy dinosaurEnemy3 = new DinosaurEnemy(getMapTile(81, 17).getLocation().addY(2), getMapTile(82, 17).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy3);

        GuardianEnemy GuardianEnemy = new GuardianEnemy(getMapTile(471, 17).getLocation().addY(0), getMapTile(473, 17).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy);

        GuardianEnemy GuardianEnemy2 = new GuardianEnemy(getMapTile(528, 21).getLocation().addY(0), getMapTile(530, 21).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy2);

        GuardianEnemy GuardianEnemy3 = new GuardianEnemy(getMapTile(526, 17).getLocation().addY(0), getMapTile(531, 17).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy3);


        GuardianEnemy GuardianEnemy4 = new GuardianEnemy(getMapTile(568, 20).getLocation().addY(0), getMapTile(571, 20).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy4);

        GuardianEnemy GuardianEnemy5 = new GuardianEnemy(getMapTile(587, 21).getLocation().addY(0), getMapTile(590, 21).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy5);

        GuardianEnemy GuardianEnemy6 = new GuardianEnemy(getMapTile(603, 22).getLocation().addY(0), getMapTile(610, 22).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy6);

        GuardianEnemy GuardianEnemy7 = new GuardianEnemy(getMapTile(646, 16).getLocation().addY(0), getMapTile(648, 16).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy7);

        
        

        

        return enemies;
    }



    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        HorizontalMovingPlatform hmp = new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getMapTile(24, 17).getLocation(),
                getMapTile(27, 17).getLocation(),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        );
        enhancedMapTiles.add(hmp);

        HorizontalMovingPlatform hmp1 = new HorizontalMovingPlatform(
            ImageLoader.load("GreenPlatform.png"),
            getMapTile(154, 15).getLocation(),
            getMapTile(158, 15).getLocation(),
            TileType.JUMP_THROUGH_PLATFORM,
            3,
            new Rectangle(0, 6,16,4),
            Direction.RIGHT
    );
    enhancedMapTiles.add(hmp1);

    HorizontalMovingPlatform hmp2 = new HorizontalMovingPlatform(
        ImageLoader.load("GreenPlatform.png"),
        getMapTile(160, 15).getLocation(),
        getMapTile(166, 15).getLocation(),
        TileType.JUMP_THROUGH_PLATFORM,
        3,
        new Rectangle(0, 6,16,4),
        Direction.RIGHT
    );
    enhancedMapTiles.add(hmp2);

    // Vertical platform
    VerticalMovingPlatform vmp = new VerticalMovingPlatform(
    ImageLoader.load("GreenPlatform.png"),
    getMapTile(10, 16).getLocation(),  // Start location
    getMapTile(10, 21).getLocation(), // End location
    TileType.JUMP_THROUGH_PLATFORM,
    3,
    new Rectangle(0, 6, 16, 5),
    Direction.UP  // Starting direction
);
enhancedMapTiles.add(vmp);


 // Add a collectible at a specific location (e.g., tile (10, 6)).

        TrailCollectible speedCollectible = new TrailCollectible(getMapTile(6, 17).getLocation());
        enhancedMapTiles.add(speedCollectible);

        HealingCollectible HealthCollectible = new HealingCollectible(getMapTile(10, 17).getLocation());
        enhancedMapTiles.add(HealthCollectible);

        LavaPerk lavaPerkCollectible = new LavaPerk(getMapTile(103, 20).getLocation());
        enhancedMapTiles.add(lavaPerkCollectible);

  
        // Portal
    Point portalLocation = getMapTile(171, 23).getLocation(); // Location for the portal tile
    PortalTile portalTile = new PortalTile(portalLocation);

    Point teleportDestination = getMapTile(693, 23).getLocation(); // Destination for the teleport
    portalTile.setTeleportDestination(teleportDestination);

    enhancedMapTiles.add(portalTile);


    // DOOR

    KeyCollectible keyCollectible = new KeyCollectible(getMapTile(5, 13).getLocation());
enhancedMapTiles.add(keyCollectible);

// Create the Door tile and add it to the enhancedMapTiles
Door doorTile = new Door(getMapTile(10, 17).getLocation());
enhancedMapTiles.add(doorTile);
    


    //Portal 3
    Point portalLocation2 = getMapTile(100, 13).getLocation(); // Location for the portal tile
    PortalTile portalTile2 = new PortalTile(portalLocation2);

    Point teleportDestination2 = getMapTile(1060, 17).getLocation(); // Destination for the teleport
    portalTile2.setTeleportDestination(teleportDestination2);

    enhancedMapTiles.add(portalTile2);

    Point portalLocation3 = getMapTile(1245, 19).getLocation(); // Location for the portal tile
    PortalTile portalTile3 = new PortalTile(portalLocation3);

    Point teleportDestination3 = getMapTile(99, 19).getLocation(); // Destination for the teleport
    portalTile3.setTeleportDestination(teleportDestination3);

    enhancedMapTiles.add(portalTile3);


        // First set of PuzzlePlatform and PuzzleTile
PuzzlePlatform pp = new PuzzlePlatform(
    ImageLoader.load("PuzzlePlatform.png"),
    getMapTile(553, 20).getLocation(),
    getMapTile(547, 20).getLocation(),
    TileType.JUMP_THROUGH_PLATFORM,
    3,
    new Rectangle(0, 6, 16, 4),
    Direction.RIGHT
);
enhancedMapTiles.add(pp);

PuzzleTile pt = new PuzzleTile(getMapTile(544, 18).getLocation());
pt.setPuzzlePlatform(pp); // Link the puzzle platform to the tile
enhancedMapTiles.add(pt);

// Second set of PuzzlePlatform and PuzzleTile
PuzzlePlatform pp1 = new PuzzlePlatform(
    ImageLoader.load("PuzzlePlatform.png"),
    getMapTile(555, 20).getLocation(),
    getMapTile(550, 20).getLocation(),
    TileType.JUMP_THROUGH_PLATFORM,
    3,
    new Rectangle(0, 6, 16, 4),
    Direction.RIGHT
);
enhancedMapTiles.add(pp1);

PuzzleTile pt1 = new PuzzleTile(getMapTile(546, 13).getLocation());
pt1.setPuzzlePlatform(pp1); // Link the puzzle platform to the tile
enhancedMapTiles.add(pt1);

PuzzlePlatform pp2 = new PuzzlePlatform(
    ImageLoader.load("PuzzlePlatform.png"),
    getMapTile(661, 17).getLocation(),
    getMapTile(658, 17).getLocation(),
    TileType.JUMP_THROUGH_PLATFORM,
    3,
    new Rectangle(0, 6, 16, 4),
    Direction.LEFT
);
enhancedMapTiles.add(pp2);

PuzzleTile pt2 = new PuzzleTile(getMapTile(648, 12).getLocation());
pt2.setPuzzlePlatform(pp2); // Link the puzzle platform to the tile
enhancedMapTiles.add(pt2);

//end level
        EndLevelBox endLevelBox = new EndLevelBox(getMapTile(750, 21).getLocation());
        enhancedMapTiles.add(endLevelBox);




        return enhancedMapTiles;
    }


    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Walrus walrus = new Walrus(getMapTile(30, 21).getLocation().subtractY(13));
        npcs.add(walrus);

        Fproof fproof = new Fproof(getMapTile(107, 12).getLocation().subtractY(13));
        npcs.add(fproof);

        Grave grave = new Grave(getMapTile(46, 23).getLocation().subtractY(13));
        npcs.add(grave);

        Sword sword = new Sword(getMapTile(119, 20).getLocation().subtractY(13));
        npcs.add(sword);

        FireballMaster FBM = new FireballMaster(getMapTile(33, 20).getLocation().subtractY(13));
        npcs.add(FBM);

        Shotgun Shtgn = new Shotgun(getMapTile(107, 20).getLocation().subtractY(13));
        npcs.add(Shtgn);

        return npcs;
    }
}


// Add coords for platform puzzle, box 648,1 plat 661,6 to 658,6