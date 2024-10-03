package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;
import Utils.SlopeTileLayoutUtils;

import java.util.ArrayList;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class CommonTileset extends Tileset {

    public CommonTileset() {
        super(ImageLoader.load("CommonTileset.png"), 16, 16, 3);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        // grass
        Frame grassFrame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassTile = new MapTileBuilder(grassFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(grassTile);

        // sky
        Frame skyFrame = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder skyTile = new MapTileBuilder(skyFrame);

        mapTiles.add(skyTile);

        // dirt
        Frame dirtFrame = new FrameBuilder(getSubImage(0, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder dirtTile = new MapTileBuilder(dirtFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(dirtTile);

        // sun
        Frame[] sunFrames = new Frame[]{
                new FrameBuilder(getSubImage(2, 0), 50)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(2, 1), 50)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder sunTile = new MapTileBuilder(sunFrames);

        mapTiles.add(sunTile);

        // tree trunk with full hole
        Frame treeTrunkWithFullHoleFrame = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(treeTrunkWithFullHoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkWithFullHoleTile);

        // left end branch
        Frame leftEndBranchFrame = new FrameBuilder(getSubImage(1, 5))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder leftEndBranchTile = new MapTileBuilder(leftEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(leftEndBranchTile);

        // right end branch
        Frame rightEndBranchFrame = new FrameBuilder(getSubImage(1, 5))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEndBranchTile = new MapTileBuilder(rightEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(rightEndBranchTile);

        // tree trunk
        Frame treeTrunkFrame = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkTile = new MapTileBuilder(treeTrunkFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkTile);


        // tree top leaves
        Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopLeavesTile = new MapTileBuilder(treeTopLeavesFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTopLeavesTile);

        // yellow flower
        Frame[] yellowFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(1, 2), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 2), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 4), 65)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder yellowFlowerTile = new MapTileBuilder(yellowFlowerFrames);

        mapTiles.add(yellowFlowerTile);

        // purple flower
        Frame[] purpleFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 4), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 5), 65)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames);

        mapTiles.add(purpleFlowerTile);

        // middle branch
        Frame middleBranchFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder middleBranchTile = new MapTileBuilder(middleBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(middleBranchTile);

        // tree trunk hole top
        Frame treeTrunkHoleTopFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkHoleTopTile = new MapTileBuilder(treeTrunkHoleTopFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkHoleTopTile);

        // tree trunk hole bottom
        Frame treeTrunkHoleBottomFrame = new FrameBuilder(getSubImage(2, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkHoleBottomTile = new MapTileBuilder(treeTrunkHoleBottomFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTrunkHoleBottomTile);

        // top water
        Frame topWaterFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrame);

        mapTiles.add(topWaterTile);

        // water
        Frame waterFrame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder waterTile = new MapTileBuilder(waterFrame)
                .withTileType(TileType.WATER);

        mapTiles.add(waterTile);

        // grey rock
        Frame greyRockFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(greyRockTile);

        // left 45 degree slope
        Frame leftSlopeFrame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftSlopeTile = new MapTileBuilder(leftSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createLeft45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftSlopeTile);

        // right 45 degree slope
        Frame rightSlopeFrame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightSlopeTile = new MapTileBuilder(rightSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createRight45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(rightSlopeTile);

        // left 30 degree slope bottom
        Frame leftStairsBottomFrame = new FrameBuilder(getSubImage(4, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftStairsBottomTile = new MapTileBuilder(leftStairsBottomFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createBottomLeft30SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftStairsBottomTile);

        // left 30 degree slope top
        Frame leftStairsTopFrame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftStairsTopTile = new MapTileBuilder(leftStairsTopFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createTopLeft30SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftStairsTopTile);

         // new endgrass tile
         Frame endgrassFrame = new FrameBuilder(getSubImage(4, 2))  
         .withScale(tileScale)
         .build();
 
         MapTileBuilder endgrass = new MapTileBuilder(endgrassFrame)
         .withTileType(TileType.NOT_PASSABLE);  // Define its type
 
         mapTiles.add(endgrass);

         // new enddirt tile
         Frame enddirtFrame = new FrameBuilder(getSubImage(4, 3))  
         .withScale(tileScale)
         .build();
 
         MapTileBuilder enddirt = new MapTileBuilder(enddirtFrame)
         .withTileType(TileType.NOT_PASSABLE);  // Define its type
 
         mapTiles.add(enddirt);

         // left 45 degree slope end
        Frame leftSlopeendFrame = new FrameBuilder(getSubImage(4, 4))
        .withScale(tileScale)
        .build();

        MapTileBuilder leftSlopeendTile = new MapTileBuilder(leftSlopeendFrame)
        .withTileType(TileType.SLOPE)
        .withTileLayout(SlopeTileLayoutUtils.createLeft45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftSlopeendTile);

        // right 45 degree slope
        Frame rightSlopeendFrame = new FrameBuilder(getSubImage(4, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightSlopeendTile = new MapTileBuilder(rightSlopeendFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createRight45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(rightSlopeendTile);

        // new end flower tile
        Frame endflowerFrame = new FrameBuilder(getSubImage(3, 5))  
        .withScale(tileScale)
        .build();

        MapTileBuilder endflower = new MapTileBuilder(endflowerFrame)
        .withTileType(TileType.PASSABLE);  // Define its type

        mapTiles.add(endflower);
 
        // new end brick tile
        Frame brickFrame = new FrameBuilder(getSubImage(0, 6))  
        .withScale(tileScale)
        .build();

        MapTileBuilder bricktile = new MapTileBuilder(brickFrame)
        .withTileType(TileType.NOT_PASSABLE);  // Define its type

        mapTiles.add(bricktile);

        // new vines tile
        Frame VinesFrame = new FrameBuilder(getSubImage(1, 6))  
        .withScale(tileScale)
        .build();

        MapTileBuilder Vines = new MapTileBuilder(VinesFrame)
        .withTileType(TileType.PASSABLE);  // Define its type

        mapTiles.add(Vines);

        // new blue fire tile
        Frame blueFireFrame = new FrameBuilder(getSubImage(2, 6))  
        .withScale(tileScale)
        .build();

        MapTileBuilder blueFire = new MapTileBuilder(blueFireFrame)
        .withTileType(TileType.PASSABLE);  // Define its type

        mapTiles.add(blueFire);

         // new biome tile
         Frame greenskyFrame = new FrameBuilder(getSubImage(0, 7))  
         .withScale(tileScale)
         .build();
 
         MapTileBuilder greensky = new MapTileBuilder(greenskyFrame)
         .withTileType(TileType.PASSABLE);  // Define its type
 
         mapTiles.add(greensky);

         // new biome tile
         Frame columnFrame = new FrameBuilder(getSubImage(1, 7))  
         .withScale(tileScale)
         .build();
 
         MapTileBuilder column = new MapTileBuilder(columnFrame)
         .withTileType(TileType.NOT_PASSABLE);  // Define its type
 
         mapTiles.add(column);

          // right 45 degree slope
        Frame rightgreenslopeFrame = new FrameBuilder(getSubImage(3, 6))
        .withScale(tileScale)
        .build();

        MapTileBuilder rightgreenslope = new MapTileBuilder(rightgreenslopeFrame)
        .withTileType(TileType.SLOPE)
        .withTileLayout(SlopeTileLayoutUtils.createRight45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(rightgreenslope);

        // left 45 degree slope
        Frame leftgreenslopeFrame = new FrameBuilder(getSubImage(4, 6))
        .withScale(tileScale)
        .build();

        MapTileBuilder leftgreenslope = new MapTileBuilder(leftgreenslopeFrame)
        .withTileType(TileType.SLOPE)
        .withTileLayout(SlopeTileLayoutUtils.createLeft45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftgreenslope);

        // new biome tile
        Frame quartzFrame = new FrameBuilder(getSubImage(2, 7))  
        .withScale(tileScale)
        .build();

        MapTileBuilder quartz = new MapTileBuilder(quartzFrame)
        .withTileType(TileType.NOT_PASSABLE);  // Define its type

        mapTiles.add(quartz);

        // new biome tile
        Frame quartzfloorFrame = new FrameBuilder(getSubImage(3, 7))  
        .withScale(tileScale)
        .build();

        MapTileBuilder quartzfloor = new MapTileBuilder(quartzfloorFrame)
        .withTileType(TileType.NOT_PASSABLE);  // Define its type

        mapTiles.add(quartzfloor);

        // new biome green fire tile
        Frame greenflameFrame = new FrameBuilder(getSubImage(4, 7))  
        .withScale(tileScale)
        .build();

        MapTileBuilder greenflame = new MapTileBuilder(greenflameFrame)
        .withTileType(TileType.PASSABLE);  // Define its type

        mapTiles.add(greenflame);

        // green lava
        Frame glavaFrame = new FrameBuilder(getSubImage(0, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder glava = new MapTileBuilder(glavaFrame)
                .withTileType(TileType.WATER);

        mapTiles.add(glava);

        // green biome gold blocks
        Frame goldenFrame = new FrameBuilder(getSubImage(1, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder golden = new MapTileBuilder(goldenFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(golden);

        // green biome chains hanging
        Frame chainFrame = new FrameBuilder(getSubImage(2, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder chain = new MapTileBuilder(chainFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(chain);

        // green biome chains hanging
        Frame skullchainFrame = new FrameBuilder(getSubImage(3, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder skullchain = new MapTileBuilder(skullchainFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(skullchain);

         // green biome chains hanging
         Frame newflame1Frame = new FrameBuilder(getSubImage(0, 9))
         .withScale(tileScale)
         .build();

 MapTileBuilder newflame1 = new MapTileBuilder(newflame1Frame)
         .withTileType(TileType.WATER);

 mapTiles.add(newflame1);

                 // green biome chains hanging
         Frame newflame2Frame = new FrameBuilder(getSubImage(4, 8))
         .withScale(tileScale)
         .build();

 MapTileBuilder newflame2 = new MapTileBuilder(newflame2Frame)
         .withTileType(TileType.WATER);

 mapTiles.add(newflame2);



//4,8 and 0,9 for fire tiles. 0,9 is bottom flame, 4,8 is top flame
        return mapTiles;
    }
}
