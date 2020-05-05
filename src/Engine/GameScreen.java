package Engine;

import GameObject.Rectangle;
import java.awt.*;

public abstract class GameScreen {
    public abstract  void initialize(Rectangle windowBounds);
    public abstract void update(Keyboard keyboard);
    public abstract void draw(Graphics2D g);
}
