import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static com.sun.javafx.fxml.expression.Expression.add;

/**
 * Created by Ledowsky on 11.09.2017.
 * https://www.youtube.com/watch?v=sCit9jzDSrE
 */
public class GameTetris {
    final String TITLE_OF_GAME = "Tetris"; // title of the game
    final int BLOCK_SIZE = 35; // block size
    final int ARC_RADIUS = 6; // radius of the corners of block size
    final int FIELD_WIDTH = 10; // width of the game field
    final int FIELD_HEIGHT = 18; // height of the game field
    final int START_LOCATION = 180; //coordinates of left upper corner of the game field
    final int FIELD_DX = 7;
    final int FIELD_DY = 26;

    final int LEFT = 37; // scan code of the key "LEFT"
    final int UP = 38; // scan code of the key "UP"
    final int RIGHT = 39; // scan code of the key "RIGHT"
    final int DOWN = 40; // scan code of the key "DOWN"
    final int SHOW_DELAY = 350; // default animation delay
    final int[][][] SHAPES = { // default block shapes, painted with different colors (according Wikipedia)
            {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}, {4, 0x00f0f0}}, // I
            {{0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}, {4, 0xf0f000}}, // O
            {{1,0,0,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x0000f0}}, // J
            {{0,0,1,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf0a000}}, // L
            {{0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x00f000}}, // S
            {{1,1,1,0}, {0,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xa000f0}}, // T
            {{1,1,0,0}, {0,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf00000}}  // Z
    };
    final int[] SCORES = {100, 300, 700, 1500}; // default score numbers for 1 line, 2 lines, 3 lines and 4 lines destruction.
    int gameScore = 0; // variable to hold player's current score;
    int[][] mine = new int[FIELD_HEIGHT+1][FIELD_WIDTH]; // size of the field

    JFrame frame;
    Canvas canvasPanel = new Canvas(); // main panel to draw in
    Random random = new Random(); // generates random numbers
    Figure figure = new Figure();
    boolean isOver = false;
    final int[][] GAME_OVER_MSG = {
            {0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,1,0},
            {1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1},
            {1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
            {1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,0,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,1,1,0,0},
            {1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
            {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,0,0},
            {1,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
            {0,1,1,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0}
    };



    public static void main(String[] args) {
        new GameTetris().go();


    }

    private void go() {
        frame = new JFrame(TITLE_OF_GAME); // creating JPanel window - main game window.
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // setting default exit method.
        frame.setSize(FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY); // setting size of the window
        frame.setLocation(START_LOCATION, START_LOCATION); // setting start location of window.
        frame.setResizable(false); // size of the window can not be changed.
        canvasPanel.setBackground(Color.BLACK); // setting the default color of game background.

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!isOver) {
                    if (e.getKeyCode() == DOWN) figure.drop();
                    if (e.getKeyCode() == UP) figure.rotate();
                    if (e.getKeyCode() == LEFT || e.getKeyCode() == RIGHT) figure.move(e.getKeyCode());
                }
                canvasPanel.repaint();
            }
        });
        add(BorderLayout.CENTER, canvasPanel);
        frame.setVisible(true); // make game window visible.


    }

}
