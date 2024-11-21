package Maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Enemies.BugEnemy;
import Enemies.Judy;
import Enemies.MiniBoss;
import Enemies.Soldier;
import Enemies.GoldKnight;
import Enemies.SkullKnight;
import Enemies.Shadow;
import Enemies.Rabbit;
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
import EnhancedMapTiles.char3Collectible;
import EnhancedMapTiles.char4Collectible;
import EnhancedMapTiles.char5Collectible;
import EnhancedMapTiles.char6Collectible;
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
import NPCs.SwordMaster;
import NPCs.FireballMaster;
import NPCs.Shotgun;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;


// FOR MAP; All Y values must be moved +11 tiles!!! Example, 6 went to 17. 
// Represents a test map to be used in a level
public class TestMap extends Map {
    protected boolean player3Access;
    protected boolean player4Access;
    protected boolean player5Access;
    protected boolean player6Access;

    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(1, 7).getLocation();
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

        VolcanoEnemy volcanoEnemy2 = new VolcanoEnemy(getMapTile(752, 21).getLocation().addY(2)); 
        enemies.add(volcanoEnemy2);

        VolcanoEnemy volcanoEnemy3 = new VolcanoEnemy(getMapTile(757, 23).getLocation().addY(2)); 
        enemies.add(volcanoEnemy3);

        VolcanoEnemy volcanoEnemy4 = new VolcanoEnemy(getMapTile(775, 23).getLocation().addY(2)); 
        enemies.add(volcanoEnemy4);

        VolcanoEnemy volcanoEnemy5 = new VolcanoEnemy(getMapTile(777, 23).getLocation().addY(2)); 
        enemies.add(volcanoEnemy5);

        Spirit spirit = new Spirit(getMapTile(737, 19).getLocation().addY(2)); 
        enemies.add(spirit);

        Spirit spirit1 = new Spirit(getMapTile(874, 18).getLocation().addY(2)); 
        enemies.add(spirit1);

        Spirit spirit2 = new Spirit(getMapTile(865, 19).getLocation().addY(2)); 
        enemies.add(spirit2);

        FishEnemy fishEnemy = new FishEnemy(getMapTile(1152, 23).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(fishEnemy);

        BugEnemy bugEnemy = new BugEnemy(getMapTile(16, 21).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy);


        PhantomEnemy PhantomEnemy = new PhantomEnemy(getMapTile(278, 15).getLocation().addY(0), getMapTile(278, 15).getLocation().addY(0), Direction.LEFT);
        enemies.add(PhantomEnemy);

        PhantomEnemy PhantomEnemy2 = new PhantomEnemy(getMapTile(316, 16).getLocation().addY(2), getMapTile(320, 16).getLocation().addY(2), Direction.LEFT);
        enemies.add(PhantomEnemy2);

        PhantomEnemy PhantomEnemy3 = new PhantomEnemy(getMapTile(312, 5).getLocation().addY(2), getMapTile(314, 5).getLocation().addY(2), Direction.LEFT);
        enemies.add(PhantomEnemy3);

        PhantomEnemy PhantomEnemy4 = new PhantomEnemy(getMapTile(368, 21).getLocation().addY(2), getMapTile(370, 21).getLocation().addY(2), Direction.LEFT);
        enemies.add(PhantomEnemy4);

        PhantomEnemy PhantomEnemy5 = new PhantomEnemy(getMapTile(403, 17).getLocation().addY(2), getMapTile(405, 17).getLocation().addY(2), Direction.LEFT);
        enemies.add(PhantomEnemy5);

        BugEnemy bugEnemy1 = new BugEnemy(getMapTile(40, 23).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy1);

        BugEnemy bugEnemy2 = new BugEnemy(getMapTile(56, 19).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy2);

        BugEnemy bugEnemy3 = new BugEnemy(getMapTile(79, 10).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy3);

        BugEnemy bugEnemy4 = new BugEnemy(getMapTile(84, 10).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy4);

        BugEnemy bugEnemy5 = new BugEnemy(getMapTile(94, 4).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy5);

        BugEnemy bugEnemy6 = new BugEnemy(getMapTile(105, 4).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(bugEnemy6);


        DemonEnemy DemonEnemy = new DemonEnemy(getMapTile(260, 17).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy);

        DemonEnemy DemonEnemy1 = new DemonEnemy(getMapTile(267, 19).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy1);

        DemonEnemy DemonEnemy2 = new DemonEnemy(getMapTile(290, 10).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy2);

        
        DemonEnemy DemonEnemy3 = new DemonEnemy(getMapTile(344, 7).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy3);

        DemonEnemy DemonEnemy4 = new DemonEnemy(getMapTile(407, 6).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy4);

        DemonEnemy DemonEnemy5 = new DemonEnemy(getMapTile(412, 6).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy5);

        DemonEnemy DemonEnemy6 = new DemonEnemy(getMapTile(347, 17).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy6);

        DemonEnemy DemonEnemy7 = new DemonEnemy(getMapTile(381, 17).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy7);
        
        DemonEnemy DemonEnemy8 = new DemonEnemy(getMapTile(394, 17).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy8);

        DemonEnemy DemonEnemy9 = new DemonEnemy(getMapTile(426, 19).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy9);

        DemonEnemy DemonEnemy10 = new DemonEnemy(getMapTile(445, 19).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy10);

        DemonEnemy DemonEnemy11 = new DemonEnemy(getMapTile(824, 18).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy11);

        DemonEnemy DemonEnemy12 = new DemonEnemy(getMapTile(828, 18).getLocation().subtractY(25), Direction.DOWN);
        enemies.add(DemonEnemy12);

        DinosaurEnemy dinosaurEnemy = new DinosaurEnemy(getMapTile(19, 12).getLocation().addY(2), getMapTile(22, 12).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy);

        DinosaurEnemy dinosaurEnemy8 = new DinosaurEnemy(getMapTile(815, 21).getLocation().addY(2), getMapTile(817, 21).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy8);

        Judy judy = new Judy(getMapTile(904, 14).getLocation().addY(-2), getMapTile(907, 14).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(judy);

        Shadow shadow = new Shadow(getMapTile(1120, 16).getLocation().addY(-2), getMapTile(907, 14).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(shadow);

        Rabbit rabbit = new Rabbit(getMapTile(904, 14).getLocation().addY(-2), getMapTile(907, 14).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(rabbit);

        Soldier soldier = new Soldier(getMapTile(207, 17).getLocation().addY(-2), getMapTile(907, 14).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(soldier);

        SkullKnight sKnight = new SkullKnight(getMapTile(690, 20).getLocation().addY(-2), getMapTile(907, 14).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(sKnight);

        GoldKnight gKnight = new GoldKnight(getMapTile(796, 16).getLocation().addY(-2), getMapTile(907, 14).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(gKnight);

        MiniBoss mb1 = new MiniBoss(getMapTile(393, 19).getLocation().addY(-2), getMapTile(396, 19).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(mb1);

        MiniBoss mb2 = new MiniBoss(getMapTile(289, 3).getLocation().addY(-2), getMapTile(293, 3).getLocation().addY(-2), Direction.RIGHT);
        enemies.add(mb2);

        



        DinosaurEnemy dinosaurEnemy2 = new DinosaurEnemy(getMapTile(63, 21).getLocation().addY(2), getMapTile(65, 21).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy2);

        DinosaurEnemy dinosaurEnemy3 = new DinosaurEnemy(getMapTile(81, 17).getLocation().addY(2), getMapTile(82, 17).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy3);

        DinosaurEnemy dinosaurEnemy4 = new DinosaurEnemy(getMapTile(207, 20).getLocation().addY(2), getMapTile(209, 20).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy4);

        DinosaurEnemy dinosaurEnemy5 = new DinosaurEnemy(getMapTile(215, 12).getLocation().addY(2), getMapTile(214, 12).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy5);

        DinosaurEnemy dinosaurEnemy6 = new DinosaurEnemy(getMapTile(235, 18).getLocation().addY(2), getMapTile(236, 18).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy6);

        DinosaurEnemy dinosaurEnemy7 = new DinosaurEnemy(getMapTile(109, 13).getLocation().addY(2), getMapTile(112, 13).getLocation().addY(2), Direction.RIGHT);
        enemies.add(dinosaurEnemy7);

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

        GuardianEnemy GuardianEnemy8 = new GuardianEnemy(getMapTile(673, 17).getLocation().addY(0), getMapTile(674, 17).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy8);

        GuardianEnemy GuardianEnemy9 = new GuardianEnemy(getMapTile(650, 10).getLocation().addY(0), getMapTile(654, 10).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy9);

        GuardianEnemy GuardianEnemy10 = new GuardianEnemy(getMapTile(517, 6).getLocation().addY(0), getMapTile(519, 6).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy10);

        GuardianEnemy GuardianEnemy11 = new GuardianEnemy(getMapTile(538, 11).getLocation().addY(0), getMapTile(543, 11).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy11);

        GuardianEnemy GuardianEnemy12 = new GuardianEnemy(getMapTile(843, 19).getLocation().addY(0), getMapTile(844, 19).getLocation().addY(0), Direction.LEFT);
        enemies.add(GuardianEnemy12);


        
        

        

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

        TrailCollectible speedCollectible = new TrailCollectible(getMapTile(105, 11).getLocation());
        enhancedMapTiles.add(speedCollectible);

        TrailCollectible speedCollectible1 = new TrailCollectible(getMapTile(296, 19).getLocation());
        enhancedMapTiles.add(speedCollectible1);

        TrailCollectible speedCollectible2 = new TrailCollectible(getMapTile(589, 21).getLocation());
        enhancedMapTiles.add(speedCollectible2);

        HealingCollectible HealthCollectible = new HealingCollectible(getMapTile(125, 19).getLocation());
        enhancedMapTiles.add(HealthCollectible);

        HealingCollectible HealthCollectible1 = new HealingCollectible(getMapTile(230, 12).getLocation());
        enhancedMapTiles.add(HealthCollectible1);

        HealingCollectible HealthCollectible2 = new HealingCollectible(getMapTile(328, 14).getLocation());
        enhancedMapTiles.add(HealthCollectible2);

        
        HealingCollectible HealthCollectible3 = new HealingCollectible(getMapTile(496, 20).getLocation());
        enhancedMapTiles.add(HealthCollectible3);

        HealingCollectible HealthCollectible4 = new HealingCollectible(getMapTile(610, 7).getLocation());
        enhancedMapTiles.add(HealthCollectible4);

        LavaPerk lavaPerkCollectible = new LavaPerk(getMapTile(103, 20).getLocation());
        enhancedMapTiles.add(lavaPerkCollectible);

        LavaPerk lavaPerkCollectible1 = new LavaPerk(getMapTile(358, 9).getLocation());
        enhancedMapTiles.add(lavaPerkCollectible1);

        LavaPerk lavaPerkCollectible2 = new LavaPerk(getMapTile(722, 19).getLocation());
        enhancedMapTiles.add(lavaPerkCollectible2);

        checkUnlockedCharacters();
        
        if(!player3Access){
            char3Collectible char3Collectible = new char3Collectible(getMapTile(1, 8).getLocation());
            enhancedMapTiles.add(char3Collectible);
        }

        if(!player4Access){
            char4Collectible char4Collectible = new char4Collectible(getMapTile(384, 9).getLocation());
            enhancedMapTiles.add(char4Collectible);
        }

        if(!player5Access){
            char5Collectible char5Collectible = new char5Collectible(getMapTile(634, 9).getLocation());
            enhancedMapTiles.add(char5Collectible);
        }
        
        if(!player6Access){
            char6Collectible char6Collectible = new char6Collectible(getMapTile(926, 20).getLocation());
            enhancedMapTiles.add(char6Collectible);
        }
  
        // Portal
    Point portalLocation = getMapTile(1090, 2).getLocation(); // Location for the portal tile
    PortalTile portalTile = new PortalTile(portalLocation);

    Point teleportDestination = getMapTile(693, 23).getLocation(); // Destination for the teleport
    portalTile.setTeleportDestination(teleportDestination);

    enhancedMapTiles.add(portalTile);


    // DOOR

    KeyCollectible keyCollectible = new KeyCollectible(getMapTile(126, 12).getLocation());
enhancedMapTiles.add(keyCollectible);

KeyCollectible keyCollectible1 = new KeyCollectible(getMapTile(339, 21).getLocation());
enhancedMapTiles.add(keyCollectible1);

// Create the Door tile and add it to the enhancedMapTiles
Door doorTile = new Door(getMapTile(124, 5).getLocation());
enhancedMapTiles.add(doorTile);

Door doorTile1 = new Door(getMapTile(297, 9).getLocation());
enhancedMapTiles.add(doorTile1);


    //Portal 3
    Point portalLocation2 = getMapTile(276, 4).getLocation(); // Location for the portal tile
    PortalTile portalTile2 = new PortalTile(portalLocation2);

    Point teleportDestination2 = getMapTile(1060, 17).getLocation(); // Destination for the teleport
    portalTile2.setTeleportDestination(teleportDestination2);

    enhancedMapTiles.add(portalTile2);

    Point portalLocation3 = getMapTile(1245, 19).getLocation(); // Location for the portal tile
    PortalTile portalTile3 = new PortalTile(portalLocation3);

    Point teleportDestination3 = getMapTile(99, 19).getLocation(); // Destination for the teleport
    portalTile3.setTeleportDestination(teleportDestination3);

    enhancedMapTiles.add(portalTile3);

    Point portalLocation4 = getMapTile(798, 16).getLocation(); // Location for the portal tile
    PortalTile portalTile4 = new PortalTile(portalLocation4);

    Point teleportDestination4 = getMapTile(939, 17).getLocation(); // Destination for the teleport
    portalTile4.setTeleportDestination(teleportDestination4);

    enhancedMapTiles.add(portalTile4);

    
    Point portalLocation5 = getMapTile(1050, 18).getLocation(); // Location for the portal tile
    PortalTile portalTile5 = new PortalTile(portalLocation5);

    Point teleportDestination5 = getMapTile(812, 20).getLocation(); // Destination for the teleport
    portalTile5.setTeleportDestination(teleportDestination5);

    enhancedMapTiles.add(portalTile5);


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
        EndLevelBox endLevelBox = new EndLevelBox(getMapTile(924, 20).getLocation());
        enhancedMapTiles.add(endLevelBox);




        return enhancedMapTiles;
    }


    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Walrus walrus = new Walrus(getMapTile(30, 21).getLocation().subtractY(13));
        npcs.add(walrus);

        Fproof fproof = new Fproof(getMapTile(148, 2).getLocation().subtractY(13));
        npcs.add(fproof);

        Grave grave = new Grave(getMapTile(46, 23).getLocation().subtractY(13));
        npcs.add(grave);

        Sword sword = new Sword(getMapTile(1097, 19).getLocation().subtractY(13));
        npcs.add(sword);

        FireballMaster FBM = new FireballMaster(getMapTile(1115, 17).getLocation().subtractY(13));
        npcs.add(FBM);

        Shotgun Shtgn = new Shotgun(getMapTile(107, 20).getLocation().subtractY(13));
        npcs.add(Shtgn);

        SwordMaster SM = new SwordMaster(getMapTile(1117, 17).getLocation().subtractY(13));
        npcs.add(SM);

        return npcs;
    }

    // The 2 next methods exist to check if each character has been unlocked so their collectible doesnt spawn in again
    public void checkUnlockedCharacters(){
        checkCharacter("player3State.txt", 3);;
        checkCharacter("player4State.txt", 4);
        checkCharacter("player5State.txt", 5);
        checkCharacter("player6State.txt", 6);
    }

    public void checkCharacter(String filepath, int player){
        boolean access;
        int State;
        try{
            //read the file for the record time
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            State = sc.nextInt();
            sc.close();
            if(State == 1){
                access = true;
            }else{
                access = false;
            }
            switch(player){
                case 3: 
                    player3Access = access;
                case 4:
                    player4Access = access;
                case 5:
                    player5Access = access;
                case 6:
                    player6Access = access;
            }
        }catch (FileNotFoundException e1) {
            try{
                File file = new File(filepath);
                FileWriter fWriter = new FileWriter(filepath, false);
                fWriter.write("0");
                fWriter.close();
            }catch (IOException e2) {
                System.out.println("An error occurred.");
                e2.printStackTrace();
            }
        }
    }
}


