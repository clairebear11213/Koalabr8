/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koalabr8;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;


/**
 * Main driver class of Koala Example.
 * Class is responsible for loading resources and
 * initializing game objects. Once completed, control will
 * be given to infinite loop which will act as our game loop.
 * A very simple game loop.
 * @originalauthor anthony-pc
 * @updated Claire McCullough Spring 2020
 */
public class Koalabr8 extends JPanel  {

    public static final int WORLD_WIDTH = 1300;
    public static final int WORLD_HEIGHT = 700;
    public static final int SCREEN_WIDTH = 1300;    //26 cols
    public static final int SCREEN_HEIGHT = 700;    //14 rows
    public static final int move = 2;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jFrame;
    private koalabr8.Koala koalaOne;
    private koalabr8.Koala koalaTwo;
    private koalabr8.Koala koalaThree;
    static long tickCount = 0;

    public static int level = 1;
    public static int count = 0;
//    public static int sawCount = 0;

    ArrayList<Koala> koalas;
    ArrayList<Wall> walls;
    ArrayList<Boulder> boulders;
//    ArrayList<Switch> switches;
    ArrayList<NormalSwitch> nss;
    ArrayList<FakeSwitch> fss;
    ArrayList<TimedSwitch> tss;
    ArrayList<PressureSwitch> pss;
    ArrayList<Exit> exits;
//    ArrayList<Lock> locks;
    ArrayList<NormalLock> nls;
    ArrayList<TimedLock> tls;
    ArrayList<PressureLock> pls;
    ArrayList<PressureLock> ogpls;
    ArrayList<TNT> tnts;
    ArrayList<Saw> saws;
    ArrayList<Long> tsCounts;
    ArrayList<Boolean> psHits;



    public static void main(String[] args) {
        Koalabr8 koalaExample = new Koalabr8();
        koalaExample.init();
        try {

            while (true) {
                for (int s = 0; s < koalaExample.saws.size(); s++) {
                    koalaExample.saws.get(s).move();
                    koalaExample.saws.get(s).updateHitBox(koalaExample.saws.get(s).getX(), koalaExample.saws.get(s).getY());
                }
//                koalaExample.saws.forEach(s -> s.move());
                int koalaSize = koalaExample.koalas.size();
                if (koalaSize == 0) {
                    if (level == 1) {
                        System.out.println("You passed level 1! :)");
                        level = 2;
                        koalaExample.init();
                    } else if (level == 2) {
                        System.out.println("You passed level 2! :)");
                        level = 3;
                        koalaExample.init();
                    } else {
                        level = -1;
                        count++;
                        if (count == 1) {
                            System.out.println("You win the game! :)");
                        }
                    }
                }
                for (int i = 0; i < koalaSize; i++) {
                    koalaExample.koalas.get(i).update();
                }
//                koalaExample.koalaOne.update();
//                koalaExample.koalaTwo.update();
//                koalaExample.koalaThree.update();

                koalaExample.repaint();
                tickCount++;
                //make sure koalas do not overlap with koalas
                for (int i = 0; i < koalaSize - 1; i++) {
                    for (int j = i + 1; j < koalaSize; j++) {
                        int oneX = koalaExample.koalas.get(i).getX();
                        int oneY = koalaExample.koalas.get(i).getY();
                        int twoX = koalaExample.koalas.get(j).getX();
                        int twoY = koalaExample.koalas.get(j).getY();
                        //bump koalas
                        koalaBump(oneX, oneY, twoX, twoY, koalaExample.koalas.get(i), koalaExample.koalas.get(j));
                        //bump K and walls
                        for (int k = 0; k < koalaExample.walls.size(); k++) {
                            if (i == koalaSize - 2) {
                                wallBump(twoX, twoY, koalaExample.walls.get(k).getX(), koalaExample.walls.get(k).getY(), koalaExample.koalas.get(j), koalaExample.walls.get(k));
                            } else {
                                wallBump(oneX, oneY, koalaExample.walls.get(k).getX(), koalaExample.walls.get(k).getY(), koalaExample.koalas.get(i), koalaExample.walls.get(k));
                            }
                        }
                    }
                }
                //koala x ...
                for (int i = 0; i < koalaSize; i++) {
                    int KX = koalaExample.koalas.get(i).getX();
                    int KY = koalaExample.koalas.get(i).getY();
                    // x normal lock
                    for (int k = 0; k < koalaExample.nls.size(); k++) {
                        lockBump(KX, KY, koalaExample.nls.get(k).getX(), koalaExample.nls.get(k).getY(), koalaExample.koalas.get(i), koalaExample.nls.get(k));
                    }
                    // x timed lock
                    for (int k = 0; k < koalaExample.tls.size(); k++) {
                        lockBump(KX, KY, koalaExample.tls.get(k).getX(), koalaExample.tls.get(k).getY(), koalaExample.koalas.get(i), koalaExample.tls.get(k));
                    }
                    // x pressure lock
                    for (int k = 0; k < koalaExample.pls.size(); k++) {
                        lockBump(KX, KY, koalaExample.pls.get(k).getX(), koalaExample.pls.get(k).getY(), koalaExample.koalas.get(i), koalaExample.pls.get(k));
                    }
                    // x boulders
                    int bSize = koalaExample.boulders.size();
                    for (int k = 0; k < bSize; k++) {
                        for (int b = k + 1; b < bSize; b++) {
                            boulderBoulder(koalaExample.boulders.get(k), koalaExample.boulders.get(b));
                        }
                        if (k >= bSize || i >= koalaSize || i < 0 || k < 0) { break;}
                        boulderBump(KX, KY, koalaExample.boulders.get(k).getX(), koalaExample.boulders.get(k).getY(), koalaExample.koalas.get(i), koalaExample.boulders.get(k));
                        //boulder x wall
                        for (int w = 0; w < koalaExample.walls.size(); w++) {
                            if (k >= bSize || i >= koalaSize || i < 0 || k < 0 || w >= koalaExample.walls.size()) { break;}
//                            System.out.println("hit" + w);
                            boulderWall(koalaExample.boulders.get(k), koalaExample.walls.get(w));
                        }
                        //boulder x tnt
                        int tntSize = koalaExample.tnts.size();
                        for (int t = 0; t < tntSize; t++) {
                            if (k >= bSize || i >= koalaSize || i < 0 || k < 0 || t >= tntSize) { break;}
//                            System.out.println(k + " " + t);
                            if (koalaExample.boulders.get(k).getHitBox().intersects(koalaExample.tnts.get(t).getHitBox())) {
//                                System.out.println("hit" + k);
                                koalaExample.boulders.remove(k);
                                koalaExample.tnts.remove(t);
                                bSize--;
                                tntSize--;
                            }
                        }
                        if (k >= bSize || i >= koalaSize || i < 0 || k < 0) { break;}
                        //boulder x saw
                        int sawSize = koalaExample.saws.size();
                        for (int s = 0; s < sawSize; s++) {
                            if (k >= bSize || i >= koalaSize || i < 0 || k < 0 || s >= sawSize) { break;}
                            if (koalaExample.boulders.get(k).getHitBox().intersects(koalaExample.saws.get(s).getHitBox())) {
//                                System.out.println("hit");
                                koalaExample.boulders.remove(k);
                                koalaExample.saws.remove(s);
                                bSize--;
                                sawSize--;
                            }
                        }
                        if (k >= bSize || i >= koalaSize || i < 0 || k < 0) { break;}
                        //boulder x normal switch
                        int nssSize = koalaExample.nss.size();
                        for (int n = 0; n < nssSize; n++) {
                            if (k < 0 || k >= nssSize) {
                                break;
                            }
                            if (koalaExample.boulders.get(k).getHitBox().intersects(koalaExample.nss.get(n).getHitBox())) {
                                koalaExample.nss.remove(n);
                                koalaExample.nls.remove(n);
                                nssSize--;
                            }
                        }
                        //boulder x timed switch
                        int tssSize = koalaExample.tss.size();
                        for (int t = 0; t < tssSize; t++) {
                            if (k < 0 || k >= tssSize) {
                                break;
                            }
                            if (koalaExample.boulders.get(k).getHitBox().intersects(koalaExample.tss.get(t).getHitBox())) {
                                koalaExample.tsCounts.set(t, koalaExample.tsCounts.get(t) + 1);
                                if (koalaExample.tsCounts.get(t) >= 100) {
                                    koalaExample.tss.remove(t);
                                    koalaExample.tsCounts.remove(t);
                                    koalaExample.tls.remove(t);
                                    tssSize--;
                                }
                            }
                        }
                        //boulder x pressure switch
                        int plsSize = koalaExample.pls.size();
                        for (int p = 0; p < plsSize; p++) {
                            if (koalaExample.boulders.get(k).getHitBox().intersects(koalaExample.pss.get(p).getHitBox())) {
                                koalaExample.psHits.set(p, true);
                                if (koalaExample.psHits.get(p) == true) {
                                    koalaExample.pls.remove(p);
                                    plsSize--;
                                } else {
                                    koalaExample.pls.add(p, koalaExample.ogpls.get(p));
                                    plsSize++;
                                }
                            }
                        }
                    }
                    // x tnt
                    int tntSize = koalaExample.tnts.size();
                    for (int k = 0; k < tntSize; k++) {
                        tntBump(koalaExample.koalas.get(i), koalaExample.tnts.get(k));
                    }
                    // x saws
                    int sawSize = koalaExample.saws.size();
                    for (int k = 0; k < sawSize; k++) {
                        sawBump(koalaExample.koalas.get(i), koalaExample.saws.get(k));
                    }
                    // x normal switch
                    int nssSize = koalaExample.nss.size();
                    for (int k = 0; k < nssSize; k++) {
                        if (i < 0 || i >= nssSize) {
                            break;
                        }
                        if (koalaExample.koalas.get(i).getHitBox().intersects(koalaExample.nss.get(k).getHitBox())) {
                            koalaExample.nss.remove(k);
                            koalaExample.nls.remove(k);
                            nssSize--;
                        }
                    }
                    // x timed switch
                    int tssSize = koalaExample.tss.size();
                    for (int k = 0; k < tssSize; k++) {
                        if (i < 0 || i >= tssSize) {
                            break;
                        }
                        if (koalaExample.koalas.get(i).getHitBox().intersects(koalaExample.tss.get(k).getHitBox())) {
                            koalaExample.tsCounts.set(k, koalaExample.tsCounts.get(k) + 1);
//                            System.out.println(koalaExample.tsCounts.get(k));
                            if (koalaExample.tsCounts.get(k) >= 100) {
                                koalaExample.tss.remove(k);
                                koalaExample.tsCounts.remove(k);
                                koalaExample.tls.remove(k);
                                tssSize--;
                            }
                        } else {
//                            koalaExample.tsCounts.set(k, (long)0);
//                            System.out.println(koalaExample.tsCounts.get(k));
                        }
//                        while (koalaExample.koalas.get(i).getHitBox().intersects(koalaExample.tss.get(k).getHitBox())) {
//                            c++;
//                            if (c >= 1000) {
//                                koalaExample.tss.remove(k);
//                                koalaExample.tls.remove(k);
//                                tssSize--;
//                                break;
//                            }
//                        }
                    }
                    // x pressure switch
                    int plsSize = koalaExample.pls.size();
                    for (int k = 0; k < plsSize; k++) {
                        if (koalaExample.koalas.get(i).getHitBox().intersects(koalaExample.pss.get(k).getHitBox())) {
                            koalaExample.psHits.set(k, true);
                            if (koalaExample.psHits.get(k) == true) {
                                koalaExample.pls.remove(k);
                                plsSize--;
                            } else {
                                koalaExample.pls.add(k, koalaExample.ogpls.get(k));
                                plsSize++;
                            }
                        } else {
//                            koalaExample.psHits.set(k, false);
                        }
//                        if (i < 0 || i >= plsSize) {
//                            break;
//                        }
//                        if (koalaExample.koalas.get(i).getHitBox().intersects(koalaExample.pss.get(k).getHitBox())) {
//                            koalaExample.pss.remove(k);
//                            koalaExample.pls.remove(k);
//                            plsSize--;
//                        }
                    }
                    // x exits
                    int eSize = koalaExample.exits.size();
                    for (int k = 0; k < eSize; k++) {
                        if (i < 0 || i >= eSize || i >= koalaSize) {
                            break;
                        }
                        if (koalaExample.koalas.get(i).getHitBox().intersects(koalaExample.exits.get(k).getHitBox())) {
                            if (koalaExample.exits.get(k).getType() == "red") {
                                //if red exit - remove koala
                                koalaExample.koalas.remove(i);
                                koalaSize--;
                            } else if (koalaExample.exits.get(k).getType() == "blue") {
                                //if blue exit - remove koala, remove exit
                                koalaExample.exits.remove(k);
                                koalaExample.koalas.remove(i);
                                koalaSize--;
                                eSize--;
                            }
                        }
                    }
                }
//                int tntSize = koalaExample.tnts.size();
//                int bSize = koalaExample.boulders.size();
//                for (int t = 0; t < tntSize; t++) {
//                    for (int b = 0; b < bSize; b++) {
//                        if (koalaExample.boulders.get(b).getHitBox().intersects(koalaExample.tnts.get(t).getHitBox())) {
//                            koalaExample.boulders.remove(b);
//                            koalaExample.tnts.remove(t);
//                            bSize--;
//                            tntSize--;
//                        }
//                    }
//                }

//                //koalas push boulders
//                for (int i = 0; i < koalaExample.boulders.size(); i++) {
//                    //bump K1 & boulders
//                    boulderBump(oneX, oneY, koalaExample.boulders.get(i).getX(), koalaExample.boulders.get(i).getY(), koalaExample.koalas.get(0), koalaExample.boulders.get(i));
//                    //bump K2 & boulders
//                    boulderBump(twoX, twoY, koalaExample.boulders.get(i).getX(), koalaExample.boulders.get(i).getY(), koalaExample.koalas.get(1), koalaExample.boulders.get(i));
//                    //bump K3 & boulders
//                    boulderBump(threeX, threeY, koalaExample.boulders.get(i).getX(), koalaExample.boulders.get(i).getY(), koalaExample.koalas.get(2), koalaExample.boulders.get(i));
//                }
                //koalas press switches - 1: normal, 2: timed, 3: pressure
//                int switchSize = koalaExample.switches.size();
//                int plsSize = koalaExample.pls.size();
//                int c = 0;
//                boolean isHit = false;
//                for (int i = 0; i < switchSize; i++) {
//                    for (int j = 0; j < koalaExample.koalas.size(); j++) {
//                        if (koalaExample.switches.get(i).getType() == 1) {
//                            if (koalaExample.koalas.get(j).getHitBox().intersects(koalaExample.switches.get(i).getHitBox())) {
//                            koalaExample.switches.remove(i);
//                            koalaExample.nls.remove(i);
//                            switchSize--;
//                        }
//                        } else if (koalaExample.switches.get(i).getType() == 2) {
//                            while (koalaExample.koalas.get(j).getHitBox().intersects(koalaExample.switches.get(i).getHitBox())) {
//                                c++;
//                                if (c >= 1000) {
//                                    koalaExample.switches.remove(i);
//                                    koalaExample.tls.remove(i);
//                                    switchSize--;
//                                    break;
//                                }
//                            }
//                        } else if (koalaExample.switches.get(i).getType() == 3) {
//                            if (i < 0) { i++; }
//                            if (i >= switchSize) { break; }
//                            PressureLock newpls = koalaExample.pls.get(i);
//                            if (koalaExample.koalas.get(j).getHitBox().intersects(koalaExample.switches.get(i).getHitBox())) {
//                                isHit = true;
//                            } else {
//                                isHit = false;
//                            }
//                            if (isHit == true) {
//                                koalaExample.pls.remove(i);
//                                plsSize--;
//                                i--;
//                            } else if (isHit == false && plsSize < switchSize) {
//                                koalaExample.pls.add(i, newpls);
//                                plsSize++;
//                            }
//                        }
//                    }
//                }
//                //koalas press switches - normal
//                int nssSize = koalaExample.nss.size();
//                for (int i = 0; i < nssSize; i++) {
//                    for (int j = 0; j < koalaExample.koalas.size(); j++) {
//                        if (i < 0 || i >= nssSize) { break; }
//                        if (koalaExample.koalas.get(j).getHitBox().intersects(koalaExample.nss.get(i).getHitBox())) {
//                            koalaExample.nss.remove(i);
//                            koalaExample.nls.remove(i);
//                            nssSize--;
//                        }
//                    }
//                    if (koalaExample.koalaOne.getHitBox().intersects(koalaExample.nss.get(i).getHitBox())) {
//                        koalaExample.nss.remove(i);
//                        koalaExample.nls.remove(i);
//                        nssSize--;
//                    } else if (koalaExample.koalaTwo.getHitBox().intersects(koalaExample.nss.get(i).getHitBox())) {
//                        koalaExample.nss.remove(i);
//                        koalaExample.nls.remove(i);
//                        nssSize--;
//                    } else if (koalaExample.koalaThree.getHitBox().intersects(koalaExample.nss.get(i).getHitBox())) {
//                        koalaExample.nss.remove(i);
//                        koalaExample.nls.remove(i);
//                        nssSize--;
//                    }
//                }
//                ArrayList<Integer> count = null;
////                for (int i = 0; i < koalaExample.tss.size(); i++) {
////                    count.add(0);
////                }
//                //koalas press switches - timed
//                int tssSize = koalaExample.tss.size();
//                for (int i = 0; i < tssSize; i++) {
//                    int c = 0;
//                    for (int j = 0; j < koalaExample.koalas.size(); j++) {
//                        if (i < 0 || i >= tssSize) { break; }
//                        while (koalaExample.koalas.get(j).getHitBox().intersects(koalaExample.tss.get(i).getHitBox())) {
////                                koalaExample.koalaOne.getHitBox().intersects(koalaExample.tss.get(i).getHitBox()) || koalaExample.koalaTwo.getHitBox().intersects(koalaExample.tss.get(i).getHitBox()) || koalaExample.koalaThree.getHitBox().intersects(koalaExample.tss.get(i).getHitBox())) {
//                            c++;
//                            if (c >= 1000) {
//                                koalaExample.tss.remove(i);
//                                koalaExample.tls.remove(i);
//                                tssSize--;
//                                break;
//                            }
//                        }
//                    }
//                    koalaExample.counts.add(tickCount);
//                    if (koalaExample.koalaOne.getHitBox().intersects(koalaExample.tss.get(i).getHitBox())) {
//                        c = tickCount;
//                    } else if (koalaExample.koalaTwo.getHitBox().intersects(koalaExample.tss.get(i).getHitBox())) {
//                        c = tickCount;
//                    } else if (koalaExample.koalaThree.getHitBox().intersects(koalaExample.tss.get(i).getHitBox())) {
//                        c = tickCount;
//                    } else {
//                        c = 0;
//                    }
//                    if (c >= 3) {
//                        koalaExample.tss.remove(i);
//                        koalaExample.tls.remove(i);
//                    }
////                    count.set(i, c);
//                }
//                //koalas press switches - pressure
//                int pssSize = koalaExample.pss.size();
//                int plsSize = koalaExample.pls.size();
//                boolean isHit = false;
//                for (int i = 0; i < pssSize; i++) {
//                    for (int j = 0; j < koalaExample.koalas.size(); j++) {
//                        if (i < 0 || i >= pssSize) { break; }
//                        if (koalaExample.koalas.get(j).getHitBox().intersects(koalaExample.pss.get(i).getHitBox())) {
//                            koalaExample.pss.remove(i);
//                            koalaExample.pls.remove(i);
//                            pssSize--;
//                        }
////                        if ((i < 0) || (plsSize <= 0)) { break; }
//                        if (i < 0) {
//                            i++;
//                        }
//                        if (i >= plsSize) {
//                            break;
//                        }
////                    if (i < 0 || pssSize <= 0 || plsSize <= 0 || i >= plsSize) { break;}
//                        PressureLock newpls = koalaExample.pls.get(i);
//                        if (koalaExample.koalas.get(j).getHitBox().intersects(koalaExample.pss.get(i).getHitBox())) {
////                        if (koalaExample.koalaOne.getHitBox().intersects(koalaExample.pss.get(i).getHitBox())) {
//                            isHit = true;
////                        } else if (koalaExample.koalaTwo.getHitBox().intersects(koalaExample.pss.get(i).getHitBox())) {
////                            isHit = true;
////                        } else if (koalaExample.koalaThree.getHitBox().intersects(koalaExample.pss.get(i).getHitBox())) {
////                            isHit = true;
//                        } else {
//                            isHit = false;
//                        }
//                        if (isHit == true) {
//                            koalaExample.pls.remove(i);
//                            plsSize--;
//                            i--;
//                        } else if (isHit == false && plsSize < pssSize) {
//                            koalaExample.pls.add(i, newpls);
//                            plsSize++;
//                        }
//                    }
//                }
                //do not remove switches, just remove them from the list?

                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }

    }

    private void init() {
        this.jFrame = new JFrame("Koala Rotation");
//        this.world = new BufferedImage(Koalabr8.WORLD_WIDTH, Koalabr8.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.world = new BufferedImage(Koalabr8.SCREEN_WIDTH, Koalabr8.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage koalaImage = null;
        BufferedImage wall = null;
        BufferedImage tnt = null;
        BufferedImage saw = null;
        BufferedImage redExit = null;
        BufferedImage blueExit = null;
        BufferedImage normalLock = null;
        BufferedImage timedLock = null;
        BufferedImage pressureLock = null;
        BufferedImage normalSwitch = null;
        BufferedImage timedSwitch = null;
        BufferedImage pressureSwitch = null;
        BufferedImage boulder = null;
        walls = new ArrayList<>();
        boulders = new ArrayList<>();
//        switches = new ArrayList<>();
        nss = new ArrayList<>();
        fss = new ArrayList<>();
        tss = new ArrayList<>();
        pss = new ArrayList<>();
        exits = new ArrayList<>();
        nls = new ArrayList<>();
        pls = new ArrayList<>();
        ogpls = new ArrayList<>();
        tls = new ArrayList<>();
        tnts = new ArrayList<>();
        saws = new ArrayList<>();
        koalas = new ArrayList<>();
        tsCounts = new ArrayList<>();
        psHits = new ArrayList<>();
        try {
//            //Using class loaders to read in resources
            koalaImage = read(Koalabr8.class.getClassLoader().getResource("koala.png"));
            wall = read(Koalabr8.class.getClassLoader().getResource("wall.png"));
            tnt = read(Koalabr8.class.getClassLoader().getResource("tnt.png"));
            saw = read(Koalabr8.class.getClassLoader().getResource("saw.png"));
            redExit = read(Koalabr8.class.getClassLoader().getResource("redexit.png"));
            blueExit = read(Koalabr8.class.getClassLoader().getResource("blueexit.png"));
            normalLock = read(Koalabr8.class.getClassLoader().getResource("redlock.png"));
            timedLock = read(Koalabr8.class.getClassLoader().getResource("bluelock.png"));
            pressureLock = read(Koalabr8.class.getClassLoader().getResource("greenlock.png"));
            normalSwitch = read(Koalabr8.class.getClassLoader().getResource("normalswitch.png"));
            timedSwitch = read(Koalabr8.class.getClassLoader().getResource("timedswitch.png"));
            pressureSwitch = read(Koalabr8.class.getClassLoader().getResource("pressureswitch.png"));
            boulder = read(Koalabr8.class.getClassLoader().getResource("boulder.png"));

            InputStreamReader isr = null;
            if (level == 1) {
                isr = new InputStreamReader(Koalabr8.class.getClassLoader().getResourceAsStream("maps/lvl1"));
            } else if (level == 2) {
                isr = new InputStreamReader(Koalabr8.class.getClassLoader().getResourceAsStream("maps/lvl2"));
            } else if (level == 3) {
                isr = new InputStreamReader(Koalabr8.class.getClassLoader().getResourceAsStream("maps/lvl3"));
            }
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if(row == null) {
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for (int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++) {
                    switch (mapInfo[curCol]) {
                        case "a":
                            //nothing
                            break;
                        case "b":
                            //normal switch
                            NormalSwitch ns = new NormalSwitch(curCol*50, curRow*50, normalSwitch);
                            this.nss.add(ns);
//                            this.switches.add(ns);
                            break;
                        case "c":
                            //timed switch
                            TimedSwitch ts = new TimedSwitch(curCol*50, curRow*50, timedSwitch);
                            this.tss.add(ts);
//                            this.switches.add(ts);
                            long c = 0;
                            this.tsCounts.add(c);
                            break;
                        case "d":
                            //pressure switch
                            PressureSwitch ps = new PressureSwitch(curCol*50, curRow*50, pressureSwitch);
                            this.pss.add(ps);
//                            this.switches.add(ps);
                            boolean h = false;
                            this.psHits.add(h);
                            break;
                        case "e":
                            //normal lock
                            NormalLock nl = new NormalLock(curCol*50, curRow*50, normalLock);
                            this.nls.add(nl);
                            break;
                        case "f":
                            //timed lock
                            TimedLock tl = new TimedLock(curCol*50, curRow*50, timedLock);
                            this.tls.add(tl);
                            break;
                        case "g":
                            //pressure lock
                            PressureLock pl = new PressureLock(curCol*50, curRow*50, pressureLock);
                            this.pls.add(pl);
                            this.ogpls.add(pl);
                            break;
                        case "h":
                            //red exit
                            RedExit re = new RedExit(curCol*50, curRow*50, redExit);
                            this.exits.add(re);
                            break;
                        case "i":
                            //blue exit
                            BlueExit be = new BlueExit(curCol*50, curRow*50, blueExit);
                            this.exits.add(be);
                            break;
                        case "j":
                            //boulder
                            Boulder b = new Boulder(curCol*50, curRow*50, boulder);
                            this.boulders.add(b);
                            break;
                        case "k":
                            //tnt
                            TNT t = new TNT(curCol*50, curRow*50, tnt);
                            this.tnts.add(t);
                            break;
                        case "l":
                            //outside walls - do not need collisions
                            Wall ow = new Wall(curCol*50, curRow*50, wall);
                            this.walls.add(ow);
                            break;
                        case "m":
                            //game walls
                            Wall w = new Wall(curCol*50, curRow*50, wall);
                            this.walls.add(w);
                            break;
                        case "n":
                            FakeSwitch fs = new FakeSwitch(curCol*50, curRow*50, normalSwitch);
                            this.fss.add(fs);
                            break;
                    }
                }
            }
            if (level == 1) {
                Saw s = new Saw(200, 250, 90, 200, 550, saw);
                this.saws.add(s);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        koalaOne = new koalabr8.Koala(200, 400, koalaImage);
        koalaTwo = new koalabr8.Koala(300, 400, koalaImage);
        koalaThree = new koalabr8.Koala(400,400, koalaImage);

        koalas.add(koalaOne);
        koalas.add(koalaTwo);
        koalas.add(koalaThree);

        KoalaControl koalaOneControl = new KoalaControl(koalaOne, KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT);
        KoalaControl koalaTwoControl = new KoalaControl(koalaTwo, KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT);
        KoalaControl koalaThreeControl = new KoalaControl(koalaThree, KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT);
        this.jFrame.setLayout(new BorderLayout());
        this.jFrame.add(this);
        this.jFrame.addKeyListener(koalaOneControl);
        this.jFrame.addKeyListener(koalaTwoControl);
        this.jFrame.addKeyListener(koalaThreeControl);
        this.jFrame.setSize(Koalabr8.SCREEN_WIDTH, Koalabr8.SCREEN_HEIGHT + 30);
        this.jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setVisible(true);
    }

    public static void koalaBump(int oneX, int oneY, int twoX, int twoY, Koala koalaOne, Koala koalaTwo) {
        //if K1 & K2 intersect:
            //if K1.X < K2.X:
                //move K2++
                //keep K1 where it is
            //if K1.X > K2.X:
                //move K2--
                //keep K1 where it is
            //repeat w/ K1.Y and K2.Y
        if (koalaOne.getHitBox().intersects(koalaTwo.getHitBox())) {
            if (oneX < twoX && oneX > twoX - 50) {
                if (twoX >= Koalabr8.WORLD_WIDTH - 100) {
                    koalaTwo.setX(twoX);
                    koalaOne.setX(oneX - move);
                } else {
                    koalaTwo.setX(twoX + move);
                    koalaOne.setX(oneX);
                }
            } else if (oneX > twoX && oneX < twoX + 50) {
                if (twoX <= 50) {
                    koalaTwo.setX(twoX);
                    koalaOne.setX(oneX + move);
                } else {
                    koalaTwo.setX(twoX - move);
                    koalaOne.setX(oneX);
                }
            } else if (oneY < twoY && oneY > twoY - 50) {
                if (twoY >= Koalabr8.WORLD_HEIGHT - 100) {
                    koalaTwo.setY(twoY);
                    koalaOne.setY(oneY - move);
                } else {
                    koalaTwo.setY(twoY + move);
                    koalaOne.setY(oneY);
                }
            } else if (oneY > twoY && oneY < twoY + 50) {
                if (twoY <= 50) {
                    koalaTwo.setY(twoY);
                    koalaOne.setY(oneY + move);
                } else {
                    koalaTwo.setY(twoY - move);
                    koalaOne.setY(oneY);
                }
            }
        }
    }

    public static void wallBump(int koalaX, int koalaY, int wallX, int wallY, Koala koala, Wall wall) {
        //if K & W intersect:
            //if K.X < W.X:
                //move K--
                //keep W where it is
            //if K.X > W.X:
                //move K++
                //keep W where it is
            //repeat w/ K.Y and W.Y
        if (koala.getHitBox().intersects(wall.getHitBox())) {
            if (koalaY < wallY && koalaY > wallY - 50) {
                koala.setY(koalaY - move);
            } else if (koalaY > wallY && koalaY < wallY + 50) {
                koala.setY(koalaY + move);
            } else if (koalaX < wallX && koalaX > wallX - 50) {
                koala.setX(koalaX - move);
            } else if (koalaX > wallX && koalaX < wallX + 50) {
                koala.setX(koalaX + move);
            }
        }
    }

    public static void lockBump(int koalaX, int koalaY, int lockX, int lockY, Koala koala, Lock lock) {
        if (koala.getHitBox().intersects(lock.getHitBox())) {
            if (koalaY < lockY && koalaY > lockY - 50) {
                koala.setY(koalaY - move);
            } else if (koalaY > lockY && koalaY < lockY + 50) {
                koala.setY(koalaY + move);
            } else if (koalaX < lockX && koalaX > lockX - 50) {
                koala.setX(koalaX - move);
            } else if (koalaX > lockX && koalaX < lockX + 50) {
                koala.setX(koalaX + move);
            }
        }
    }

    public static void boulderBump(int koalaX, int koalaY, int boulderX, int boulderY, Koala koala, Boulder boulder) {
        //if K & B intersect:
            //if K.X < B.X:
                //move B++
                //keep K where it is
            //if K.X > B.X:
                //move B--
                //keep K where it is
            //repeat w/ K.Y and B.Y
        if (koala.getHitBox().intersects(boulder.getHitBox())) {    //if K is touching B
            if (koalaY < boulderY && koalaY > boulderY - 50) {      //if K is on top
                if (boulderY >= Koalabr8.WORLD_HEIGHT - 100) {      //if B against lower wall
                    koala.setY(koalaY - move);                          //move koala up
                } else {                                            //else
                    boulder.setY(boulderY + move);                      //move boulder down
                }
            } else if (koalaY > boulderY && koalaY < boulderY + 50) {   //if K below
                if (boulderY <= 50) {                                   //if B against top
                    koala.setY(koalaY + move);
                } else {
                    boulder.setY(boulderY - move);
                }
            } else if (koalaX < boulderX && koalaX > boulderX - 50) {
                if (boulderX >= Koalabr8.WORLD_WIDTH - 100) {
                    koala.setX(koalaX - move);
                } else {
                    boulder.setX(boulderX + move);
                }
            } else if (koalaX > boulderX && koalaX < boulderX + 50) {
                if (boulderX <= 50) {
                    koala.setX(koalaX + move);
                } else {
                    boulder.setX(boulderX - move);
                }
            }
        }
        boulder.updateHitBox(boulder.getX(), boulder.getY());
    }

    public static void boulderWall(Boulder boulder, Wall wall) {
        if (boulder.getHitBox().intersects(wall.getHitBox())) {
//            System.out.println("hit");
            if (boulder.getY() < wall.getY() && boulder.getY() > wall.getY() - 50) {
                boulder.setY(boulder.getY() - move);
            } else if (boulder.getY() > wall.getY() && boulder.getY() < wall.getY() + 50) {
                boulder.setY(boulder.getY() + move);
            } else if (boulder.getX() < wall.getX() && boulder.getX() > wall.getX() - 50) {
                boulder.setX(boulder.getX() - move);
            } else if (boulder.getX() > wall.getX() && boulder.getX() < wall.getX() + 50) {
                boulder.setX(boulder.getX() + move);
            }
        }
        boulder.updateHitBox(boulder.getX(), boulder.getY());
    }

    public static void boulderBoulder(Boulder b1, Boulder b2) {
        if (b1.getHitBox().intersects(b2.getHitBox())) {
            if (b1.getX() < b2.getX() && b1.getX() > b2.getX() - 50) {
                if (b2.getX() >= Koalabr8.WORLD_WIDTH - 100) {
                    b2.setX(b2.getX());
                    b1.setX(b1.getX() - move);
                } else {
                    b2.setX(b2.getX() + move);
                    b1.setX(b1.getX());
                }
            } else if (b1.getX() > b2.getX() && b1.getX() < b2.getX() + 50) {
                if (b2.getX() <= 50) {
                    b2.setX(b2.getX());
                    b1.setX(b1.getX() + move);
                } else {
                    b2.setX(b2.getX() - move);
                    b1.setX(b1.getX());
                }
            } else if (b1.getY() < b2.getY() && b1.getY() > b2.getY() - 50) {
                if (b2.getY() >= Koalabr8.WORLD_HEIGHT - 100) {
                    b2.setY(b2.getY());
                    b1.setY(b1.getY() - move);
                } else {
                    b2.setY(b2.getY() + move);
                    b1.setY(b1.getY());
                }
            } else if (b1.getY() > b2.getY() && b1.getY() < b2.getY() + 50) {
                if (b2.getY() <= 50) {
                    b2.setY(b2.getY());
                    b1.setY(b1.getY() - move);
                } else {
                    b2.setY(b2.getY() - move);
                    b1.setY(b1.getY());
                }
            }
        }
    }

    public static void tntBump(Koala koala, TNT tnt) {
        if (koala.getHitBox().intersects(tnt.getHitBox())) {
            count++;
            if (count == 1) {
                System.out.println("You hit tnt on level " + level + " and died. :(");
            }
            level = 0;
        }
    }

    public static void sawBump(Koala koala, Saw saw) {
        if (koala.getHitBox().intersects(saw.getHitBox())) {
            count++;
            if (count == 1) {
                System.out.println("You hit a saw on level " + level + " and died. :(");
            }
            level = 0;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        buffer = world.createGraphics();
        if (level > 0) {
            buffer.setColor(Color.BLACK);
            buffer.fillRect(0, 0, Koalabr8.WORLD_WIDTH, Koalabr8.WORLD_HEIGHT);
//            buffer.fillRect(0, 0, Koalabr8.SCREEN_WIDTH, Koalabr8.SCREEN_HEIGHT);
            this.walls.forEach(w -> w.drawImage(buffer));
            this.boulders.forEach(b -> b.drawImage(buffer));
//            this.switches.forEach(s -> s.drawImage(buffer));
            this.nss.forEach(s -> s.drawImage(buffer));
            this.fss.forEach(f -> f.drawImage(buffer));
            this.tss.forEach(s -> s.drawImage(buffer));
            this.pss.forEach(s -> s.drawImage(buffer));
            this.exits.forEach(e -> e.drawImage(buffer));
//            this.locks.forEach(l -> l.drawImage(buffer));
            this.nls.forEach(l -> l.drawImage(buffer));
            this.tls.forEach(l -> l.drawImage(buffer));
            this.pls.forEach(l -> l.drawImage(buffer));
            this.tnts.forEach(t -> t.drawImage(buffer));
            this.saws.forEach(s -> s.drawImage(buffer));
//            this.koalaOne.drawImage(buffer);
//            this.koalaTwo.drawImage(buffer);
//            this.koalaThree.drawImage(buffer);
            this.koalas.forEach(k -> k.drawImage(buffer));
        } else if (level < 0) {
            buffer.setColor(Color.GREEN);
            buffer.fillRect(0, 0, Koalabr8.WORLD_WIDTH, Koalabr8.WORLD_HEIGHT);
            BufferedImage win = null;
            try {
                win = read(Koalabr8.class.getClassLoader().getResource("win.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2.drawImage(win, 0, 0, null);
        } else {
//            g2.drawString("You died!", 20, 500);
            buffer.setColor(Color.RED);
//            g2.drawRect(200,200,150,50);
//            Rectangle b = new Rectangle(200, 200, 100, 200);
//            g2.setColor(Color.BLACK);
//            g2.fill(b);
            buffer.fillRect(0, 0, Koalabr8.WORLD_WIDTH, Koalabr8.WORLD_HEIGHT);
            BufferedImage boom = null;
            try {
                boom = read(Koalabr8.class.getClassLoader().getResource("boom.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2.drawImage(boom, 0, 0, null);
//            this.boom.drawImage
//            buffer = world.terminate();
        }
        g2.drawImage(world, 0, 0, null);
    }
}