package koalabr8;

import java.awt.*;

public abstract class Switch {
    public abstract void drawImage(Graphics g);

    public abstract int getX();

    public abstract int getY();

    public abstract Rectangle getHitBox();

    public abstract int getType();  //1: normal, 2: timed, 3: pressure
}
