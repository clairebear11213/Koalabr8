package koalabr8;

import java.awt.*;

public abstract class Lock {
    public abstract void drawImage(Graphics g);

    public abstract int getX();

    public abstract int getY();

    public abstract Rectangle getHitBox();
}
