import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gameboard implements ActionListener {

    private JFrame frame;
    private JPanel container, grid, menu;
    private JButton start, end, pause, clear;
    private Timer timer;
    private Cell[][] gameboard;

    private final int WIDTH = 8;
    private final int HEIGHT = 8;
    private final int DELAY = 250;
    private static final int[][] NEIGHBOURS = { { -1, -1 }, { -1, 0 }, { -1, +1 },
            { 0, -1 }, 			   { 0, +1 },
            { +1, -1 }, { +1, 0 }, { +1, +1 } };

    public Gameboard() {
        frame = new JFrame("Game Of Life");

        container = new JPanel();
        grid = new JPanel();
        menu = new JPanel();

        start = new JButton("Start");
        end = new JButton("End");
        pause = new JButton("Pause");
        clear = new JButton("Clear");

        gameboard = new Cell[WIDTH][HEIGHT];
    }

    public void setUpBoard() {

        frame.setSize(700, 500);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        grid.setLayout(new GridLayout(WIDTH, HEIGHT));

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                gameboard[i][j] = new Cell();
                grid.add(gameboard[i][j]);
            }
        }

        start.addActionListener(this);
        end.addActionListener(this);
        pause.addActionListener(this);
        clear.addActionListener(this);

        pause.setEnabled(false);
        clear.setEnabled(false);

        menu.add(start);
        menu.add(end);
        menu.add(pause);
        menu.add(clear);

        menu.setMaximumSize(new Dimension(400, 0));

        container.add(grid);
        container.add(menu);
        frame.add(container);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JButton) {
            if (evt.getActionCommand().equals("Start")) {
                timer = new Timer();
                start.setEnabled(false);
                end.setEnabled(true);
                pause.setEnabled(true);
                clear.setEnabled(true);
                timer.schedule(new TimerTask() {
                    public void run() {
                        iterateNextGeneration();
                    }
                }, 0, DELAY);
            } else if (evt.getActionCommand().equals("End")) {
                endGame();
            } else if (evt.getActionCommand().equals("Pause")) {
                start.setEnabled(true);
                end.setEnabled(true);
                pause.setEnabled(false);
                clear.setEnabled(true);
                timer.cancel();
            } else if (evt.getActionCommand().equals("Clear")) {
                start.setEnabled(true);
                end.setEnabled(true);
                pause.setEnabled(false);
                clear.setEnabled(false);
                clearGame();
                timer.cancel();
            }
        }
    }

    public void getLiveNeighbors() {
        int numberOfLiveNeighbors = 0;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                for (int[] offset : NEIGHBOURS) {
                    try {
                        if (gameboard[i + offset[1]][j + offset[0]].getStatus()) {
                            numberOfLiveNeighbors++;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                if ((numberOfLiveNeighbors == 2 || numberOfLiveNeighbors == 3) && gameboard[i][j].getStatus()) {
                    gameboard[i][j].setNextStatus(true);
                } else if (numberOfLiveNeighbors == 3 && !gameboard[i][j].getStatus()) {
                    gameboard[i][j].setNextStatus(true);
                } else if (numberOfLiveNeighbors < 2) {
                    gameboard[i][j].setNextStatus(false);
                } else if (numberOfLiveNeighbors > 3) {
                    gameboard[i][j].setNextStatus(false);
                }
                numberOfLiveNeighbors = 0;
            }
        }
    }

    public void iterateNextGeneration() {
        getLiveNeighbors();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                gameboard[i][j].setStatus(gameboard[i][j].getNextStatus());
            }
        }
    }

    public void endGame() {
        System.exit(0);
    }

    public void clearGame() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                gameboard[i][j].setStatus(false);
            }
        }
    }
}
