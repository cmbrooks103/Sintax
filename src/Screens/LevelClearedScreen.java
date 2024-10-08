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
    protected SpriteFont recordBrokenMessage;
    protected int currentRun;
    protected int recordRun;
    protected boolean recordBroken;

    public LevelClearedScreen() {
        initialize();
    }

    @Override
    public void initialize() {

        winMessage = new SpriteFont("Level Cleared", 265, 179, "Times New Roman", 30, Color.white);
    
        RecordTime = new SpriteFont("Record Time: 00:00", 265, 229, "Times New Roman", 30, Color.red);

        CurrentTime = new SpriteFont("Current Time: 00:00", 265, 279, "Times New Roman", 30, Color.green);

        recordBroken = false;
    }

    @Override
    public void update() {

    }

    public void draw(GraphicsHandler graphicsHandler) {
        // paint entire screen black and dislpay level cleared text
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        
        try{
            //read the file for the record time
            File file = new File("highscore.txt");
            Scanner sc = new Scanner(file);
            recordRun = sc.nextInt();
            sc.close();
            // if current time is faster, then update the fastest time in the txt file
            if (recordRun>currentRun){
                recordRun = currentRun;
                recordBroken = true;
                FileWriter fWriter = new FileWriter("highscore.txt", false);
                fWriter.write(String.valueOf(currentRun));
                fWriter.close();
            }
            
        }catch (FileNotFoundException e) {
            try{
                File file = new File("highscore.txt");
                FileWriter fWriter = new FileWriter("highscore.txt", false);
                fWriter.write(String.valueOf(currentRun));
                fWriter.close();
                recordBroken = true;

            }catch (IOException e2){
                e2.printStackTrace();
            }

            
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //convert record time for display
        int minutesR = Math.round(recordRun/60);
        int secondsR = recordRun%60;
        String sMinutesR = String.format("%02d", minutesR);
        String sSecondsR = String.format("%02d", secondsR);

        RecordTime = new SpriteFont("Record Time: " + sMinutesR + ":" + sSecondsR , 265, 229, "Times New Roman", 30, Color.red);
        
        //convert current time for display
        int minutesC = Math.round(currentRun/60);
        int secondsC = currentRun%60;
        String sMinutesC = String.format("%02d", minutesC);
        String sSecondsC = String.format("%02d", secondsC);

        CurrentTime = new SpriteFont("Current Time: " + sMinutesC + ":" + sSecondsC , 265, 279, "Times New Roman", 30, Color.green);
        // display the times and the win messages
        winMessage.draw(graphicsHandler);
        RecordTime.draw(graphicsHandler);
        CurrentTime.draw(graphicsHandler);

        
        if (recordBroken){
            recordBrokenMessage = new SpriteFont("Congratulations! You broke the record!", 265, 329, "Times New Roman", 30, Color.yellow);
            recordBrokenMessage.draw(graphicsHandler);
        }

    }
    
    //set the time for the current attempt and convert it to seconds
    public void setTime(int time){
        currentRun = time/60;
    }
}
