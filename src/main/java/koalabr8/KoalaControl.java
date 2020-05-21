package koalabr8;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KoalaControl implements KeyListener {
    private Koala koala;
    private final int up;
    private final int down;
    private final int right;
    private final int left;

    public KoalaControl(Koala koala, int up, int down, int left, int right) {
        this.koala = koala;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyTyped = ke.getKeyCode();
        if (keyTyped == up) {
            this.koala.toggleUpPressed();
        }
        if (keyTyped == down) {
            this.koala.toggleDownPressed();
        }
        if (keyTyped == left) {
            this.koala.toggleLeftPressed();
        }
        if (keyTyped == right) {
            this.koala.toggleRightPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.koala.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.koala.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.koala.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.koala.unToggleRightPressed();
        }

    }


}
