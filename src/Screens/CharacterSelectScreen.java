package Screens;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Players.PlayerType;
import SpriteFont.SpriteFont;

public class CharacterSelectScreen extends Screen {
    private ScreenCoordinator screenCoordinator;
    private SpriteFont character1;
    private SpriteFont character2;
    private SpriteFont character3; // New option for PlayerThree
    private SpriteFont character4; // New option for PlayerFour
    private SpriteFont character5;
    private SpriteFont character6;
    private int currentOptionHovered = 0;
    private int pointerLocationX, pointerLocationY;
    private KeyLocker keyLocker = new KeyLocker();
    private int keyPressTimer = 0;
    private BufferedImage characterSelectImage;
    private PlayerType selectedPlayerType;
    protected boolean player1Access = true;  
    protected SpriteFont accessMsg1;
    protected boolean player2Access = true;  
    protected SpriteFont accessMsg2;
    protected boolean player3Access = false;
    protected SpriteFont accessMsg3;
    protected boolean player4Access = false;
    protected SpriteFont accessMsg4;
    protected boolean player5Access = false;
    protected SpriteFont accessMsg5;
    protected boolean player6Access = false;
    protected SpriteFont accessMsg6;
    protected int State;

    private int imageWidth = 1200;
    private int imageHeight = 1200;
    private int imageX = 0;
    private int imageY = 0;

    public CharacterSelectScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
        this.selectedPlayerType = null; // Default to PROF
    }

    @Override
    public void initialize() {
        checkUnlockedCharacters();

        character1 = new SpriteFont("Professor Oneil: Run Faster!", 100, 0, "Arial", 25, new Color(198, 49, 17));
        character1.setOutlineColor(Color.black);
        character1.setOutlineThickness(3);
        if (player1Access){
            accessMsg1 = new SpriteFont("UNLOCKED", 450, 0, "Arial", 20, Color.yellow);
        }
        else{
            accessMsg1 = new SpriteFont("LOCKED", 450, 0, "Arial", 20, Color.red);
        }

        character2 = new SpriteFont("Professor Alex: Double Jump!", 100, 100, "Arial", 25, new Color(198, 49, 17));
        character2.setOutlineColor(Color.black);
        character2.setOutlineThickness(3);
        if (player2Access){
            accessMsg2 = new SpriteFont("UNLOCKED", 450, 100, "Arial", 20, Color.yellow);
        }
        else{
            accessMsg2 = new SpriteFont("LOCKED", 450, 100, "Arial", 20, Color.red);
        }
        
        character3 = new SpriteFont("Professor Oneil Variant: Jack O' Lantern!", 100, 200, "Arial", 25, new Color(198, 49, 17)); // New third option
        character3.setOutlineColor(Color.black);
        character3.setOutlineThickness(3);
        if (player3Access){
            accessMsg3 = new SpriteFont("UNLOCKED", 570, 200, "Arial", 20, Color.yellow);
        }
        else{
            accessMsg3 = new SpriteFont("LOCKED", 570, 200, "Arial", 20, Color.red);
        }

        character4 = new SpriteFont("Professor Alex Variant: Dracula!", 100, 300, "Arial", 25, new Color(198, 49, 17)); // New fourth option
        character4.setOutlineColor(Color.black);
        character4.setOutlineThickness(3);
        if (player4Access){
            accessMsg4 = new SpriteFont("UNLOCKED", 470, 300, "Arial", 20, Color.yellow);
        }
        else{
            accessMsg4 = new SpriteFont("LOCKED", 470, 300, "Arial", 20, Color.red);
        }

        character5 = new SpriteFont("The Knight: Shift to dash!", 100, 400, "Arial", 25, new Color(198, 49, 17)); // New fifth option
        character5.setOutlineColor(Color.black);
        character5.setOutlineThickness(3);
        if (player5Access){
            accessMsg5 = new SpriteFont("UNLOCKED", 400, 400, "Arial", 20, Color.yellow);
        }
        else{
            accessMsg5 = new SpriteFont("LOCKED", 400, 400, "Arial", 20, Color.red);
        }

        character6= new SpriteFont("Doomguy: Doesn't Fucking die.", 100, 500, "Arial", 25, new Color(198, 49, 17)); // New fifth option
        character6.setOutlineColor(Color.black);
        character6.setOutlineThickness(3);
        if (player6Access){
            accessMsg6 = new SpriteFont("UNLOCKED", 470, 500, "Arial", 20, Color.yellow);
        }
        else{
            accessMsg6 = new SpriteFont("LOCKED", 470, 500, "Arial", 20, Color.red);
        }

        pointerLocationX = 60;
        pointerLocationY = 5;

        characterSelectImage = ImageLoader.load("Resources/hellish.png");
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
            keyPressTimer = 14;
            if (currentOptionHovered < 5) { // Adjusted to allow for the fourth option
                currentOptionHovered++;
                pointerLocationY += 100; // Adjusted to move to the fourth option
            }
        } else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
            keyPressTimer = 14;
            if (currentOptionHovered > 0) {
                currentOptionHovered--;
                pointerLocationY -= 100; // Adjusted for upward movement
            }
        }

        if (keyPressTimer > 0) {
            keyPressTimer--;
        }

        // Update colors based on the current option hovered
        character1.setColor(currentOptionHovered == 0 ? new Color(225, 136, 67) : new Color(198, 49, 17));
        character2.setColor(currentOptionHovered == 1 ? new Color(225, 136, 67) : new Color(198, 49, 17));
        character3.setColor(currentOptionHovered == 2 ? new Color(225, 136, 67) : new Color(198, 49, 17)); // Color update for third option
        character4.setColor(currentOptionHovered == 3 ? new Color(225, 136, 67) : new Color(198, 49, 17)); // Color update for fourth option
        character5.setColor(currentOptionHovered == 4 ? new Color(225, 136, 67) : new Color(198, 49, 17)); // Color update for fifth option
        character6.setColor(currentOptionHovered == 5 ? new Color(225, 136, 67) : new Color(198, 49, 17)); // Color update for sixth option


        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            switch (currentOptionHovered) {
                case 0: 
                if(player1Access){
                    selectedPlayerType = PlayerType.PROF;
                }
                break;
                case 1:
                if(player2Access){
                    selectedPlayerType = PlayerType.PLAYER_TWO;
                }
                break;
                case 2:
                if(player3Access){
                    selectedPlayerType = PlayerType.PLAYER_THREE; // Handle the third option
                }
                break;
                case 3:
                if(player4Access){
                    selectedPlayerType = PlayerType.PLAYER_FOUR; // Handle the fourth option
                }
                break;

                case 4:
                if(player5Access){
                    selectedPlayerType = PlayerType.PLAYER_FIVE; // Handle the fifth option
                }
                break;

                case 5:
                if(player6Access){
                    selectedPlayerType = PlayerType.PLAYER_SIX; // Handle the sixth option
                }
                break;
            }if(selectedPlayerType != null){
                screenCoordinator.setSelectedPlayer(selectedPlayerType);
                screenCoordinator.setGameState(GameState.LEVEL);    
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        if (characterSelectImage != null) {
            graphicsHandler.drawImage(characterSelectImage, imageX, imageY, imageWidth, imageHeight);
        }
        character1.draw(graphicsHandler);
        character2.draw(graphicsHandler);
        character3.draw(graphicsHandler); // Draw third option
        character4.draw(graphicsHandler); // Draw fourth option
        character5.draw(graphicsHandler); // Draw fifth option
        character6.draw(graphicsHandler); // Draw fifth option
        accessMsg1.draw(graphicsHandler);
        accessMsg2.draw(graphicsHandler);
        accessMsg3.draw(graphicsHandler);
        accessMsg4.draw(graphicsHandler);
        accessMsg5.draw(graphicsHandler);
        accessMsg6.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(255, 0, 0), Color.black, 2);
    }

    public void checkUnlockedCharacters(){
        checkCharacter("player3State.txt", 3);;
        checkCharacter("player4State.txt", 4);
        checkCharacter("player5State.txt", 5);
        checkCharacter("player6State.txt", 6);
    }

    public void checkCharacter(String filepath, int player){
        boolean access;
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
