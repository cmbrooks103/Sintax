package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import SpriteFont.SpriteFont;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


import java.awt.*;

// This class is for the level cleared screen
public class LevelClearedScreen extends Screen {
    protected SpriteFont winMessage;
    protected SpriteFont RecordTime;
    protected SpriteFont CurrentTime;
    protected int currentRun;
    protected int recordRun;

    public LevelClearedScreen() {
        initialize();
    }

    @Override
    public void initialize() {

        winMessage = new SpriteFont("Level Cleared", 265, 179, "Times New Roman", 30, Color.white);
    
        RecordTime = new SpriteFont("Record Time: 00:00", 265, 229, "Times New Roman", 30, Color.red);

        CurrentTime = new SpriteFont("Current Time: 00:00", 265, 279, "Times New Roman", 30, Color.green);
    }

    @Override
    public void update() {

    }

    public void draw(GraphicsHandler graphicsHandler) {
        // paint entire screen black and dislpay level cleared text
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        
        try{
            File file = new File("C:\\\\Users\\\\thoma\\\\Desktop\\\\Sintax\\\\PlayerData\\\\highscore.txt");
            Scanner sc = new Scanner(file);
            recordRun = sc.nextInt();
            sc.close();
            if (recordRun>currentRun){
                FileWriter fWriter = new FileWriter("C:\\\\Users\\\\thoma\\\\Desktop\\\\Sintax\\\\PlayerData\\\\highscore.txt", false);
                fWriter.write(String.valueOf(currentRun));
                fWriter.close();
            }
            
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int minutesR = Math.round(recordRun/60);
        int secondsR = recordRun%60;
        String sMinutesR = String.format("%02d", minutesR);
        String sSecondsR = String.format("%02d", secondsR);

        RecordTime = new SpriteFont("Record Time: " + sMinutesR + ":" + sSecondsR , 265, 229, "Times New Roman", 30, Color.red);

        int minutesC = Math.round(currentRun/60);
        int secondsC = currentRun%60;
        String sMinutesC = String.format("%02d", minutesC);
        String sSecondsC = String.format("%02d", secondsC);

        CurrentTime = new SpriteFont("Current Time: " + sMinutesC + ":" + sSecondsC , 265, 279, "Times New Roman", 30, Color.green);
    
        winMessage.draw(graphicsHandler);
        RecordTime.draw(graphicsHandler);
        CurrentTime.draw(graphicsHandler);
    }
    
    public void setTime(int time){
        currentRun = time/60;
    }
}
